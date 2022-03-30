import { Axios } from 'axios';
import React, { useState, useEffect, useLayoutEffect } from 'react';
import {
  View,
  Text,
  Button,
  StyleSheet,
  ScrollView,
  Image,
} from 'react-native';
import API from '../api/API';
import { useStore } from '../states';
import axios from 'axios';

const CourseDetail = ({ route, navigation }) => {
  const [detail, setDetail] = useState({});

  const logo = {
    uri: 'https://mblogthumb-phinf.pstatic.net/MjAxOTEyMTNfMjQz/MDAxNTc2MjM4NzE1ODEz.rJgwvkYWJR60irHgY6QlB68xKu-sxwKS4u3D0Jk83REg.zbDGJ1tGhwM1oucn_L1iKdqX5aJviN7zO8XqpL_T3uQg.PNG.daddybike/%EC%9E%90%EC%A0%84%EA%B1%B0%EA%B5%AD%ED%86%A0%EC%A2%85%EC%A3%BC.PNG?type=w800',
    width: 320,
    height: 320,
  };

  const getCourse = () => {
    axios({
      url: `http://j6c208.p.ssafy.io/api/bikeRoads/${route.params}`,
      method: 'get',
    })
      .then(res => {
        console.log(res.data);
        setDetail(res.data);
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
      <ScrollView>
        <View
          style={{
            flex: 1,
            alignItems: 'center',
            justifyContent: 'center',
          }}>
          <Image
            style={{ height: 500, width: 500, resizeMode: 'contain' }}
            source={{
              uri: 'https://mblogthumb-phinf.pstatic.net/MjAxOTEyMTNfMjQz/MDAxNTc2MjM4NzE1ODEz.rJgwvkYWJR60irHgY6QlB68xKu-sxwKS4u3D0Jk83REg.zbDGJ1tGhwM1oucn_L1iKdqX5aJviN7zO8XqpL_T3uQg.PNG.daddybike/%EC%9E%90%EC%A0%84%EA%B1%B0%EA%B5%AD%ED%86%A0%EC%A2%85%EC%A3%BC.PNG?type=w800',
            }}
          />
        </View>
        <View
          style={{
            flex: 1,
            // backgroundColor: 'blue',
            alignItems: 'center',
            justifyContent: 'center',
          }}>
          <Text style={{ fontSize: 28, marginTop: 3 }}>
            {detail.bikeRoad.name}
          </Text>
          <Text style={{ fontSize: 28, marginTop: 3 }}>
            {detail.bikeRoad.name}
          </Text>
        </View>
        <View style={{ flex: 1 }}>
          <Text style={{ fontSize: 18, margin: 3 }}>
            {detail.bikeRoad.introduce}
          </Text>
        </View>
        {/* navigation 영역 */}
        <View style={{ flex: 1, height: 100 }}></View>
      </ScrollView>
    </View>
  );
};

export default CourseDetail;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
});
