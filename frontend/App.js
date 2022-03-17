import React, { Component } from 'react';
import { NavigationContainer } from '@react-navigation/native';
import {
  View,
  Image,
  StyleSheet,
  TouchableOpacity,
  TouchableWithoutFeedback,
  Animated,
  Text,
  Alert,
} from 'react-native';

import Tabs from './navigation/tabs';

const App = () => {
  return (
    <NavigationContainer>
      <Tabs />
    </NavigationContainer>
  );
};

export default App;
