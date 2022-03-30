import { Axios } from 'axios';
import React, { useState, useEffect, useLayoutEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  Image,
  TouchableOpacity,
} from 'react-native';
import axios from 'axios';

const logo = {
  uri: 'https://mblogthumb-phinf.pstatic.net/MjAxOTEyMTNfMjQz/MDAxNTc2MjM4NzE1ODEz.rJgwvkYWJR60irHgY6QlB68xKu-sxwKS4u3D0Jk83REg.zbDGJ1tGhwM1oucn_L1iKdqX5aJviN7zO8XqpL_T3uQg.PNG.daddybike/%EC%9E%90%EC%A0%84%EA%B1%B0%EA%B5%AD%ED%86%A0%EC%A2%85%EC%A3%BC.PNG?type=w800',
  width: 64,
  height: 64,
};

const Course = ({ navigation }) => {
  const [bikeCourse, setBikeCourse] = useState([]);

  const getCourse = () => {
    axios({
      url: 'http://j6c208.p.ssafy.io/api/bikeRoads',
      method: 'get',
      params: {
        latitude: 33.44166668453058,
        longitude: 126.28961433038387,
      },
    })
      .then(res => {
        console.log('success');
        console.log(res.data);
        setBikeCourse(res.data.bikeroadList);
        axios({
          url: `http://j6c208.p.ssafy.io/api/bikeRoads/${res.data.bikeroadList[0].bikeroadId}`,
          method: 'get',
        })
          .then(res => {
            console.log(res.data);
          })
          .catch(err => {
            console.log(err);
          });
      })
      .catch(err => {
        console.log(err);
      });
  };

  useLayoutEffect(() => {
    getCourse();
  }, []);

  return (
    <View style={styles.container}>
      <View
        style={{
          flex: 1,
          alignItems: 'center',
          justifyContent: 'center',
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
              <View style={{ flex: 1, flexDirection: 'column' }}>
                <TouchableOpacity
                  onPress={() =>
                    navigation.navigate('CourseDetail', { id: bc.bikeroadId })
                  }
                  key={bc.bikeroadId}
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
                    <Image source={logo} />
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
                        width: 25,
                        height: 25,
                      }}
                      resizeMode="contain"
                      source={require('../assets/icons/star.png')}
                    />
                    <Text style={{ fontSize: 21, margin: 3, color: '#024430' }}>
                      {bc.reviewCnt ? bc.score : 0}
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
});
