package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.response.StoreResponseDto;
import com.ssafy.rikey.db.entity.Store;
import com.ssafy.rikey.db.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    public List<StoreResponseDto> getStores(Double latitude, Double longitude) {
        List<Store> stores = storeRepository.findByRange(latitude, longitude);
        return stores.stream().map(StoreResponseDto::new).collect(Collectors.toList());
    }
}
