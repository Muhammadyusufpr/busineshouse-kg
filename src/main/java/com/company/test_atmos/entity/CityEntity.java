package com.company.test_atmos.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "city")
public class CityEntity extends BaseEntity {

    @Column("name")
    private String name;

    @Column("latitude")
    private Double latitude;

    @Column("longitude")
    private Double longitude;

    @Column("weather_status")
    private String weatherStatus;


}
