package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.Geometry.Direction;
import com.ssafy.rikey.Geometry.GeometryUtil;
import com.ssafy.rikey.Geometry.Location;
import com.ssafy.rikey.db.entity.Cvs;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.rikey.Geometry.Haversine.distanceInKilometerByHaversine;

@RequiredArgsConstructor
public class CvsRepositoryImpl implements CvsRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Cvs> findByRange(Double latitude, Double longitude) {

        Location northEast = GeometryUtil
                .calculate(latitude, longitude, 1.0, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil
                .calculate(latitude, longitude, 1.0, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);
        Query query = em.createNativeQuery("SELECT  c.cvs_id, c.brand_name, c.name, c.address, c.road_address, c.latitude, c.longitude FROM cvs AS c "
                        + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", POINT(c.latitude, c.longitude))", Cvs.class)
                .setMaxResults(20);
        List<Cvs> cvss = query.getResultList();


        // 가까운 순으로 정렬
        List<Cvs> sortedCvs = cvss.stream()
                .sorted(Comparator.comparing((Cvs cvs) -> distanceInKilometerByHaversine(latitude, longitude, cvs.getLatitude(), cvs.getLongitude()))).collect(Collectors.toList());

        return sortedCvs;
    }
}
