package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.Geometry.GeometryUtil;
import com.ssafy.rikey.Geometry.Location;
import com.ssafy.rikey.db.entity.BikeRoad;
import com.ssafy.rikey.Geometry.Direction;
import com.ssafy.rikey.db.entity.Center;
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
                .calculate(latitude, longitude, 10.0, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil
                .calculate(latitude, longitude, 10.0, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);

        // 출발지, 도착지를 기준으로
        Query query = em.createNativeQuery("SELECT b.bike_road_id, b.course, b.name, "
                        + "b.departureLatitude, b.departureLongitude, b.arrivalLatitude, b.arrivalLongitude, "
                        + "b.hour, b.minute, b.introduce, b.start_point, b.end_point"
                        + "FROM bikeroad AS b "
                        + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", b.start_point()))"
                        + "or MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", b.end_point()))", BikeRoad.class)
                .setMaxResults(10);

        List<BikeRoad> bikeRoads = query.getResultList();

        return bikeRoads;
    }
}
