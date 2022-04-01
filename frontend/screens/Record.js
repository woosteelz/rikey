import React, { useState, useEffect, useLayoutEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Platform,
  Button,
  Pressable,
  PermissionsAndroid,
} from 'react-native';
import MapView, {
  Marker,
  Polyline,
  PROVIDER_GOOGLE,
  PROVIDER_DEFAULT,
} from 'react-native-maps'; // remove PROVIDER_GOOGLE import if not using Google Maps
import Geolocation from 'react-native-geolocation-service';
import axios from 'axios';
import { useStore } from '../states';

const WEIGHT = 70;

// 칼로라 소비 계수 -> 몸무게(KG) X 1000 X kcalConsumption[속도] X 시간(분)
const kcalConsumption = [
  0.035, 0.065, 0.065, 0.065, 0.065, 0.065, 0.065, 0.065, 0.065, 0.065, 0.065,
  0.065, 0.065, 0.065, 0.0783, 0.0783, 0.0783, 0.0939, 0.0939, 0.0939, 0.113,
  0.113, 0.113, 0.124, 0.124, 0.136, 0.136, 0.149, 0.163, 0.163, 0.179, 0.179,
  0.196, 0.215, 0.215, 0.259, 0.259, 0.259, 0.311, 0.311, 0.311,
];

function isEmptyObj(obj) {
  if (obj.constructor === Object && Object.keys(obj).length === 0) {
    return true;
  }
  return false;
}

async function requestPermission() {
  try {
    if (Platform.OS === 'ios') {
      return await Geolocation.requestAuthorization('always');
    }
    if (Platform.OS === 'android') {
      return await PermissionsAndroid.request(
        PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION,
      );
    }
  } catch (e) {
    console.log(e);
  }
}

// 칼로리 계산
function getKcal(d, t, weight) {
  let speed = Math.round((d / t) * 3600);
  if (speed > 40) {
    speed = 40;
  }
  console.log(speed);
  console.log(d, t, kcalConsumption[speed], speed);
  return (weight * 1000 * kcalConsumption[speed] * t) / 60;
}

// 2개의 위도 경도 입력시 거리 반환(KM)
function getDIstance(prevLat, prevLng, currLat, currLng) {
  const toRad = x => (x * Math.PI) / 180;
  const R = 6371; // 지구 반지름

  const dLat = toRad(currLat - prevLat);
  const dLatSin = Math.sin(dLat / 2);
  const dLon = toRad(currLng - prevLng);
  const dLonSin = Math.sin(dLon / 2);

  const a =
    dLatSin * dLatSin +
    Math.cos(toRad(prevLng)) * Math.cos(toRad(currLng)) * dLonSin * dLonSin;
  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

  return R * c;
}

