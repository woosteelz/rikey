import axios from "axios";
import React, { Component, useState, useEffect } from "react";
import { KeyboardAvoidingView,Image, StyleSheet, View, Text,Dimensions,TouchableOpacity,TextInput, SafeAreaView } from "react-native";
import Rikey from '../assets/rikey.png'
import guywoo from '../assets/정우.jpg'

const CommunityDetail = ( { route, navigation} ) => {
    
    const screenWidth = Dimensions.get('window').width;
    const [text, onChangeText] = React.useState("");
    const articleId = route.params.articleId;
    const articleAuthor = route.params.author;

    // 글 제목,내용등
    const [articleCotnent, setArticleContent] = useState('');
    const [articleTitle, setArticleTitle] = useState('제목 입력중');

    const articleCall = async() => {
      const response = await axios.get(`http://j6c208.p.ssafy.io/api/articles/${articleId}?nickName=${articleAuthor}`)
      console.log(response.data.article.content)
      setArticleContent(response.data.article.content)
      setArticleTitle(response.data.article.title)
        
    }
    useEffect( () => {
      articleCall()
    },[])
    

    return (
      <KeyboardAvoidingView style={{ flex: 1 }} behavior="padding" enabled>
      <View>

        <View>
          <View style={{flexDirection: "row", justifyContent: "space-around"}}>
          <TouchableOpacity style={styles.communityButton2} onPress={() => navigation.goBack()}> 
                <Text> ← 뒤로 </Text>
          </TouchableOpacity>
          <Image style={{ resizeMode: "cover", height: 80, width: 160}} source={Rikey} />
          <View style={{marginRight : "15%"}}></View>
          </View>

        <View style={{ flexDirection: "row", marginLeft: "5%"}}> 
        <Image style={{ resizeMode: "cover", height: 60, width: 60, borderRadius: 50}} source={guywoo} />
        <View>
          <Text style={{fontWeight: "bold",fontSize:20, color:"black", marginLeft: "12%",marginBottom: 0}}>{articleAuthor}</Text>
          <Text style={{marginLeft: "13%"}}>8 시간 전</Text>
        </View>

        </View>
        
        <View style={{marginTop: "5%", marginLeft:"5%", width: "90%", height: "60%"}}>
          <Text>{articleTitle}</Text>
          <Text>제목나와야됨</Text>
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
export default CommunityDetail;