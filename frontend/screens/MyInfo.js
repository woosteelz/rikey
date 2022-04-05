import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { ScrollView, KeyboardAvoidingView, TouchableOpacity } from 'react-native';
import { NativeBaseProvider, Select, Center, Box, CheckIcon } from "native-base";

import Kirby from '../assets/images/Kirby.png'
import Black from '../assets/images/Black.png'
import Plus from '../assets/images/Plus.png'

import MultipleImagePicker from '@baronha/react-native-multiple-image-picker';
import API from './../api/API'
import { useStore } from '../states';
import MyInfoInputBox from '../components/Profile/MyInfoInputBox';
const MyInfo = ({ navigation }) => {
  const [nickName, setNickName] = useState('');
  const [greeting, setGreeting] = useState('');
  const [area, setArea] = useState('');
  const [height, setHeight] = useState('');
  const [weight, setWeight] = useState('');
  const [image, setImage] = useState('');

  const [defaultImage, setDefaultImage] = useState('https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/4arX/image/FToC1jQw1U0mAhJYnEmTPg7ZQD8.jpg');

  const { userId, userNickName, setUserNickName, setUserArea } = useStore();

  const editProfile = () => {
    API.put('/users', {
			"userId" : userId,
      "nickName" : nickName,
      "greeting" : greeting,
      "area" : area,
      "profilePic" : image,
      "height" : height,
      "weight" : weight,
    })
    .then(() => {
      setUserNickName(nickName);
      setUserArea(area);
      navigation.navigate("Profile")
    })
  }

  const sendImages = async() => {
    try {
      let formData = new FormData();
      const response = await MultipleImagePicker.openPicker({
        mediaType: "image",
        usedCameraButton: true,
        isExportThumbnail: true,
        maxSelectedAssets: 1,
        selectedAssets: 1,
        doneTitle: '완료',
        cancelTitle: '취소',
        tapHereToChange: '변경하려면 여기를 누르세요.',
        singleSelectedMode: true,
      })
      formData.append("uploadFile", {
        uri: 'file://' + response.realPath,
        type: response.mime,
        name: response.fileName,
      });
      // API.post('/users/upload', {
      //   "uploadFile":formData,
      //   headers : {
      //     'Content-Type' : 'multipart/form-data'
      //   }
      // })
      // console.log(formData);
      fetch('http://j6c208.p.ssafy.io/api/users/upload',{
        method : "post" ,
        body: formData
      })
      .then(res => res.json())
      .then(res => {
        console.log(res)
        setImage(res.url)
      })
      .catch((error) => {
        console.log(error)
      });
      
    } catch (e) {
      console.log(e);
    }
  }

  const SignOut = () => {
    navigation.navigate('SignIn')
  }

  const ReSign = () => {
    API.delete('/users', {
      data: {
        "userId" : userId
      }
    })
    .then((response) => {
      console.log(response);
      alert("회원 정보가 삭제되었습니다.")
      navigation.navigate("SignIn")
    })
    .catch((error) => {
      console.log(error, "회원탈퇴");
      console.log(userId);
    })
  }

  // 기본 정보값
  useEffect(() => {
    API.get(`/users/${userNickName}`)
    .then((response) => {
      setNickName(response.data.user.nickName);
      setGreeting(response.data.user.greeting)
      setImage(response.data.user.profilePic)
      setHeight(response.data.user.height)
      setWeight(response.data.user.weight)
    })
  }, [])

  return (

    <ScrollView>
      <KeyboardAvoidingView>
        <NativeBaseProvider>
          <ContainerBox>

            <Profileflex>
              <ProfileImageBox>
                {/* <ProfileImage source={{ uri : image }} /> */}
                <ProfileImage source={ image ? { uri : image } : { uri : defaultImage }} />
                <TouchableOpacity
                  onPress={() => sendImages()}
                >
                  <PlusButton source={Plus}/>
                </TouchableOpacity>
              </ProfileImageBox>
            </Profileflex>

            <InputFlex>
              <MyInfoInputBox title={"닉네임"} setState={setNickName} value={nickName} />
              <MyInfoInputBox title={"소개"} setState={setGreeting} value={greeting} />
              <InputBox>
                <InputLabel>지역을 선택해 주세요.</InputLabel>
                  <Select selectedValue={area} minWidth="120" height="50" marginTop="5%" borderaccessibilityLabel="지역" placeholder="지역" 
                  _selectedItem={{
                    bg: "teal.600",
                    endIcon: <CheckIcon size="5" />
                  }} mt={1} onValueChange={itemValue => setArea(itemValue)}>
                    
                    <Select.Item label="경기" value="GYEONGGI" />
                    <Select.Item label="경남" value="GYEONGNAM" />
                    <Select.Item label="경북" value="GYEONGBUK" />
                    <Select.Item label="광주" value="GWANGJU" />
                    <Select.Item label="대구" value="DAEGU" />
                    <Select.Item label="대전" value="DAEJEON" />
                    <Select.Item label="부산" value="BUSAN" />
                    <Select.Item label="서울" value="SEOUL" />
                    <Select.Item label="세종" value="SEJONG" />
                    <Select.Item label="울산" value="ULSAN" />
                    <Select.Item label="인천" value="INCHEON" />
                    <Select.Item label="전남" value="JEONNAM" />
                    <Select.Item label="전북" value="JEONBUK" />
                    <Select.Item label="제주" value="JEJU" />
                    <Select.Item label="충남" value="CHUNGNAM" />
                    <Select.Item label="충북" value="CHUNGBUK" />

                  </Select>
              </InputBox>
              <MyInfoInputBox title={"키"} setState={setHeight} value={String(height)} />
              <MyInfoInputBox title={"몸무게"} setState={setWeight} value={String(weight)} />


              <ButtonContainer>
                <ConfirmButton
                  title="완료"
                  color="#00C689"
                  onPress={() => editProfile()}
                />
              </ButtonContainer>
            </InputFlex>

            <BottomFlex>
              <TouchableOpacity>
                <RedBtn
                  onPress={() => SignOut()}
                >로그아웃</RedBtn>
              </TouchableOpacity>
              <TouchableOpacity>
                <RedBtn
                  onPress={() => ReSign()}
                >회원탈퇴</RedBtn>
              </TouchableOpacity>
            </BottomFlex>

          </ContainerBox>
        </NativeBaseProvider>
      </KeyboardAvoidingView>
    </ScrollView>
  )
}

export default MyInfo;

const ContainerBox = styled.View`
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
  width: 20px;
  height: 20px;
  left: 40%;
  bottom: 40%;
  background-color: white;
  border-radius: 25px;
`
const InputFlex = styled.View`
  flex: 3.5;
  width : 85%;
  justify-content: center;
  align-items: center;
`
const InputBox = styled.View`
  flex: 1;
  width:100%;
  height: 100%;
  justify-content: center;
  margin-bottom: 10%;
`
const InputLabel = styled.Text`
  margin-bottom: 5%;
  font-size: 13px;
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