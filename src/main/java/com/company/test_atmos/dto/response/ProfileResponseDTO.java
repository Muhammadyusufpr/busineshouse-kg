package com.company.test_atmos.dto.response;

import com.company.test_atmos.entity.ProfileEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileResponseDTO {
    String id;
    String username;
    String name;

    public static ProfileResponseDTO toDTO(ProfileEntity entity) {
        return ProfileResponseDTO
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .username(entity.getUsername())
                .build();
    }

}
