package com.ushakov.movieland.dao.jdbc.mapper;

import com.ushakov.movieland.common.SecurityToken;
import com.ushakov.movieland.common.UserRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SecurityTokenRowMapper implements RowMapper<SecurityToken> {
    @Override
    public SecurityToken mapRow(ResultSet resultSet, int i) throws SQLException {
        UUID uuid = UUID.randomUUID();

        SecurityToken securityToken = new SecurityToken();
        securityToken.setUuid(uuid.toString());
        securityToken.setNickName(resultSet.getString("nickName"));
        securityToken.setUserRole(UserRole.valueOf(resultSet.getString("userRole")));
        securityToken.setId(resultSet.getInt("id"));

        return securityToken;
    }
}
