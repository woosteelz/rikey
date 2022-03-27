import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import WeeklyInnerBox from '../components/Profile/WeeklyInnerBox';
import MyHistory from '../components/Profile/MyHistory';

import Kirby from '../assets/images/Kirby.png'
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

const Profile = () => {

  const [nickName, setNickName] = useState("무면허 라이더");
  const [instruction, setInstruction] = useState("안녕하세요!");
  const [area, setArea] = useState("서울");

  const [kcal, setKcal] = useState("0 kcal")
  const [distance, setdistance] = useState("0 km")
  const [time, setTime] = useState("0시간")

  const { userNickName } = useStore();

  useEffect(() => {
    API.get(`/users/${userNickName}`)
    .then((response) => {
      setNickName(response.data.user.nickName)
      setInstruction(response.data.user.greeting)
      setArea(response.data.user.area.name)

      setKcal(response.data.user.weeklyCalories)
      setdistance(response.data.user.weeklyDistance)
      setTime(response.data.user.weeklyTime)
    })
  })

  return (

    <TheBox>

      <TopBox>
        <ProfileImgBox>
          <ProfileImage source={Kirby}/>
        </ProfileImgBox>

        <ProfileDetailBox>
          <NickNameText>{nickName}</NickNameText>
          <InstructionText>{instruction}</InstructionText>
          <AreaText>{area}</AreaText>
        </ProfileDetailBox>
      </TopBox>

      <BotBox>

        <WeeklyBox>
          <WeeklyInnerBox title={"누적 칼로리"} imageLink={Fire} measurand={`${kcal} kcal`} />
          <WeeklyInnerBox title={"누적 거리"} imageLink={Bicycle} measurand={`${distance} km`}/>
          <WeeklyInnerBox title={"누적 시간"} imageLink={Clock} measurand={`${time} 분`} />
        </WeeklyBox>

        <MyBox>

          <MyInnerBox>
            <MyHistory content={"내가 쓴 글"} logo={MyPencil} arrow={RightArrow}/>
            <MyHistory content={"내가 쓴 댓글"} logo={MyComment} arrow={RightArrow} />
            <MyHistory content={"내가 쓴 코스 후기"} logo={MyReview} arrow={RightArrow} />
            <MyHistory content={"나의 주행 기록"} logo={MyRidingRecord} arrow={RightArrow} />
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
  flex: 1.2;
  justify-content: center;
  align-items: center;
  background-color : #EFF1F6;
  width: 82.5%;
  border-radius: 20px;
`
const ProfileImgBox = styled.View`
  flex : 1;
  justify-content: center;
  align-items: center;
  width: 25%;
  height: 25%;
  background-color: white;
  border-radius: 50px;
`
const ProfileImage = styled.Image`
  flex: 1;
  resize-mode: contain;
  margin-top: 10%;
`
const ProfileDetailBox = styled.View`
  flex: 1.2;
  justify-content: center;
  align-items: center;
`
const NickNameText = styled.Text`
  font-weight: bold;
  color: black;
  font-size: 20px;
  margin-top: 1.5%;
`
const InstructionText = styled.Text`
  font-weight: bold;
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