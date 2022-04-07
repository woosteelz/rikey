import React, { Component, useState, useEffect } from "react";
import { KeyboardAvoidingView,Image, StyleSheet, View, Text,Dimensions,TouchableOpacity,
  TextInput, Keyboard, TouchableWithoutFeedback, Button,SafeAreaView,ScrollView,Alert } from "react-native";
import { Radio, NativeBaseProvider,Center,} from "native-base";

//사진 임포트한 부분
import Back from '../assets/images/Back.png'
import Rikey from '../assets/rikey.png'
import WooSteel from '../assets/defaultimage.jpg'
/////
import { useStore } from "../states";
import ImageResizer from 'react-native-image-resizer';
import MultipleImagePicker from '@baronha/react-native-multiple-image-picker';
import axios from "axios";
import API from "../api/API";



// const Textout = ( ) => {
//   return <Box alignItems="center" w="100%">
//       <TextArea h={410} placeholder="Text Area Placeholder" w="100%" maxW="100%" borderColor="black"/>
//     </Box>;
// };





const WritePage = ( { navigation } ) => {
  const { userId, userNickName } = useStore()
  const [imagesize, setImageSize] = useState({})
  const [cvalue, setCValue] = React.useState("FREE");
  const Boardvarious = () => {
    return<Radio.Group defaultValue="FREE" name="exampleGroup" value={cvalue} onChange={value => {setCValue(value)}} accessibilityLabel="favorite colorscheme">
      <Radio colorScheme="emerald" size="sm" value="FREE" my={1}>
        자유 게시판
      </Radio>

      <Radio colorScheme="warning" size="sm" value="RECRUIT" my={1}>
        라이더 모집
      </Radio>
    </Radio.Group>;
};
  
  // const Boardvarious = () => {
    
  //   const ref = React.useRef(null);
  //   React.useEffect(() => {
   
  
  //     ref.current.focus();
  //   }, [cvalue]);
  //   return <Radio.Group name="myRadioGroup" accessibilityLabel="favorite number" value={cvalue} onChange={nextValue => {
  //     setCValue(nextValue);
  //   }}>
  //       <Radio value="FREE" size="sm" my={1} >
  //         자유 게시판
  //       </Radio>
  //       <Radio value="RECRUIT" size="sm" my={1} ref={ref}>
  //         라이더 모집
  //       </Radio>
  //     </Radio.Group>;
  // };

  let imagedata = new FormData()


  // 이미지에 관해
  const uploadprocess = () => {
    
    if (images.length !== 0){
    return new Promise(async(resolve) => {
    const promiseList = images.map((item, index) => {
      
      return ImageResizer.createResizedImage(
        item.realPath,
        300,
        300,
        'JPEG',
        50,
        0,
        null
      ).then(response => {
        // setImageSize(response)
        imagedata.append("uploadFiles", {
          // uri: 'file://' + item.realPath,
          uri : response.uri,
          type: 'image/jpeg',
          name: item.fileName,
        });
      })
      .catch(err => {
        console.log(err)
      });
      

    

     
    });
    await Promise.all(promiseList)
    console.log(imagedata)
    // const response = API.post('articles/upload',{
    // body : imagedata
    // })
    // console.log(response)
    // 가보즈아~~
    
    fetch('http://j6c208.p.ssafy.io/api/articles/upload',{
      method : "post" ,
      body: imagedata
    }).then(res => res.json())
    .then(res => {

      
      

      console.log('리주',res.urls)
      resolve(res.urls)
    })
    .catch(err => {

      console.error("error uploading images: ", err);
    });
    
  })
  }else {
    console.log("빈이미지")
  }
  }



  // const newtry = async() => {
  //   console.log(images)
  //   const formData = new FormData();

  //   formData.append("uploadFile", {
  //     uri : images[0].realPath,
  //     name: images[0].fileName,
  //     type: images[0].mime,
  //     headers: {
  //       Accept: "application/json",
  //       "Content-Type" : "multipart/form-data"
  //     }
  //   });
  //   console.log(formData)
  //   const options = {
  //     method : "POST",
  //     body : formData
  //   };

  //   try {
  //     const response = await fetch(
  //       'http://j6c208.p.ssafy.io/api/users/upload',
  //       options

  //     );
  //     if (response) {
  //       console.warn("response", response);
  //     }
  //   } catch (error) {
  //     console.warn("에러", error);
  //   }
  //   };

  
  let imageList = [];
  const [images, setImages] = React.useState([])
  // const [boardimages, setBoardImages] = React.useState([])
  // useEffect(()=> {
  //   if (images.length === 0) {
    
  //   console.log("아직")
  //   }else {
  //     console.log("도착했다!!")
  //     uploadprocess()
  //     console.log('보드이미지',boardimages)
      
  //   }
  // },[images])

  // 이미지 프리뷰제공 코드
  const imagepreview = images.map( (item,key) => {
    return <View key={key}>
      {/* <Text>하잉</Text> */}
      <Image style={{height: 100, width: 100 }} source={{uri: "file://"+ item.realPath}} />
    </View>
  })


  //////////////////
   const handleImagePicker = async () => {
      try {
          const image = await MultipleImagePicker.openPicker({
              mediaType: "image",
              usedCameraButton: true,
              isPreview: true,
              isExportThumbnail: true,
              maxSelectedAssets: 3,
              selectedAssets: 3,
              doneTitle: '완료',
              cancelTitle: '취소',
              tapHereToChange: '변경하려면 여기를 누르세요.',
              singleSelectedMode: false
              // selectedColor: '#f9813a',
          });
          
          console.log(image)
          setImages(image)

          image.map(imag => {
            imageList.push({
              filename: imag.fileName,
              path: imag.path,
              data: imag.data,

            })
          })

          

          

          // console.log(JSON.stringify(imagedata));
        
        
      } catch (e) {

          console.log('error', e);
      } finally {
          console.log('finally')
         
      }
  };
    // 글쓰기버튼 클릭
    const writeprocessTemp = async() => {
      
      if (!onChangeTitle) {
        Alert.alert("제목 미입력", "제목을 입력해주세요.")
        return
      }else if (!onChangeContent) {
        Alert.alert("내용 미입력", "내용을 입력하세요")
        return
      }
      
    

      const urls = await uploadprocess()
      const response = await axios.post(
        "http://j6c208.p.ssafy.io/api/articles",
        {
          content : onChangeContent,
          title : onChangeTitle,
          category : cvalue,
          pics : urls,
          userId : userId
        }
      );
      if (response) {
        navigation.navigate('CommunityDetail', {articleId: response.data.article, author : userNickName})

      } else {
        alert("오류발생")
      }
    };


    // 텍스트 박스에관해

    
    const screenWidth = Dimensions.get('window').width;
    const [onChangeTitle, setonChangeTitle] = React.useState("");
    const [onChangeContent, setonChangeContent] = React.useState("");



    return (
      <TouchableWithoutFeedback onPress={Keyboard.dismiss}>
      <ScrollView>
      <KeyboardAvoidingView style={{ flex: 1 }} behavior="padding" enabled>
      

        <View>
          <View style={{flexDirection: "row", justifyContent: "space-between"}}>
          <TouchableOpacity style={styles.communityButton2} onPress={() => navigation.goBack() }>
          <Image style={{ resizeMode: "center", height: 20, width: 20, marginLeft:"20%"}} source={Back} />
          </TouchableOpacity>
          <Text style={{fontSize: 25, fontWeight: 'bold',marginTop:'6%',marginRight:"5%"}}>글쓰기</Text>
          {/* <Image style={{ resizeMode: "cover", height: 60, width: 120, marginTop:"3%"}} source={Rikey}  /> */}
          <View style={{ marginRight: "5%", marginTop: "6.5%"}}>
          <Button
          
          title="작성"
          color="#00C689"
          onPress={() => writeprocessTemp()}
          />
          </View>
          </View>
            <NativeBaseProvider  >
              <Center style={{marginRight:"60%"}} flex={1} px="1">
                  <Boardvarious />
              </Center>
            </NativeBaseProvider>

            {/* for 문으로 image 한개씩띄워야됨 */}

              
            
          <SafeAreaView>
          
            <TextInput
              style={styles.inputTitle}
              onChangeText={setonChangeTitle}
              value={onChangeTitle}
              placeholder="제목"
              placeholderTextColor="#242424" 
            />

            <TextInput
              multiline
              numberOfLines={15}
              maxLength={450}
              style={styles.inputContent}
              onChangeText={setonChangeContent}
              value={onChangeContent}
              placeholder="내용을 입력하세요..."
              placeholderTextColor="#242424" 
            />
            
            {/* <NativeBaseProvider>
              <Center flex={1} px="3">
                  <Textout />
              </Center>
            </NativeBaseProvider> */}
         
         <View>
            <View
            style={{
              width:"95%",
              marginTop:"2%",
              marginLeft:"2.5%",
              marginBottom:"3%",
              borderBottomColor: '#969696',
              borderBottomWidth: 0.5,
            }}
          />
          <TouchableOpacity

            
            onPress={handleImagePicker}
          >
          {
            images.length !== 0 ?
          <View style={{flexDirection: "row",justifyContent:'space-around'}}>
 
          
            {imagepreview}

          </View>
          : 
          <Image style={{marginLeft: "5%", height: 100, width: 100}} source={WooSteel} />
          }

         
          </TouchableOpacity>
          


        </View>

          </SafeAreaView>
          

        </View>
        
        
        
        </KeyboardAvoidingView>
        </ScrollView>
        </TouchableWithoutFeedback>
    
    );
         
          };


const styles = StyleSheet.create({
  
  communityButton2 : { 
    marginTop: "8%",
    width: "20%",
    
  },
  inputTitle: {
    height: 36,
    margin: 12,
    color:'black',
    borderBottomWidth: 1,
    borderBottomColor: "#AAAAAA",
    padding: 4,
  },
  inputContent: {
    textAlign:"left",
    color:'black',
    textAlignVertical: "top",
    borderRadius: 7,
    height: "50%",
    margin: 12,

    padding: 5,
  },
  
});
export default WritePage;