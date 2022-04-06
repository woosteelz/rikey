import React, { Component, useState, useEffect } from "react";
import { Image, StyleSheet, View, Text, ScrollView, Dimensions,LinearLayout,TouchableOpacity  } from "react-native";
// 사진 임포트
import Rikey from '../assets/rikey.png'
import writebutton from '../assets/images/WriteButton.png'
import TitleBack from '../assets/images/TitleBack.png'
import Pencil from '../assets/images/Pencil.png'
import HOT from '../assets/images/HOT.png'
import Search from '../assets/images/Search.png'
import Back from '../assets/images/Back.png'
/////
import { Button, Modal, FormControl, Input, Select, Box, CheckIcon, Center, NativeBaseProvider } from "native-base";
import { useIsFocused } from '@react-navigation/native';
import API from "../api/API";
import axios from "axios";





const CommunityBoard = ( { navigation } ) => {
  const isFocused = useIsFocused();
  const [tempboard, setTempboard] = useState([]);
  const [hotArticleBoard, setHotArticleBoard] = useState([]);
  const [service, setService] = React.useState("All");
  

  useEffect(() => {
    console.log(service)
    // console.log('여기까지?')
    boardservice()

  },[service])

  useEffect(() => {
    async function get() {
      const res = await API.get(
        '/articles?category=ALL'
      );
      console.log('시작되었다')
      console.log(res.data.hitArticleList)
      setHotArticleBoard(res.data.hitArticleList)
      console.log(res.data.articleList)
      setTempboard(res.data.articleList)
    }
    get();
    return
  },[isFocused])
  
    



  




  //  useEffect 통해서 게시판변화에따른 게시판 제공 API 사용
  const boardservice = async() => {
    const response = await API.get(`/articles?category=${service}`);
    
    console.log("뭐야!!")
    setHotArticleBoard(response.data.hitArticleList)
    console.log(response.data.articleList)
    setTempboard(response.data.articleList)
    console.log('템프보드')
    console.log(tempboard)
    console.log('템프보드')
  }
  //검색 로직

  //검색 모달
  const SearchProcess = () => {
    const [showModal, setShowModal] = useState(false);
    const [search, setSearch] = useState('');
    // 검색로직
    const SearchRequest = async () => {
      console.log(search)
      const response = await API.get(`/articles/search?keyword=${search}`)
      setHotArticleBoard([])
      setTempboard(response.data.articleList)
      console.log(response.data.articleList)
      
    }
    ///
    return <Center>
        <TouchableOpacity onPress={() => setShowModal(true)} >
        <Image style={{ resizeMode: "center", height: 25, width: 25, marginTop:"8%",marginLeft:"40%"}} source={Search} />
        </TouchableOpacity>
        <Modal isOpen={showModal} onClose={() => setShowModal(false)}>
          <Modal.Content maxWidth="400px">
            <Modal.CloseButton />
            <Modal.Header>검색</Modal.Header>
            <Modal.Body>
              <FormControl>
                <Input value={search} onChangeText={setSearch} placeholder="검색어를 입력하세요..."/>
              </FormControl>
            </Modal.Body>
            <Modal.Footer>
              <Button.Group space={2}>
                <Button variant="ghost" colorScheme="blueGray" onPress={() => {
                setShowModal(false);
              }}>
                  닫기
                </Button>
                <Button style={{backgroundColor:'#00C689'}} onPress={() => {
                SearchRequest()
                setShowModal(false);
              }}>
                  검색
                </Button>
              </Button.Group>
            </Modal.Footer>
          </Modal.Content>
        </Modal>
      </Center>;
  };


  ////////
  // 핫 게시물이 보여지는 부분
  const Hotvariousboard = hotArticleBoard.map( (item,key) => {
    return <View key={key} style={{marginLeft : "8%", marginBottom: "4%"}}>
      <TouchableOpacity onPress={ ()=> navigation.navigate('CommunityDetail', {articleId : item.articleId , author : item.author})}>
      <View style={{flexDirection:'row'}}>
      <Image style={{resizeMode:"center", width: 25, height: 25}} source={HOT} />
      <Text style={{fontSize:15, fontWeight:'bold', color:'#424242', paddingTop:2, marginBottom: 3,marginLeft:"1%"}}>{item.title}</Text>
      </View>
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
      <View
        style={{
          width:"100%",
          marginTop:"4%",
          borderBottomColor: '#969696',
          borderBottomWidth: 0.5,
        }}
      />
      </View>
      

      
  })

  // 핫이 아닌 일반 게시물을 보여지게하는 부분
  const variousboard = tempboard.map( (item,key) => {

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




  




  const Example = () => {

    // let [service, setService] = React.useState("");
    return <Center>
        <Box w="3/4" size="10" maxH="10" maxW="100" >
          <Select variant="filled" selectedValue={service} minWidth="100" accessibilityLabel="Choose Service" placeholder="전체" _selectedItem={{
          bg: "teal.600",
          endIcon: <CheckIcon size="2" />
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
          <Image style={{ resizeMode: "center", height: 20, width: 20, marginLeft:"20%"}} source={Back} />

          </TouchableOpacity>
          <Image style={{ resizeMode: "cover", height: 60, width: 120, marginTop:"3%",marginLeft:"16%"}} source={Rikey} />
          <NativeBaseProvider>
            <Center flex={1} px="3">
                <SearchProcess />
            </Center>
          </NativeBaseProvider>
          {/* <Image style={{ resizeMode: "center", height: 25, width: 25, marginTop:"7%"}} source={Search} /> */}

          </View>

        <View style={{ flexDirection: "row"}}>
   
        <Image style={{ resizeMode:"cover", width: 25, height : 25 ,marginTop:"5%",marginLeft:"7%"}} source={Pencil} />
        <Image style={{ position:"absolute", width: 90, height: "10%" ,marginTop:"7%", marginLeft:"14.5%"}} source={TitleBack} />
        { (service === "FREE") ?
        <Text style={{ fontSize: 16, marginLeft: "2%", fontWeight:"bold", marginTop: "5%",width : "30%"}}> 자유로운 게시판</Text>
        :
        (service === "RECRUIT" ?
        <Text style={{ fontSize: 16, marginLeft: "2%", fontWeight:"bold", marginTop: "5%",width : "30%"}}> 라이딩크루 모집</Text>
        :
        <Text style={{ fontSize: 16, marginLeft: "2%", color:"black", fontWeight:"bold", marginTop: "5%",width : "40%"}}> 커뮤니티 게시판</Text>
        )
        }
        {/* <Text style={{ flex:0.9, marginTop: "5%", textAlign: "right"}} >분류 : </Text> */}
         <NativeBaseProvider>
            <Center style={{marginTop: "7%",marginLeft:"auto",marginRight:"45%"}}>
                <Example />
            </Center>
          </NativeBaseProvider>
          

        </View>
        
        <View style={{ marginTop: "5%", width: "90%", height: "77%"}}>
        <ScrollView showsVerticalScrollIndicator={false} >
          <View>{Hotvariousboard}</View>
          <View>{variousboard}</View>
        </ScrollView>
        </View>
        
        {/* 글쓰기 버튼 */}
        <View style={{flex:1}}>

        <View style={{marginRight:"5%",marginBottom:"5%", position:'absolute',bottom:0,alignSelf:'flex-end'}}>
          <TouchableOpacity style={styles.writebutton} onPress={() => navigation.navigate('WritePage')}> 
                <Image style={{resizeMode: "cover", height:70, width: 70}} source={writebutton} />
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