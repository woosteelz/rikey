package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.Geometry.Direction;
import com.ssafy.rikey.Geometry.GeometryUtil;
import com.ssafy.rikey.Geometry.Location;
import com.ssafy.rikey.db.entity.Toilet;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.rikey.Geometry.Haversine.distanceInKilometerByHaversine;

@RequiredArgsConstructor
public class ToiletRepositoryImpl implements ToiletRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Toilet> findByRange(Double latitude, Double longitude) {

        Location northEast = GeometryUtil
                .calculate(latitude, longitude, 1.0, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil
                .calculate(latitude, longitude, 1.0, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);
        // 출발지, 도착지를 기준으로
        Query query = em.createNativeQuery("SELECT t.toilet_id, t.name, t.address, t.open_time, t.road_address, t.latitude, t.longitude FROM toilet AS t "
                        + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", POINT(t.latitude, t.longitude))", Toilet.class)
                .setMaxResults(20);
        List<Toilet> toilets = query.getResultList();

        // 가까운 순으로 정렬
        List<Toilet> sortedToilet = toilets.stream()
                .sorted(Comparator.comparing((Toilet toilet) -> distanceInKilometerByHaversine(latitude, longitude, toilet.getLatitude(), toilet.getLongitude()))).collect(Collectors.toList());

        return sortedToilet;
    }
}
