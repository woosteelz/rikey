import React, { useEffect, useState } from 'react';
import { View, Text, Button, ScrollView, Image } from 'react-native';
import Geolocation from 'react-native-geolocation-service';
import axios from 'axios';
import styled from 'styled-components';

import ClearSky from '../assets/images/ClearSky.png'
import TemperImg from '../assets/images/TemperImg.png'
import HumidImg from '../assets/images/HumidImg.png'
import WindImg from '../assets/images/WindImg.png'
import CloudImg from '../assets/images/CloudImg.png'
import ExclamationMark from '../assets/images/ExclamationMark.png'
import RidingMan from '../assets/images/RidingMan.png'

const Home = ({ navigation }) => {

  const API_KEY = "4ed0ebc28fd6064863095c9cd2c107e1"
  const [weather, setWeather] = useState('맑음');
  const [temp, setTemp] = useState('');
  const [humidity, setHumidity] = useState('');
  const [windSpeed, setWindSpeed] = useState('');
  const [clouds, setClouds] = useState('');
  const [icon, setIcon] = useState('');
  
  const [dayNight, setDayNight] = useState('');
  const [hours, setHours] = useState('');
  const [minutes, setMinutes] = useState('');
  const [year, setYear] = useState('');
  const [month, setMonth] = useState('');
  const [day, setDay] = useState('');
  const date = new Date();

  const weekday = date.getDay();
  const safetyInstructions = [
    ["안전모 착용하기", "자전거 운전자 및 동승자는 반드시 안전모 착용"],
    ["자전거 음주운전 금지","음주 후 자전거를 운행하면 도로교통법에 의해 처벌"],
    ["야간 운행 시 라이트 켜기", "사고 예방을 위해 야간 전조등과 후미등 장착"],
    ["주행 시 휴대전화, 이어폰 사용하지 않기", "주변 상황을 둘러보지 못해 사고 위험이 큼"],
    ["자전거 도로 주행은 이렇게!", "자전거도로가 설치된 경우 자전거 도로로, 자전거 도로가 없는 경우 도로 우측 가장자리에 붙어서 이용"],
    ["2대 이상 나란히 차도를 통행하지 않기", "안전표지로 통행이 허용된 경우는 제외"],
    ["안전한 전기자전거 운행", "일정한 요건을 갖춘 전기자전거만 자전거도로 통행 허용"]
  ]
  const [safetyTitle, safetyContent] = safetyInstructions[weekday]


  const getLocation = () => {
    return new Promise((resolve, reject) => {
      Geolocation.getCurrentPosition(
        (response) => {
          const latitude = response.coords.latitude;
          const longitude = response.coords.longitude;
          const val = `https://api.openweathermap.org/data/2.5/weather?lat=${latitude}&lon=${longitude}&appid=${API_KEY}&units=metric`
          resolve(val)
        },
        (fail) => {
          console.log(fail);
          reject()
        }
    )})
  }
  const getWeather = async () => {
    try {
      const url = await getLocation();
      const response = await axios.get(url)
      setIcon(response.data.weather[0].icon);
      setWeather(response.data.weather[0].description);
      setTemp( Math.ceil(response.data.main.temp * 10) / 10);
      setHumidity(response.data.main.humidity);
      setWindSpeed( Math.ceil(response.data.wind.speed * 10) / 10 );
      setClouds(response.data.clouds.all);
      // 아이콘 이미지 받아오기 구현
    }
    catch(e) {
    }
  }
  const getClock = () => {
    setYear(date.getFullYear());
    setMonth(date.getMonth() + 1);
    setDay(date.getDate());

    const totalHour = date.getHours();
    const theDayNight = totalHour < 12 ? "오전" : "오후"
    const theHour = totalHour > 12 ? totalHour - 12 : totalHour

    setDayNight(theDayNight);
    setHours(theHour);
    setMinutes(date.getMinutes());
  }

  useEffect(() => {
    getWeather();
    getClock();
  }, [])

  return (
    
    <ScrollView>
      <Box>

        <WeatherBox>
          <TopBox>
            <WeatherImage source={{ uri : `http://openweathermap.org/img/wn/${icon}@2x.png` }} />
            {/* <WeatherImage source={ClearSky} /> */}
            {/* <Image source={ClearSky} /> */}
            {/* <Text>{weather}</Text> */}
            <WeatherTextBox>
              <WeatherText>{year}년 {month}월 {day}일</WeatherText>
              <WeatherText>{dayNight} {hours}시 {minutes}분 </WeatherText>
            </WeatherTextBox>
          </TopBox>

          <BottomBox>

            <GapBox />

            <RowBox>
              <DescriptionImageBox>
                <DescriptionImage source={TemperImg} />
              </DescriptionImageBox>
              <DescriptionText>기온</DescriptionText>
              <DescriptionText>{temp}ºC</DescriptionText>
            </RowBox>

            <RowBox>
              <DescriptionImageBox>
                <DescriptionImage source={HumidImg} />
              </DescriptionImageBox>
              <DescriptionText>습도</DescriptionText>
              <DescriptionText>{humidity}%</DescriptionText>
            </RowBox>
            
            <RowBox>
              <DescriptionImageBox>
                <DescriptionImage source={WindImg} />
              </DescriptionImageBox>
              <DescriptionText>풍속</DescriptionText>
              <DescriptionText>{windSpeed}m/s</DescriptionText>
            </RowBox>

            <RowBox>
              <DescriptionImageBox>
                <DescriptionImage source={CloudImg} />
              </DescriptionImageBox>
              <DescriptionText>구름</DescriptionText>
              <DescriptionText>{clouds}%</DescriptionText>
            </RowBox>

            <GapBox />

          </BottomBox>

        </WeatherBox>

        <SafeBox>

          <TitleBox>
            <Image source={ExclamationMark} />

            <TitleTextBox>
              <TitleText><RIKEYText>RIKEY</RIKEYText>와 함께하는</TitleText>
              <TitleText>오늘의 자전거 안전 수칙</TitleText>
            </TitleTextBox>

            <RiderView>
              <RiderImage source={RidingMan} />
            </RiderView>
          </TitleBox>

          <InstructBox>
            <InstructTitle>{safetyTitle}</InstructTitle>
            <InstructDetail>{safetyContent}</InstructDetail>
          </InstructBox>

        </SafeBox>

      </Box>
    </ScrollView>

  );
};

