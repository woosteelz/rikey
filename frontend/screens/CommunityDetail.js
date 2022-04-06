import axios from "axios";
import React, { Component, useState, useEffect, createRef } from "react";
import { TouchableWithoutFeedback ,Alert, Button, Keyboard, ScrollView, KeyboardAvoidingView,Image, StyleSheet, View, Text,Dimensions,TouchableOpacity,TextInput, SafeAreaView } from "react-native";

//사진
import Rikey from '../assets/rikey.png'
import commentbutton from '../assets/commentbutton.png'
import hamburgerbutton from '../assets/hamburgerbutton.png'
import defaultprofiepic from '../assets/images/Default.png'
import LikeButton from '../assets/images/FilledHeart.png'
import DisLikeButton from '../assets/images/Heart.png'
import Back from '../assets/images/Back.png'
// 상태관리
import { useStore } from "../states"
import { useIsFocused } from '@react-navigation/native';
// 시간관리
import moment from 'moment'
import 'moment/locale/ko';
//API
import API from "../api/API";
// 하단시트모달
import ActionSheet, { SheetManager } from "react-native-actions-sheet";




const ActionSheetRef = createRef();
const nonActionSheetRef = createRef();
const articleActionSheetRef = createRef();
let actionSheet;

const CommunityDetail = ( { props, route, navigation} ) => {
  const isFocused = useIsFocused();
  const { userId,userNickName } = useStore()
  const screenWidth = Dimensions.get('window').width;
  const [text, onChangeText] = React.useState("");
  const articleId = route.params.articleId;
  const articleAuthor = route.params.author;

  // 글 제목,내용등
  const [articleContent, setArticleContent] = useState('');
  const [articleTitle, setArticleTitle] = useState('');
  const [articleTime, setArticleTime] = useState('');
  const [articleComment, setArticleComment] = useState([]);
  const [articleBoard, setArticleBoard] = useState('');
  const [articlePics, setArticlePics] = useState([]);
  const [articleProfilePic, setprofilePic] = useState('');
  const [islike,setIslike] = useState(false);
  const [likeCnt,setLikeCnt] = useState('');
  
  
  // 게시글을 API로 호출하는 함수
  const articleCall = async() => {
 
    const response = await axios.get(`http://j6c208.p.ssafy.io/api/articles/${articleId}?nickName=${userNickName}`)
    console.log(response.data)
    setArticleContent(response.data.article.content)
    setArticleTitle(response.data.article.title)
    setArticleComment(response.data.article.commentList)
    setArticleBoard(response.data.article.category)
    console.log(response.data.article.isLike)
    setIslike(response.data.article.isLike)
    setArticlePics(response.data.article.pics)
    setprofilePic(response.data.article.profilePic)
    setLikeCnt(response.data.article.likeCnt)
    var detaildate = moment(response.data.article.createdTime).format("YYYY-MM-DD HH:mm:ss")
    setArticleTime(moment(detaildate).fromNow())
    console.log(articleComment)
  }
  useEffect( () => {
    articleCall()
    
  },[isFocused])
    
    


    // 댓글 삭제 로직
    const removeComment = async (commentId) => {
      const response = await API.delete(`/comments/${commentId}`,{

        data : {
          userId : userId
        }
        
      })
      articleCall()
    }

    // 게시글 삭제 로직
    const removeArticle = async() => {
      const response = await API.delete(`/articles/${articleId}`,{

        data : {
          userId : userId
        }
        
      })
      console.log(response)
      navigation.navigate('CommunityBoard')
    }

    // 댓글 표현하는 부분
    const [tempremove, setTempremove] = useState('');
    const sheetout = (sheet) => {
      SheetManager.hideAll()
    }
    
    // 댓글 생성될때마다 갱신되야되는데...시트모달갱신이안된다.."
    // const commentboard = articleComment.map( (item,key) => {
    //   const commentTime = moment(item.createdTime).format("YYYY-MM-DD HH:mm:ss")
    //   const gapCommentTime = moment(commentTime).fromNow()
    //   console.log("아티클코맨트", articleComment)
    //   console.log(item.commentId)
      
    //   return <View key={key} style={{marginBottom: "2%"}}>
    //      <TouchableOpacity onPress={ () => {
    //             if(userNickName === item.author) {
    //             SheetManager.show(item.commentId);
    //             }else{
    //             SheetManager.show("nonuser_sheet");
    //             }
    //             // setModalVisible(true);
    //             // setTempremove(item.commentId)
    //           }}>
    //     <View style={{flexDirection: "row"}}>
    //       { item.profilePic ?
    //         <Image style={{ resizeMode: "cover", height: 30, width: 30 ,marginRight: "3%" ,borderRadius: 15}} source={{uri : item.profilePic}}/>
    //         :
    //         <Image style={{ resizeMode: "cover", height: 30, width: 30 ,marginRight: "3%"}} source={defaultprofiepic}/>
    //       }
    //       <Text style={{fontWeight:"bold", color:'#282828',marginTop:"1%"}}>{item.author}</Text>
    //       <Text style={{marginLeft: "5%", marginTop:"1%"}}>{gapCommentTime}</Text>
    //       {
    //         userNickName === item.author ?
    //         <View>
             
    //           {/* <Image style={{ marginLeft: "auto" ,resizeMode: "cover", height: 20, width: 20}} source={hamburgerbutton} /> */}
              

    //           <ActionSheet id={item.commentId} ref={ActionSheetRef}>
    //           <View style={{height: 120}}>
    //                 <TouchableOpacity onPress={() => removeComment(item.commentId)}>
    //                 <Text style={{fontSize: 20, margin:"4%"}}>삭제하기</Text>
    //                 </TouchableOpacity>
    //                 <View style={{ borderBottomColor: '#484848', borderBottomWidth: 0.5,height: "0.1%", width: "100%"}} />
    //                 <TouchableOpacity onPress={() => sheetout("카")} >
    //                 <Text style={{fontSize: 20 ,margin: "4%" }}>닫기</Text>   
    //                 </TouchableOpacity>
    //           </View>
    //         </ActionSheet>
            
    //         </View>
            

            
    //         :
    //         <ActionSheet id="nonuser_sheet" ref={nonActionSheetRef}>
    //           <View style={{height: 120}}>
    //                 <TouchableOpacity onPress={() => {
    //                   Alert.alert("신고완료", '신고되었습니다. 조속히 처리하겠습니다.')
    //                   sheetout("nonuser_sheet")
    //                 }}>
    //                 <Text style={{fontSize: 20, margin:"4%"}}>신고하기</Text>
    //                 </TouchableOpacity>
    //                 <View style={{ borderBottomColor: '#484848', borderBottomWidth: 0.5,height: "0.1%", width: "100%"}} />
    //                 <TouchableOpacity onPress={() => sheetout("nonuser_sheet")}>
    //                 <Text style={{fontSize: 20 ,margin: "4%" }} >닫기</Text>  
    //                 </TouchableOpacity> 
    //           </View>
    //         </ActionSheet>

    //       }
    //     </View>
    //     <View style={{marginTop:"1%"}}>
    //     <Text>{item.content}</Text>

    //     </View>
    //     </TouchableOpacity>
        
    //   </View>
    // })

    //사진 맵함수
    const ArticlePicutres = articlePics.map( (item, key) => {
      return <View key={key}>
      <Image style={{resizeMode:"cover", height: 100, width: 100 , marginRight: "7%"}} source={{uri: item}} />
    </View>
    })


    ////
    const CommentBox = ({props}) => {

    return(
          <View style={{marginBottom: "2%"}}>
         <TouchableOpacity onPress={ () => {
                if(userNickName === props.author) {
                SheetManager.show(props.commentId);
                }else{
                SheetManager.show("nonuser_sheet");
                }
                // setModalVisible(true);
                // setTempremove(item.commentId)
              }}>
        <View style={{flexDirection: "row"}}>
          { props.profilePic ?
            <Image style={{ resizeMode: "cover", height: 30, width: 30 ,marginRight: "3%" ,borderRadius: 15}} source={{uri : props.profilePic}}/>
            :
            <Image style={{ resizeMode: "cover", height: 30, width: 30 ,marginRight: "3%"}} source={defaultprofiepic}/>
          }
          <Text style={{fontWeight:"bold", color:'#282828',marginTop:"1%"}}>{props.author}</Text>
          <Text style={{marginLeft: "5%", marginTop:"1%"}}>{moment(props.createdTime).fromNow()}</Text>
          {
            userNickName === props.author ?
            <View>
             
              {/* <Image style={{ marginLeft: "auto" ,resizeMode: "cover", height: 20, width: 20}} source={hamburgerbutton} /> */}
              

              <ActionSheet id={props.commentId} ref={ActionSheetRef}>
              <View style={{height: 120}}>
                    <TouchableOpacity onPress={() => removeComment(props.commentId)}>
                    <Text style={{fontSize: 20, margin:"4%"}}>삭제하기</Text>
                    </TouchableOpacity>
                    <View style={{ borderBottomColor: '#484848', borderBottomWidth: 0.5,height: "0.1%", width: "100%"}} />
                    <TouchableOpacity onPress={() => sheetout("카")} >
                    <Text style={{fontSize: 20 ,margin: "4%" }}>닫기</Text>   
                    </TouchableOpacity>
              </View>
            </ActionSheet>
            
            </View>
            

            
            :
            <ActionSheet id="nonuser_sheet" ref={nonActionSheetRef}>
              <View style={{height: 120}}>
                    <TouchableOpacity onPress={() => {
                      Alert.alert("신고완료", '신고되었습니다. 조속히 처리하겠습니다.')
                      sheetout("nonuser_sheet")
                    }}>
                    <Text style={{fontSize: 20, margin:"4%"}}>신고하기</Text>
                    </TouchableOpacity>
                    <View style={{ borderBottomColor: '#484848', borderBottomWidth: 0.5,height: "0.1%", width: "100%"}} />
                    <TouchableOpacity onPress={() => sheetout("nonuser_sheet")}>
                    <Text style={{fontSize: 20 ,margin: "4%" }} >닫기</Text>  
                    </TouchableOpacity> 
              </View>
            </ActionSheet>

          }
        </View>
        <View style={{marginTop:"1%",marginLeft:"11%"}}>
        <Text style={{color:"black"}}>{props.content}</Text>

        </View>
        </TouchableOpacity>
        
      </View>
    )}
    ////////////////////

    // 댓글작성 로직
    const WriteComment = async() => {
      if (text) {
      const response  =  await axios.post('http://j6c208.p.ssafy.io/api/comments/',{
        articleId : articleId,
        content : text,
        userId : userId
      })
      onChangeText('')
      articleCall()
  
      console.log(response)
    }else{

      Alert.alert('작성 오류','한글자 이상 작성해야 합니다.')
    }
  }
    
    // 좋아요 로직
    const SendIsLike = () =>{
      
      { islike ?
        
        UnLikePost()
        
        :
        
        LikePost()

      }
    }
      // 좋아요 전송 로직
      const LikePost = async() =>{
        const response = await axios.post('http://j6c208.p.ssafy.io/api/likes',{
          
          articleId : articleId,
          userId : userId
        })
        console.log(response)
        setLikeCnt(likeCnt+1)
        setIslike(true)

      }

      // 좋아요 삭제 로직
      const UnLikePost = async() =>{
        const response = await axios.delete('http://j6c208.p.ssafy.io/api/likes',{

          data : {
          articleId : articleId,
          userId : userId
          }

        })
        console.log(response)
        setLikeCnt(likeCnt-1)
        setIslike(false)

      }
    ///


    return (
      <TouchableWithoutFeedback onPress={Keyboard.dismiss}>
      <KeyboardAvoidingView style={{ flex: 1 }} behavior="position" enabled>
      <ScrollView>
      
      <View>
        
        <View>
          <View style={{flexDirection: "row", justifyContent: "space-around"}}>
          <TouchableOpacity style={styles.communityButton2} onPress={() => 
            {(navigation.goBack() == navigation.navigate('WritePage')) || (navigation.goBack() == navigation.navigate('UpdatePage')) ?
            navigation.navigate('CommunityBoard')
            
            // navigation.goBack()
            
            :
              navigation.goBack()
            }
          }> 
                <Image style={{ resizeMode: "center", height: 20, width: 20, marginLeft:"2%"}} source={Back} />
          </TouchableOpacity>
          <Image style={{ resizeMode: "cover", height: 60, width: 120, marginTop:"3%"}} source={Rikey} />
          <View style={{marginRight : "15%"}}></View>
          </View>

        <View style={{ flexDirection: "row", marginLeft: "6%",marginTop:"2%"}}>
        { (articleProfilePic) ?
        <Image style={{ resizeMode: "cover", height: 60, width: 60, borderRadius: 50}} source={{uri : articleProfilePic}} />
        :
        <Image style={{ resizeMode: "cover", height: 60, width: 60}} source={defaultprofiepic} />
        }
        <View>
          <View style={{flexDirection: "row"}}>
          <Text style={{fontWeight: "bold", marginLeft:"5%",fontSize:20, color:"black"}}>{articleAuthor}</Text>
          { (articleAuthor === userNickName) ?
          <TouchableOpacity style={{position:"absolute", marginLeft:screenWidth - screenWidth*0.3,marginTop:"1%"}} onPress={()=> SheetManager.show("article_sheet")}>
          <Image style={{resizeMode:"cover", width:20, height:20}} source={hamburgerbutton}/>
          
          <ActionSheet id="article_sheet" ref={articleActionSheetRef}>
          <View style={{height: 170}}>
                    <TouchableOpacity onPress={() => {
                      sheetout()
                      removeArticle()
                    }}>
                    <Text style={{fontSize: 20, margin:"4%"}}>글 삭제하기</Text>
                    </TouchableOpacity>
                    <View style={{ borderBottomColor: '#484848', borderBottomWidth: 0.5,height: "0.1%", width: "100%"}} />
                    <TouchableOpacity onPress={() => {
                    sheetout("article_sheet")
                    navigation.navigate('UpdatePage', 
                    {onChangeContent : articleContent, 
                    onChangeTitle : articleTitle, 
                    boardvarious : articleBoard,
                    pictures : articlePics,
                    GivenArticleId : articleId
                    })
                    
                    }
                    
                    }>
                    <Text style={{fontSize: 20, margin:"4%"}}>수정하기</Text>
                    </TouchableOpacity>
                    <View style={{ borderBottomColor: '#484848', borderBottomWidth: 0.5,height: "0.1%", width: "100%"}} />
                    <TouchableOpacity onPress={() => sheetout("article_sheet")} >
                    <Text style={{fontSize: 20 ,margin: "4%" }}>닫기</Text>   
                    </TouchableOpacity>
              </View>
          </ActionSheet>
          </TouchableOpacity>
          :
          <View style={{width: "68%"}}/>
          }
          </View>
  
          <Text style={{marginLeft: "8%"}}>{articleTime}</Text>
   
        </View>

        </View>
        
        <View style={{marginTop: "5%", width: "100%", height: "100%"}}>
          <Text style={{marginLeft:"6%" , marginBottom: "5%" , fontSize: 22, color:"black"}}>{articleTitle}</Text>
          <Text style={{marginLeft:"6%" ,fontSize: 14, color:"#484848"}}>
          {articleContent}
          </Text>
          <View style={{ flexDirection: "row",marginLeft:"5%", marginTop:"5%",marginRight:"3%"}} >
          {ArticlePicutres}
          </View>
          
          { islike ?
          <View style={{flexDirection:'row',  marginRight:"4%", marginLeft:"auto", marginTop:"4%"}}>
          <TouchableOpacity onPress={()=>SendIsLike()}>
          <Image style={{resizeMode:"center", width: 30, height: 30}} source={LikeButton}/>
          </TouchableOpacity>
          <Text style={{marginTop:"1.5%", marginLeft:"2%"}}>{likeCnt}</Text>
          </View>
          :
          <View style={{flexDirection:'row',  marginRight:"4%", marginLeft:"auto", marginTop:"4%"}}>
          <TouchableOpacity onPress={()=>SendIsLike()}>
          <Image style={{resizeMode:"center", width: 30, height: 30}} source={DisLikeButton}/>
          </TouchableOpacity>
          <Text style={{marginTop:"1.5%", marginLeft:"2%"}}>{likeCnt}</Text>
          </View>
          }
          <View>
          <View style={{width:"100%",backgroundColor: "#DDDDDD",marginTop:"3%"}}>
          
          <SafeAreaView>
            <TextInput
              multiline
              maxLength={100}
              style={styles.input}
              onChangeText={onChangeText}
              value={text}
              placeholder="댓글을 입력하세요.."
              placeholderTextColor="#242424" 

            />
          <TouchableOpacity style={{ position:'absolute',marginLeft:"90%",marginTop:"3%",zIndex:2}}onPress={() => WriteComment()}>
          <Image style={{resizeMode: "center", height: 25, width: 25}} source={commentbutton}/>
          </TouchableOpacity>
          </SafeAreaView>

          </View>
          {/* <View style={{ backgroundColor: "#969696", marginTop:"2.6%", height:"10%",width:"10%"}} >
          <TouchableOpacity onPress={() => WriteComment()}>
            <View style={{position:'absolute' , marginLeft: "17%" ,marginTop: "25%"}}>
            <Image style={{ resizeMode: "cover", height: 25, width: 25}} source={commentbutton}/>
            </View>
          </TouchableOpacity>
          </View> */}
        </View>


          
        <View style={{marginTop: "5%" ,marginLeft: "3%"}}>
          {/* {commentboard} */}
          {articleComment.map( (item,key) => {
            return <CommentBox props={item} key={key}/>
          })}
        </View>
          
            
        </View>
          
        </View>
        </View>
        </ScrollView>
        </KeyboardAvoidingView>
        </TouchableWithoutFeedback>
    );
  }


const styles = StyleSheet.create({
  input : {
    width: "90%",
    paddingLeft: 15,

    
    zIndex: 1,
  },
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
