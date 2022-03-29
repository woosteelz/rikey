import React, { Component, useState, useEffect } from "react";
import { KeyboardAvoidingView,Image, StyleSheet, View, Text,Dimensions,TouchableOpacity,
  TextInput, Keyboard, TouchableWithoutFeedback, Button,SafeAreaView,ScrollView,Container } from "react-native";
import { Radio, NativeBaseProvider, TextArea, Box, Center,Select, CheckIcon} from "native-base";

import Rikey from '../assets/rikey.png'
import WooSteel from '../assets/defaultimage.jpg'
import { color } from "native-base/lib/typescript/theme/styled-system";
import MultipleImagePicker from '@baronha/react-native-multiple-image-picker';
import axios from "axios";



const Boardvarious = () => {
  const [value, setValue] = React.useState("FREE");
  const ref = React.useRef(null);
  React.useEffect(() => {
    console.log(value); // ref.current.setNativeProps({
    //   backgroundColor: 'red',
    // });
    // ref.current.setNativeProps({
    //   backgroundColor: 'red',
    // });

    ref.current.focus();
  }, [value]);
  return <Radio.Group name="myRadioGroup" accessibilityLabel="favorite number" value={value} onChange={nextValue => {
    setValue(nextValue);
  }}>
      <Radio value="FREE" size="sm" my={1} >
        자유 게시판
      </Radio>
      <Radio value="RECRUIT" size="sm" my={1} ref={ref}>
        라이더 모집
      </Radio>
    </Radio.Group>;
};
// const Textout = ( ) => {
//   return <Box alignItems="center" w="100%">
//       <TextArea h={410} placeholder="Text Area Placeholder" w="100%" maxW="100%" borderColor="black"/>
//     </Box>;
// };







