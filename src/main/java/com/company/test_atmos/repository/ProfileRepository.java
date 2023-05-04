package com.company.test_atmos.repository;

import com.company.test_atmos.entity.ProfileEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProfileRepository extends ReactiveSortingRepository<ProfileEntity, String> {
    Mono<ProfileEntity> findByUsernameAndVisibleIsTrue(String username);


}
