package com.rneverlinksample;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.everlink.broadcast.util.Everlink;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import org.jetbrains.annotations.NotNull;

public class EverlinkModule extends ReactContextBaseJavaModule {

    private static final int REQUEST_MICROPHONE = 8000;
    private Everlink EverlinkObj;

    EverlinkModule(ReactApplicationContext context) {
        super(context);
    }

    @NonNull
    @NotNull
    @Override
    public String getName() {
        return "EverlinkModule";
    }

    @ReactMethod
    public void addListener(String eventName) {
        // Set up any upstream listeners or background tasks as necessary
    }

    @ReactMethod
    public void removeListeners(Integer count) {
        // Remove upstream listeners, stop unnecessary background tasks
    }

    @ReactMethod
    public void initialize(String appIDKey) {
        //check permissions
        if (ContextCompat.checkSelfPermission(getReactApplicationContext(), android.Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( getCurrentActivity(), new String[] {
                    android.Manifest.permission.RECORD_AUDIO  }, REQUEST_MICROPHONE);
        }

        EverlinkObj = new Everlink(getReactApplicationContext(), getCurrentActivity(), appIDKey);
        EverlinkObj.setAudioListener(new Everlink.audioListener() {

            @Override
            public void onAudioCodeReceived(String token) {
                // you can now identify, via the server returned token, what location/device was heard
                Log.d("newToken", token);
                getReactApplicationContext()
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("onAudioCodeReceived", token);
            }

            @Override
            public void onEverlinkError(String error) {
                //return the type of error received: server response, no internet, no permissions
                Log.d("error", error);
                getReactApplicationContext()
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("onEverlinkError", error);
            }

            @Override
            public void onMyTokenGenerated(String oldToken, String newToken) {
                //a new token generated, to save in your database
                Log.d("newToken", newToken);
                Log.d("oldToken", oldToken);
                WritableMap params = Arguments.createMap();
                params.putString("oldToken", oldToken);
                params.putString("newToken", newToken);
                getReactApplicationContext()
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("onMyTokenGenerated", params);
            }
        });
    }

    @ReactMethod
    public void startListening(boolean isOffline) {
        EverlinkObj.startListening(isOffline);
    }

    @ReactMethod
    public void stopListening() {
        EverlinkObj.stopListening();
    }

    @ReactMethod
    public void playVolume(double volume, boolean useLoudspeaker) {
        EverlinkObj.playVolume(volume, useLoudspeaker);
    }

    @ReactMethod
    public void startEmitting() {
        EverlinkObj.startEmitting();
    }

    @ReactMethod
    public void stopEmitting() {
        EverlinkObj.stopEmitting();
    }

    @ReactMethod
    public void startEmittingToken(String token, boolean isOffline) {
        EverlinkObj.startEmittingToken(token, isOffline);
    }

    @ReactMethod
    public void createNewToken(String startDate) {
        EverlinkObj.createNewToken(startDate);
    }

    @ReactMethod
    public void saveSounds(ReadableArray tokensArray) {
        String[] tokens = new String[tokensArray.size()];
        for (int i = 0; i < tokensArray.size(); i++) {
            tokens[i] = tokensArray.getString(i);
        }
        EverlinkObj.saveSounds(tokens);
    }

    @ReactMethod
    public void clearSounds() {
        EverlinkObj.clearSounds();
    }
}
