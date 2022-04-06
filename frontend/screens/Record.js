import React, { useState, useEffect, useLayoutEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Platform,
  Button,
  Pressable,
  PermissionsAndroid,
  TouchableOpacity,
} from 'react-native';
import MapView, {
  Marker,
  Polyline,
  PROVIDER_GOOGLE,
  PROVIDER_DEFAULT,
} from 'react-native-maps'; // remove PROVIDER_GOOGLE import if not using Google Maps
import {
  NativeBaseProvider,
  Select,
  Center,
  Box,
  CheckIcon,
} from 'native-base';

import Geolocation from 'react-native-geolocation-service';
import axios from 'axios';
import { useStore } from '../states';

const WEIGHT = 70;

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

const getCurrTime = async () => {
  const today = new Date();
  const year = today.getFullYear();
  const month = ('0' + (today.getMonth() + 1)).slice(-2);
  const day = ('0' + today.getDate()).slice(-2);
  const hours = ('0' + today.getHours()).slice(-2);
  const minutes = ('0' + today.getMinutes()).slice(-2);
  const seconds = ('0' + today.getSeconds()).slice(-2);
  return (
    year +
    '-' +
    month +
    '-' +
    day +
    ' ' +
    hours +
    ':' +
    minutes +
    ':' +
    seconds +
    '.500'
  );
};

// 2개의 위도 경도 입력시 거리 반환(KM)
function getDistance(prevLat, prevLng, currLat, currLng) {
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

  const [startTime, setStartTime] = useState('');
  const [endTime, setEndTime] = useState('');

  // stopwatch
  const [isActive, setIsActive] = useState(false);
  const [isPaused, setIsPaused] = useState(true);
  const [time, setTime] = useState(0);

  const [toilets, setToilets] = useState([]);
  const [stores, setStores] = useState([]);
  const [cvss, setCvss] = useState([]);

  const [service, setService] = useState('');
  const [selected, setSelected] = useState([]);

  const setFacilities = item => {
    if (item === 'cvs') setSelected(cvss);
    if (item === 'toilet') setSelected(toilets);
    if (item === 'store') setSelected(stores);
  };

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
          const dis = getDistance(
            latitude,
            longitude,
            wayPoint[wayPoint.length - 1].latitude,
            wayPoint[wayPoint.length - 1].longitude,
          );

          if (dis >= 0.005) {
            const cal = (2.3 * WEIGHT) / 900;
            setKcal(state => state + cal);
          }

          setLocation({ latitude, longitude });
          setDistance(state => state + dis);

          setWayPoint(state => [...state, { latitude, longitude }]);
        },
        error => {
          console.log(error);
        },
        {
          enableHighAccuracy: true,
          distanceFilter: 0,
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
      // console.log(res);
      if (res === 'granted') {
        setGranted(true);
        Geolocation.getCurrentPosition(
          pos => {
            const { latitude, longitude } = pos.coords;
            // console.log('내 위치', latitude, longitude);
            setLocation({ latitude, longitude });
            if (wayPoint.length === 0) {
              setWayPoint([{ latitude, longitude }]);
              // console.log('웨이포인트', wayPoint);
            }
            axios({
              url: 'http://j6c208.p.ssafy.io/api/bikeRoads/facilities',
              method: 'get',
              params: {
                latitude,
                longitude,
              },
            })
              .then(res => {
                console.log(res.data);
                setToilets(res.data.toilets);
                setStores(res.data.stores);
                setCvss(res.data.cvss);
              })
              .catch(err => {
                console.log('에러발생', err);
              });
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

  const SelectMenu = () => {
    return (
      <Center>
        <Box w="3/4" maxW="250">
          <Select
            selectedValue={service}
            minWidth="90"
            accessibilityLabel="Choose Service"
            placeholder="편의시설 고르기"
            color="black"
            backgroundColor="white"
            _selectedItem={{
              bg: 'white',
              endIcon: <CheckIcon size="5" />,
            }}
            mt={1}
            onValueChange={itemValue => {
              setService(itemValue);
              setFacilities(itemValue);
            }}>
            <Select.Item label="편의점" value="cvs" />
            <Select.Item label="자전거 보관소" value="store" />
            <Select.Item label="화장실" value="toilet" />
          </Select>
        </Box>
      </Center>
    );
  };

  // 위치기록 시작하기 + Stopwatch 시작
  const recordStart = async () => {
    if (startTime === '') {
      try {
        const currTime = await getCurrTime();
        console.log(currTime);
        setStartTime(currTime);
      } catch (err) {
        console.log(err);
      }
    }
    setRecord(true);
    setIsActive(true);
    setIsPaused(false);
  };

  // 위치기록 중단하기
  const recordStop = async () => {
    try {
      const currTime = await getCurrTime();
      console.log(currTime);
      axios({
        url: 'http://j6c208.p.ssafy.io/api/ridings',
        method: 'post',
        data: {
          userId,
          ridingTime: time / 60000,
          ridingCalorie: Math.round(kcal * 10) / 10,
          ridingDist: Math.round(distance * 10) / 10,
          startTime,
          endTime: currTime,
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
    } catch (err) {
      console.log(err);
    }
    setStartTime('');
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
      <NativeBaseProvider>
        <View style={styles.mapContainer}>
          <MapView
            provider={
              Platform.OS === 'ios' ? PROVIDER_DEFAULT : PROVIDER_GOOGLE
            } // ios일 경우 apple map 사용
            showsUserLocation={true}
            showsMyLocationButton={true} // 현재위치 업데이트 버튼은 Google Map일 경우만 렌더링 됨
            followsUserLocation={true}
            style={styles.map}
            region={{
              latitude: !isEmptyObj(location) ? location.latitude : 37.510425,
              longitude: !isEmptyObj(location)
                ? location.longitude
                : 126.996236,
              latitudeDelta: 0.01,
              longitudeDelta: 0.01,
            }}>
            {service === 'cvs'
              ? cvss.map((cvs, idx) => {
                  return (
                    <TouchableOpacity>
                      <Marker
                        key={idx}
                        coordinate={{
                          latitude: cvs.latitude,
                          longitude: cvs.longitude,
                        }}
                        title={cvs.brandName + cvs.name}
                        description={cvs.roadAddress}
                      />
                    </TouchableOpacity>
                  );
                })
              : null}
            {service === 'store'
              ? stores.map((store, idx) => {
                  return (
                    <TouchableOpacity>
                      <Marker
                        key={idx}
                        coordinate={{
                          latitude: store.latitude,
                          longitude: store.longitude,
                        }}
                        title={store.name}
                        description={
                          store.airInjector
                            ? '공기 주입기 있음'
                            : '공기 주입기 없음'
                        }
                      />
                    </TouchableOpacity>
                  );
                })
              : null}
            {service === 'toilet'
              ? toilets.map((toilet, idx) => {
                  return (
                    <TouchableOpacity>
                      <Marker
                        key={idx}
                        coordinate={{
                          latitude: toilet.latitude,
                          longitude: toilet.longitude,
                        }}
                        title={toilet.name}
                        description={`운영시간 : ${toilet.openTime}`}
                      />
                    </TouchableOpacity>
                  );
                })
              : null}
            <Polyline
              coordinates={wayPoint} //specify our coordinates
              strokeColor={'#00C689'}
              strokeWidth={5}
            />
          </MapView>
          <SelectMenu />
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
      </NativeBaseProvider>
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
