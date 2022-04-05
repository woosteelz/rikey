import React, { Component, useState, useEffect } from "react";
import { Image, StyleSheet, View, Text, ScrollView, Dimensions,LinearLayout,TouchableOpacity,Button  } from "react-native";


/// 사진 데이터 ////
import TitleBack from '../assets/images/TitleBack.png'
import Pencil from '../assets/images/Pencil.png'
import HandsUp from '../assets/images/Handsup.png'
import Fire from '../assets/images/Fire.png'
import Bicycle from '../assets/images/Bicycle.png'
import Clock from '../assets/images/Clock.png'


/////////////////
import { Select, Box, CheckIcon, Center, NativeBaseProvider } from "native-base";
import { useIsFocused } from '@react-navigation/native';
import API from "../api/API";
import { useStore } from "../states";


const Community = ( { navigation } ) => {
  const { userNickName, userArea } = useStore();
  const isFocused = useIsFocused();

  const [RankCal,setRankCal] = useState('');
  const [RankDis,setRankDis] = useState('');
  const [RankTime,setRankTime] = useState('');
  useEffect(() =>{

    async function get() {
      const completed = false
      const response = await API.get(
        '/articles/recent'
      );
      

      setminioverview(response.data.articleList)
      

      
      if (!completed) setminioverview(response.data.articleList);
        console.log(minioverview)
    }

    async function weekly() {
      console.log(userArea)
      console.log(userNickName)
      const response = await API.get(
        `articles/rankings/${userNickName}?area=${userArea}`
      );
      console.log(response)
      setRankCal(response.data.rankingByCalorie)
      setRankDis(response.data.rankingsByDistance)
      setRankTime(response.data.rankingByTime)
    }
    get();
    weekly()
    
    

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
        <View
        style={{
          width:"100%",
          marginTop:"2%",
          borderBottomColor: '#969696',
          borderBottomWidth: 0.5,
        }}
      />
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
       <View style={{flexDirection:"row"}}>
            <Image style={{ resizeMode:"cover", width: 25, height : 25 ,marginTop:"4%",marginLeft:"7%"}} source={HandsUp} />
            <Image style={{ position:"absolute", width: 25, height: "5%" ,marginTop:"5.9%", marginLeft:"14.5%"}} source={TitleBack} />
            <Text style={{marginLeft: "3%",marginTop: "4%",color:"black", fontSize: 16, fontWeight:'bold'}}>내 랭킹</Text>
            </View>

        </View>
                <View style={{flexDirection: "row",marginTop:"6%"}}>
                <Text style={{fontSize: 13, marginLeft:"6%",color:"black"}}>소비 칼로리 랭킹</Text>
                <Text style={{fontSize: 13, marginLeft:"10.5%",color:"black"}}>주행 거리 랭킹</Text>
                <Text style={{fontSize: 13, marginLeft:"13.5%",color:"black"}}>누적 시간 랭킹</Text>
                </View>
            <View style={{ flexDirection: "row", height:"20%" }}> 
                
                <View style={{ width: "25.5%", marginLeft: 2 }}>

                  <View style={ styles.box }>
                  <View style={{marginLeft:"13%",marginTop:"1%",backgroundColor:"white",width:"75%",height:"75%",borderRadius:40}}>
                  <Image style={{ resizeMode:"cover", width: 30, height : 30 ,marginTop:"20%",marginLeft:"22%"}} source={Fire} />
                  </View>
                  <Text style={{color:'#484848',marginTop:"2%",textAlign:"center", width:"100%"}}>{RankCal} 등</Text>
                  </View>
                </View>

                <View style={{ width: "25.5%" , marginLeft: 30 }}>
 
                  <View style={ styles.box }>
                  <View style={{marginLeft:"13%",marginTop:"1%",backgroundColor:"white",width:"75%",height:"75%",borderRadius:40}}>
                  <Image style={{ resizeMode:"cover", width: 30, height : 30 ,marginTop:"20%",marginLeft:"22%"}} source={Bicycle} />
                  </View>
                  <Text style={{color:'#484848',marginTop:"2%",textAlign:"center", width:"100%"}}>{RankDis} 등</Text>
                  </View>
                </View>

                <View style={{ width: "25.5%" , marginLeft: 30 }}>
 
                  <View style={ styles.box }>
                  <View style={{marginLeft:"13%",marginTop:"1%",backgroundColor:"white",width:"75%",height:"75%",borderRadius:40}}>
                  <Image style={{ resizeMode:"cover", width: 30, height : 30 ,marginTop:"20%",marginLeft:"22%"}} source={Clock} />
                  </View>
                  <Text style={{color:'#484848',marginTop:"2%",width:"100%",textAlign:"center"}}>{RankTime} 등</Text>
                  </View>
                </View>

            
            </View>

            <View style={{flexDirection:"row"}}>
            <Image style={{ resizeMode:"cover", width: 25, height : 25 ,marginTop:"3%",marginLeft:"7%"}} source={Pencil} />
            <Image style={{ position:"absolute", width: 90, height: "15%" ,marginTop:"5%", marginLeft:"14.5%"}} source={TitleBack} />
            <Text style={{marginLeft: "3%",color:"black", marginTop: "2%",fontSize: 16, fontWeight:'bold'}}>커뮤니티 최근글</Text>
            </View>
            <View style={{marginTop: "5%",width: "90%", height: "47%"}}>
                <ScrollView showsVerticalScrollIndicator={false}>
                <View>{elements}</View>
                </ScrollView>
            </View>
            <TouchableOpacity style={styles.communityButton} onPress={() => navigation.navigate('CommunityBoard')}> 
              <Text>커뮤니티 모든 글 보기 > </Text>
            </TouchableOpacity> 
        </View>


    );
  }


const styles = StyleSheet.create({
  writebutton : {

    elevation : 5
  },
  communityButton : {
    marginLeft: "auto", 
    margin: "3%",
    marginTop: "8%",
    
  },
  communityButton2 : { 
    marginTop: "8%",
    width: "20%",
    
  },
  myrank: {
    marginLeft : "14%",
    marginTop : "8%",
    fontWeight: 'bold',
    fontSize : 17
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