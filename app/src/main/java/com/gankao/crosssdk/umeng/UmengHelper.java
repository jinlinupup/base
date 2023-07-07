package com.gankao.crosssdk.umeng;

import android.content.Context;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.BuildConfig;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.listener.OnGetOaidListener;

public class UmengHelper {

    public static String appkey = "64741f28a1a164591b25b8ed";
    public static String pushSecret = "";

    public static void preInit(Context context){
        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);

        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

        //友盟预初始化
        UMConfigure.preInit(context.getApplicationContext(),appkey, "test");
    }

    public static void init(Context context){
        //友盟正式初始化
        UmInitConfig umInitConfig=new UmInitConfig();
        umInitConfig.UMinit(context.getApplicationContext(), appkey, pushSecret);
        UMConfigure.getOaid(context,new OnGetOaidListener() {
            @Override
            public void onGetOaid(String oaid) {
                Log.d("getOaid", "Android Q oaid : "+oaid);
            }});
    }

    public static void initPush(Context context){
        /**
         * 注意：如果您已经在AndroidManifest.xml中配置过appkey和channel值，可以调用此版本初始化函数。
         * pushSecret: Push推送业务的secret
         */
        UMConfigure.init(context, UMConfigure.DEVICE_TYPE_PHONE, pushSecret);
    }
}
