package com.samsung.accessory.hearablemgr.module.setupwizard;

import android.util.Log;
import com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity;

import lanchon.dexpatcher.annotation.*;

@DexEdit
public class TermsAndConditionsActivity extends OrientationPolicyActivity {
    @DexReplace
    private boolean hasNotificationAccessPermission() {
        Log.e("WEAR_PATCH", "Live Buds have notification access ;)");
        return true;
    }
}