package net.thisislaz.angularwithspringboot.rowmapper;

import net.thisislaz.angularwithspringboot.models.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Role.builder()
                .id(resultSet.getLong("id"))
                .roleName(resultSet.getString("name"))
                .permissions(resultSet.getString("permission"))
                .build();
    }















}
