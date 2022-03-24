package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Cvs;

import java.util.List;

public interface CvsRepositoryCustom {

    List<Cvs> findByRange(Double latitude, Double longitude);
}
