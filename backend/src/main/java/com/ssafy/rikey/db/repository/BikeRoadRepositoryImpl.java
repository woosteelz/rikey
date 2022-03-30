package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.Geometry.GeometryUtil;
import com.ssafy.rikey.Geometry.Location;
import com.ssafy.rikey.db.entity.BikeRoad;
import com.ssafy.rikey.Geometry.Direction;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@RequiredArgsConstructor
public class BikeRoadRepositoryImpl implements BikeRoadRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<BikeRoad> findBikeRoadByRange(Double latitude, Double longitude) {

        Location northEast = GeometryUtil
                .calculate(latitude, longitude, 30.0, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil
                .calculate(latitude, longitude, 30.0, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);
        // 출발지, 도착지를 기준으로
        Query query = em.createNativeQuery("SELECT b.bikeroad_id, b.departure, b.destination, "
                        + "b.name, b.hour, b.minute, b.introduce, "
                        + "b.start_latitude, b.start_longitude, b.end_latitude, b.end_longitude "
                        + "FROM bikeroad AS b "
                        + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", POINT(b.start_latitude, b.start_longitude)) "
                        + "OR MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", POINT(b.end_latitude, b.end_longitude))", BikeRoad.class)
                .setMaxResults(10);

        List<BikeRoad> bikeRoads = query.getResultList();

        return bikeRoads;
    }
}
