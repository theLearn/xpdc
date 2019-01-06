package com.example.hongcheng.common.view.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.example.hongcheng.common.view.shortcutbadger.Badger;
import com.example.hongcheng.common.view.shortcutbadger.ShortcutBadgeException;
import com.example.hongcheng.common.view.shortcutbadger.util.BroadcastHelper;

import java.util.Arrays;
import java.util.List;

/**
 * @author Leo Lin
 * Deprecated, LG devices will use DefaultBadger
 */
@Deprecated
public class LGHomeBadger implements Badger {

    private static final String INTENT_ACTION = IntentConstants.DEFAULT_INTENT_ACTION;
    private static final String INTENT_EXTRA_BADGE_COUNT = "badge_count";
    private static final String INTENT_EXTRA_PACKAGENAME = "badge_count_package_name";
    private static final String INTENT_EXTRA_ACTIVITY_NAME = "badge_count_class_name";

    @Override
    public void executeBadge(Context context, ComponentName componentName, int badgeCount) throws ShortcutBadgeException {
        Intent intent = new Intent(INTENT_ACTION);
        intent.putExtra(INTENT_EXTRA_BADGE_COUNT, badgeCount);
        intent.putExtra(INTENT_EXTRA_PACKAGENAME, componentName.getPackageName());
        intent.putExtra(INTENT_EXTRA_ACTIVITY_NAME, componentName.getClassName());

        BroadcastHelper.sendDefaultIntentExplicitly(context, intent);
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList(
                "com.lge.launcher",
                "com.lge.launcher2"
        );
    }
}
