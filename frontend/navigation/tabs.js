import React from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { StyleSheet, Text, View, Image, TouchableOpacity } from 'react-native';

import Course from '../screens/Course';
import Chat from '../screens/Chat';
import Record from '../screens/Record';
import Community from '../screens/Community';
import { HomeScreen, CommunityScreen, CourseScreen } from './Stack';

const CustomTabBarButton = ({ children, onPress }) => {
  return (
    <TouchableOpacity
      style={{
        top: -25,
        justifyContent: 'center',
        alignItems: 'center',
        ...styles.shadow,
      }}
      onPress={onPress}>
      <View
        style={{
          width: 60,
          height: 60,
          borderRadius: 35,
          backgroundColor: 'white',
        }}>
        {children}
      </View>
    </TouchableOpacity>
  );
};

const Tab = createBottomTabNavigator();

const Tabs = () => {
  return (
    <Tab.Navigator
      screenOptions={{
        tabBarShowLabel: false,
        headerShown: false,
        tabBarStyle: {
          position: 'absolute',
          elevation: 0,
          backgroundColor: '#ffffff',
          borderTopStartRadius: 15,
          height: 60,
          ...styles.shadow,
        },
      }}>
      <Tab.Screen
        name="Home"
        component={HomeScreen}
        options={{
          tabBarIcon: ({ focused }) => (
            <View
              style={{
                alignItems: 'center',
                justifyContent: 'center',
              }}>
              <Image
                style={{
                  width: 30,
                  height: 30,
                  tintColor: focused ? '#00C689' : 'grey',
                }}
                source={require('../assets/icons/cloudy.png')}
              />
            </View>
          ),
        }}
      />
      <Tab.Screen
        name="CommunityScreen"
        component={CommunityScreen}
        options={{
          tabBarIcon: ({ focused }) => (
            <View
              style={{
                alignItems: 'center',
                justifyContent: 'center',
              }}>
              <Image
                style={{
                  width: 30,
                  height: 30,
                  tintColor: focused ? '#00C689' : 'grey',
                }}
                source={require('../assets/icons/group-users.png')}></Image>
            </View>
          ),
        }}
      />
      <Tab.Screen
        name="Record"
        component={Record}
        options={{
          tabBarIcon: () => (
            <Image
              source={require('../assets/icons/bike.png')}
              resizeMode="contain"
              style={{
                width: 45,
                height: 45,
                tintColor: '#00C689',
                borderRadius: 10,
                borderWidth: 10,
              }}
            />
          ),
          tabBarButton: props => <CustomTabBarButton {...props} />,
        }}
      />
      <Tab.Screen
        name="Course"
        component={CourseScreen}
        options={{
          tabBarIcon: ({ focused }) => (
            <View
              style={{
                alignItems: 'center',
                justifyContent: 'center',
              }}>
              <Image
                style={{
                  width: 30,
                  height: 30,
                  tintColor: focused ? '#00C689' : 'grey',
                }}
                source={require('../assets/icons/map.png')}></Image>
            </View>
          ),
        }}
      />
      <Tab.Screen
        name="Profile"
        component={Chat}
        options={{
          tabBarIcon: ({ focused }) => (
            <View
              style={{
                alignItems: 'center',
                justifyContent: 'center',
              }}>
              <Image
                style={{
                  width: 30,
                  height: 30,
                  tintColor: focused ? '#00C689' : 'grey',
                }}
                source={require('../assets/icons/bubble-chat.png')}></Image>
            </View>
          ),
        }}
      />
    </Tab.Navigator>
  );
};

export default Tabs;

const styles = StyleSheet.create({
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
  MainContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'blue',
  },
  button: {
    width: 60,
    height: 60,
    alignItems: 'center',
    justifyContent: 'center',
    shadowColor: 'grey',
    shadowOpacity: 0.1,
    shadowOffset: { x: 2, y: 0 },
    shadowRadius: 2,
    borderRadius: 30,
    position: 'absolute',
    bottom: 20,
    right: 0,
    top: 5,
    left: 5,
    shadowOpacity: 5.0,
  },
  actionBtn: {
    backgroundColor: '#1E90FF',
    textShadowOffset: { width: 5, height: 5 },
    textShadowRadius: 10,
    borderWidth: 2,
    borderColor: '#fff',
  },
});
