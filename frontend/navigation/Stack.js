import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import { Button, Image, TouchableOpacity } from 'react-native';

import Tabs from './tabs';
import SignIn from '../screens/SignIn';
import SignUp from '../screens/SignUp';

import Home from '../screens/Home';
import Profile from '../screens/Profile';
import MyArticles from '../screens/MyArticles';
import Course from '../screens/Course';
import CourseDetail from '../screens/CourseDetail';
import Facilities from '../screens/Facilities';
import MyComments from '../screens/MyComments';
import MyReviews from '../screens/MyReviews';
import MyRecords from '../screens/MyRecords';
import MyInfo from '../screens/MyInfo';
import LogoTitle from '../components/Header/LogoTitle';
import ProfileIcon from '../assets/icons/ProfileIcon.jpg';
import 'react-native-gesture-handler';

import UpdatePage from '../screens/UpdatePage';
import CommunityDetail from '../screens/CommunityDetail';
import CommunityBoard from '../screens/CommunityBoard';
import Community from '../screens/Community';
import WritePage from '../screens/WritePage';

const HomeStack = createStackNavigator();
const HomeScreen = () => {
  return (
    <HomeStack.Navigator
      screenOptions={{
        cardStyle: { backgroundColor: '#ffffff' },
        headerTitleAlign: 'center',
      }}>
      <Stack.Screen
        name="Home"
        component={Home}
        options={({ navigation }) => ({
          headerTitle: props => <LogoTitle {...props} />,
          headerTitleStyle: { flex: 1 },
          headerBackTitleVisible: false,
          headerLeft: null,
          // headerRight: () => (
          //   <TouchableOpacity
          //     onPress={() => navigation.navigate('Profile')}
          //     style={{ marginRight: '13%' }}>
          //     <Image
          //       source={ProfileIcon}
          //       style={{ width: 20, height: 28, marginBottom: '3%' }}
          //     />
          //   </TouchableOpacity>
          // ),
        })}
      />
    </HomeStack.Navigator>
  );
};

const CommunityStack = createStackNavigator();
const CommunityScreen = () => {
  return (
    <CommunityStack.Navigator
      screenOptions={{
        cardStyle: { backgroundColor: '#ffffff' },
        headerShown: false,
      }}
      initialRouteName="Community">
      <Stack.Screen name="Community" component={Community} />
      <Stack.Screen name="CommunityBoard" component={CommunityBoard} />
      <Stack.Screen name="CommunityDetail" component={CommunityDetail} />
      <Stack.Screen name="WritePage" component={WritePage} />
      <Stack.Screen name="UpdatePage" component={UpdatePage} />
    </CommunityStack.Navigator>
  );
};
const CourseStack = createStackNavigator();
const CourseScreen = () => {
  return (
    <CourseStack.Navigator screenOptions={{ headerShown: false }}>
      <Stack.Screen name="Course" component={Course} />
      <Stack.Screen name="CourseDetail" component={CourseDetail} />
      <Stack.Screen name="Facilities" component={Facilities} />
    </CourseStack.Navigator>
  );
};

const ProfileStack = createStackNavigator();
const ProfileScreen = () => {
  return (
    <ProfileStack.Navigator
      screenOptions={{
        cardStyle: { backgroundColor: '#ffffff' },
        headerTitleAlign: 'center',
      }}>
      <Stack.Screen
        name="Profile"
        component={Profile}
        options={{
          headerTitle: '프로필',
          headerTitleAlign: 'center',
          cardStyle: { backgroundColor: '#ffffff' },
          headerLeft: null,
        }}
      />
      <Stack.Screen
        name="MyArticles"
        component={MyArticles}
        options={{
          headerTitle: '내 게시글',
        }}
      />
      <Stack.Screen
        name="MyComments"
        component={MyComments}
        options={{
          headerTitle: '내 댓글',
        }}
      />
      <Stack.Screen
        name="MyReviews"
        component={MyReviews}
        options={{
          headerTitle: '내 코스 후기',
        }}
      />
      <Stack.Screen
        name="MyRecords"
        component={MyRecords}
        options={{
          headerTitle: '주행 기록',
        }}
      />
      <Stack.Screen
        name="MyInfo"
        component={MyInfo}
        options={{
          headerTitle: '내 정보',
        }}
      />
    </ProfileStack.Navigator>
  );
};

const Stack = createStackNavigator();
const StackNavigation = () => {
  return (
    <Stack.Navigator
      screenOptions={{
        headerShown: false,
        cardStyle: { backgroundColor: '#ffffff' },
      }}>
      <Stack.Screen name="SignIn" component={SignIn} />
      <Stack.Screen name="SignUp" component={SignUp} />
      <Stack.Screen name="Tabs" component={Tabs} />
    </Stack.Navigator>
  );
};

export {
  StackNavigation,
  HomeScreen,
  CommunityScreen,
  CourseScreen,
  ProfileScreen,
};
