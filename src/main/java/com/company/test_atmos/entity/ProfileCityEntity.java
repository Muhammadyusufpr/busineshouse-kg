package com.company.test_atmos.entity;

import liquibase.pro.packaged.A;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "profile_city")
public class ProfileCityEntity {
    @Id
    private String id;

    @Column
    private LocalDateTime createdDate;

    @Column("city_id")
    private String cityId;

    @Column("profile_id")
    private String profileId;
}
