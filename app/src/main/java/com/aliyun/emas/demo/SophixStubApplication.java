package com.aliyun.emas.demo;

import android.content.Context;
import android.support.annotation.Keep;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Sophix入口类，专门用于初始化Sophix，不应包含任何业务逻辑。
 * 此类必须继承自SophixApplication，onCreate方法不需要实现。
 * 此类不应与项目中的其他类有任何互相调用的逻辑，必须完全做到隔离。
 * AndroidManifest中设置application为此类，而SophixEntry中设为原先Application类。
 * 注意原先Application里不需要再重复初始化Sophix，并且需要避免混淆原先Application类。
 * 如有其它自定义改造，请咨询官方后妥善处理。
 */
public class SophixStubApplication extends SophixApplication {
    private final String TAG = SophixStubApplication.class.getSimpleName();
    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    public static String mMTOPDoman = "aserver.emas-poc.com";
    public static boolean mUseHttp = true;
    public static String mAppkey = "20000170";
    public static String mAppSecret = "f111a7c4ee6f5de9ad315f9e36f13091";
    public static String RSAPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCjKNccwUXYp9S9goiZSHXi1yJjD80z8usXgNvezCnPuFX4CFhHT7swJpgjsxiQS2s4SLpxw5jmjkYkGRfB/7/PSSK1uZp3Mh/egOZuwza+5l8nXNaN3dsXYT0CE2pTeTbzmE0OdgeOB4MxAMXgrc4R5WvPV3y/J6c7VxRuSLZWVwIDAQAB";



    @Keep
    @SophixEntry(CustomApplication.class)
    static class RealApplicationStub {}
    
    
    //添加拉取Patch包方法
    @Override
    public void onCreate() {
        super.onCreate();
        SophixManager.getInstance().queryAndLoadNewPatch();
    }
    
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
//         MultiDex.install(this);

        initSophix();
    }
    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> tags = new ArrayList<>();
        tags.add("emas");
        tags.add("test");
        tags.add("sophix");
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setHost(mMTOPDoman, !mUseHttp)
                .setSecretMetaData(mAppkey, mAppSecret, RSAPublicKey)
                .setEnableFullLog()
                .setTags(tags)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        Log.i(TAG, "PatchLoadStatusListener onLoad进来了---mode="+mode+"，code="+code+"，info="+info+"，handlePatchVersion="+handlePatchVersion);
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                        }
                    }
                }).initialize();
    }
}