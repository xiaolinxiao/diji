package com.zrdz.diji.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Environment;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.view.Window;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.model.HttpParams;
import com.zrdz.diji.R;
import com.zrdz.diji.bean.DistricOfCityInfo;
import com.zrdz.diji.bean.InspectorInfo;
import com.zrdz.diji.bean.Photo;
import com.zrdz.diji.bean.PileNoQueryInfo;
import com.zrdz.diji.listener.DialogListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by ASUS on 2017/4/17.
 * 通用方法工具类
 */

public class CommonUtils {


    /**
     * 通过sp保存信息
     *
     * @param key    键
     * @param values 值
     */
    public static void saveBySp(Context context, String key, Object values) {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.spNmae), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (values instanceof String) {
            edit.putString(key, (String) values);
        } else if (values instanceof Boolean) {
            edit.putBoolean(key, (Boolean) values);
        } else if (values instanceof Integer) {
            edit.putInt(key, (Integer) values);
        } else if (values instanceof Float) {
            edit.putFloat(key, (Float) values);
        }
        SharedPreferencesCompat.EditorCompat.getInstance().apply(edit);
    }

    /**
     * 获取sp保存的 数据
     *
     * @param context
     * @param key
     * @return
     */
    public static Object getSp(Context context, String key, Object defValue) {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.spNmae), Context.MODE_PRIVATE);
        Object result = null;
        if (!sp.contains(key)) {
            return defValue;
        }
        if (defValue instanceof String) {
            result = sp.getString(key, (String) defValue);
        } else if (defValue instanceof Boolean) {
            result = sp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Integer) {
            result = sp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Float) {
            result = sp.getFloat(key, (Float) defValue);
        }
        return result;
    }

    /**
     * get请求  传入bean，json解析
     *
     * @param context  上下文
     * @param urlModel 模块
     * @param params   参数
     * @param callback 返回监听
     * @param <T>
     */
    public static <T> void httpGet(Context context, String url, String urlModel, HttpParams params, AbsCallback<T> callback) {
        OkHttpUtils.get(url + urlModel).tag(context).params(params).execute(callback);
    }

    /**
     * 建立dialog提示框,带底部点击回调
     *
     * @param context
     * @param title
     * @param message
     * @param listener
     * @return
     */
    public static AlertDialog createDialog(Context context, String title, String message, final DialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setTitle(title)
                .setPositiveButton(context.getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.isSure(dialogInterface);
                    }
                })
                .setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.isCannel(dialog);
                    }

                });
        AlertDialog dialog = builder.create();
        return dialog;
    }

    /**
     * 建立dialog提示框,不带底部点击回调
     *
     * @param context
     * @param title
     * @param message
     * @return
     */
    public static AlertDialog createDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setTitle(title);
        AlertDialog dialog = builder.create();
        return dialog;
    }

    /**
     * 读取raw文件夹下的json数据库
     *
     * @param context
     * @return
     */
    public static String readLocalJson(Context context) {
        String resultString = "";
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.json);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            resultString = new String(buffer, "GB2312");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

    /**
     * 摄像头权限是否打开
     *
     * @return
     */
    public static boolean isCameraUseable() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            // setParameters 是针对魅族MX5。MX5通过Camera.open()拿到的Camera对象不为null
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            canUse = false;
        }
        if (mCamera != null) {
            mCamera.release();
        }
        return canUse;
    }

    /**
     * 对城市区解析
     *
     * @param json
     * @param mContext
     * @return
     */
    public static List<List<DistricOfCityInfo>> parsingJson(String json, Context mContext) {
        LogUtils.e("========parsingJson=====");
        List<List<DistricOfCityInfo>> result = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = array.length() - 1; i >= 0; i--) {
                JSONObject jsonObject = array.getJSONObject(i);
                List<DistricOfCityInfo> districOfCityInfos = paringStation(jsonObject.toString());
                result.add(districOfCityInfos);
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 监督站解析
     *
     * @throws JSONException
     */
    public static List<DistricOfCityInfo> paringStation(String json) throws JSONException {
        List<DistricOfCityInfo> list = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray alljsonArr1 = jsonObject.getJSONArray("toollist");
        LogUtils.e("==length===" + alljsonArr1.length());
        for (int j = 0; j < alljsonArr1.length(); j++) {
            JSONObject jsonObject1 = alljsonArr1.getJSONObject(j);
            String inspectInstitution = jsonObject1.getString("inspectInstitution");
            JSONObject jsonObject2 = new JSONObject(inspectInstitution);
            String inspectInstitutionName = jsonObject2.getString("inspectInstitutionName");
            String jcjgId = jsonObject2.getString("jcjgId");
            DistricOfCityInfo info = new DistricOfCityInfo(inspectInstitutionName, jcjgId);
            list.add(info);
        }
        return list;
    }

    /**
     * json解析,监督站负责人员信息
     *
     * @param context
     * @param pstr
     * @return
     */
    public static List<InspectorInfo> JsonPeopleList(Context context, String pstr) {
        List<InspectorInfo> list = new ArrayList();
        JSONObject personfirst = null;
        try {
            JSONObject allData = new JSONObject(pstr);
            String allStr = allData.getString("toollist");
            if (allStr.equals(context.getResources().getString(R.string.PromptNoEquipmentID))
                    || allStr.equals(context.getResources().getString(R.string.PromptNoEquipment))
                    || allStr.equals(context.getResources().getString(R.string.PromptNoParametersShort))
                    || allStr.equals(context.getResources().getString(R.string.PromptCanNotFindInfo))
                    || allStr.equals(context.getResources().getString(R.string.PromptNoPermission))) {
                return null;
            }
            JSONArray alljsonArr = allData.getJSONArray("toollist");
            for (int i = 0; i < alljsonArr.length(); i++) {
                try {
                    personfirst = alljsonArr.getJSONObject(i);
                    String userName = personfirst.getString("userName");
                    String jcjgName = personfirst.getString("jcjgName");
                    String userPhone = "";
                    if (personfirst.has("userPhone")) {
                        userPhone = personfirst.getString("userPhone");
                    }
                    InspectorInfo peopleModel = new InspectorInfo(jcjgName, userName, userPhone);
                    list.add(peopleModel);
                } catch (JSONException ex) {
                    ex.printStackTrace();
                    return null;
                }
            }
            return list;
        } catch (JSONException e) {
            return null;
        }
    }
    /**
     * 根据传入的key，进行对应的解析操作
     *
     * @param context
     * @param url
     * @param urlModel
     * @param params
     * @param titleList
     */
//    public void httpGet(Context context, String url, String urlModel, HttpParams params, final List<String> titleList) {
//        OkHttpUtils.get(url + urlModel).tag(context).params(params).execute(new StringCallback() {
//
//            @Override
//            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
//                Log.e("==httpget==response", s);
//                for (String title : titleList) {
//                    JsonObject jsonObject = new JsonObject();
//                }
//
//
//            }
//
//            @Override
//            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
//                super.onError(isFromCache, call, response, e);
//            }
//        });
//
//    }

    /**
     * 获取手机id
     *
     * @return
     */
    public static String getPhoneID(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String sb = tm.getDeviceId();
        return sb;
    }

    /**
     * 获取版本号
     *
     * @param mContext
     * @return
     */
    public static String getVersionName(Context mContext) {

        String versionName = null;
        try {
            versionName = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            versionName = "0";
        }
        return versionName;
    }

    /**
     * 初始化dialog
     */
    public static ProgressDialog initProgressDialog(Context context) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(context.getString(R.string.connectnet));
        return progressDialog;
    }

    /**
     * 初始化dialog,带message
     */
    public static ProgressDialog initProgressDialog(Context context, String message) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(message);
        return progressDialog;
    }

    /**
     * 解析基桩的json数据字符串
     *
     * @param pileStr
     * @return
     */
    public static List<PileNoQueryInfo> paringPlieJsonStr(String pileStr) {
        List<PileNoQueryInfo> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(pileStr);
            JSONArray toollist = jsonObject.getJSONArray("toollist");
            if (toollist.length() <= 0) {
                return null;
            } else {
                for (int i = 0; i < toollist.length(); i++) {
                    JSONObject object = (JSONObject) toollist.get(i);
                    String pile_pid = object.getString("pile_pid");
                    String pile_id = object.getString("pile_Id");
                    String projectName = object.getString("projectName");
                    String inSpectInstituTionName = object.getString("inSpectInstituTionName");
                    String districtIdName = object.getString("districtIdName");
                    String startDate = object.getString("startDate");
                    String startTime = object.getString("startTime");
                    PileNoQueryInfo pileInfo = new PileNoQueryInfo(pile_id, pile_pid, projectName, inSpectInstituTionName, districtIdName, startDate, startTime);
                    list.add(pileInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    /**
     * 解析图片url
     *
     * @param photopath 需要解析数据 json
     */
    public static List<Photo> paringPhotoUrl(String photopath) {
        List<Photo> list = new ArrayList<>();
        if (null == photopath) {
            return null;
        } else {
            try {
                JSONObject jsonObject = new JSONObject(photopath);
                JSONArray toollist = jsonObject.getJSONArray("toollist");
                for (int i = 0; i < toollist.length(); i++) {
                    JSONObject object = (JSONObject) toollist.get(i);
                    String filePath = object.getString("filePath");
                    String photoTime = object.getString("photoTime");
                    String againUp = object.getString("againUp");
                    String place = object.getString("place");
                    Photo photo = new Photo(filePath, againUp, photoTime, place);
                    list.add(photo);
                }
                return list;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * 检验用户
     *
     * @param mobile 电话号码
     * @param pid
     * @return
     */
    public static String checkUser(String mobile, String pid) {
        LinkServiceUtils linkServiceUtils = LinkServiceUtils.getInstance();
        String response = linkServiceUtils.checkAccount(mobile, pid);
        String result = null;
        try {
            if (response != null) {
                JSONObject obj = new JSONObject(response);
                result = obj.getString("toollist");
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 裁剪图片
     *
     * @param srcPath
     * @return
     */
    public static Bitmap getImage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是1280*720分辨率，所以高和宽我们设置为
        float hh = 1280f;// 这里设置高度为800f
        float ww = 720f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 压缩图片
     *
     * @param image
     * @return
     */
    private static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 200) { // 循环判断如果压缩后图片是否大于200kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 更新上传图片
     *
     * @param result
     */
    public static int uploadPhoto(String result, Callback callback) {
        String photoPid = "";
        String photoName = "";
        JSONObject jsonObject1 = null;
        try {
            jsonObject1 = new JSONObject(result);
            photoPid = jsonObject1.getString("photoPid");
            photoName = jsonObject1.getString("fileName");
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/zrphotos", "save.jpg");
        if (!file.exists() || file == null) {
            return -1;
        } else {
            LogUtils.e("==file exists==");
            MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
            OkHttpClient client = new OkHttpClient();
            RequestBody fileBody = RequestBody.create(MEDIA_TYPE_PNG, file);
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("fileType", "save.jpg")
                    .addFormDataPart("photoPid", photoPid)
                    .addFormDataPart("fileName", photoName)
                    .addFormDataPart("image", file.getName(), fileBody)
                    .build();
            Request request = new Request.Builder().url(Const.MAIN_URL + Const.UPLOADPHOTO_MODULE)
                    .post(requestBody).build();
            try {
                client.newCall(request).enqueue(callback);
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
        return 0;
    }
}
