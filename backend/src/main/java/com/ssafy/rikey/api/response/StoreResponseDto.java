package com.ssafy.rikey.api.response;

import com.ssafy.rikey.db.entity.Store;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("StoreResponseDto")
public class StoreResponseDto {

    @ApiModelProperty(value = "보관소명", example = "노들역보관소")
    private String name;

    @ApiModelProperty(value = "보관소 도로명 주소", example = "서울시 노들동 노들로 1")
    private String roadAddress;

    @ApiModelProperty(value = "보관소 주소", example = "서울시 노들동 노들역")
    private String address;

    @ApiModelProperty(value = "공기 주입기 여부", example = "1")
    private Boolean airInjector;

    @ApiModelProperty(value = "수리대 여부", example = "1")
    private Boolean repairTable;

    @ApiModelProperty(value = "보관소 위도", example = "170.34")
    private Double latitude;

    @ApiModelProperty(value = "보관소 경도", example = "170.34")
    private Double longitude;

    public StoreResponseDto(Store store) {
        name = store.getName();
        roadAddress = store.getRoadAddress();
        address = store.getAddress();
        airInjector = store.getAirInjector();
        repairTable = store.getRepairTable();
        latitude = store.getLatitude();
        longitude = store.getLongitude();
    }
}
