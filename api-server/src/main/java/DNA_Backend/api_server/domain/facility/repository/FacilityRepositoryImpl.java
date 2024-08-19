package DNA_Backend.api_server.domain.facility.repository;

import static DNA_Backend.api_server.domain.facility.model.QFacility.facility;

import DNA_Backend.api_server.domain.facility.dto.FacilityDto.LocationTotalFacilityCountResponse;
import DNA_Backend.api_server.domain.facility.dto.QFacilityDto_LocationTotalFacilityCountResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FacilityRepositoryImpl implements FacilityRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<LocationTotalFacilityCountResponse> countTotalFacilitiesGroupedByLocation() {
        return queryFactory
                .select(new QFacilityDto_LocationTotalFacilityCountResponse(facility.location.id, facility.count()))
                .from(facility)
                .where(facility.location.id.isNotNull())
                .groupBy(facility.location.id)
                .orderBy(facility.location.id.asc())
                .fetch();
    }

}

