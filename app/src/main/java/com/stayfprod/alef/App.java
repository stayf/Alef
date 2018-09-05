package com.stayfprod.alef;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

public class App extends Application {

    public static final String API_ENDPOINT_URL = "http://dev-tasks.alef.im/task-m-001/";
    private static App instance;

    public App() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }

    public static Resources getResource() {
        return instance.getApplicationContext().getResources();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(instance)
                .setMaxCacheSize(50 * ByteConstants.MB)
                .setMaxCacheSizeOnLowDiskSpace(20 * ByteConstants.MB)
                .setMaxCacheSizeOnVeryLowDiskSpace(10 * ByteConstants.MB)
                .build();

        ImagePipelineConfig frescoConfig = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();

        Fresco.initialize(this, frescoConfig);
    }
}
