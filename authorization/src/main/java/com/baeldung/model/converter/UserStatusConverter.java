package com.baeldung.model.converter;


import com.baeldung.enums.UserStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 */
@Converter(autoApply = true)
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {

    @Override
    public String convertToDatabaseColumn(UserStatus attribute) {
        return attribute.getValue();
    }

    @Override
    public UserStatus convertToEntityAttribute(String dbData) {
        return UserStatus.getEnum(dbData);
    }

}