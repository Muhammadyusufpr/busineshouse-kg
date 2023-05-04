package com.company.test_atmos.repository;

import com.company.test_atmos.entity.CityEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CityRepository extends ReactiveCrudRepository<CityEntity, String> {


}
