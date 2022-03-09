import React, { useState } from 'react';
import {SafeAreaView, View, Text, Button} from 'react-native';
import {
  KakaoOAuthToken,
  KakaoProfile,
  getProfile as getKakaoProfile,
  login,
  logout,
  unlink,
} from '@react-native-seoul/kakao-login';

const App = () => {

  const [result, setResult] = useState('');

  const signInWithKakao = async (): Promise<void> => {
    const token: KakaoOAuthToken = await login();
  
    setResult(JSON.stringify(token));
  };
  
  const signOutWithKakao = async (): Promise<void> => {
    const message = await logout();
    setResult(message);
  };
  
  const getKakaoProfile = async (): Promise<void> => {
    const profile: KakaoProfile = await getProfile();
  
    setResult(JSON.stringify(profile));
  };
  
  const unlinkKakao = async (): Promise<void> => {
    const message = await unlink();
  
    setResult(message);
  };

  return (
    <SafeAreaView>
      <View>
        <Text>Hello :!</Text>
        <Button title="카카오 회원가입" onPress={() => signInWithKakao()} />
        

        <Button title="실험" onPress={() => alert('hi')} />
      </View>
    </SafeAreaView>
  );
};

export default App;