export default Home;

const Box = styled.View`
  flex: 1;
  background-color: white;
`

const WeatherBox = styled.View`
  flex: 1;
  margin-top: 5%;
  justify-content : center;
  align-items: center;
`
const WeatherText = styled.Text`
  color: #979797;
  font-weight: bold;
  font-size: 15px;
  color: black;
`

const TopBox = styled.View`
  flex: 1;
  justify-content : center;
  align-items: center;
`
const WeatherImage = styled.Image`
  width: 55px;
  height: 55px;
`
const WeatherTextBox = styled.View`
  margin-top: 2.5%;
  justify-content : center;
  align-items: center;
`
const BottomBox = styled.View`
  flex: 1;
  flex-direction : row;
  margin-top: 5%;
  background-color: #EFF1F6;
  border-radius: 20px;
  width: 80%;
  height: 120px;
`
const GapBox = styled.View`
  flex: 0.2;
`
const RowBox = styled.View`
  flex: 1;
  justify-content : center;
  align-items: center;
`
const DescriptionImageBox = styled.View`
  width:  50px;
  height: 50px;
  justify-content : center;
  align-items: center;
`
const DescriptionImage = styled.Image`
  resize-mode: contain;
  height: 65%;
  width: 65%;
`
const DescriptionText = styled.Text`
  font-weight: bold;
  color : black;
`

const SafeBox = styled.View`
  flex: 1.4;
  margin-top: 8%;
`
const TitleBox = styled.View`
  justify-content : center;
  align-items: center;
`
const TitleTextBox = styled.View`
  margin-top: 3%;
  justify-content : center;
  align-items: center;
`
const RIKEYText = styled.Text`
  color : #03B190;
`
const TitleText = styled.Text`
  font-weight: bold;
  font-size: 20px;
  color: black;
`
const RiderView = styled.View`
  margin-top: 5%;
  height: 215px;
  width: 350px;
`
const RiderImage = styled.Image`
  resize-mode: contain;
  height: 100%;
  width: 100%;
  border-radius: 35px;
`
const InstructBox = styled.View`
  margin-top: 5%;
  margin-bottom: 30%;
  justify-content : center;
  align-items: center;
`
const InstructTitle = styled.Text`
  font-weight: bold;
  color: black;
  font-size: 16px;
`
const InstructDetail = styled.Text`
  margin-top: 3%;
  width: 85%;
  text-align: center;
  font-weight: bold;
  font-size: 15px;
  color: black;
`