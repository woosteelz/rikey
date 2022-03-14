import React from 'react';
import { View, Text, Button, StyleSheet } from 'react-native';

const Record = ({ navigation }) => {
  return (
    <View style={styles.container}>
      <Text>Record</Text>
      <Button
        title="click"
        onPress={() => {
          alert('Communinty');
        }}
      />
    </View>
  );
};

export default Record;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'white',
  },
});
