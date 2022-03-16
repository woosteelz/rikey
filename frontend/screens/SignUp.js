import styled from "styled-components";
import { NativeBaseProvider, Select, Center, Box, CheckIcon } from "native-base";
import React, { useState, useEffect} from "react";
import { Button } from 'react-native';
import axios from 'axios'

const SignUp = () => {
    const [validUser, setValidUser] = useState(false);
    const [area, setArea] = useState('');

		const [data, setData] = useState('');
		const onClick = async () => {
			try { 
				const response = await axios.get('https://jsonplaceholder.typicode.com/todos/1')
			} catch (e) {
				console.log(e);
			}
		}


    return (
			<NativeBaseProvider>
				
					<Center>
						<Button title="시험" onPress={() => onClick()}/>
						<SignUpText>{"닉네임을 입력해 주세요." }</SignUpText>
						<SignUpInput />

						<SignUpText>{"소개말을 입력해 주세요."}</SignUpText>
						<SignUpInput />

						<SignUpText>{"지역을 선택해 주세요."}</SignUpText>

						<Box w="3/4" maxW="300">

							<Select selectedValue={area} minWidth="200" accessibilityLabel="지역" placeholder="Choose Service" 
							_selectedItem={{
								bg: "teal.600",
								endIcon: <CheckIcon size="5" />
							}} mt={1} onValueChange={itemValue => setArea(itemValue)}>
								
								<Select.Item label="경기" value="GYEONGGI" />
								<Select.Item label="경남" value="GYEONGNAM" />
								<Select.Item label="경북" value="GYEONGBUK" />
								<Select.Item label="광주" value="GWANGJU" />
								<Select.Item label="대구" value="DAEGU" />
								<Select.Item label="대전" value="DAEJEON" />
								<Select.Item label="부산" value="BUSAN" />
								<Select.Item label="서울" value="SEOUL" />
								<Select.Item label="세종" value="SEJONG" />
								<Select.Item label="울산" value="ULSAN" />
								<Select.Item label="인천" value="INCHEON" />
								<Select.Item label="전남" value="JEONNAM" />
								<Select.Item label="전북" value="JEONBUK" />
								<Select.Item label="제주" value="JEJU" />
								<Select.Item label="충남" value="CHUNGNAM" />
								<Select.Item label="충북" value="CHUNGBUK" />

							</Select>

						</Box>
					</Center>   
					{/* {!!naverToken && <Button title="로그아웃하기" onPress={naverLogout} />} */}
				
			</NativeBaseProvider>
    )
}

export default SignUp;

const SignUpInput = styled.TextInput`
  border: 1px solid;
`
const SignUpText = styled.Text`
`