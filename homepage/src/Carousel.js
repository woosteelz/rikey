import React, { useState } from "react";
import "./Carousel.css";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";

import homescreen from "../src/images/home.png";
import login from "../src/images/login.png";

const images = [homescreen, login];

function Carousel() {
	const [currImg, setCurrImg] = useState(0);

	const goLeft = () => {
		if (currImg > 0) {
			setCurrImg(currImg - 1);
		} else if (currImg === 0) {
			setCurrImg(images.length - 1);
		}
	};

	const goRight = () => {
		if (currImg === images.length - 1) {
			setCurrImg(0);
		} else {
			setCurrImg(currImg + 1);
		}
	};

	return (
		<div className="carousel">
			<div
				className="carouselInner"
				style={{ backgroundImage: `url(${images[currImg]})` }}
			>
				<div className="left" onClick={goLeft}>
					<ArrowBackIosIcon style={{ fontSize: 30 }} />
				</div>
				<div className="right" onClick={goRight}>
					<ArrowForwardIosIcon style={{ fontSize: 30 }} />
				</div>
			</div>
		</div>
	);
}

export default Carousel;
