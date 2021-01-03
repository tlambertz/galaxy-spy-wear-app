package com.samsung.accessory.hearablemgr.module.base;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import lanchon.dexpatcher.annotation.*;

@DexEdit
public class PermissionCheckImpl {
    @DexReplace
    public boolean isAllPermissionGranted() {
        Log.d("WEAR_PATCH", "FAKE isAllPermissionGranted() : true" );
        return true;
    }
}