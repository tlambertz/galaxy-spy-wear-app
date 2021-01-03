package com.samsung.android.app.watchmanager.setupwizard;

import lanchon.dexpatcher.annotation.*;

import android.app.Activity;
import android.app.Fragment;
import android.util.Log;

@DexEdit
public class PermissionFragment extends Fragment {

    @DexIgnore
    public interface IGrantedTask {
        void doTask();
    }

    // This function usually checks if permissions are granted, requests them if not, then calls doTask of iGrantedTask.
    @DexReplace
    public static void verifyPermissions(Activity activity, IGrantedTask iGrantedTask, String[] strArr) {
        Log.e("WEAR_PATCH", "verifyPermissions called with permissions " + String.join(", ", strArr) + "! Ignoring and calling task directly!");
        // Do not check any permissions, just call iGrantedTask
        iGrantedTask.doTask();

        // Alternative idea:
        // In twatchmanager.util.PermissionsUtils, replace the INITIAL_PERMISSION array to include fewer permissions:
        //@DexEdit
        //public static final String[] INITIAL_PERMISSION = {"android.permission.ACCESS_FINE_LOCATION"};
        // previously:
        // public static final String[] INITIAL_PERMISSION = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_PHONE_STATE", "android.permission.GET_ACCOUNTS"};
    }
}
