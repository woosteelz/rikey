package com.ssafy.rikey.api.controller;

import com.ssafy.rikey.api.request.RidingInfoRequestDto;
import com.ssafy.rikey.api.service.RidingInfoService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "RidingInfo", value = "주행정보 API")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/ridings")
@RequiredArgsConstructor
public class RidingInfoController {

    private final RidingInfoService ridingInfoService;

    @PostMapping
    @ApiOperation(value = "주행정보 등록", notes = "새로운 주행정보를 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 204, message = "주행정보 등록 오류"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> createRidingInfo(
            @RequestBody @ApiParam(value = "게시글 정보", required = true) RidingInfoRequestDto ridingInfoRequestDto) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        try {
            ridingInfoService.createRidingInfo(user, ridingInfoRequestDto);
            httpStatus = HttpStatus.OK;
            result.put("success", true);
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);
        }

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }
}
