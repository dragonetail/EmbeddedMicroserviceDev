package com.github.dragonetail.model.converter;


import com.github.dragonetail.enums.UserStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 用户状态DB转换，序列化
 *
 * @author sunyx
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