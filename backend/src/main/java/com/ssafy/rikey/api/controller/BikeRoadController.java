package com.ssafy.rikey.api.controller;

import com.ssafy.rikey.api.response.BikeRoadDetailResponseDto;
import com.ssafy.rikey.api.response.BikeRoadResponseDto;
import com.ssafy.rikey.api.service.BikeRoadService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Api(tags = "BikeRoad", value = "자전거길 API")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/bikeRoads")
@RequiredArgsConstructor
public class BikeRoadController {

    private final BikeRoadService bikeRoadService;

    @GetMapping
    @ApiOperation(value = "추천 코스 리스트 조회", notes = "추천 코스 리트스를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> getBikeRoads(
            @RequestParam(required = true) @ApiParam(value = "현재 위도") Double latitude,
            @RequestParam(required = true) @ApiParam(value = "현재 경도") Double longitude) {

        Map<String, Object> result = new HashMap<>();
        List<BikeRoadResponseDto> bikeroadList = null;
        HttpStatus httpStatus = null;

        try {
            bikeroadList = bikeRoadService.getBikeRoads(latitude, longitude);
            httpStatus = HttpStatus.CREATED;
            result.put("status", "SUCCESS");
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        result.put("bikeroadList", bikeroadList);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @GetMapping("/{bikeroadId}")
    @ApiOperation(value = "자전거길 상세 조회", notes = "자전거길 id에 해당하는 자전거길 정보를 불러온다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "자전거길 탐색 오류"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> getBikeRoad(
            @PathVariable @ApiParam(value = "자전거길 id", required = true) Long bikeroadId) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        BikeRoadDetailResponseDto bikeRoad = null;

        try {
            bikeRoad = bikeRoadService.getBikeRoad(bikeroadId);
            httpStatus = HttpStatus.CREATED;
            result.put("status", "SUCCESS");
        } catch (NoSuchElementException e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            result.put("status", "NO ARTICLE");
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        result.put("bikeRoad", bikeRoad);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @GetMapping("/facilities")
    @ApiOperation(value = "주변 편의시설 조회", notes = "내 주변에 있는 편의시설 정보를 불러온다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "자전거길 탐색 오류"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> getBikeRoad(
            @RequestParam(required = true) @ApiParam(value = "현재 위도") Double latitude,
            @RequestParam(required = true) @ApiParam(value = "현재 경도") Double longitude) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        BikeRoadDetailResponseDto bikeRoad = null;

        try {
//            bikeRoad = bikeRoadService.getBikeRoad(bikeroadId);
            httpStatus = HttpStatus.CREATED;
            result.put("status", "SUCCESS");
        } catch (NoSuchElementException e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            result.put("status", "NO ARTICLE");
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        result.put("bikeRoad", bikeRoad);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }
}
