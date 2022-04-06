import { Axios } from 'axios';
import React, { useState, useEffect, useLayoutEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  Image,
  TouchableOpacity,
  PermissionsAndroid,
} from 'react-native';
import axios from 'axios';

import Geolocation from 'react-native-geolocation-service';

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

const Course = ({ navigation }) => {
  const [bikeCourse, setBikeCourse] = useState([]);
  const [location, setLocation] = useState({}); // 현재 위치

  const getCourse = () => {
    requestPermission().then(res => {
      console.log(res);
      if (res === 'granted') {
        Geolocation.getCurrentPosition(
          pos => {
            const { latitude, longitude } = pos.coords;
            console.log('내 위치', latitude, longitude);
            setLocation({ latitude, longitude });
            axios({
              url: 'http://j6c208.p.ssafy.io/api/bikeRoads',
              method: 'get',
              params: {
                latitude,
                longitude,
              },
            })
              .then(res => {
                console.log('success');
                console.log(res.data);
                setBikeCourse(res.data.bikeroadList);
              })
              .catch(err => {
                console.log(err);
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
    getCourse();
  }, []);

  return (
    <View style={styles.container}>
      <View style={{ flexDirection: 'row', justifyContent: 'space-between' }}>
        <TouchableOpacity
          style={{ alignSelf: 'center' }}
          onPress={() => navigation.navigate('')}>
          <Image
            source={require('../assets/images/Back.png')}
            style={{
              resizeMode: 'contain',
              height: 20,
              width: 20,
              marginLeft: 20,
              tintColor: 'black',
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
      <View
        style={{
          flex: 1,
          alignItems: 'center',
          justifyContent: 'center',
          marginTop: -20,
        }}>
        <Text style={{ fontSize: 18 }}>내 주변 자전거길을 소개합니다!</Text>
      </View>
      <View style={{ flex: 7 }}>
        <ScrollView>
          {bikeCourse.length === 0 ? (
            <View style={{ alignSelf: 'center' }}>
              <Text>이런! 주변에 자전거 길이 없네요...</Text>
            </View>
          ) : (
            bikeCourse.map(bc => (
              <View
                key={bc.bikeroadId}
                style={{ flex: 1, flexDirection: 'column' }}>
                <TouchableOpacity
                  onPress={() =>
                    navigation.navigate('CourseDetail', { id: bc.bikeroadId })
                  }
                  style={{
                    flex: 1,
                    flexDirection: 'row',
                  }}>
                  <View
                    style={{
                      flex: 1,
                      margin: 3,
                      justifyContent: 'center',
                      alignItems: 'center',
                    }}>
                    <Image
                      source={{ uri: `${bc.image}`, width: 64, height: 64 }}
                    />
                  </View>
                  <View
                    style={{
                      flex: 3,
                      margin: 3,
                      justifyContent: 'flex-start',
                      alignItems: 'flex-start',
                    }}>
                    <Text
                      style={{ fontSize: 18, marginTop: 3, color: 'black' }}>
                      {bc.name}
                    </Text>
                    <Text
                      style={{ fontSize: 12, marginTop: 12, color: '#024430' }}>
                      리뷰 {bc.reviewCnt}개
                    </Text>
                  </View>
                  <View
                    style={{
                      flex: 1,
                      flexDirection: 'row',
                      margin: 3,
                      justifyContent: 'center',
                      alignItems: 'center',
                    }}>
                    <Image
                      style={{
                        width: 18,
                        height: 18,
                      }}
                      resizeMode="contain"
                      source={require('../assets/icons/star.png')}
                    />
                    <Text style={{ fontSize: 21, margin: 3, color: '#024430' }}>
                      {bc.reviewCnt ? Math.round(bc.score * 10) / 10 : 0}
                    </Text>
                    <Text style={{ marginRight: 20, color: '#024430' }}>
                      {' '}
                      / 5
                    </Text>
                  </View>
                </TouchableOpacity>
              </View>
            ))
          )}
        </ScrollView>
      </View>
    </View>
  );
};

export default Course;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
  communityButton2: {
    marginTop: '8%',
    width: '20%',
  },
});
