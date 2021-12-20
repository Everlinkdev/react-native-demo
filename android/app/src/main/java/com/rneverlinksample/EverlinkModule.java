package com.rneverlinksample;
import android.util.Log;

import androidx.annotation.NonNull;

import com.everlink.broadcast.util.Everlink;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.HashMap;

public class EverlinkModule extends ReactContextBaseJavaModule {

    private Everlink EverLinkConnect;

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
    public void initialize(String appIDKey) {
        EverLinkConnect = new Everlink(getReactApplicationContext(), getCurrentActivity(), appIDKey);
        EverLinkConnect.setAudioListener(new Everlink.audioListener() {

            @Override
            public void onAudioCodeReceived(String token) {
                // you can now identify, via the server returned token, what location/device was heard
                Log.d("newToken", token);
                getReactApplicationContext()
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("onAudioCodeReceived", token);
            }

            @Override
            public void onEverLinkError(String error) {
                //return the type of error received: server response, no internet, no permissions
                Log.d("error", error);
                getReactApplicationContext()
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("onEverLinkError", error);
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
        EverLinkConnect.startListening(isOffline);
    }

    @ReactMethod
    public void stopListening() {
        EverLinkConnect.stopListening();
    }

    @ReactMethod
    public void playVolume(double volume, boolean useLoudspeaker) {
        EverLinkConnect.playVolume(volume, useLoudspeaker);
    }

    @ReactMethod
    public void startEmitting() {
        EverLinkConnect.startEmitting();
    }

    @ReactMethod
    public void stopEmitting() {
        EverLinkConnect.stopEmitting();
    }

    @ReactMethod
    public void startEmittingToken(String token, boolean isOffline) {
        EverLinkConnect.startEmittingToken(token, isOffline);
    }

    @ReactMethod
    public void createNewToken(String startDate) {
        EverLinkConnect.createNewToken(startDate);
    }

    @ReactMethod
    public void saveSounds(ReadableArray tokensArray) {
        String[] tokens = new String[tokensArray.size()];
        for (int i = 0; i < tokensArray.size(); i++) {
            tokens[i] = tokensArray.getString(i);
        }
        EverLinkConnect.saveSounds(tokens);
    }

    @ReactMethod
    public void clearSounds() {
        EverLinkConnect.clearSounds();
    }
}
