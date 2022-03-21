package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.Geometry.Direction;
import com.ssafy.rikey.Geometry.GeometryUtil;
import com.ssafy.rikey.Geometry.Location;
import com.ssafy.rikey.db.entity.Cvs;
import com.ssafy.rikey.db.entity.Store;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.rikey.Geometry.Haversine.distanceInKilometerByHaversine;

@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Store> findByRange(Double latitude, Double longitude) {

        Location northEast = GeometryUtil
                .calculate(latitude, longitude, 1.0, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil
                .calculate(latitude, longitude, 1.0, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);
        Query query = em.createNativeQuery("SELECT  s.store_id, s.address, s.name, s.road_address, s.air_injector, s.repair_table, "
                        + "s.latitude, s.longitude FROM store AS s "
                        + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", POINT(s.latitude, s.longitude))", Store.class)
                .setMaxResults(20);
        List<Store> stores = query.getResultList();


        // 가까운 순으로 정렬
        List<Store> sortedStore = stores.stream()
                .sorted(Comparator.comparing((Store store) -> distanceInKilometerByHaversine(latitude, longitude, store.getLatitude(), store.getLongitude()))).collect(Collectors.toList());

        System.out.println("sortedStore = " + sortedStore);

        return sortedStore;
    }
}
