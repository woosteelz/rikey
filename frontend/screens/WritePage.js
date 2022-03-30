import React, { Component, useState, useEffect } from "react";
import { KeyboardAvoidingView,Image, StyleSheet, View, Text,Dimensions,TouchableOpacity,
  TextInput, Keyboard, TouchableWithoutFeedback, Button,SafeAreaView,ScrollView } from "react-native";
import { Radio, NativeBaseProvider,Center,} from "native-base";

import Rikey from '../assets/rikey.png'
import WooSteel from '../assets/defaultimage.jpg'
import { useStore } from "../states";
import MultipleImagePicker from '@baronha/react-native-multiple-image-picker';
import axios from "axios";




// const Textout = ( ) => {
//   return <Box alignItems="center" w="100%">
//       <TextArea h={410} placeholder="Text Area Placeholder" w="100%" maxW="100%" borderColor="black"/>
//     </Box>;
// };







const WritePage = ( { navigation } ) => {
  const { userId } = useStore()
  const [cvalue, setCValue] = React.useState("FREE");
  const Boardvarious = () => {
    
    const ref = React.useRef(null);
    React.useEffect(() => {
   
  
      ref.current.focus();
    }, [cvalue]);
    return <Radio.Group name="myRadioGroup" accessibilityLabel="favorite number" value={cvalue} onChange={nextValue => {
      setCValue(nextValue);
    }}>
        <Radio value="FREE" size="sm" my={1} >
          자유 게시판
        </Radio>
        <Radio value="RECRUIT" size="sm" my={1} ref={ref}>
          라이더 모집
        </Radio>
      </Radio.Group>;
  };

  let imagedata = new FormData()


  // 이미지에 관해
  const uploadprocess = () => {
    return new Promise( (resolove) => {
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
    
    fetch('http://j6c208.p.ssafy.io/api/articles/upload',{
      method : "post" ,
      body: imagedata
    }).then(res => res.json())
    .then(res => {
      

      
      alert("Success")
      console.log("업로드시", res.urls)
      resolove(res.urls)
    })
    .catch(err => {
      console.log("이게들어갔음", imagedata)
      console.error("error uploading images: ", err);
    });
    
  })
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
      <Image style={{height: 100, width: 100 , marginRight: "7%"}} source={{uri: "file://"+ item.realPath}} />
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
          console.log('모야')
          
          console.log("이미지리스트임", imageList)
          console.log(images)
          

          
      
          // console.log(JSON.stringify(imagedata));
        
        
      } catch (e) {
          console.log('error', e);
      } finally {
          console.log('finally')
         
      }
  };
    // 글쓰기버튼 클릭
    const writeprocessTemp = async() => {
      const urls = await uploadprocess()
      console.log("글쓰기시", urls)
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
        console.log(response)
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
          <View style={{flexDirection: "row", justifyContent: "space-around"}}>
          <TouchableOpacity style={styles.communityButton2} onPress={() => navigation.goBack() }>
                <Text> ← 뒤로 </Text>
          </TouchableOpacity>
          <Image style={{ resizeMode: "cover", height: 80, width: 160}} source={Rikey}  />
          <View style={{ marginTop: "6%"}}>
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
          {
            images.length !== 0 ?
          <View style={{ flexDirection: "row" }}>

            {imagepreview}

          </View>
          : 
          <Image style={{height: 100, width: 100}} source={WooSteel} />
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
    borderBottomWidth: 1,
    borderBottomColor: "grey",
    padding: 4,
  },
  inputContent: {
    textAlign:"left",
    textAlignVertical: "top",
    borderRadius: 7,
    height: "50%",
    margin: 12,
    borderWidth: 1,
    borderBottomColor: "grey",
    padding: 5,
  },
  
});
export default WritePage;