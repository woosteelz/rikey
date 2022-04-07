import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Platform,
  TouchableOpacity,
  Image,
} from 'react-native';
import MapView, {
  Marker,
  PROVIDER_GOOGLE,
  PROVIDER_DEFAULT,
  Polyline,
} from 'react-native-maps';
import {
  NativeBaseProvider,
  Select,
  Center,
  Box,
  CheckIcon,
} from 'native-base';

function isEmptyObj(obj) {
  if (obj.constructor === Object && Object.keys(obj).length === 0) {
    return true;
  }
  return false;
}

function CourseComport({ route, navigation }) {
  const detail = route.params.detail;
  const [location, setLocation] = useState({}); // 현재 위치

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
        <View
          style={{
            flexDirection: 'row',
            justifyContent: 'space-between',
            backgroundColor: 'white',
          }}>
          <TouchableOpacity
            style={{ alignSelf: 'center' }}
            onPress={() => navigation.goBack()}>
            <Image
              source={require('../assets/images/Back.png')}
              style={{
                resizeMode: 'contain',
                height: 20,
                width: 20,
                tintColor: 'black',
                marginLeft: 15,
              }}
            />
          </TouchableOpacity>
          <View style={{ alignSelf: 'center' }}>
            <Image
              style={{
                resizeMode: 'contain',
                height: 70,
                width: 140,
                marginLeft: -25,
              }}
              source={require('../assets/rikey.png')}
            />
          </View>
          <View>{null}</View>
        </View>
        <View style={styles.mapContainer}>
          <MapView
            provider={
              Platform.OS === 'ios' ? PROVIDER_DEFAULT : PROVIDER_GOOGLE
            } // ios일 경우 apple map 사용
            showsUserLocation={true}
            showsMyLocationButton={true} // 현재위치 업데이트 버튼은 Google Map일 경우만 렌더링 됨
            style={styles.map}
            region={{
              latitude: detail.centerList[1].latitude,
              longitude: detail.centerList[1].longitude,
              latitudeDelta: 1,
              longitudeDelta: 1,
            }}>
            {service === 'cvs'
              ? detail.cvsList.map((cvs, idx) => {
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
              ? detail.storeList.map((store, idx) => {
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
              ? detail.toiletList.map((toilet, idx) => {
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
              coordinates={detail.centerList}
              strokeColor={'#00C689'}
              strokeWidth={5}
            />
          </MapView>
          <SelectMenu />
        </View>
        <View style={{ flex: 1, height: 150 }}></View>
      </NativeBaseProvider>
    </View>
  );
}
export default CourseComport;

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  mapContainer: {
    flex: 9,
  },
  map: {
    ...StyleSheet.absoluteFillObject,
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
