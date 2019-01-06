package com.example.hongcheng.common.provider;

import android.support.v4.content.FileProvider;

public class BaseFileProvider extends FileProvider {
    @Override
    public boolean onCreate() {
        return true;
    }
}
