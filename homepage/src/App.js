import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Link from "@mui/material/Link";
import Paper from "@mui/material/Paper";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { green } from "@mui/material/colors";
import FileDownloadIcon from "@mui/icons-material/FileDownload";

import Carousel from "../src/Carousel";

const color = green[600];
const white = green[50];

function Copyright(props) {
	return (
		<Typography
			variant="body2"
			color="text.secondary"
			align="center"
			{...props}
		>
			{"Copyright © "}
			<Link color="inherit" href="https://mui.com/">
				Your Website
			</Link>{" "}
			{new Date().getFullYear()}
			{"."}
		</Typography>
	);
}

const theme = createTheme();

export default function Homepage() {
	return (
		<ThemeProvider theme={theme}>
			<Grid container component="main" sx={{ height: "100vh" }}>
				<CssBaseline />
				<Grid
					item
					xs={false}
					sm={4}
					md={7}
					sx={{
						display: "flex",
						alignItems: "center",
						justifyContent: "center",
					}}
				>
					<Carousel />
				</Grid>
				<Grid
					item
					xs={12}
					sm={8}
					md={5}
					component={Paper}
					square
					sx={{ backgroundColor: color }}
					elevation={10}
				>
					<Box
						sx={{
							my: 8,
							mx: 4,
							display: "flex",
							flexDirection: "column",
							alignItems: "center",
						}}
					>
						<Typography component="h1" variant="h5" color="white">
							RIKEY에 오신걸 환영합니다!
						</Typography>
						<Typography
							component="h1"
							variant="h6"
							color="white"
							align="inline"
							sx={{ mt: 3, alignSelf: "flex-start" }}
						>
							라이키와 함께 자전거 여행을 떠나보세요!
						</Typography>
						<Typography
							component="h1"
							variant="h6"
							color="white"
							sx={{ alignSelf: "flex-start" }}
						>
							내 주변 가까운 인기 자전거 코스를 추천해드립니다 :)
							라이키를 이용한다면 실시간으로 화장실, 편의점,
							자전거 보관소 등의 편의시설 검색이 가능합니다!
							라이더 모집 게시판을 통해 같이 자전거를 탈
							라이더들을 모아 단체 라이딩을 즐겨보세요~
						</Typography>
						<Typography
							component="h1"
							variant="h6"
							color="white"
							sx={{ mt: 20 }}
						>
							나의 라이딩 메이트, RIKEY
						</Typography>
						<Button
							variant="outlined"
							startIcon={<FileDownloadIcon />}
							sx={{ color: white, mt: 5 }}
						>
							다운로드 받으러 가기
						</Button>
					</Box>
				</Grid>
			</Grid>
		</ThemeProvider>
	);
}
