package com.ssafy.rikey.api.controller;

import com.ssafy.rikey.api.request.RidingInfoRequestDto;
import com.ssafy.rikey.api.response.ArticleResponseDto;
import com.ssafy.rikey.api.response.RidingInfoResponseDto;
import com.ssafy.rikey.api.service.RidingInfoService;
import com.ssafy.rikey.db.entity.RidingInfo;
import com.ssafy.rikey.db.repository.RidingInfoRepository;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "RidingInfo", value = "주행정보 API")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/ridings")
@RequiredArgsConstructor
public class RidingInfoController {

    private final RidingInfoService ridingInfoService;
    private final RidingInfoRepository ridingInfoRepository;

    @GetMapping("/{nickname}")
    @ApiOperation(value = "주행 정보 조회", notes = "나의 주행 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> getRidingInfos(
            @PathVariable @ApiParam(value = "사용자 닉네임", required = true) String nickname) {

        Map<String, Object> result = new HashMap<>();
        List<RidingInfoResponseDto> ridingList = null;
        HttpStatus httpStatus = null;

        try {
            ridingList = ridingInfoService.getRidingInfoes(nickname);
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        result.put("ridingList", ridingList);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @PostMapping
    @ApiOperation(value = "주행정보 등록", notes = "새로운 주행정보를 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 204, message = "주행정보 등록 오류"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> createRidingInfo(
            @RequestBody @ApiParam(value = "주행 정보", required = true) RidingInfoRequestDto ridingInfoRequestDto) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        try {
            ridingInfoService.createRidingInfo(ridingInfoRequestDto.getUserId(), ridingInfoRequestDto);
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @DeleteMapping("/{ridingInfoId}")
    @ApiOperation(value = "주행정보 삭제", notes = "주행정보를 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 400, message = "주행정보 탐색 오류"),
            @ApiResponse(code = 403, message = "잘못된 유저"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> deleteRidingInfo(
            @RequestBody @ApiParam(value = "유저 id") Map<String, String> body,
            @PathVariable @ApiParam(value = "주행정보 id", required = true) Long ridingInfoId) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        try {
            RidingInfo ridingInfo = ridingInfoRepository.getById(ridingInfoId);
            if (ridingInfo.getUser().getId().equals(body.get("userId"))) {
                ridingInfoService.deleteRidingInfo(ridingInfoId);
                httpStatus = HttpStatus.OK;
                result.put("status", "SUCCESS");
            } else {
                httpStatus = HttpStatus.FORBIDDEN;
                result.put("status", "WRONG USER");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        } catch (Exception e) {
        httpStatus = HttpStatus.BAD_REQUEST;
        result.put("status", "BAD REQUEST");
    }


        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }
}
