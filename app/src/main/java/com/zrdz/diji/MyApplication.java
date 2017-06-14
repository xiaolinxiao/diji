package com.zrdz.diji;

import android.app.Application;
import android.content.Context;

import com.lzy.okhttputils.OkHttpUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zrdz.diji.utils.LogUtils;

/**
 * Created by ASUS on 2017/4/17.
 */

public class MyApplication extends Application {
    public static boolean isLogin = false;//登录判断标志
    public static String user_loginname = "";//用户名
    public static String user_name = "";//机构名称
    public static String user_code = "";//查询机构代码
    public static String user_pid = "";//监管pid
    public static String user_roleType = "";//用户角色
    public static String user_userType = "";//用户类型
    public static String user_id = "";//用戶id
    public static String user_mobile = "";//用户电话号码


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("==application==oncreate");
        initOkHttpUtils();
        initImageLoader(this);
    }

    /**
     * okhttp初始化
     */
    private void initOkHttpUtils() {
        //必须调用初始化
        OkHttpUtils.init(this);
        //以下都不是必须的，根据需要自行选择
        OkHttpUtils.getInstance()//
                .debug("OkHttpUtils")                                              //是否打开调试
                .setConnectTimeout(10000)               //全局的连接超时时间
                .setReadTimeOut(10000)                  //全局的读取超时时间
                .setWriteTimeOut(10000);               //全局的写入超时时间
    }

    /**
     * image
     *
     * @param context
     */
    private void initImageLoader(Context context) {
        // 使用默认的配置
        // ImageLoaderConfiguration configuration =
        // ImageLoaderConfiguration.createDefault(this);
        // 使用自定义的配置（官方版）
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        // 线程池内加载的数量
        config.threadPoolSize(3);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs();
        config.defaultDisplayImageOptions(getListOptions());
        ImageLoader.getInstance().init(config.build());
    }

    /**
     * 设置options
     */
    private DisplayImageOptions getListOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // 设置图片在下载期间显示的图片
                .showImageOnLoading(R.drawable.default_image)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.drawable.default_image)
                // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.drawable.default_image)
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisc(true)
                // 保留Exif信息
//                .considerExifParams(true)
                // 设置图片以如何的编码方式显示
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                // 设置图片的解码类型
//                .bitmapConfig(Bitmap.Config.RGB_565)
                // .decodingOptions(android.graphics.BitmapFactory.Options
                // decodingOptions)//设置图片的解码配置
//                .considerExifParams(true)
                // 设置图片下载前的延迟
//                .delayBeforeLoading(100)// int
                // delayInMillis为你设置的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)
//                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
//                .displayer(new FadeInBitmapDisplayer(100))// 淡入
                .build();
        return options;
    }
}
