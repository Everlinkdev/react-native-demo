# react-native-everlink-example

---

## Installation

### Android

- Edit `android/build.gradle` to look like this

    ```diff
    allprojects {
        repositories {
            ...
            google()
            maven { url 'https://www.jitpack.io' }
    +       maven {
    +           url "https://repo.everlink.co/repository/maven-releases/"
    +       }
        }
    }
    ```

- Edit `android/app/build.gradle` to look like this

    ```diff
    dependencies {
        implementation fileTree(dir: "libs", include: ["*.jar"])
        //noinspection GradleDynamicVersion
        implementation "com.facebook.react:react-native:+"  // From node_modules

        implementation "androidx.swiperefreshlayout:swiperefreshlayout:1.0.0"
    +   implementation 'com.everlink:broadcast:2.0.1'
    +   implementation 'org.apache.commons:commons-math3:3.6.1'
        ...
    }
    ```

- Edit `android/app/src/main/AndroidManifest.xml` to look like this

    ```diff
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
        package="com.rneverlinksample">

        <uses-permission android:name="android.permission.INTERNET" />
    +   <uses-permission android:name="android.permission.RECORD_AUDIO" />
    +   <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />

        <application
        ...
        </application>
    </manifest>
    ```

- Copy `android/app/src/main/java/com/rneverlinksample/EverlinkModule.java` file to `android/app/src/main/java/com/your-app-name/` folder.

- Copy `android/app/src/main/java/com/rneverlinksample/EverlinkPackage.java` file to `android/app/src/main/java/com/your-app-name/` folder.

- Edit `android/app/src/main/java/MainApplication.java` to look like this

    ```diff
    @Override
    protected List<ReactPackage> getPackages() {
        @SuppressWarnings("UnnecessaryLocalVariable")
        List<ReactPackage> packages = new PackageList(this).getPackages();
        // Packages that cannot be autolinked yet can be added manually here, for example:
        // packages.add(new MyReactNativePackage());
    +   packages.add(new EverlinkPackage());
        return packages;
    }
    ```

### iOS

- Add the following to your `Podfile` and run `npx pod-install`

    ```
    pod 'EverlinkBroadcastSDK', '2.0.0'
    ```

- Add microphone permission to your app `Info.plist`

    ```
    <key>NSMicrophoneUsageDescription</key>
    <string>Needs to access microphone to record audio</string>
    ```

- Add `ios/EverlinkModule.m` file to your iOS project. In the popup, please select your target and be sure "Copy items if needed" is checked.

    <p>
    <img src="images/Screenshot at Dec 21 23-25-30.png" width="100%"  />
    </p>

- Add `ios/EverlinkModule.swift` file to your iOS project. In the popup, please select your target and be sure "Copy items if needed" is checked. You should be prompted to choose if you want to configure an Objective-C Bridging Header. Select “Create Bridging Header”.

    <p>
    <img src="images/Screenshot at Dec 21 23-27-40.png" width="100%"  />
    </p>

- Edit `YourProject-Bridging-Header.h` to look like this

    ```diff
    + #import <React/RCTBridgeModule.h>
    + #import <React/RCTEventEmitter.h>
    ```

---

## Usage

- Copy `/Everlink.js` file to your project

- Import Everlink.js

    ```javascript
    import Everlink from '/path/Everlink';
    ```

- Initialize sdk

    ```javascript
    useEffect(() => {
        Everlink.initialize('your appID key');
    }, []);
    ```

- Set up event listeners

    ```javascript
    useEffect(() => {
        const eventEmitter = new NativeEventEmitter(Everlink);
        const onAudioCodeReceivedListener = eventEmitter.addListener('onAudioCodeReceived', (token) => {
            console.log(token);
        });
        const onEverlinkErrorListener = eventEmitter.addListener('onEverlinkError', (error) => {
            console.log(error);
        });
        const onMyTokenGeneratedListener = eventEmitter.addListener('onMyTokenGenerated', (res) => {
            console.log(res);
        });

        return () => {
            onAudioCodeReceivedListener.remove();
            onEverlinkErrorListener.remove();
            onMyTokenGeneratedListener.remove();
        }
    }, []);
    ```

- Detect code

    ```javascript
    <TouchableOpacity onPress={() => Everlink.startListening(isOffline)}>
        <Text>startListening</Text>
    </TouchableOpacity>
    <TouchableOpacity onPress={() => Everlink.stopListening()}>
        <Text>stopListening</Text>
    </TouchableOpacity>
    ```

- Send code

    ```javascript
    <TouchableOpacity onPress={() => Everlink.playVolume(volume, useLoudspeaker)}>
        <Text>playVolume</Text>
    </TouchableOpacity>
    <TouchableOpacity onPress={() => Everlink.startEmitting()}>
        <Text>startEmitting</Text>
    </TouchableOpacity>
    <TouchableOpacity onPress={() => Everlink.stopEmitting()}>
        <Text>stopEmitting</Text>
    </TouchableOpacity>
    <TouchableOpacity onPress={() => Everlink.startEmittingToken(token, isOffline)}>
        <Text>startEmittingToken</Text>
    </TouchableOpacity>
    ```

- Create token

    ```javascript
    <TouchableOpacity onPress={() => Everlink.createNewToken(date)}>
        <Text>createNewToken</Text>
    </TouchableOpacity>
    ```

- Downloading tokens (*Only needed if you want the SDK to work offline*)

    ```javascript
    <TouchableOpacity onPress={() => Everlink.saveSounds(tokens)}>
        <Text>saveSounds</Text>
    </TouchableOpacity>
    <TouchableOpacity onPress={() => Everlink.clearSounds()}>
        <Text>clearSounds</Text>
    </TouchableOpacity>
    ```

---

To learn more, **[read this](https://reactnative.dev/docs/native-modules-intro)**.