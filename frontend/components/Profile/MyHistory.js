import React from "react";
import styled from "styled-components";

const MyHistory = ({ content, logo, arrow }) => {

  return (
    <Box>
      <LeftBox>
        <ImageBox>
          <MyImage source={logo}/>
        </ImageBox>
        <MyHistoryDetail>{content}</MyHistoryDetail>
      </LeftBox>

      <RightBox>
        <RightImage source={arrow} />
      </RightBox>
    </Box>
  )
}

export default MyHistory;

const Box = styled.View`
  flex: 1;
  flex-direction: row;
  width: 85%;
  justify-content: flex-start;
  align-items: center;
  margin-top: 5%;
`
const LeftBox = styled.View`
  flex: 3;
  flex-direction: row;
  justfiy-content: flex-start;
  align-items: center;
`

const ImageBox = styled.View`
  width: 20%;
  height: 10%;
  margin-right: 3%;
`
const MyImage = styled.Image`
  resize-mode: contain;
`
const MyHistoryDetail = styled.Text`
  font-weight: bold;
  color: black;
  font-size: 15px;
`
const RightBox = styled.View`
  flex: 1;
  flex-direction: row;
  justify-content: flex-end;
`
const RightImage = styled.Image`
  width: 20px;
  height: 20px;
`