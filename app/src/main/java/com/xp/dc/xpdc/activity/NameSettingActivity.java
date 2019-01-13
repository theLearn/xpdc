package com.xp.dc.xpdc.activity;

import android.view.View;
import com.xp.dc.xpdc.R;
import org.jetbrains.annotations.NotNull;

public class NameSettingActivity extends AppCommonActivity {


    @Override
    public boolean isNeedShowBack() {
        return true;
    }

    @Override
    public int setToolbarTitle() {
        return R.string.change_nick;
    }

    @Override
    public int getBodyLayoutResId() {
        return R.layout.activity_name_setting;
    }

    @Override
    public void initBodyView(@NotNull View view) {

    }
}
