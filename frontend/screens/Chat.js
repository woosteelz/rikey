import React from 'react';
import { View, Text, Button, StyleSheet } from 'react-native';

const Chat = ({ navigation }) => {
  return (
    <View style={styles.container}>
      <Text>Profile</Text>
      <Button
        title="click"
        onPress={() => {
          alert('Communinty');
        }}
      />
    </View>
  );
};

export default Chat;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'white',
  },
});
