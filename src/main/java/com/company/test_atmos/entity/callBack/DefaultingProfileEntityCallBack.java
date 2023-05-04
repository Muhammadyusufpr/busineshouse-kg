package com.company.test_atmos.entity.callBack;

import com.company.test_atmos.entity.ProfileEntity;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.BeforeSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class DefaultingProfileEntityCallBack implements BeforeSaveCallback<ProfileEntity> {

    @Override
    public Publisher<ProfileEntity> onBeforeSave(ProfileEntity entity,
                                                 OutboundRow row,
                                                 SqlIdentifier table) {

        entity.setId(UUID.randomUUID().toString());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setRole("USER");
        return Mono.just(entity);
    }

}
