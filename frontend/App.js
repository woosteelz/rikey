import React, { Component } from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { StackNavigation } from './navigation/Stack';
import Tabs from './navigation/tabs'


const App = () => {
  return (
    <NavigationContainer>
        <StackNavigation />
    </NavigationContainer>
  );
};

export default App;
