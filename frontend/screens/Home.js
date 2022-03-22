import React, { useEffect } from 'react';
import { View, Text, Button, StyleSheet } from 'react-native';
import { useStore } from '../states';


const Home = ({ navigation }) => {
  
  const { userId } = useStore();
  useEffect(() => {
    console.log(userId);
  })

  return (
    <View style={styles.container}>
      <Text>Home</Text>
      <Button
        title="click"
        onPress={() => {
          alert('Communinty');
        }}
      />
    </View>
  );
};

export default Home;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'white',
  },
});
