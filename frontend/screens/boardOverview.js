import React, { Component, useState } from "react";
import { Image, StyleSheet, View, Text, ScrollView, Dimensions,LinearLayout,TouchableOpacity,Button  } from "react-native";
import Rikey from '../assets/rikey.png'
import writebutton from '../assets/writebutton.png'
import { Select, Box, CheckIcon, Center, NativeBaseProvider } from "native-base";

const App = () => {
 
    const screenWidth = Dimensions.get('window').width;
    const [overView, setOverview] = useState(true)

    const onBoardOverview = () => {
      setOverview(false)
    }
    const onBoardOverviewBack = () => {
      setOverview(true)
    }

    const Example = () => {
      let [service, setService] = React.useState("");
      return <Center>
          <Box w="3/4" maxH="10" maxW="100">
            <Select selectedValue={service} minWidth="100" accessibilityLabel="Choose Service" placeholder="전체" _selectedItem={{
            bg: "teal.600",
            endIcon: <CheckIcon size="5" />
          }} mt={1} onValueChange={itemValue => setService(itemValue)}>
              <Select.Item label="전체" value="ux" />
              <Select.Item label="라이더 모집" value="web" />
              <Select.Item label="일반 글" value="cross" />
            </Select>
          </Box>
        </Center>;
    };

    return (
      <View>
      { overView ?
        

      
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
            <View style={{backgroundColor: "green", marginTop: "5%", marginLeft:"5%", width: "90%", height: "50%"}}>
            </View>
            <TouchableOpacity style={styles.communityButton} onPress={onBoardOverview}> 
              <Text>커뮤니티 모든 글 보러가기 > </Text>
            </TouchableOpacity>
        </View>

        :  
        <View>
          <View style={{flexDirection: "row", justifyContent: "space-around"}}>
          <TouchableOpacity style={styles.communityButton2} onPress={onBoardOverviewBack}> 
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
        
        <View style={{backgroundColor: "green", marginTop: "5%", marginLeft:"5%", width: "90%", height: "75%"}}>
        </View>
        
        {/* 글쓰기 버튼 */}
        <View style={{flex:1}}>
        <View style={{marginRight:"10%",marginBottom:"5%", position:'absolute',bottom:0,alignSelf:'flex-end'}}>
          <TouchableOpacity style={styles.writebutton} onPress={onBoardOverviewBack}> 
                <Image style={{border:1, resizeMode: "cover", height:60, width: 60}} source={writebutton} />
          </TouchableOpacity>
        </View>
      </View>
        </View>
        }
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
export default App;