function Record({ navigation }) {
  const { userId } = useStore();

  const [granted, setGranted] = useState(false); // 승인 여부
  const [location, setLocation] = useState({}); // 현재 위치
  const [wayPoint, setWayPoint] = useState([]); // 주행 기록
  const [record, setRecord] = useState(false); // 기록 시작 / 중지
  const [kcal, setKcal] = useState(0); // 소비 칼로리
  const [distance, setDistance] = useState(0); // 주행 거리

  // stopwatch
  const [isActive, setIsActive] = useState(false);
  const [isPaused, setIsPaused] = useState(true);
  const [time, setTime] = useState(0);

  useEffect(() => {
    getMyPosition();
  }, []);

  // 위치기록 시작 / 중지 useEffect
  useEffect(() => {
    getMyPosition();
    let _watchId = null;

    if (record && !isPaused) {
      _watchId = Geolocation.watchPosition(
        position => {
          const { latitude, longitude } = position.coords;
          const dis = getDIstance(
            latitude,
            longitude,
            wayPoint[0].latitude,
            wayPoint[0].longitude,
          );
          // console.log('변경위치: ', latitude, longitude);
          console.log('거리, 시간', distance, time);
          setLocation({ latitude, longitude });
          setDistance(state => state + dis);
          setKcal(
            state => state + getKcal(distance, (time + 1) / 1000, WEIGHT),
          );
          setWayPoint(state => [{ latitude, longitude }, ...state]);
        },
        error => {
          console.log(error);
        },
        {
          enableHighAccuracy: true,
          distanceFilter: 0,
          interval: 500000,
          fastestInterval: 2000,
        },
      );
      return () => {
        Geolocation.clearWatch(_watchId);
      };
    }
  }, [record, isActive, isPaused]);

  // Stopwatch용 useEffect
  useEffect(() => {
    let interval = null;

    if (isActive && isPaused === false) {
      interval = setInterval(() => {
        setTime(time => time + 1000);
      }, 1000);
    } else {
      clearInterval(interval);
    }

    return () => {
      clearInterval(interval);
    };
  }, [isActive, isPaused]);

  // 내 위치정보 가져오기
  const getMyPosition = () => {
    requestPermission().then(res => {
      console.log(res);
      if (res === 'granted') {
        setGranted(true);
        Geolocation.getCurrentPosition(
          pos => {
            const { latitude, longitude } = pos.coords;
            console.log('내 위치', latitude, longitude);
            setLocation({ latitude, longitude });
            if (wayPoint.length === 0) {
              setWayPoint([{ latitude, longitude }]);
              console.log('웨이포인트', wayPoint);
            }
          },
          error => {
            console.log(error);
            console.log('내 위치를 불러올수 없습니다');
            setGranted(false);
          },
          { enableHighAccuracy: true, timeout: 3600, maximumAge: 3600 },
        );
      }
    });
  };

  // 위치기록 시작하기 + Stopwatch 시작
  const recordStart = () => {
    setRecord(true);
    setIsActive(true);
    setIsPaused(false);
  };

  // 위치기록 중단하기
  const recordStop = () => {
    axios({
      url: 'http://j6c208.p.ssafy.io/api/ridings',
      method: 'post',
      data: {
        userId,
        ridingTime: time / 3600000,
        ridingCalorie: Math.round(kcal * 10) / 10,
        ridingDist: Math.round(distance * 10) / 10,
        startTime: '2022-02-01 23:59:59.500',
        endTime: '2022-02-01 23:59:59.500',
      },
    })
      .then(res => {
        alert('주행 정보가 성공적으로 기록되었습니다');
        console.log(res);
      })
      .catch(err => {
        alert('등록에 실패하였습니다');
        console.log(err);
      });
    setRecord(false);
    setIsActive(false);
    setDistance(0);
    setTime(0);
    setKcal(0);
    setWayPoint([]);
  };

  // 위치기록 일시정지 / 재개
  const recordPause = () => {
    setIsPaused(!isPaused);
    // setRecord(!record);
  };

  // 로딩 중  화면
  if (!location) {
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Text>위치 정보를 불러오는 중입니다...</Text>
      </View>
    );
  }
  return (
    <View style={styles.container}>
      <View style={styles.mapContainer}>
        <MapView
          provider={Platform.OS === 'ios' ? PROVIDER_DEFAULT : PROVIDER_GOOGLE} // ios일 경우 apple map 사용
          showsUserLocation={true}
          showsMyLocationButton={true} // 현재위치 업데이트 버튼은 Google Map일 경우만 렌더링 됨
          style={styles.map}
          region={{
            latitude: !isEmptyObj(location) ? location.latitude : 37.510425,
            longitude: !isEmptyObj(location) ? location.longitude : 126.996236,
            latitudeDelta: 0.01,
            longitudeDelta: 0.01,
          }}>
          <Polyline
            coordinates={wayPoint} //specify our coordinates
            strokeColor={'#00C689'}
            strokeWidth={5}
          />
        </MapView>
      </View>
      <View style={styles.recordContainer}>
        <View style={{ flex: 1, marginVertical: 20 }}>
          <Text style={{ fontWeight: 'bold', alignSelf: 'center' }}>
            {('0' + Math.floor((time / 60000) % 60)).slice(-2)} :{' '}
            {('0' + Math.floor((time / 1000) % 60)).slice(-2)} /{' '}
            {Math.round((distance + Number.EPSILON) * 100) / 100}Km /{' '}
            {Math.round(kcal * 100) / 100} Kcal
          </Text>
          <View style={{ flexDirection: 'row' }}>
            <Pressable
              style={styles.button}
              onPress={record ? recordStop : recordStart}>
              <Text style={{ color: 'white', fontWeight: 'bold' }}>
                {record ? '기록 중단하기' : '기록 시작하기'}
              </Text>
            </Pressable>
            {record ? (
              <Pressable style={styles.button} onPress={recordPause}>
                <Text style={{ color: 'white', fontWeight: 'bold' }}>
                  {!isPaused ? '일시정지' : '재개'}
                </Text>
              </Pressable>
            ) : null}
          </View>
        </View>
      </View>
    </View>
  );
}
export default Record;

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  mapContainer: {
    flex: 3.5,
  },
  map: {
    ...StyleSheet.absoluteFillObject,
  },
  recordContainer: {
    flex: 1,
    marginBottom: 30,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'white',
  },
  button: {
    alignItems: 'center',
    justifyContent: 'center',
    paddingVertical: 12,
    paddingHorizontal: 32,
    borderRadius: 4,
    elevation: 3,
    backgroundColor: '#00C689',
    margin: 5,
  },
});
