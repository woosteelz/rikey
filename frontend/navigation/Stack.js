import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import { Button, Image, TouchableOpacity } from 'react-native';
import Tabs from './tabs';
import SignIn from '../screens/SignIn';
import SignUp from '../screens/SignUp';
import Home from '../screens/Home';
import Profile from '../screens/Profile';
import LogoTitle from '../components/Header/LogoTitle'
import ProfileIcon from '../assets/icons/ProfileIcon.jpg'
import 'react-native-gesture-handler';

const HomeStack = createStackNavigator();
const HomeScreen = () => {
  return (
    <HomeStack.Navigator initialRouteName='Home'>
      <Stack.Screen
        name="Home"
        component={Home}
        options = {({ navigation }) => ({
          headerTitle : (props) => <LogoTitle {...props} />,
          headerTitleStyle: { flex: 1 },
          headerBackTitleVisible : false,
          headerLeft: null,
          headerRight: () => (
            <TouchableOpacity
              onPress={() => navigation.navigate('Profile')}
              style={{marginRight:"13%"}}
            >
              <Image 
                source={ProfileIcon}
                style = {{ width:20, height:28, marginBottom: "3%" }}
              />
          </TouchableOpacity>
          ),
        })}
      />
      <Stack.Screen name="Logo" component={LogoTitle}/>
      <Stack.Screen name="Profile" component={Profile}/>
    </HomeStack.Navigator>
  )
}


const Stack = createStackNavigator();
const StackNavigation = () => {
  return (
    <Stack.Navigator 
      screenOptions={{ 
        headerShown: false,
        cardStyle: { backgroundColor: '#ffffff'}
      }}
    >
      <Stack.Screen name="SignIn" component={SignIn}/>
      <Stack.Screen name="SignUp" component={SignUp}/>
      <Stack.Screen name="Tabs" component={Tabs}/>
    </Stack.Navigator>
  );
}

export { StackNavigation, HomeScreen };