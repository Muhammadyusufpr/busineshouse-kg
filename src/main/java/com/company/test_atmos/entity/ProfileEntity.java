package com.company.test_atmos.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profile")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileEntity extends BaseEntity {

    @Column
    String username;

    @Column
    String password;

    @Column
    String name;

    @Column("role")
    String role;


}
