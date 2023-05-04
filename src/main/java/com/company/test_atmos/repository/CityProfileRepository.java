package com.company.test_atmos.repository;

import com.company.test_atmos.dto.response.CityProfileDTO;
import com.company.test_atmos.dto.response.CityProfileMapperDTO;
import com.company.test_atmos.entity.ProfileCityEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CityProfileRepository extends ReactiveSortingRepository<ProfileCityEntity, String> {
    @Query(" select pc.city_id as city_id, pc.profile_id as profile_id," +
            " p.username as username, c.name as city_name " +
            " from profile_city as pc " +
            " inner join profile p on p.id = pc.profile_id " +
            " inner join city c on pc.city_id = c.id " +
            " where c.visible is true ")
    Flux<CityProfileDTO> findAllSub();



}
