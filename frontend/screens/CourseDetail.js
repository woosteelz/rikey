import React, { useState, useEffect, useLayoutEffect } from 'react';
import {
  View,
  Text,
  Button,
  StyleSheet,
  ScrollView,
  Image,
  TouchableOpacity,
  Modal,
  Pressable,
  TextInput,
  FlatList,
} from 'react-native';

import { LogoTitle } from '../components/Header/LogoTitle';

import { useStore } from '../states';
import axios from 'axios';

function isEmptyObj(obj) {
  if (obj.constructor === Object && Object.keys(obj).length === 0) {
    return true;
  }
  return false;
}

const CustomCommentList = ({ reviewList }) => {
  console.log(reviewList);
  return (
    <FlatList
      style={styles.root}
      data={reviewList}
      //   extraData={this.state}
      ItemSeparatorComponent={() => {
        return <View style={styles.separator} />;
      }}
      keyExtractor={item => {
        return item.reviewId;
      }}
      renderItem={item => {
        return (
          <View style={styles.container}>
            <TouchableOpacity onPress={() => {}}>
              <Image
                style={styles.image}
                source={
                  item.item.profilePic
                    ? { uri: item.item.profilePic }
                    : require('../assets/icons/user-profile.png')
                }
              />
            </TouchableOpacity>
            <View style={styles.content}>
              <View style={styles.contentHeader}>
                <Text style={styles.name}>{item.item.author}</Text>
                <Text style={styles.time}>
                  {item.item.createdTime.slice(0, 10)}
                </Text>
              </View>
              <Text rkType="primary3 mediumLine">{item.item.content}</Text>
            </View>
          </View>
        );
      }}
    />
  );
};

