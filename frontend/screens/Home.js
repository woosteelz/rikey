import React, { useEffect, useState } from 'react';
import { View, Text, Button, ScrollView } from 'react-native';
import { useStore } from '../states';
import Geolocation from 'react-native-geolocation-service';
import axios from 'axios';
import styled from 'styled-components';

const Home = ({ navigation }) => {
  
  const [weather, setWeather] = useState('맑음');
  const [temp, setTemp] = useState('');
  const [humidity, setHumidity] = useState('');
  const [windSpeed, setWindSpeed] = useState('');
  const [clouds, setClouds] = useState('');
  
  const [dayNight, setDayNight] = useState('');
  const [hours, setHours] = useState('');
  const [minutes, setMinutes] = useState('');
  const [year, setYear] = useState('');
  const [month, setMonth] = useState('');
  const [day, setDay] = useState('');

  const API_KEY = "4ed0ebc28fd6064863095c9cd2c107e1"

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
      console.log(response);
      setWeather(response.data.weather[0].description);
      setTemp(response.data.main.temp);
      setHumidity(response.data.main.humidity);
      setWindSpeed(response.data.wind.speed);
      setClouds(response.data.clouds.all);
    }
    catch(e) {
    }
  }

  const date = new Date();
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
  // setInterval(getClock, 1000);

  useEffect(() => {
    getWeather();
    getClock();
    // console.log(weather, "날씨")
    // console.log(temp, "온도")
    // console.log(humidity, "습도")
    // console.log(windSpeed, "바람")
    // console.log(clouds, "구름")
  }, [])

  const { userId } = useStore();


  return (
    
    <ScrollView>
      <Box>

        <WeatherBox>
          <TopBox>
            <Text> {year}년 {month}월 {day}일 {dayNight} {hours}시 {minutes}분 </Text>
            <Text> 날씨 : {weather} </Text>
          </TopBox>

          <BottomBox>
            <Text>{temp}</Text>
            <Text>{humidity}</Text>
            <Text>{windSpeed}</Text>
            <Text>{clouds}</Text>
          </BottomBox>

        </WeatherBox>

        <SafeBox>
          <TitleBox></TitleBox>
          <ImageBox></ImageBox>
          <DetailBox></DetailBox>
        </SafeBox>

        {/* <Button
          title="click"
          onPress={() => {
            navigation.navigate('Profile');
          }}
        /> */}
      </Box>
    </ScrollView>

  );
};

export default Home;

const Box = styled.View`
  flex: 1;
`
const WeatherBox = styled.View`
`
const TopBox = styled.View`
`
const BottomBox = styled.View`
`
const SafeBox = styled.View`
`
const TitleBox = styled.View`
`
const ImageBox = styled.View`
`
const DetailBox = styled.View`
`