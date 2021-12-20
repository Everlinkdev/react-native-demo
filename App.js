/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, {useEffect} from 'react';
import {
  NativeEventEmitter,
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  TouchableOpacity,
  useColorScheme,
  View,
} from 'react-native';

import Everlink from './Everlink';

const App = () => {
  const isDarkMode = useColorScheme() === 'dark';

  useEffect(() => {
    const eventEmitter = new NativeEventEmitter(Everlink);
    const onAudioCodeReceivedListener = eventEmitter.addListener('onAudioCodeReceived', (token) => {
      console.log(token);
    });
    const onEverLinkErrorListener = eventEmitter.addListener('onEverLinkError', (error) => {
      console.log(error);
    });
    const onMyTokenGeneratedListener = eventEmitter.addListener('onMyTokenGenerated', (res) => {
      console.log(res);
    });

    return () => {
      onAudioCodeReceivedListener.remove();
      onEverLinkErrorListener.remove();
      onMyTokenGeneratedListener.remove();
    }
  })

  const startListening = (isOffline) => {
    Everlink.startListening(isOffline);
  }

  const stopListening = () => {
    Everlink.stopListening();
  }

  return (
    <SafeAreaView style={{flex: 1, backgroundColor: 'white'}}>
      <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />
      <ScrollView>
        <View style={styles.container}>
          <TouchableOpacity style={styles.button} onPress={() => startListening(true)}>
            <Text style={styles.buttonText}>startListening</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={stopListening}>
            <Text style={styles.buttonText}>stopListening</Text>
          </TouchableOpacity>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    padding: 16,
    backgroundColor: 'white',
  },
  button: {
    height: 36,
    marginBottom: 16,
    backgroundColor: 'orange',
    justifyContent: 'center',
    alignItems: 'center',
    elevation: 8,
    borderRadius: 2,
  },
  buttonText: {
    color: 'black',
  },
});

export default App;
