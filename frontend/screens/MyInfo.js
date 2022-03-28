import React from 'react';
import styled from 'styled-components';
import { ScrollView, KeyboardAvoidingView, TouchableOpacity } from 'react-native';

import Kirby from '../assets/images/Kirby.png'
import Black from '../assets/images/Black.png'
import Plus from '../assets/images/Plus.png'

const MyInfo = () => {
  return (

    <ScrollView>
      <KeyboardAvoidingView>
        <Box>

          <Profileflex>
            <ProfileImageBox>
              <ProfileImage source={Black}/>
              <TouchableOpacity>
                <PlusButton source={Plus}/>
              </TouchableOpacity>
            </ProfileImageBox>
          </Profileflex>

          <InputFlex>

            <InputBox>
              <InputLabel>닉네임</InputLabel>
              <InfoInput></InfoInput>
              <InfoBotLine></InfoBotLine>
            </InputBox>

            <InputBox>
              <InputLabel>소개</InputLabel>
              <InfoInput></InfoInput>
              <InfoBotLine></InfoBotLine>
            </InputBox>

            <InputBox>
              <InputLabel>지역</InputLabel>
              <InfoInput></InfoInput>
              <InfoBotLine></InfoBotLine>
            </InputBox>

            <InputBox>
              <InputLabel>키</InputLabel>
              <InfoInput></InfoInput>
              <InfoBotLine></InfoBotLine>
            </InputBox>

            <InputBox>
              <InputLabel>몸무게</InputLabel>
              <InfoInput></InfoInput>
              <InfoBotLine></InfoBotLine>
            </InputBox>

            <ButtonContainer>
              <ConfirmButton
                title="완료"
                color="#00C689"
              />
            </ButtonContainer>

          </InputFlex>

          <BottomFlex>
            <TouchableOpacity>
              <RedBtn>로그아웃</RedBtn>
            </TouchableOpacity>
            <TouchableOpacity>
              <RedBtn>회원탈퇴</RedBtn>
            </TouchableOpacity>
          </BottomFlex>

        </Box>

      </KeyboardAvoidingView>
    </ScrollView>
  )
}


export default MyInfo;

const Box = styled.View`
  flex: 1;
  justify-content: center;
  align-items: center;
  width:100%;
  height:100%;
` 

const Profileflex = styled.View`
  flex: 1;
  justify-content: center;
  align-items: center;
  width:100%;
  height:165px;
`

const ProfileImageBox = styled.View`
  justify-content: center;
  align-items: center;
  width: 50px;
  height: 50px;
`

const ProfileImage = styled.Image`
  width: 100px;
  height: 100px;
  border-radius: 50px;
`
const PlusButton = styled.Image`
  position: absolute;
  width: 25px;
  height: 25px;
  left: 40%;
  bottom: 40%;
  background-color: white;
  border-radius: 25px;
`

const InputFlex = styled.View`
  flex: 3.5;
  width : 85%;
  height : 450px;
  justify-content: center;
  align-items: center;
`
const InputBox = styled.View`
  flex: 1;
  width:100%;
  height: 100%;
  justify-content: center;
`
const InputLabel = styled.Text`
  top: 30%;
  margin-bottom: 5%;
  font-size: 13px;
`
const InfoInput = styled.TextInput`
  width: 50%;
  font-weight: bold;
  font-size: 15px;
`
const InfoBotLine = styled.View`
  border-top-width: 1px;
  bottom: 12%;
`
const ButtonContainer = styled.View`
  top: 5%;
  width: 100%;
`
const ConfirmButton = styled.Button`
`

const BottomFlex = styled.View`
  flex: 0.5;
  margin-top: 12%;
  margin-bottom: 30%;
  width:85%;
  height:130px;
  justify-content: space-around;
  align-items: flex-start;
`

const RedBtn = styled.Text`
  color: #EA3131;
  font-size: 15px;
  font-weight: bold;
`