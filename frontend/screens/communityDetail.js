import React, { Component, useState } from "react";
import { KeyboardAvoidingView,Image, StyleSheet, View, Text,Dimensions,TouchableOpacity,TextInput, SafeAreaView } from "react-native";
import Rikey from '../assets/rikey.png'
import guywoo from '../assets/정우.jpg'

const App = () => {
 
    const screenWidth = Dimensions.get('window').width;
    const [text, onChangeText] = React.useState("");
    const [overView, setOverview] = useState(true)

    const onBoardOverview = () => {
      setOverview(false)
    }
    const onBoardOverviewBack = () => {
      setOverview(true)
    }
    



    return (
      <KeyboardAvoidingView style={{ flex: 1 }} behavior="padding" enabled>
      <View>

        <View>
          <View style={{flexDirection: "row", justifyContent: "space-around"}}>
          <TouchableOpacity style={styles.communityButton2} onPress={onBoardOverviewBack}> 
                <Text> ← 뒤로 </Text>
          </TouchableOpacity>
          <Image style={{ resizeMode: "cover", height: 80, width: 160}} source={Rikey} />
          <View style={{marginRight : "15%"}}></View>
          </View>

        <View style={{ flexDirection: "row", marginLeft: "5%"}}> 
        <Image style={{ resizeMode: "cover", height: 60, width: 60, borderRadius: 50}} source={guywoo} />
        <View>
          <Text style={{fontWeight: "bold",fontSize:20, color:"black", marginLeft: "12%",marginBottom: 0}}>박정우</Text>
          <Text style={{marginLeft: "13%"}}>8 시간 전</Text>
        </View>

        </View>
        
        <View style={{backgroundColor: "green", marginTop: "5%", marginLeft:"5%", width: "90%", height: "60%"}}>
        
        </View>
        
          <SafeAreaView>
            <TextInput
              style={styles.input}
              onChangeText={onChangeText}
              value={text}
              placeholder="useless placeholder"

            />
          </SafeAreaView>
            
        </View>
      
        </View>
        </KeyboardAvoidingView>
    );
  }


const styles = StyleSheet.create({
  writebutton : {
    elevation : 5
  },
  communityButton : {
    marginLeft: "auto", 
    margin: "4%",
    
  },
  communityButton2 : { 
    marginTop: "8%",
    width: "20%",
    
  },
  myrank: {
    marginLeft : "5%",
    marginTop : "5%",
    fontWeight: 'bold'
  },
  myrankProfile: {
    width: "100%",
    marginLeft: "14%",
    paddingLeft : "8%",
    marginTop : "5%",
    fontWeight: 'bold'
  },

  box: {
    height: "70%",
    width: "100%",
    marginLeft : "15%",
    marginTop: 12,
    padding: 12,
    borderRadius: 8,
    color: "#666",
    backgroundColor: "#eaeaea"
  },
});
export default App;