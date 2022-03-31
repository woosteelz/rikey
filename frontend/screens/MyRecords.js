import React, { useEffect, useState } from 'react';
import { TouchableOpacity } from 'react-native';
import styled from 'styled-components';
import API from '../api/API'
import { useStore } from '../states'
import BlackX from '../assets/icons/BlackX.png'

const MyReviews = () => {
  const [records, setRecords] = useState([]);
  const { userId, userNickName } = useStore();

  const deleteRecord = (theId) => {
    API.delete(`/ridings/${theId}`, {
      data: {"userId" : userId}
    })
    .then((response) => {
      console.log(response);
    })
  }

  useEffect(() => {
    API.get(`/ridings/${userNickName}`)
    .then((response) => {
      const show = response.data.ridingList.map((el, key) => {
        let year = el.startTime.slice(0, 4)
        let month = Number(el.startTime.slice(5, 7))
        let day = Number(el.startTime.slice(8, 10))
        let theHour = Number(el.startTime.slice(11, 13))
        let minute = Number(el.startTime.slice(14, 16))
        const theDayNight = hour < 12 ? "오전" : "오후"
        const hour = theHour > 12 ? theHour - 12 : theHour
        const theId = el.id
        return(
          <SeparateBox key={key}>
            <TopBox>
              <WrittenDate>{year}년 {month}월 {day}일 {theDayNight} {hour}시 {minute}분 </WrittenDate>
              <DeleteImgBox>
                <TouchableOpacity
                  onPress={() => {deleteRecord(theId)}}
                >
                  <DeleteImg source={BlackX}/>
                </TouchableOpacity>
              </DeleteImgBox>
            </TopBox>
            <RowBox>
              <BoxTitle><BigText>{el.ridingTime}</BigText> 분 / </BoxTitle>
              <BoxTitle><BigText>{el.ridingDist}</BigText> km / </BoxTitle>
              <BoxTitle><BigText>{el.ridingCalorie}</BigText> kcal</BoxTitle>
            </RowBox>
          </SeparateBox>
        )
      })
      setRecords(show)
    })
  }, [])

  return(
    <Container>
      <InnerContainer>
        <Instruction />
        <InnerScroll>
          <InsideScrollContainer>
            {records}
          </InsideScrollContainer>  
        </InnerScroll>
      </InnerContainer>
    </Container>
  )
}

export default MyReviews;
const Container = styled.View`
  flex: 1;
  justify-content: center;
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
const TopBox = styled.View`
  flex-direction: row;
`
const Instruction = styled.View`
`
const InnerScroll = styled.ScrollView`
  width: 100%;
  height: 100%;
`
const InsideScrollContainer = styled.View`
  align-items: center;
`
const SeparateBox = styled.View`
  background-color: white;
  width: 85%;
  height: 78px;
  border-radius: 13px;
  padding-top: 2%;
  padding-bottom: 3%;
  justify-content: center;
  margin-bottom: 5%;
`
const WrittenDate = styled.Text`
  margin-left: 6%;
  bottom: 1%;
  font-weight: bold;
  font-size: 13px;
`
const DeleteImgBox = styled.View`
  flex: 1;
  margin-left: 22%;
`
const DeleteImg = styled.Image`
  width: 15px;
  height: 15px;
`
const RowBox = styled.View`
  flex-direction: row;
  justify-content: center;
  align-items: center;
`
const BigText = styled.Text`
  font-size: 18px;
`
const BoxTitle = styled.Text`
  font-weight: bold;
  color: black;
  font-size: 15px;
  top: 3%;
`
