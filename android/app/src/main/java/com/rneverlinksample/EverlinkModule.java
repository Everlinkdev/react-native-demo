package com.rneverlinksample;
import androidx.annotation.NonNull;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.HashMap;

public class EverlinkModule extends ReactContextBaseJavaModule {
    EverlinkModule(ReactApplicationContext context) {
        super(context);
    }

    @NonNull
    @NotNull
    @Override
    public String getName() {
        return "EverlinkModule";
    }
}
