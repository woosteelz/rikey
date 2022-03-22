import React, { useState, useEffect } from 'react';
import { useIsFocused } from '@react-navigation/native';
import { View, Text, Button, StyleSheet, Platform, Box } from 'react-native';
import MapView, { Marker, Polyline, PROVIDER_GOOGLE } from 'react-native-maps'; // remove PROVIDER_GOOGLE import if not using Google Maps
import Geolocation from 'react-native-geolocation-service';
import { Actionsheet, Center } from 'native-base';
import { useDisclose } from 'native-base';
import { useTheme } from 'native-base';

async function requestPermission() {
  try {
    if (Platform.OS === 'ios') {
      return await Geolocation.requestAuthorization('always');
    }
  } catch (e) {
    console.log(e);
  }
}

function Record({ navigation }) {
  const [granted, setGranted] = useState(false);
  const [location, setLocation] = useState({});
  const [wayPoint, setWayPoint] = useState([]);
  const [kcal, setKcal] = useState(0);

  const { isOpen, onOpen, onClose } = useDisclose();

  // 내 위치정보 가져오기
  const getMyPosition = () => {
    requestPermission().then(result => {
      if (result === 'granted') {
        setGranted(true);
        Geolocation.getCurrentPosition(
          pos => {
            const { latitude, longitude } = pos.coords;
            console.log('내 위치', latitude, longitude);
            setLocation({ latitude, longitude });
          },
          error => {
            console.log(error);
          },
          { enableHighAccuracy: true, timeout: 3600, maximumAge: 3600 },
        );
      } else {
        setGranted(false);
      }
    });
  };

  useEffect(() => {
    getMyPosition();
    const _watchId = Geolocation.watchPosition(
      position => {
        const { latitude, longitude } = position.coords;
        // console.log('변경위치: ', latitude, longitude);
        setLocation({ latitude, longitude });
        setWayPoint(state => [...state, { latitude, longitude }]);
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
      if (_watchId) {
        Geolocation.clearWatch(_watchId);
      }
    };
  }, []);

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
      <MapView
        // provider={PROVIDER_GOOGLE} //
        showsUserLocation={true}
        showsMyLocationButton={true} // 현재위치 업데이트 버튼은 Google Map일 경우만 렌더링 됨
        style={styles.map}
        region={{
          latitude: location.latitude,
          longitude: location.longitude,
          latitudeDelta: 0.015,
          longitudeDelta: 0.0121,
        }}>
        <Polyline
          coordinates={wayPoint} //specify our coordinates
          strokeColor={'#000'}
          strokeWidth={1}
          lineDashPattern={[0]}
        />
        {/* <Marker
          coordinate={{ latitude: 37.504449, longitude: 127.04886 }}
          // image={require('../assets/icons/curr_location.png')}
          title="this is a marker"
          pinColor="blue"
          description="this is a marker example"
        /> */}
      </MapView>
    </View>
  );
}
export default Record;

const styles = StyleSheet.create({
  container: {
    // flex: 1,
    ...StyleSheet.absoluteFillObject,
    height: '90%',
    width: '100%',
    justifyContent: 'flex-end',
    alignItems: 'center',
  },
  map: {
    ...StyleSheet.absoluteFillObject,
  },
});
