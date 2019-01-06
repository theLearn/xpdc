package com.example.hongcheng.common.lifecycle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {

    private final static String PERMISSION_ACTIVITY_CLASS_NAME =
            "com.example.hongcheng.common.util.PermissionUtils$PermissionActivity";

    public final static class ActivityLifecycleImplHolder {
        public final static ActivityLifecycleImpl INSTANCE = new ActivityLifecycleImpl();
    }

    private ActivityLifecycleImpl() {
    }

    public static ActivityLifecycleImpl getInstance() {
        return ActivityLifecycleImplHolder.INSTANCE;
    }

    final LinkedList<Activity> mActivityList = new LinkedList<>();
    final HashMap<Object, OnAppStatusChangedListener> mStatusListenerMap = new HashMap<>();

    public List<Activity> getActivityList() {
        return mActivityList;
    }

    private int mForegroundCount = 0;
    private int mConfigCount = 0;

    void addListener(final Object object, final OnAppStatusChangedListener listener) {
        mStatusListenerMap.put(object, listener);
    }

    void removeListener(final Object object) {
        mStatusListenerMap.remove(object);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        setTopActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        setTopActivity(activity);
        if (mForegroundCount <= 0) {
            postStatus(true);
        }
        if (mConfigCount < 0) {
            ++mConfigCount;
        } else {
            ++mForegroundCount;
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        setTopActivity(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {/**/}

    @Override
    public void onActivityStopped(Activity activity) {
        if (activity.isChangingConfigurations()) {
            --mConfigCount;
        } else {
            --mForegroundCount;
            if (mForegroundCount <= 0) {
                postStatus(false);
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {/**/}

    @Override
    public void onActivityDestroyed(Activity activity) {
        mActivityList.remove(activity);
    }

    private void postStatus(final boolean isForeground) {
        if (mStatusListenerMap.isEmpty()) return;
        for (OnAppStatusChangedListener onAppStatusChangedListener : mStatusListenerMap.values()) {
            if (onAppStatusChangedListener == null) return;
            if (isForeground) {
                onAppStatusChangedListener.onForeground();
            } else {
                onAppStatusChangedListener.onBackground();
            }
        }
    }

    private void setTopActivity(final Activity activity) {
        if (PERMISSION_ACTIVITY_CLASS_NAME.equals(activity.getClass().getName())) return;
        if (mActivityList.contains(activity)) {
            if (!mActivityList.getLast().equals(activity)) {
                mActivityList.remove(activity);
                mActivityList.addLast(activity);
            }
        } else {
            mActivityList.addLast(activity);
        }
    }

    public Activity getTopActivity() {
        if (!mActivityList.isEmpty()) {
            final Activity topActivity = mActivityList.getLast();
            if (topActivity != null) {
                return topActivity;
            }
        }
        Activity topActivityByReflect = getTopActivityByReflect();
        if (topActivityByReflect != null) {
            setTopActivity(topActivityByReflect);
        }
        return topActivityByReflect;
    }

    private Activity getTopActivityByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivityList");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            if (activities == null) return null;
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return (Activity) activityField.get(activityRecord);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////

    public interface OnAppStatusChangedListener {
        void onForeground();

        void onBackground();
    }
}

