package com.miraclemessages;

import android.app.Application;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

import static com.miraclemessages.utils.GenericUtil.getS3Client;

/**
 * This is a singleton application class.
 * The onCreate for this is the first thing called when app starts.
 * We can initialize one singleton things here.
 * <p>
 * Created by shobhit on 2017-08-06.
 */

public class MMApplication extends Application {

    private static MMApplication mApplicationInstance = null;
    private TransferUtility mTransferUtility;

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        // Initialize Fabric
        Fabric.with(this, new Crashlytics());
    }

    /**
     * Gets an instance of the TransferUtility which is constructed using the
     * given Context
     *
     * @return a TransferUtility instance
     */
    public TransferUtility getTransferUtility() {
        if (mTransferUtility == null) {
            mTransferUtility = new TransferUtility(getS3Client(this), this);
        }

        return mTransferUtility;
    }

    public static MMApplication getInstance() {
        return mApplicationInstance;
    }

    public static void setInstance(MMApplication applicationInstance) {
        MMApplication.mApplicationInstance = applicationInstance;
    }
}
