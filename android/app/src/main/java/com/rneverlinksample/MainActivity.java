package com.rneverlinksample;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.everlink.broadcast.util.Everlink;
import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;

public class MainActivity extends ReactActivity {

  private static final int REQUEST_MICROPHONE = 8000;
  private Everlink EverLinkConnect;

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "RNEverlinkSample";
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(null);

    String myAppID = "upworkDemoKey11";

    //check permissions
    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED ) {
      ActivityCompat.requestPermissions( this, new String[] {
              android.Manifest.permission.RECORD_AUDIO  }, REQUEST_MICROPHONE);
    }

    EverLinkConnect = new Everlink(getApplicationContext(), this, myAppID);
    EverLinkConnect.setAudioListener(new Everlink.audioListener() {

      @Override
      public void onAudioCodeReceived(String token) {
        // you can now identify, via the server returned token, what location/device was heard
        Log.d("newToken", token);
      }

      @Override
      public void onEverLinkError(String error) {
        //return the type of error received: server response, no internet, no permissions
        Log.d("error", error);
      }

      @Override
      public void onMyTokenGenerated(String oldToken, String newToken) {
        //a new token generated, to save in your database
        Log.d("newToken", newToken);
        Log.d("oldToken", oldToken);
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
