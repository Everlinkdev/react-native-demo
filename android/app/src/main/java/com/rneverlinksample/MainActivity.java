package com.rneverlinksample;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.react.ReactActivity;

public class MainActivity extends ReactActivity {

  private static final int REQUEST_MICROPHONE = 8000;

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

    //check permissions
    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED ) {
      ActivityCompat.requestPermissions( this, new String[] {
              android.Manifest.permission.RECORD_AUDIO  }, REQUEST_MICROPHONE);
    }
  }
}
