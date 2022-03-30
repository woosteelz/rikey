import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import Tabs from './tabs';
import SignIn from '../screens/SignIn';
import SignUp from '../screens/SignUp';
import Home from '../screens/Home';
import Profile from '../screens/Profile';
import Course from '../screens/Course';
import CourseDetail from '../screens/CourseDetail';
import 'react-native-gesture-handler';

const HomeStack = createStackNavigator();
const HomeScreen = () => {
  return (
    <HomeStack.Navigator initialRouteName="Home">
      <Stack.Screen name="Home" component={Home} />
      <Stack.Screen name="Profile" component={Profile} />
    </HomeStack.Navigator>
  );
};

const Stack = createStackNavigator();
const StackNavigation = () => {
  return (
    <Stack.Navigator screenOptions={{ headerShown: false }}>
      <Stack.Screen name="SignIn" component={SignIn} />
      <Stack.Screen name="SignUp" component={SignUp} />
      <Stack.Screen name="Tabs" component={Tabs} />
    </Stack.Navigator>
  );
};

const CourseStack = createStackNavigator();
const CourseScreen = () => {
  return (
    <CourseStack.Navigator screenOptions={{ headerShown: false }}>
      <Stack.Screen name="Course" component={Course} />
      <Stack.Screen name="CourseDetail" component={CourseDetail} />
    </CourseStack.Navigator>
  );
};

export { StackNavigation, HomeScreen, CourseScreen };
