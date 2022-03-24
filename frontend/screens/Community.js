import React from 'react';
import { View, Text, Button, StyleSheet } from 'react-native';

const Community = ({ navigation }) => {
  return (
    <View style={styles.container}>
      <Text>Community</Text>
      <Button
        title="click"
        onPress={() => {
          alert('Communinty');
        }}
      />
    </View>
  );
};

export default Community;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'white',
  },
});
