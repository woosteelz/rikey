package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.Geometry.Direction;
import com.ssafy.rikey.Geometry.GeometryUtil;
import com.ssafy.rikey.Geometry.Location;
import com.ssafy.rikey.db.entity.BikeRoad;
import com.ssafy.rikey.db.entity.Center;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CenterRepositoryImpl implements CenterRepositoryCustom {

    private final EntityManager em;
    private final BikeRoadRepository bikeRoadRepository;

    @Override
    public List<BikeRoad> findBikeRoadByCenterByRange(Double latitude, Double longitude) {
        Location northEast = GeometryUtil
                .calculate(latitude, longitude, 30.0, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil
                .calculate(latitude, longitude, 30.0, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);

        // 센터를 기준으로
        Query query = em.createNativeQuery("SELECT c.center_id, c.address, c.bikeroad_id, c.latitude, c.longitude, c.name FROM center AS c "
                        + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", POINT(c.latitude, c.longitude))", Center.class)
                .setMaxResults(10);

        List<Center> centers = query.getResultList();
        List<BikeRoad> bikeRoads = new ArrayList<>();

        for (Center center : centers) {
            BikeRoad bikeRoad = bikeRoadRepository.findById(center.getBikeroadId()).get();
            bikeRoads.add(bikeRoad);
        }

        return bikeRoads;
    }
}
