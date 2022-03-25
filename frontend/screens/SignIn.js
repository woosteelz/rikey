import React, { useState, useEffect} from "react";
import {
  Alert,
  SafeAreaView,
  StyleSheet,
  Button,
  Platform,
  Image,
} from "react-native";
import { NaverLogin, getProfile } from "@react-native-seoul/naver-login";
import styled from "styled-components";
import Logo from '../assets/images/RIKEY_LOGO.png'
import Bike from '../assets/images/SIGNIN_IMAGE.png'
import API from "../api/API";
import { useStore } from "../states";

const iosKeys = {
  kConsumerKey: "naver client id",
  kConsumerSecret: "naver secret id",
  kServiceAppName: "테스트앱(iOS)",
  kServiceAppUrlScheme: "testSignIn" // only for iOS
};

const androidKeys = {
  kConsumerKey: "WfLiuAOSqT_2PxhawHwp",
  kConsumerSecret: "ymJj5C80sb",
  kServiceAppName: "RIKEY"
};

const naverLogin = props => {
  return new Promise((resolve, reject) => {
    NaverLogin.login(props, (err, token) => {
      console.log(`\n\n  Token is fetched  :: ${token} \n\n`);
      if (err) {
        reject(err);
        return;
      }
      resolve(token);
    });
  });
};

const getUserProfile = async (token) => {
  const profileResult = await getProfile(token);
  if (profileResult.resultcode === "024") {
    Alert.alert("로그인 실패", profileResult.message);
    throw Error();
  } else {
    return profileResult.response.id;
  }
}; 


const initials = Platform.OS === "ios" ? iosKeys : androidKeys;
// Promise: https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Promise

// naverLogin: token => getUserProfile(token): naverId

const SignIn = ({ navigation }) => {
  const [naverToken, setNaverToken] = useState('');
  const [userNaverId, setUserNaverId] = useState('');
  const { setUserId } = useStore();

  const naverLogout = () => {
    NaverLogin.logout();
    setNaverToken("");
    setUserNaverId('');
    console.log('로그아웃 완료')
  };

  const naverLoginProcess = async () => {
    try {
      const token = await naverLogin(initials)
      const id = await getUserProfile(token.accessToken);
      // 네이버 로그인 실패
      if (id == undefined) {
        console.log("네이버 로그인 실패");
        return
      }
      API.post('/users/login', {
        "authId": id
      })
      .then((response) => {
        const result = response.data.profile;
        if (result === null) {
          // 아이디가 유효하면서 정보가 없다면?
          // 반환되는게 null이면 회원가입으로 redirect
          navigation.navigate('SignUp', {id : id});
        } 
        else {
          // 반환 success면 Home으로 가기
          setUserId(result.id);
          navigation.navigate('Tabs');
        }
      })
      .catch((error) => {
        console.log(error);
      })
    }
    catch(e)  {
    }
  }

  return (
    <Container>

        <TopGap />

        <LogoContainer>
          <LogoImg source={Logo} />
        </LogoContainer>

        <ContentsFlex>
            <InnerFlex>

                <BikeContainer>
                  <BikeImg source={Bike}/>
                </BikeContainer>

                <BottomFlex>

                  <LoginTextcontainer/>
                    <LoginText>{"즐거운 라이딩을 위한"}</LoginText>
                    <LoginText>{"현명한 선택"}</LoginText>
                  <LoginTextcontainer/>

                  <ButtonContainer>
                    <LoginButton
                      title="네이버 로그인"
                      onPress={() => naverLoginProcess()}
                      color="#19ce60"
                      />
                  </ButtonContainer>

                </BottomFlex>

                <BottomGap/>

            </InnerFlex>
        </ContentsFlex>

    </Container>
);
};
{/* {!!naverToken && (
  <Button title="회원정보 가져오기" onPress={getUserProfile} />
)} */}

{/* {!!naverToken && <Button title="로그아웃하기" onPress={naverLogout} />} */}

export default SignIn;


const Container = styled.View`
  flex: 1;
  align-items: center;
  background-color: white;
`

const TopGap = styled.View`
  flex: 0.7;
`
const LogoContainer = styled.View`
  flex: 1.5;
  margin-left: 10%;
  margin-top: 10%;
`
const LogoImg = styled.Image`
  flex: 1;
  resize-mode: contain;
`
const ContentsFlex = styled.View`
  flex: 6;
`
const InnerFlex = styled.View`
  flex: 1;
  align-items: center;
`
const BikeContainer = styled.View`
  flex: 5;
`
const BottomFlex = styled.View`
  flex: 4;
  align-items: center;
  width: 120%;
`
const LoginTextcontainer = styled.View`
  flex: 1;
`
const ButtonContainer = styled.View`
  flex: 1;
  width: 60%;
`
const BottomGap = styled.View`
  flex: 3;
`

const BikeImg = styled.Image`
  resize-mode: contain;
`
const LoginText = styled.Text`
  font-size: 20px;
  font-weight: bold;
`
const LoginButton = styled.Button`
`


// callback hell
// promise hell
// promise change
// Promise
// - 상태: pending / fulfilled / rejected
// - .then(thenable), .catch
// - async, await

// setTimeout, https://sculove.github.io/post/javascriptflow/
// https://jakearchibald.com/2015/tasks-microtasks-queues-and-schedules/
// https://ko.javascript.info/async

// 티스토리, 네이버 블로그, 스택 오버플로 => 실력이 안늠

// MDN, 자바스크립트 튜토리얼, 
// ECMASCRIPT(TC39) 
// 웹표준 W3C, WHATWG