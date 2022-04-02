import React, { useEffect, useRef, useState } from 'react';
import { View, Text, Button, StyleSheet, TextInput } from 'react-native';
// import SockJs from "sockjs-client";
// import StompJs from "stompjs";
// import API from '../api/API';
import { useStore } from '../states';


import SockJS from "sockjs-client";
// import Stomp from "webstomp-client";

var connected =false;
var socket ='';
var stompClient = '';

const ChatList = () => {
  const [roomChat, setRoomChat] = useState([]);
  const { userNickName } = useStore();

  useEffect(() => {
    API.get(`chats/${userNickName}`)
    .then((response) => {
      console.log(response);
    })
   
  })

// const  send = ()=> {
//   let send_message = 'hello !';
//   if (stompClient && stompClient.connected) {
//     const msg = { name: send_message };
//     stompClient.send("/app/hello", JSON.stringify(msg), {});
//   }
//   }
// const connect =()=> {
//   socket = new SockJS("http://j6c208.p.ssafy.io/ws-stomp");
//   stompClient = Stomp.over(socket);
//   stompClient.connect(
//     {},
//     frame => {
//       connected = true;
//       stompClient.subscribe("/sub/chat/room/1", tick => {
//       });
//     },
//     error => {
//       console.log(error);
//       connected = false;
//     }
//   );
// }
// const disconnect =()=> {
//   if (stompClient) {
//     stompClient.disconnect();
//   }
//   connected = false;
// }
// const tickleConnection =()=> {
//   connected ? disconnect() : connect();
// } 

  return (
    <View>
      <Text>채팅은 카톡으로 하세요! ㅋ</Text>
      <Button title="연결" onPress={() => connect()}></Button>
    </View>
  );
};

export default ChatList;