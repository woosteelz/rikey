import React, { useEffect, useState } from 'react';
import { Image, Text, ScrollView } from 'react-native';
import styled from 'styled-components';
import API from '../api/API'
import { useStore } from '../states'

const MyArticles = () => {
  const [articles, setArticles] = useState([]);
  const { userNickName } = useStore();

  useEffect(() => {
    API.get(`articles/profile/${userNickName}`)
    .then((response) => {
      console.log(response);
      const show = response.data.articleList.map((el, key) => {
        return(
          <SeparateBox key={key}>
            <BoxTitle>{el.title}</BoxTitle>
            <WrittenDate>{el.createdTime.slice(0, 10)}</WrittenDate>
          </SeparateBox>
        )
      })
      setArticles(show)
    })
  }, [])


  return(
    <Container>
      <InnerContainer>
        <InnerScroll>
          <InsideScrollContainer>
            {articles}
          </InsideScrollContainer>  
        </InnerScroll>
      </InnerContainer>
    </Container>
  )
}

export default MyArticles;
const Container = styled.View`
  flex: 1;
  justify-content: center;
  align-items: center;
`
const InnerScroll = styled.ScrollView`
  width: 100%;
  height: 100%;
`
const InsideScrollContainer = styled.View`
  align-items: center;
`
const InnerContainer = styled.View`
  width: 90%;
  height: 95%;
  background-color: #EFF1F6;
  border-radius: 25px;
  align-items: center;
  padding-top: 20px;
`
const SeparateBox = styled.View`
  background-color: white;
  width: 85%;
  height: 65px;
  border-radius: 13px;
  padding-bottom: 3%;
  padding-left: 4%;
  justify-content: center;
  margin-bottom: 5%;
`
const BoxTitle = styled.Text`
  font-weight: bold;
  color: black;
  font-size: 14px;
`
const WrittenDate = styled.Text`
  margin-left: 70%;
  top: 18.5%;
`