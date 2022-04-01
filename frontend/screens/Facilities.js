import React, { useState, useEffect, useLayoutEffect } from 'react';
import {
  View,
  Text,
  Button,
  StyleSheet,
  TouchableOpacity,
  Image,
  PermissionsAndroid,
} from 'react-native';

import {
  NativeBaseProvider,
  Select,
  Center,
  Box,
  CheckIcon,
} from 'native-base';

import MapView, { Marker, Polyline, PROVIDER_GOOGLE } from 'react-native-maps';
import Geolocation from 'react-native-geolocation-service';

import axios from 'axios';

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

const Facilities = ({ navigation }) => {
  const [location, setLocation] = useState({});

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

  const getMyPosition = () => {
    requestPermission().then(res => {
      console.log(res);
      if (res === 'granted') {
        Geolocation.getCurrentPosition(
          pos => {
            const { latitude, longitude } = pos.coords;
            console.log('내 위치', latitude, longitude);
            setLocation({ latitude, longitude });
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

  useEffect(() => {
    getMyPosition();
  }, []);

  return (
    <View style={styles.container}>
      <NativeBaseProvider>
        <View
          style={{
            flex: 0.75,
            flexDirection: 'row',
            justifyContent: 'flex-start',
          }}>
          <TouchableOpacity
            style={{ alignSelf: 'center' }}
            onPress={() => navigation.navigate('Course')}>
            <Text style={{ marginLeft: 20 }}> ← 뒤로 </Text>
          </TouchableOpacity>
          <Image
            style={{
              resizeMode: 'cover',
              height: 80,
              width: 160,
              marginLeft: 55,
            }}
            source={require('../assets/rikey.png')}
          />
        </View>
        <View style={{ flex: 7 }}>
          <MapView
            provider={PROVIDER_GOOGLE} //
            showsUserLocation={true}
            showsMyLocationButton={true} // 현재위치 업데이트 버튼은 Google Map일 경우만 렌더링 됨
            style={styles.map}
            region={{
              latitude: !isEmptyObj(location) ? location.latitude : 37.566535,
              longitude: !isEmptyObj(location)
                ? location.longitude
                : 126.9779683,
              latitudeDelta: 0.02,
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
            {/* <Marker
              coordinate={{
                latitude: location.latitude,
                longitude: location.longitude,
              }}
              title="this is a marker"
              description="this is a marker example"
            /> */}
          </MapView>
          <SelectMenu />
        </View>
        <View style={{ flex: 0.7 }}></View>
      </NativeBaseProvider>
    </View>
  );
};

export default Facilities;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
  mapContainer: {
    flex: 3.5,
  },
  map: {
    ...StyleSheet.absoluteFillObject,
  },
});
