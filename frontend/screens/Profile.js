import React, { useEffect, useState } from 'react';
import { TouchableOpacity, Image } from 'react-native';
import styled from 'styled-components';
import WeeklyInnerBox from '../components/Profile/WeeklyInnerBox';
import MyHistory from '../components/Profile/MyHistory';

import Edit from '../assets/images/Edit.png'
import Fire from '../assets/images/Fire.png'
import Bicycle from '../assets/images/Bicycle.png'
import Clock from '../assets/images/Clock.png'

import MyPencil from '../assets/images/MyPencil.png'
import MyComment from '../assets/images/MyComment.png'
import MyReview from '../assets/images/MyReview.png'
import MyRidingRecord from '../assets/images/MyRidingRecord.png'
import RightArrow from '../assets/images/RightArrow.png'

import API from '../api/API'
import { useStore } from '../states';

const Profile = ({ navigation }) => {
  const [nickName, setNickName] = useState("무면허 라이더");
  const [greeting, setGreeting] = useState("안녕하세요!");
  const [area, setArea] = useState("서울");
  const [getImage, setGetImage] = useState('https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/4arX/image/FToC1jQw1U0mAhJYnEmTPg7ZQD8.jpg');
  const [defaultImage, setDefaultImage] = useState('https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/4arX/image/FToC1jQw1U0mAhJYnEmTPg7ZQD8.jpg');

  const [kcal, setKcal] = useState("0")
  const [distance, setdistance] = useState("0")
  const [time, setTime] = useState("0")
  const [moreHour, setMoreHour] = useState('');
  const [moreMinute, setMoreMinute] = useState('');
  const { userNickName } = useStore();

  useEffect(() => {
    API.get(`/users/${userNickName}`)
    .then((response) => {
      setNickName(response.data.user.nickName)
      setGreeting(response.data.user.greeting)
      setArea(response.data.user.area.name)

      setKcal(response.data.user.weeklyCalories)
      setdistance(response.data.user.weeklyDistance)
      const theHour = response.data.user.weeklyTime / 60
      const theMinute = response.data.user.weeklyTime % 60
      setMoreHour(theHour);
      setMoreMinute(theMinute);
      setTime(response.data.user.weeklyTime)
      setGetImage(response.data.user.profilePic)
    })
  })

  return (

    <TheBox>
      <TopBox>
        <UpperBox>

          <ProfileImgBox>
            <ProfileBackBox>
              <ProfileImage source={ getImage ? { uri : getImage } : { uri : defaultImage }}/>
            </ProfileBackBox>
          </ProfileImgBox>

          <EditBox>
            <TouchableOpacity
              onPress={() => navigation.navigate("MyInfo")}
            >
              <ProfileEditImage source={Edit}/>
            </TouchableOpacity>
          </EditBox>

        </UpperBox>

        <ProfileDetailBox>
          <NickNameText>{nickName}</NickNameText>
          <InstructionText>{greeting}</InstructionText>
          <AreaText>{area}</AreaText>
        </ProfileDetailBox>

      </TopBox>
      <BotBox>
        
        <WeeklyBox>
          <WeeklyInnerBox title={"누적 칼로리"} imageLink={Fire} measurand={`${kcal} kcal`} />
          <WeeklyInnerBox title={"누적 거리"} imageLink={Bicycle} measurand={`${distance} km`}/>
          <WeeklyInnerBox title={"누적 시간"} imageLink={Clock} measurand={time > 60 ? `${moreHour}시간 ${moreMinute}분` : `${time} 분`} />
        </WeeklyBox>

        <MyBox>
          <MyInnerBox>
            <MyHistory content={"나의 주행 기록"} logo={MyRidingRecord} arrow={RightArrow} navigation={navigation} location={"MyRecords"} />
            <MyHistory content={"내가 쓴 글"} logo={MyPencil} arrow={RightArrow} navigation={navigation} location={"MyArticles"} />
            <MyHistory content={"내가 쓴 댓글"} logo={MyComment} arrow={RightArrow} navigation={navigation} location={"MyComments"} />
            <MyHistory content={"내가 쓴 코스 후기"} logo={MyReview} arrow={RightArrow} navigation={navigation} location={"MyReviews"} />
          </MyInnerBox>
        </MyBox>

      </BotBox>

    </TheBox>
    
  )
}

export default Profile;

const TheBox = styled.View`
  flex: 1;
  justify-content: center;
  align-items: center;
`
const TopBox = styled.View`
  flex: 1.3;
  justify-content: center;
  align-items: center;
  background-color : #EFF1F6;
  width: 82.5%;
  border-radius: 20px;
`
const UpperBox = styled.View`
  flex:1;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  margin-top: 2.5%;
`
const ProfileImgBox = styled.View`
  flex: 3;
  justify-content: center;
  align-items: center;
  margin-left: 22%;
  margin-top: 6%;
`
const ProfileBackBox = styled.View`
  justify-content: center;
  align-items: center;
  background-color: white;
  border-radius: 50px;
  width : 65px;
  height: 65px;
`
const ProfileImage = styled.Image`
  width: 60px;
  height: 60px;
  border-radius: 50px;
`
const EditBox = styled.View`
  flex: 1;
  flex-direction: row;
  justify-content: flex-end;
  margin-right: 4.5%;
  margin-bottom: 13.5%;
`
const ProfileEditImage = styled.Image`
  width: 15px;
  height: 15px;
`
const ProfileDetailBox = styled.View`
  flex: 1.2;
  justify-content: center;
  align-items: center;
  margin-bottom: 3%;
`
const NickNameText = styled.Text`
  font-weight: bold;
  color: black;
  font-size: 20px;
  margin-top: 1.5%;
`
const InstructionText = styled.Text`
  color: black;
  font-size: 15px;
`
const AreaText = styled.Text`
  font-size: 13px;
  color: #979797;
  margin-top: 1%;
`
const BotBox = styled.View`
  flex: 3.5;
  margin-top: 5%;
`
const WeeklyBox = styled.View`
  flex: 1.5;
  flex-direction: row;
  width: 85%;
  margin-top: 3.5%;
  justify-content: space-around;
`
const MyBox = styled.View`
  flex: 4.5;
  justify-content: center;
  align-items: center;
`
const MyInnerBox = styled.View`
  margin-bottom: 38%
  margin-top: 10%;
  flex: 1;
  justify-content: center;
  align-items: center;
`