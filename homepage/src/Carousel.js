import React, { useState } from "react";
import "./Carousel.css";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";

import homescreen from "../src/images/home.png";
import login from "../src/images/login.png";

const images = [homescreen, login];

function Carousel() {
	const [currImg, setCurrImg] = useState(0);

	return (
		<div className="carousel">
			<div
				className="carouselInner"
				style={{ backgroundImage: `url(${images[currImg]})` }}
			>
				<div
					className="left"
					onClick={() => {
						currImg > 0 && setCurrImg(currImg - 1);
					}}
				>
					<ArrowBackIosIcon style={{ fontSize: 30 }} />
				</div>
				<div
					className="right"
					onClick={() => {
						currImg < images.length - 1 && setCurrImg(currImg + 1);
					}}
				>
					<ArrowForwardIosIcon style={{ fontSize: 30 }} />
				</div>
			</div>
		</div>
	);
}

export default Carousel;
