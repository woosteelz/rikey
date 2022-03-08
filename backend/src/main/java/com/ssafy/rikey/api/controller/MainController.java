package com.ssafy.rikey.api.controller;

import com.ssafy.rikey.api.response.MainResponseDto;
import com.ssafy.rikey.api.service.MainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MainController
 */
@Api(tags = "Main", value = "메인 API")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    // 메인 화면 API
    @GetMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
    })
    @ApiOperation(value = "메인 화면", notes = "사용자 설정 지역의 날씨와 자전거 안전 수칙 정보를 불러온다.")
    public ResponseEntity<MainResponseDto> loadMainInfo() {

        // 아래의 area에는 추후 회원관리 완료되면 회원이 설정한 지역을 넣을 예정
        MainResponseDto mainResponseDto = mainService.loadMainInfo("서울");
        return new ResponseEntity<MainResponseDto>(mainResponseDto, HttpStatus.OK);
    }
}
