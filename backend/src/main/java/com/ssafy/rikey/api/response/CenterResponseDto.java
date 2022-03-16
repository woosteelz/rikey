package com.ssafy.rikey.api.response;

import com.ssafy.rikey.db.entity.Center;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Data
@NoArgsConstructor
@ApiModel("CenterResponseDto")
public class CenterResponseDto {

    @ApiModelProperty(value = "센터 id", example = "1")
    private Long centerId;

    @ApiModelProperty(value = "센터 이름", example = "한강 인증 센터")
    private String name;

    @ApiModelProperty(value = "센터 위도, 경도", example = "(37.23, 170.34)")
    private Point point;

    public CenterResponseDto(Center center) {
        centerId = center.getId();
        name = center.getName();
        point = center.getPoint();
    }
}
