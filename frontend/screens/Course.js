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
        latitude: 37.504449335359766,
        longitude: 126.98076992442245,
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
  };

  useEffect(() => {
    getCourse();
  }, []);

  return (
    <View style={styles.container}>
      <View style={{ flexDirection: 'row', justifyContent: 'space-around' }}>
        <TouchableOpacity
          style={styles.communityButton2}
          onPress={() => navigation.navigate('Community')}>
          <Text> ← 뒤로 </Text>
        </TouchableOpacity>
        <Image
          style={{ resizeMode: 'cover', height: 80, width: 160 }}
          source={require('../assets/rikey.png')}
        />
        <TouchableOpacity
          style={styles.communityButton2}
          onPress={() => navigation.navigate('Facilities')}>
          <Image
            source={require('../assets/icons/shop.png')}
            style={{
              resizeMode: 'cover',
              height: 24,
              width: 24,
              marginLeft: 40,
              tintColor: 'grey',
            }}
          />
        </TouchableOpacity>
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
