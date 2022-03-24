import React from 'react';
import { View, Text, Button, StyleSheet } from 'react-native';

const Map = ({ navigation }) => {
  return (
    <View style={styles.container}>
      <Text>Map</Text>
      <Button
        title="click"
        onPress={() => {
          alert('Communinty');
        }}
      />
    </View>
  );
};

export default Map;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'white',
  },
});
