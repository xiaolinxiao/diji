package com.zrdz.diji.home.testmanage.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zrdz.diji.MyApplication;
import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseBackTitleActivity;
import com.zrdz.diji.bean.Photo;
import com.zrdz.diji.utils.CommonUtils;
import com.zrdz.diji.utils.Const;
import com.zrdz.diji.utils.LinkServiceUtils;
import com.zrdz.diji.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 图片补传界面
 */
public class FillPicActivity extends BaseBackTitleActivity implements View.OnClickListener {


    private String photopath;
    private LinearLayout layoutChoice;
    private LinearLayout layout_image;
    private ImageView imageView;
    private int flag = 0;//记录补传的模块
    private ProgressDialog progressDialog;
    private TextView text_flag;
    private String pid;
    private String againUp;
    private Button btn_jingzai;//静载仪连接情况
    private Button btn_peizhongzheng;//正面
    private Button btn_peizhongce;//侧面
    private Button btn_peizhongshang;//上方
    private Button btn_report;//记录表

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -1:
                    String message = (String) msg.obj;
                    if (null == message) {
                        showToast(getString(R.string.fialtopic));
                    } else {
                        showToast(getString(R.string.fialtopic) + message);
                    }
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    break;
                case 0:
                    showToast(getString(R.string.datapostfail));
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    break;
                case 1:
                    String path = (String) msg.obj;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 6;
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    Bitmap mBitmap = BitmapFactory.decodeFile(path, options);
                    imageView.setImageBitmap(mBitmap);
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    break;
                case 2:
                    String result = (String) msg.obj;
                    int reponse = CommonUtils.uploadPhoto(result, httpCallback);
                    if (-1 == reponse) {
                        showToast(getString(R.string.datapostfail));
                        layoutChoice.setVisibility(View.VISIBLE);
                        layout_image.setVisibility(View.GONE);
                    }
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    break;
                case 3:
                    String str = (String) msg.obj;
                    initBtnShow(str);
                    layoutChoice.setVisibility(View.VISIBLE);
                    layout_image.setVisibility(View.GONE);
                    break;
            }
        }
    };

    /**
     * 上传图片回调
     */
    private okhttp3.Callback httpCallback = new okhttp3.Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            LogUtils.e(" upload jsonString error=" + e.toString());
            showToast(getString(R.string.uploadphotofail) + e.toString());
            layoutChoice.setVisibility(View.VISIBLE);
            layout_image.setVisibility(View.GONE);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String jsonString = response.body().string();
            LogUtils.e(" upload jsonString =" + jsonString);
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                String result = jsonObject.getString("result");
                if ("true".equals(result)) {
                    showToast(getString(R.string.uploadsucess));
                } else {
                    showToast(getString(R.string.uploadphotofail));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                showToast(getString(R.string.uploadphotofail) + e.toString());
            }
            //重新判断哪些图片已经上传过
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LinkServiceUtils linkServiceUtils = new LinkServiceUtils();
                    String result = linkServiceUtils.linkqueryPhotoPath(FillPicActivity.this, pid);
                    if (null == result || "".equals(result)) {
                        handler.sendEmptyMessage(0);
                    } else {
                        Message message = new Message();
                        message.what = 3;
                        message.obj = result;
                        handler.sendMessage(message);
                    }
                }
            }).start();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photopath = getIntent().getStringExtra("photopath");
        pid = getIntent().getStringExtra("pid");
        LogUtils.e("==user_loginname==" + MyApplication.user_loginname);
        initArgument(R.layout.activity_full_pic, getString(R.string.fillpicture));
        initView();
    }

    private void initView() {
        layoutChoice = (LinearLayout) findViewById(R.id.layout_choice);
        //已动态图的形式加载控件
        AnimationSet set = new AnimationSet(true);
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(200);
        set.addAnimation(animation);
        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(500);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
        layoutChoice.setLayoutAnimation(controller);
        btn_jingzai = (Button) findViewById(R.id.btn_jingzai_photo);
        btn_peizhongzheng = (Button) findViewById(R.id.btn_peizhongzheng_photo);
        btn_peizhongce = (Button) findViewById(R.id.btn_peizhongce_photo);
        btn_peizhongshang = (Button) findViewById(R.id.btn_peizhongshang_photo);
        btn_report = (Button) findViewById(R.id.btn_report_photo);
        layout_image = (LinearLayout) findViewById(R.id.layout_showimage);
        imageView = (ImageView) findViewById(R.id.fillpic_image);
        Button btn_sure = (Button) findViewById(R.id.fillpic_btn_sure);
        Button btn_retakephoto = (Button) findViewById(R.id.fillpic_btn_retakephoto);
        text_flag = (TextView) findViewById(R.id.fillpic_text_flag);
        btn_jingzai.setOnClickListener(this);
        btn_peizhongzheng.setOnClickListener(this);
        btn_peizhongce.setOnClickListener(this);
        btn_peizhongshang.setOnClickListener(this);
        btn_report.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
        btn_retakephoto.setOnClickListener(this);
        initBtnShow(photopath);
    }

    /**
     * 设置button上的显示内容
     *
     * @param photopath
     */
    private void initBtnShow(String photopath) {
        List<Photo> result = CommonUtils.paringPhotoUrl(photopath);
        if (null != result && result.size() > 0) {
            for (int i = 0; i < result.size(); i++) {
                String place = result.get(i).place;
                if ("1".equals(place)) {
                    btn_jingzai.setText(getString(R.string.takeconnectstatus) + "(已上传)");
                } else if ("2".equals(place)) {
                    btn_peizhongzheng.setText(getString(R.string.takepositive) + "(已上传)");
                } else if ("3".equals(place)) {
                    btn_peizhongce.setText(getString(R.string.takeside) + "(已上传)");
                } else if ("4".equals(place)) {
                    btn_peizhongshang.setText(getString(R.string.takeabove) + "(已上传)");
                } else {
                    btn_report.setText(getString(R.string.takerecordword) + "(已上传)");
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jingzai_photo:
                flag = 0;
                gotoTakePhoto();
                break;
            case R.id.btn_peizhongzheng_photo:
                flag = 1;
                gotoTakePhoto();
                break;
            case R.id.btn_peizhongce_photo:
                flag = 2;
                gotoTakePhoto();
                break;
            case R.id.btn_peizhongshang_photo:
                flag = 3;
                gotoTakePhoto();
                break;
            case R.id.btn_report_photo:
                flag = 4;
                gotoTakePhoto();
                break;
            case R.id.fillpic_btn_sure://上传图片
                progressDialog = CommonUtils.initProgressDialog(this);
                progressDialog.show();
                compressPic();
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        String versionName = CommonUtils.getVersionName(FillPicActivity.this);
                        LinkServiceUtils linkServiceUtils = new LinkServiceUtils();
                        LogUtils.e("==user_loginname==" + MyApplication.user_loginname);
                        String result = linkServiceUtils.addPhoto(MyApplication.user_loginname, pid, versionName, "" + (flag + 1));
                        if (result == null) {
                            handler.sendEmptyMessage(-1);
                        } else {
                            if (!result.equals("")) {
                                try {
                                    JSONObject obj = new JSONObject(result);
                                    String state = obj.getString("state");
                                    if ("false".equals(state)) {
                                        showToast(getString(R.string.uploadphotofail));
                                    } else {
                                        Message message = new Message();
                                        message.what = 2;
                                        message.obj = result;
                                        handler.sendMessage(message);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }).start();
                break;
        }

    }

    /**
     * 压缩图片
     */
    private void compressPic() {
        String saveDir = Environment.getExternalStorageDirectory() + "/temple";
        File myfile = new File(saveDir, "temp.jpg");
        if (myfile.exists()) {
            Bitmap bitmap = CommonUtils.getImage(myfile.getAbsolutePath());
            File saveImage = new File(Environment.getExternalStorageDirectory().getPath() + "/zrphotos", "save.jpg");
            if (!saveImage.getParentFile().exists()) {
                saveImage.getParentFile().mkdirs();
            }
            if (saveImage.exists()) {
                saveImage.delete();
            }
            try {
                saveImage.createNewFile();
                FileOutputStream out = new FileOutputStream(saveImage);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
                bitmap = null;
            } catch (IOException e) {
                e.printStackTrace();
                LogUtils.e("==" + e.toString());
            }
        } else {
            showToast(getString(R.string.compressionfail));
        }

    }

    /**
     * 去相机拍摄照片
     */
    private void gotoTakePhoto() {
        boolean cameraUseable = CommonUtils.isCameraUseable();
        if (!cameraUseable) {
            showToast(getString(R.string.nopermissions));
        } else {
            String state = Environment.getExternalStorageState();
            if (state.equals(Environment.MEDIA_MOUNTED)) {
                String saveDir = Environment.getExternalStorageDirectory() + "/temple";
                File dir = new File(saveDir);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                File file = new File(saveDir, "temp.jpg");
                file.delete();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    showToast(e.getMessage());
                    return;
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent, Const.CAMERA_REQUEST);
            } else {
                showToast(getString(R.string.nosdcard));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Const.CAMERA_REQUEST:
                if (resultCode == RESULT_OK) {
                    getPhoto();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 加载图片
     */
    private void getPhoto() {
        layoutChoice.setVisibility(View.GONE);
        layout_image.setVisibility(View.VISIBLE);
        showCurrentPhoto();
        progressDialog = CommonUtils.initProgressDialog(this, getString(R.string.loadpic));
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    String saveDir = Environment.getExternalStorageDirectory() + "/temple";
                    LogUtils.e("===图片路径====" + saveDir);
                    File myfile = new File(saveDir, "temp.jpg");
                    if (myfile != null && myfile.exists()) {
                        LogUtils.e("===图片file存在====");
                        String path = myfile.getPath();
                        Message message = new Message();
                        message.what = 1;
                        message.obj = path;
                        handler.sendMessage(message);
                    } else {
                        handler.sendEmptyMessage(-1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Message message = new Message();
                    message.what = -1;
                    message.obj = e.toString();
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    /**
     * 显示当前拍摄的是哪张照片
     */
    private void showCurrentPhoto() {
        switch (flag) {
            case 0:
                text_flag.setText(getString(R.string.topboxconnectstatus));
                break;
            case 1:
                text_flag.setText(getString(R.string.positivefigure));
                break;
            case 2:
                text_flag.setText(getString(R.string.profile));
                break;
            case 3:
                text_flag.setText(getString(R.string.above));
                break;
            case 4:
                text_flag.setText(getString(R.string.pilerecordword));
                break;
        }

    }

    @Override
    protected void onStop() {
        LogUtils.e("==fill onStop==");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LogUtils.e("==fill onDestroy==");
        super.onDestroy();
    }
}