const WritePage = ( { navigation } ) => {
  let imagedata = new FormData()


  // 이미지에 관해

  const uploadprocess = () => {
    images.map((item, index) => {
      imagedata.append("uploadFiles", {
        uri: 'file://' + item.realPath,
        type: item.mime,
        name: item.fileName,
      });
    });
    console.log("내놔제발 ㅠㅠ", imagedata)
    console.log(JSON.stringify(imagedata))

    // 가보즈아~~

    fetch('http://j6c208.p.ssafy.io/api/users/upload',{
      method : "post" ,
      body: imagedata
    }).then(res => res.json())
    .then(res => {
      console.log(res)
      alert("Success")
    })
    .catch(err => {
      console.log("이게들어갔음", imagedata)
      console.error("error uploading images: ", err);
    });

    // console.log(JSON.stringify(imagedata));
  }



  const newtry = async() => {
    console.log(images)
    const formData = new FormData();

    formData.append("uploadFile", {
      uri : images[0].realPath,
      name: images[0].fileName,
      type: images[0].mime,
      headers: {
        Accept: "application/json",
        "Content-Type" : "multipart/form-data"
      }
    });
    console.log(formData)
    const options = {
      method : "POST",
      body : formData
    };

    try {
      const response = await fetch(
        'http://j6c208.p.ssafy.io/api/users/upload',
        options

      );
      if (response) {
        console.warn("response", response);
      }
    } catch (error) {
      console.warn("에러", error);
    }
    };






  const sooArticle = async() => {
    const uploadFiles = new FormData();
    images.forEach((image, i) => {
      uploadFiles.append('uploadFiles', {
        
        uri : image.realPath,
        name: image.fileName,
        type: image.mime ,
      });
    });
    const writeurl = 'http://j6c208.p.ssafy.io/api/articles/upload'
    axios({
      method : "post",
      url : writeurl,
      data : uploadFiles,
    })
  }

const sendImages =() => {
    
    let formData = new FormData();
    for(var i=0;i<images.length;i++) {
      formData.append("uploadFiles", 
      {
        uri : images[i].realPath,
        name : images[i].fileName,
        type: images[i].mime
      });
    }
    console.log(formData)
    const url = "http://j6c208.p.ssafy.io/api/articles/upload";
    const body = formData;
    const header = {
      "Content-Type": "multipart/form-data",
    };
    try {
      axios
        .post(url, body, header)
        .then((response) => console.log(response))
        .catch((e) => console.log(e.message));
    } catch (e) {
      console.log(e.message);
    }
  }

  const newAritcle = async() => {
    
    console.log(typeof(images))
    console.log(images)
    let newArticleForm = new FormData();
    let pipi = new Array();
    
   
    
    // images.forEach(image=> setRealpostman(image))
    
    images.map( (picture,index) => {
      var photo = {
        uri : picture.realPath,
        type : picture.mime,
        name: picture.fileName
      }
      console.log(photo)
      newArticleForm.append('uploadFile',photo)

    })
    
    console.log(newArticleForm)
    
    const writeturl = 'http://j6c208.p.ssafy.io/api/users/upload'
    axios({
      method : "post",
      url : writeturl,
      data : newArticleForm,
      // headers: {
      //   'Content-Type': 'multipart/form-data'
      // },
    // })
    })
  }
  
  
  // 강현스
  // const uploadData = async () => {
  //   // 폼데이터 생성
  //   const body = new FormData();
  //   const serverUrl ='http://j6c208.p.ssafy.io/api/articles/upload'
  //   // 현재 사용자가 불러온 이미지 리스트들 => 각각 폼데이터에 넣어준다.
  //   images.map( (image,index) =>{
  //   var photo = {
  //     uri: image.realPath ,
  //     type: image.mime,
  //     name: image.fileName
  //     }
  //   body.append('uploadFiles', photo);
  //   })
  //   console.log(body)
  //   axios({
  //     method:"post",
  //     url : serverUrl,
  //     data: body,
      
  //   })
  // }
    
    
    
    // let newArticleForm = new FormData();
    // {
    //   newArticleForm.append("articleRequestDto", new Blob([JSON.stringify({
    //     content : onChangeContent,
    //     title : onChangeTitle,
    //     category : value,
    //     pics : pass,
  
    //   })]))
    // }
  
  
  const [images, setImages] = React.useState([])
   const handleImagePicker = async () => {
      try {
          const image = await MultipleImagePicker.openPicker({
              mediaType: "image",
              usedCameraButton: true,
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
          // console.log(qimages)
          console.log(images)
          
          image.map((item, index) => {
            imagedata.append("doc[]", {
              uri: item.realPath,
              type: item.mime,
              name: item.fileName,
            });
          });
          console.log("내놔", imagedata)
      } catch (e) {
          console.log('error', e);
      } finally {
          console.log('finally')
         
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
          <View style={{flexDirection: "row", justifyContent: "space-around"}}>
          <TouchableOpacity style={styles.communityButton2} onPress={() => navigation.goBack() }>
                <Text> ← 뒤로 </Text>
          </TouchableOpacity>
          <Image style={{ resizeMode: "cover", height: 80, width: 160}} source={Rikey}  />
          <View style={{ marginTop: "6%"}}>
          <Button 
          title="작성"
          color="#00C689"
          onPress={() => uploadprocess()}
          />
          </View>
          </View>
            <NativeBaseProvider  >
              <Center style={{marginRight:"60%"}} flex={1} px="1">
                  <Boardvarious />
              </Center>
            </NativeBaseProvider>

          


          <SafeAreaView>
          
            
            <TextInput
              style={styles.inputTitle}
              onChangeText={setonChangeTitle}
              value={onChangeTitle}
              placeholder="제목"
            />

            <TextInput
              multiline
              numberOfLines={30}
              maxLength={450}
              style={styles.inputContent}
              onChangeText={setonChangeContent}
              value={onChangeContent}
              placeholder="내용"
            />
            {/* <NativeBaseProvider>
              <Center flex={1} px="3">
                  <Textout />
              </Center>
            </NativeBaseProvider> */}
         
         <View>
          <TouchableOpacity

            style={{marginLeft: "5%"}}
            onPress={handleImagePicker}
          >
          <Image style={{height: 100, width: 100}}source={WooSteel} />
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
    borderBottomWidth: 1,
    borderBottomColor: "grey",
    padding: 4,
  },
  inputContent: {
    textAlign:"left",
    textAlignVertical: "top",
    borderRadius: 7,
    height: "60%",
    margin: 12,
    borderWidth: 1,
    borderBottomColor: "grey",
    padding: 5,
  },
  
});
export default WritePage;