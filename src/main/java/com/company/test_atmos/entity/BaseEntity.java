package com.company.test_atmos.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    @Id
    protected String id;

    @Column
    protected LocalDateTime createdDate;

    @Column("visible")
    protected Boolean visible = true;
}
