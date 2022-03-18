package com.ssafy.rikey.api.response;

import com.ssafy.rikey.db.entity.Center;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.persistence.ElementCollection;
import java.util.List;

@Data
@NoArgsConstructor
@ApiModel("CenterDetailResponseDto")
public class CenterDetailResponseDto {

    @ApiModelProperty(value = "센터 id", example = "1")
    private Long centerId;

    @ApiModelProperty(value = "센터 이름", example = "한강 인증 센터")
    private String name;

    @ApiModelProperty(value = "센터 위치", example = "서울시 노들동 노들로")
    private String address;

    @ApiModelProperty(value = "센터 위도", example = "170.34")
    private Double latitude;

    @ApiModelProperty(value = "센터 경도", example = "170.34")
    private Double longitude;

    @ApiModelProperty(value = "센터와 가까운 화장실 리스트")
    @ElementCollection
    private List<ToiletResponseDto> toiletList;

    @ApiModelProperty(value = "센터와 가까운 편의점 리스트")
    @ElementCollection
    private List<CvsResponseDto> cvsList;

    @ApiModelProperty(value = "센터와 가까운 자전거 보관소 리스트")
    @ElementCollection
    private List<StoreResponseDto> storeList;

    public CenterDetailResponseDto(Center center,
//                             List<ToiletResponseDto> toiletResponseDtos,
                             List<CvsResponseDto> cvsResponseDtos)
//                             List<StoreResponseDto> storeResponseDtos) {
    {
        centerId = center.getId();
        name = center.getName();
        address = center.getAddress();
        latitude = center.getLatitude();
        longitude = center.getLongitude();

//        toiletList = toiletResponseDtos;
        cvsList = cvsResponseDtos;
//        storeList = storeResponseDtos;
    }
}
