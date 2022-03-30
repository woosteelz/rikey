import React, { Component, useState, useEffect } from "react";
import { Image, StyleSheet, View, Text, ScrollView, Dimensions,LinearLayout,TouchableOpacity,Button  } from "react-native";
import Rikey from '../assets/rikey.png'
import writebutton from '../assets/writebutton.png'
import { Select, Box, CheckIcon, Center, NativeBaseProvider } from "native-base";

import API from "../api/API";




const CommunityBoard = ( { navigation } ) => {
  const [tempboard, setTempboard] = useState([]);
  useEffect(() => {
    async function get() {
      const res = await API.get(
        '/articles?category=ALL'
      );
      console.log('시작되었다')
      console.log(res.data.articleList)
      setTempboard(res.data.articleList)
    }
    get();
    return
  },[])
    
    

  const screenWidth = Dimensions.get('window').width;


  



  //  useEffect 통해서 게시판변화에따른 게시판 제공 API 사용
  const boardservice = async() => {
    const response = await API.get(`/articles?category=${service}`);
    
    console.log("뭐야!!")
    console.log(response.data.articleList)
    setTempboard(response.data.articleList)
    console.log('템프보드')
    console.log(tempboard)
    console.log('템프보드')
  }
  

  const variousboard = tempboard.map( (item,key) => {
    console.log(tempboard)
    console.log('뭐양')
    return <View key={key} style={{marginLeft : "8%", marginBottom: "4%"}}>
      <TouchableOpacity onPress={ ()=> navigation.navigate('CommunityDetail', {articleId : item.articleId , author : item.author})}>
      <Text style={{fontSize:15, fontWeight:'bold', color:'#424242', marginBottom: 3}}>{item.title}</Text>
      <Text style={{fontSize:13,color:'#363636'}} ellipsizeMode='tail' numberOfLines={1}>{item.content}</Text>
      {/* 내용이 길면 ...으로대체하는 로직을 작성할것 */}
      <Text style={{fontSize:12}}>{item.author}</Text>
      {/* <View
        style={{
          marginTop: "3%",
          marginRight: "5%",
          borderBottomColor: '#484848',
          borderBottomWidth: 0.5,
        }}
      /> */}
      </TouchableOpacity>
      </View>

      
  })




  
  const [service, setService] = React.useState("All");
  useEffect(() => {
    console.log(service)
    // console.log('여기까지?')
    boardservice()

  },[service])



  const Example = () => {
    // let [service, setService] = React.useState("");
    return <Center>
        <Box w="3/4" maxH="10" maxW="100">
          <Select selectedValue={service} minWidth="100" accessibilityLabel="Choose Service" placeholder="전체" _selectedItem={{
          bg: "teal.600",
          endIcon: <CheckIcon size="5" />
        }} mt={1} onValueChange={itemValue => setService(itemValue)}>
            <Select.Item label="전체" value="ALL" />
            <Select.Item label="라이더 모집" value="RECRUIT" />
            <Select.Item label="자유게시판" value="FREE" />
          </Select>
        </Box>
      </Center>;
  };

    return (
      
      <View>
      
        <View>
          <View style={{flexDirection: "row", justifyContent: "space-around"}}>
          <TouchableOpacity style={styles.communityButton2} onPress={() => navigation.navigate('Community')}> 
                <Text> ← 뒤로 </Text>
          </TouchableOpacity>
          <Image style={{ resizeMode: "cover", height: 80, width: 160}} source={Rikey} />
          <Text style={{marginTop: "8%"}}> 대충 돋보기</Text>
          </View>

        <View style={{ flexDirection: "row"}}> 
        <Text style={{marginLeft: "5%",marginTop: "5%",width : "30%"}}>📋 커뮤니티 모든글</Text>
  
        <Text style={{ flex:0.9, marginTop: "5%", textAlign: "right"}} >분류 : </Text>
         <NativeBaseProvider>
            <Center style={{marginTop: "4.5%",marginRight:"auto"}}>
                <Example />
            </Center>
          </NativeBaseProvider>
          

        </View>
        
        <View style={{ marginTop: "5%", width: "90%", height: "73%"}}>
        <ScrollView showsVerticalScrollIndicator={false} >
          <View>{variousboard}</View>
        </ScrollView>
        </View>
        
        {/* 글쓰기 버튼 */}
        <View style={{flex:1}}>

        <View style={{marginRight:"5%",marginBottom:"2%", position:'absolute',bottom:0,alignSelf:'flex-end'}}>
          <TouchableOpacity style={styles.writebutton} onPress={() => navigation.navigate('Writepage')}> 
                <Image style={{resizeMode: "cover", height:60, width: 60}} source={writebutton} />
          </TouchableOpacity>
        
        </View>

      </View>
        </View>
        
        </View>
        
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
export default CommunityBoard;