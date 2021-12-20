/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, {useState, useEffect} from 'react';
import {
  LogBox,
  NativeEventEmitter,
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  useColorScheme,
  View,
} from 'react-native';
import CheckBox from '@react-native-community/checkbox';
import DatePicker from 'react-native-date-picker';
import moment from 'moment';

import Everlink from './Everlink';

LogBox.ignoreAllLogs(); 

const App = () => {
  const isDarkMode = useColorScheme() === 'dark';

  const [audioCode, setAudioCode] = useState('');
  const [everlinkError, setEverlinkError] = useState('');
  const [myToken, setMyToken] = useState(null);

  const [isOffline, setOffline] = useState(false);
  const [volume, setVolume] = useState('0.5');
  const [useLoudspeaker, setUseLoudspeaker] = useState(false);
  const [token, setToken] = useState('');
  const [isOffline2, setOffline2] = useState(false);
  const [date, setDate] = useState(new Date());
  const [isDatepickerVisible, setDatepickerVisible] = useState(false);
  const [tokens, setTokens] = useState('');

  useEffect(() => {
    Everlink.initialize('upworkDemoKey11');
  }, []);

  useEffect(() => {
    const eventEmitter = new NativeEventEmitter(Everlink);
    const onAudioCodeReceivedListener = eventEmitter.addListener('onAudioCodeReceived', (token) => {
      console.log(token);
      setAudioCode(token);
    });
    const onEverLinkErrorListener = eventEmitter.addListener('onEverLinkError', (error) => {
      console.log(error);
      setEverlinkError(error);
    });
    const onMyTokenGeneratedListener = eventEmitter.addListener('onMyTokenGenerated', (res) => {
      console.log(res);
      setMyToken(res);
    });

    return () => {
      onAudioCodeReceivedListener.remove();
      onEverLinkErrorListener.remove();
      onMyTokenGeneratedListener.remove();
    }
  }, []);

  return (
    <SafeAreaView style={{flex: 1, backgroundColor: 'white'}}>
      <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />
      <ScrollView keyboardShouldPersistTaps="handled">
        <View style={styles.container}>
          {/* Show status */}
          <View style={{marginBottom: 16}}>
            <View>
              <Text style={{fontWeight: 'bold'}}>onAudioCodeReceived: </Text>
              <Text style={{marginTop: 4}}>{audioCode}</Text>
            </View>
            <View>
              <Text style={{fontWeight: 'bold'}}>onEverLinkError: </Text>
              <Text style={{marginTop: 4}}>{everlinkError}</Text>
            </View>
            <View>
              <Text style={{fontWeight: 'bold'}}>onMyTokenGenerated: </Text>
              <Text style={{marginTop: 4}}>oldToken: {myToken?.oldToken}</Text>
              <Text style={{marginTop: 4}}>newToken: {myToken?.newToken}</Text>
            </View>
          </View>

          <View style={styles.divider} />

          {/* Detect Code */}
          <View style={{flexDirection: 'row', alignItems: 'center'}}>
            <CheckBox
              value={isOffline}
              onValueChange={(newValue) => setOffline(newValue)}
            />
            <Text>isOffline</Text>
          </View>
          <TouchableOpacity style={styles.button} onPress={() => Everlink.startListening(isOffline)}>
            <Text style={styles.buttonText}>startListening</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => Everlink.stopListening()}>
            <Text style={styles.buttonText}>stopListening</Text>
          </TouchableOpacity>

          <View style={styles.divider} />

          {/* Send Code */}
          <View style={{flexDirection: 'row', alignItems: 'center', marginBottom: 16}}>
            <View style={{flex: 1, flexDirection: 'row', alignItems: 'center'}}>
              <Text>Volume: </Text>
              <TextInput style={{flex: 1, height: 40, borderWidth: 1}} value={volume} onChangeText={setVolume} keyboardType='numeric' />
            </View>
            <View style={{flexDirection: 'row', alignItems: 'center'}}>
              <CheckBox
                value={useLoudspeaker}
                onValueChange={(newValue) => setUseLoudspeaker(newValue)}
              />
              <Text>useLoudspeaker</Text>
            </View>
          </View>
          <TouchableOpacity style={styles.button} onPress={() => Everlink.playVolume(volume, useLoudspeaker)}>
            <Text style={styles.buttonText}>playVolume</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => Everlink.startEmitting()}>
            <Text style={styles.buttonText}>startEmitting</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => Everlink.stopEmitting()}>
            <Text style={styles.buttonText}>stopEmitting</Text>
          </TouchableOpacity>
          <View style={{flexDirection: 'row', alignItems: 'center', marginTop: 16, marginBottom: 16}}>
            <View style={{flex: 1, flexDirection: 'row', alignItems: 'center'}}>
              <Text>Token: </Text>
              <TextInput style={{flex: 1, height: 40, borderWidth: 1}} value={token} onChangeText={setToken} />
            </View>
            <View style={{flexDirection: 'row', alignItems: 'center'}}>
              <CheckBox
                value={isOffline2}
                onValueChange={(newValue) => setOffline2(newValue)}
              />
              <Text>isOffline</Text>
            </View>
          </View>
          <TouchableOpacity style={styles.button} onPress={() => Everlink.startEmittingToken(token, isOffline2)}>
            <Text style={styles.buttonText}>startEmittingToken</Text>
          </TouchableOpacity>

          <View style={styles.divider} />

          {/* Create token */}
          <TouchableOpacity style={{marginBottom: 8}} onPress={() => setDatepickerVisible(true)}>
            <Text>{moment(date).format('YYYY-MM-DD')}</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => Everlink.createNewToken(moment(date).format('YYYY-MM-DD'))}>
            <Text style={styles.buttonText}>createNewToken</Text>
          </TouchableOpacity>

          <View style={styles.divider} />

          {/* Downloading Tokens */}
          <TextInput
            style={{height: 120, borderWidth: 1, marginBottom: 12}}
            value={tokens}
            onChangeText={setTokens}
            multiline
            textAlignVertical="top"
            placeholder={`exampleToken1\nexampleToken2\nexampleToken3`} />
          <TouchableOpacity style={styles.button} onPress={() => Everlink.saveSounds(tokens.split(/\r?\n/))}>
            <Text style={styles.buttonText}>saveSounds</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => Everlink.clearSounds()}>
            <Text style={styles.buttonText}>clearSounds</Text>
          </TouchableOpacity>
        </View>
      </ScrollView>

      <DatePicker
        modal
        open={isDatepickerVisible}
        date={date}
        onConfirm={(date) => {
          setDatepickerVisible(false);
          setDate(date);
        }}
        onCancel={() => {
          setDatepickerVisible(false);
        }}
        mode="date"
      />
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
  divider: {
    backgroundColor: 'lightgray',
    width: '100%',
    height: 1,
    marginBottom: 16,
  }
});

export default App;
