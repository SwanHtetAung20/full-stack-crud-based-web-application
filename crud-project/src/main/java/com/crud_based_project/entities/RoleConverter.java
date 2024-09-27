package com.crud_based_project.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

public class RoleConverter implements DynamoDBTypeConverter<String, ROLE> {

    @Override
    public String convert(ROLE role) {
        return role != null ? role.name() : null;
    }

    @Override
    public ROLE unconvert(String roleName) {
        return roleName != null ? ROLE.valueOf(roleName) : null;
    }
}
