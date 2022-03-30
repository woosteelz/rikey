import React from "react";
import styled from "styled-components";

const MyInfoInputBox = ({ title, setState, value }) => {

  return (
    <InputBox>
      <InputLabel>{title}</InputLabel>
      <InfoInput onChangeText={text => setState(text)} value={value} />
      <InfoBotLine></InfoBotLine>
    </InputBox>
  )
}

export default MyInfoInputBox;

const InputBox = styled.View`
  flex: 1;
  width:100%;
  height: 100%;
  justify-content: center;
`
const InputLabel = styled.Text`
  top: 30%;
  margin-bottom: 5%;
  font-size: 13px;
`
const InfoInput = styled.TextInput`
  width: 50%;
  font-weight: bold;
  font-size: 15px;
`
const InfoBotLine = styled.View`
  border-top-width: 1px;
  bottom: 12%;
`