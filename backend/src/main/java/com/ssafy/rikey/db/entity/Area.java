package com.ssafy.rikey.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Area {
    SEOUL("서울"),
    SEJONG("세종"),
    INCHEON("인천"),
    DAEJEON("대전"),
    DAEGU("대구"),
    GWANJU("광주"),
    ULSAN("울산"),
    BUSAN("부산"),
    GYEONGGI("경기"),
    GANGWON("강원"),
    CHUNGNAM("충남"),
    CHUNGBUK("충북"),
    JEONNAM("전남"),
    JEONBUK("전북"),
    JEJU("제주"),
    GYEONGNAM("경남"),
    GYEONGBUK("경북");

    final private String name;

    private Area(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public static Area nameOf(String name) {
        for (Area status : Area.values()) {
            if (status.getName().equals(name)) {
                return status;
            }
        }
        return null;
    }
}
