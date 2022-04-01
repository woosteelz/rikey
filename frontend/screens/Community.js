import React, { Component, useState, useEffect } from "react";
import { Image, StyleSheet, View, Text, ScrollView, Dimensions,LinearLayout,TouchableOpacity,Button  } from "react-native";
import Rikey from '../assets/rikey.png'
import writebutton from '../assets/writebutton.png'
import { Select, Box, CheckIcon, Center, NativeBaseProvider } from "native-base";
import { useIsFocused } from '@react-navigation/native';
import API from "../api/API";



const Community = ( { navigation } ) => {
  const isFocused = useIsFocused();
  useEffect(() =>{
    console.log('됫냐')
    async function get() {
      const completed = false
      const response = await API.get(
        '/articles/recent'
      );
      
      console.log(response.data.articleList)
      setminioverview(response.data.articleList)
      
      console.log(minioverview)
      console.log('왜안되')
      
      if (!completed) setminioverview(response.data.articleList);
        console.log(minioverview)
    }
    get();
    return () => {
      completed = true;
    };
    
    

  },[isFocused])
    const [minioverview, setminioverview] = useState([])
    const screenWidth = Dimensions.get('window').width;
    const [overView, setOverview] = useState(true)

    const elements = minioverview.map( (item,key) => {
      return <View key={key} style={{marginLeft : "8%",marginBottom: "4%"}}>
        <TouchableOpacity onPress={ ()=> navigation.navigate('CommunityDetail', {articleId : item.articleId , author : item.author})}>
        <Text style={{fontSize:15, fontWeight:'bold', color:'#363636', marginBottom: 2}}>{item.title}</Text>
        <Text style={{fontSize:13,color:'#424242', marginBottom: 3}} ellipsizeMode='tail' numberOfLines={1}>{item.content}</Text>
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
    const onBoardOverview = () => {
      setOverview(false)
      
    }
    

    const onBoardOverviewBack = () => {
      setOverview(true)
    }

    

    


   

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
          <Text style={styles.myrank}>🚴🏻‍♂️내 랭킹</Text>

 
            <ScrollView 
            horizontal={true} 
            showsHorizontalScrollIndicator={false}
            contentContainerStyle={{
              flexGrow :1
            }}     
            style= {{
              width: "100%",
              height: "20%"
            }}
            >

              

            <View style={{ flexDirection: "row", marginTop:"5%" }}> 
                <View style={{ width: "25%" }}>
                  <Text style={{marginLeft: "20%"}}> 주간 소비 칼로리</Text> 
                  <View style={ styles.box }>
                  </View>
                </View>

                <View style={{ width: "25%" , marginLeft: 50 }}>
                  <Text> 주간 주행 거리</Text> 
                  <View style={ styles.box }>
                  </View>
                </View>

                <View style={{ width: "25%" , marginLeft: 50 }}>
                  <Text> 주간 주행 거리</Text> 
                  <View style={ styles.box }>
                  </View>
                </View>

                <View style={{ width: "25%" , marginLeft: 50 }}>
                  <Text> 주간 주행 거리</Text> 
                  <View style={ styles.box }>
                  </View>
                </View>

            </View>
            </ScrollView>
            <Text style={{marginLeft: "5%",marginTop: "5%"}}>📋 커뮤니티 최근글</Text>
            <View style={{marginTop: "5%",width: "90%", height: "51%"}}>
               
                <View>{elements}</View>

            </View>
            <TouchableOpacity style={styles.communityButton} onPress={() => navigation.navigate('CommunityBoard')}> 
              <Text>커뮤니티 모든 글 보러가기 > </Text>
            </TouchableOpacity>
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
export default Community;