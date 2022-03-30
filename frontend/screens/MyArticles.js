import React from 'react';
import { Image, Text } from 'react-native';
import styled from 'styled-components';
import API from '../api/API'

const MyArticles = () => {
  return(
    <Container>
      <InnerContainer>

        <SeparateBox>
          <BoxTitle>오늘 날씨 진짜 좋던데요?</BoxTitle>
        </SeparateBox>

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
const InnerContainer = styled.View`
  width: 90%;
  height: 95%;
  background-color: #EFF1F6;
  align-items: center;
  padding-top: 20px;
`
const SeparateBox = styled.View`
  background-color: white;
  width: 85%;
  height: 50px;
  border-radius: 13px;
  padding-top: 3%;
  padding-left: 4%;
`
const BoxTitle = styled.Text`
  font-weight: bold;
  font-size: 14px;
`