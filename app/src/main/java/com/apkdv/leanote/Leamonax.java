package com.apkdv.leanote;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import net.danlew.android.joda.JodaTimeAndroid;

import org.greenrobot.eventbus.EventBus;
import com.apkdv.leanote.R;
import com.apkdv.leanote.ui.MainActivity2;
import com.apkdv.leanote.utils.DvSharedPreferences;

import java.util.ArrayList;

public class Leamonax extends Application {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public ArrayList<Activity> mActivities = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initBugly();

        EventBus.builder()
                .logNoSubscriberMessages(false)
                .sendNoSubscriberEvent(false)
                .throwSubscriberException(true)
                .installDefaultEventBus();
        FlowManager.init(new FlowConfig.Builder(this).build());
        Stetho.initializeWithDefaults(this);
        JodaTimeAndroid.init(this);
        DvSharedPreferences.init(this);
    }

    private void initBugly() {
        Beta.canShowUpgradeActs.add(MainActivity2.class);
        Beta.upgradeCheckPeriod = 60 * 1000; // 1 minute

        Resources res = getResources();
        Beta.strToastYourAreTheLatestVersion = res.getString(R.string.your_are_the_latest_version);
        Beta.strToastCheckUpgradeError = res.getString(R.string.check_upgrade_error);
        Beta.strToastCheckingUpgrade = res.getString(R.string.checking_upgrade);
        Beta.strNotificationDownloading = res.getString(R.string.downloading);
        Beta.strNotificationClickToView = res.getString(R.string.click_to_view);
        Beta.strNotificationClickToInstall = res.getString(R.string.click_to_install);
        Beta.strNotificationClickToRetry = res.getString(R.string.click_to_retry);
        Beta.strNotificationClickToContinue = res.getString(R.string.continue_download);
        Beta.strNotificationDownloadSucc = res.getString(R.string.download_successful);
        Beta.strNotificationDownloadError = res.getString(R.string.download_error);
        Beta.strNotificationHaveNewVersion = res.getString(R.string.have_new_version);
        Beta.strNetworkTipsMessage = res.getString(R.string.should_continue_download);
        Beta.strNetworkTipsTitle = res.getString(R.string.network_prompt);
        Beta.strNetworkTipsConfirmBtn = res.getString(R.string.continue_download);
        Beta.strNetworkTipsCancelBtn = res.getString(R.string.cancel);
        Beta.strUpgradeDialogVersionLabel = res.getString(R.string.version);
        Beta.strUpgradeDialogFileSizeLabel = res.getString(R.string.file_size);
        Beta.strUpgradeDialogUpdateTimeLabel = res.getString(R.string.update_time);
        Beta.strUpgradeDialogFeatureLabel = res.getString(R.string.what_s_new);
        Beta.strUpgradeDialogUpgradeBtn = res.getString(R.string.upgrade_now);
        Beta.strUpgradeDialogInstallBtn = res.getString(R.string.install);
        Beta.strUpgradeDialogRetryBtn = res.getString(R.string.retry);
        Beta.strUpgradeDialogContinueBtn = res.getString(R.string.continue_text);
        Beta.strUpgradeDialogCancelBtn = res.getString(R.string.next_time);

        Bugly.init(this, BuildConfig.BUGLY_KEY, BuildConfig.DEBUG);
    }

    public void exit() {
        for (Activity activity : mActivities) {
            activity.finish();
        }
        System.exit(0);
    }
}
