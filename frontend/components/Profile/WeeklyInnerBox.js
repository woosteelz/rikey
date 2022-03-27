import React from 'react';
import styled from 'styled-components';

const WeeklyInnerBox = ({ title, imageLink, measurand }) => {

  return (
    <Box>
      <WeeklyText>{title}</WeeklyText>

      <BotBox>

        <BotImageBox>
          <InnerImageBox>
            <BotImage source={imageLink}/>
          </InnerImageBox>
        </BotImageBox>

        <BotText>{measurand}</BotText>

      </BotBox>

    </Box>
  )
}

export default WeeklyInnerBox;

const Box = styled.View`
  flex: 1;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
`

const WeeklyText = styled.Text`
  flex: 0.5;
  font-weight: bold;
  font-size: 15px;
  color: black;
`
const BotBox = styled.View`
  flex: 1.5;
  justify-content: center;
  align-items: center;
  width: 60%;
  height: 60%;
`
const BotImageBox = styled.View`
  flex: 2.5;
  width: 100%;
  justify-content: center;
  align-items: center;
`
const InnerImageBox = styled.View`
  background-color : #EFF1F6;
  border-radius: 50px;
  width: 70%;
  height: 70%;
  justify-content: center;
  align-items: center;
`
const BotImage = styled.Image`
  width: 30;
  height: 30;
`
const BotText = styled.Text`
  margin-top: 7%;
  font-weight: bold;
  font-size: 12px;
  color: black;
`