const CourseDetail = ({ route, navigation }) => {
  const [detail, setDetail] = useState({});
  const [modalVisible, setModalVisible] = useState(false);

  const [content, setContent] = useState('');
  const [defaultRating, setdefaultRating] = useState(0);
  const [maxRating, setmaxRating] = useState([1, 2, 3, 4, 5]);

  const starImgFilled = require('../assets/icons/star.png');
  const starImgCorner = require('../assets/icons/starcorner.png');

  const { userId } = useStore();

  const CustomRatingBar = () => {
    return (
      <View style={styles.customRatingBar}>
        {maxRating.map(item => {
          return (
            <TouchableOpacity
              activeOpacity={0.7}
              key={item}
              onPress={() => setdefaultRating(item)}>
              <Image
                style={styles.starImg}
                source={item <= defaultRating ? starImgFilled : starImgCorner}
              />
            </TouchableOpacity>
          );
        })}
      </View>
    );
  };

  const sendReview = () => {
    axios({
      url: 'http://j6c208.p.ssafy.io/api/reviews',
      method: 'post',
      data: {
        bikeRoadId: route.params.id,
        content,
        score: defaultRating,
        userId,
      },
    })
      .then(res => {
        console.log(res);
      })
      .catch(err => console.log(err));
    setModalVisible(!modalVisible);
  };

  const getDetail = () => {
    axios({
      url: `http://j6c208.p.ssafy.io/api/bikeRoads/${route.params.id}`,
      method: 'get',
    })
      .then(res => {
        console.log(res.data);
        setDetail(res.data.bikeRoad);
      })
      .catch(err => {
        console.log(err);
      });
  };

  useEffect(() => {
    getDetail();
  }, [modalVisible]);

  return (
    <View style={{ ...styles.container, backgroundColor: 'white' }}>
      <Modal
        animationType="slide"
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => {
          setModalVisible(!modalVisible);
        }}>
        <View style={{ ...styles.centeredView }}>
          <View style={styles.modalView}>
            <Text style={styles.modalText}>리뷰 작성하기</Text>
            <CustomRatingBar />
            <TextInput
              style={styles.input}
              value={content}
              onChangeText={text => setContent(text)}
              autoCapitalize="sentences"
              autoCorrect
              returnKeyType="next"
              //   onEndEditing={() => console.log('onEndEditing')}
              onSubmitEditing={sendReview}
            />
            <View style={{ flexDirection: 'row' }}>
              <Pressable
                style={{
                  ...styles.button,
                  backgroundColor: 'grey',
                }}
                onPress={() => setModalVisible(!modalVisible)}>
                <Text style={{ ...styles.textStyle }}>취소</Text>
              </Pressable>
              <Pressable
                style={[styles.button, styles.buttonClose]}
                onPress={sendReview}>
                <Text style={styles.textStyle}>작성하기</Text>
              </Pressable>
            </View>
          </View>
        </View>
      </Modal>
      <View style={{ backgroundColor: 'white' }}>
        <View
          style={{
            flexDirection: 'row',
            justifyContent: 'space-between',
          }}>
          <TouchableOpacity
            style={{ alignSelf: 'center' }}
            onPress={() => navigation.navigate('Course')}>
            <Image
              source={require('../assets/images/Back.png')}
              style={{
                resizeMode: 'contain',
                height: 20,
                width: 20,
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
        <ScrollView>
          <View
            style={{
              flex: 1,
              alignItems: 'center',
              justifyContent: 'flex-start',
            }}>
            <Image
              style={{
                height: 250,
                width: 380,
                resizeMode: 'stretch',
              }}
              source={{
                uri: `${detail.image}`,
              }}
            />
          </View>
          <View
            style={{
              flex: 1,
              alignItems: 'center',
              justifyContent: 'center',
            }}>
            <Text
              style={{
                fontSize: 28,
                margin: 5,
                marginTop: 20,
                fontWeight: 'bold',
                color: 'black',
              }}>
              {detail.name}
            </Text>
            <View
              style={{
                flex: 1,
                flexDirection: 'row',
                margin: 5,
                justifyContent: 'space-between',
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
              <Text style={{ fontSize: 21, color: '#024430' }}>
                {detail.score !== 'NaN'
                  ? Math.round(detail.score * 10) / 10
                  : 0}
              </Text>
              <Text style={{ marginRight: 20, color: '#024430' }}> / 5</Text>
              <Text style={{ color: '#024430' }}>
                라이더 리뷰 {!isEmptyObj(detail) ? detail.reviewList.length : 0}
                개
              </Text>
            </View>
            <Text style={{ color: 'grey', letterSpacing: -1 }}>
              ________________________________________________________________
            </Text>
          </View>
          <View style={{ flex: 1 }}>
            <Text
              style={{
                fontSize: 18,
                margin: 10,
                color: 'black',
                fontWeight: 'bold',
              }}>
              코스 정보
            </Text>
            <Text
              style={{
                fontSize: 16,
                margin: 3,
                marginLeft: 10,
                color: 'black',
              }}>
              코스 거리 : 추후 업데이트 예정
            </Text>
            <Text
              style={{
                fontSize: 16,
                margin: 3,
                marginLeft: 10,
                color: 'black',
              }}>
              예상 소요시간 : {!isEmptyObj(detail) ? detail.hour : 0}시간{' '}
              {!isEmptyObj(detail) ? detail.minute : 0}분
            </Text>
            <Text
              style={{ color: 'grey', letterSpacing: -1, alignSelf: 'center' }}>
              ________________________________________________________________
            </Text>
          </View>
          <View style={{ flex: 1 }}>
            <Text
              style={{
                fontSize: 18,
                margin: 10,
                color: 'black',
                fontWeight: 'bold',
              }}>
              코스 설명
            </Text>
            <Text
              style={{
                fontSize: 15,
                margin: 20,
                marginTop: -3,
                color: 'black',
              }}>
              {detail.introduce}
            </Text>
            <Text
              style={{
                color: 'grey',
                letterSpacing: -1,
                alignSelf: 'center',
                marginTop: -20,
              }}>
              ________________________________________________________________
            </Text>
            <View
              style={{
                flex: 1,
                flexDirection: 'row',
                margin: 5,
                justifyContent: 'space-between',
                alignItems: 'center',
              }}>
              <Text
                style={{
                  fontSize: 18,
                  margin: 5,
                  color: 'black',
                  fontWeight: 'bold',
                }}>
                라이더 리뷰
              </Text>
              <TouchableOpacity
                style={{ marginRight: 15 }}
                onPress={() => setModalVisible(!modalVisible)}>
                <Image
                  style={{
                    tintColor: '#024430',
                    width: 24,
                    height: 24,
                  }}
                  source={require('../assets/icons/more.png')}
                />
              </TouchableOpacity>
            </View>
          </View>
          {!isEmptyObj(detail) ? (
            <CustomCommentList reviewList={detail.reviewList.reverse()} />
          ) : (
            <View
              style={{
                flex: 1,
                alignContent: 'center',
                justifyContent: 'center',
              }}>
              <Text>리뷰가 아직 없네요..</Text>
            </View>
          )}
          {/* navigation 영역 */}
          <View style={{ flex: 1, height: 100 }}></View>
        </ScrollView>
      </View>
    </View>
  );
};

export default CourseDetail;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
  shadow: {
    shadowColor: '#7F5DF0',
    shadowOffset: {
      width: 0,
      height: 10,
    },
    shadowOpacity: 0.25,
    shadowRadius: 3.5,
    elevation: 5,
  },
  centeredView: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 22,
  },
  modalView: {
    margin: 20,
    backgroundColor: 'white',
    borderRadius: 20,
    padding: 35,
    alignItems: 'center',
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
  },
  button: {
    borderRadius: 10,
    padding: 10,
    elevation: 2,
    marginTop: 15,
    marginHorizontal: 5,
  },
  buttonClose: {
    backgroundColor: '#00C689',
  },
  textStyle: {
    color: 'white',
    fontWeight: 'bold',
    textAlign: 'center',
  },
  modalText: {
    marginBottom: 15,
    textAlign: 'center',
  },
  customRatingBar: {
    justifyContent: 'center',
    flexDirection: 'row',
    margin: 5,
  },
  starImg: {
    width: 30,
    height: 30,
    resizeMode: 'contain',
    tintColor: '#FFB110',
  },
  input: {
    borderRadius: 10,
    borderColor: 'gray',
    borderWidth: 1,
    width: 200,
    height: 40,
    marginTop: 10,
  },
  root: {
    backgroundColor: '#ffffff',
    marginTop: 10,
  },
  container: {
    paddingLeft: 19,
    paddingRight: 16,
    paddingVertical: 12,
    flexDirection: 'row',
    alignItems: 'flex-start',
  },
  content: {
    marginLeft: 16,
    flex: 1,
  },
  contentHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 6,
  },
  separator: {
    height: 1,
    backgroundColor: '#CCCCCC',
  },
  image: {
    width: 45,
    height: 45,
    borderRadius: 20,
    marginLeft: 0,
  },
  time: {
    fontSize: 11,
    color: '#808080',
  },
  name: {
    fontSize: 16,
    fontWeight: 'bold',
  },
  communityButton2: {
    marginTop: '8%',
    width: '20%',
    marginLeft: -100,
  },
});
