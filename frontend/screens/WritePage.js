import React, { Component, useState } from "react";
import { KeyboardAvoidingView,Image, StyleSheet, View, Text,Dimensions,TouchableOpacity,
  TextInput, SafeAreaViewBase,Platform,SafeAreaView,ScrollView } from "react-native";
import { TextArea, Box, Center, NativeBaseProvider } from "native-base";
import Rikey from '../assets/rikey.png'
import WooSteel from '../assets/defaultimage.jpg'
import { color } from "native-base/lib/typescript/theme/styled-system";
import MultipleImagePicker from '@baronha/react-native-multiple-image-picker';



const Textout = ( ) => {
  return <Box alignItems="center" w="100%">
      <TextArea h={410} placeholder="Text Area Placeholder" w="100%" maxW="100%" borderColor="black"/>
    </Box>;
};

const WritePage = ( { navigation } ) => {
    // 이미지에 관해
    const [images, setImages] = useState()
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
    const [overView, setOverview] = useState(true)

    const onBoardOverview = () => {
      setOverview(false)
    }
    const onBoardOverviewBack = () => {
      setOverview(true)
    }
    




    return (
      <ScrollView>
      <KeyboardAvoidingView style={{ flex: 1 }} behavior="padding" enabled>
      

        <View>
          <View style={{flexDirection: "row", justifyContent: "space-around"}}>
          <TouchableOpacity style={styles.communityButton2} onPress={() => navigation.goBack() }>
                <Text> ← 뒤로 </Text>
          </TouchableOpacity>
          <Image style={{ resizeMode: "cover", height: 80, width: 160}} source={Rikey}  />
          <View style={{marginRight : "15%"}}></View>
          </View>

       

        
          <SafeAreaView>
            <TextInput
              style={styles.inputTitle}
              onChangeText={setonChangeTitle}
              value={onChangeTitle}
              placeholder="제목"
            />
{/* 
            <TextInput
              multiline
              numberOfLines={15}
              maxLength={450}
              style={styles.inputContent}
              onChangeText={setonChangeContent}
              value={onChangeContent}
              placeholder="내용"
            /> */}
            <NativeBaseProvider>
              <Center flex={1} px="3">
                  <Textout />
              </Center>
            </NativeBaseProvider>
         
              

          </SafeAreaView>
          
        </View>
        
        <View style={{ marginTop: "5%", marginLeft: "10%"}}>
        <TouchableOpacity
          onPress={handleImagePicker}
        >
        <Image  style={{ resizeMode: "cover", height: 80, width: 80}} source={WooSteel} />
        </TouchableOpacity>
        </View>
        
        </KeyboardAvoidingView>
        </ScrollView>
    
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
    height: "70%",
    margin: 12,
    borderWidth: 1,
    borderBottomColor: "grey",
    padding: 5,
  },
  
});
export default WritePage;