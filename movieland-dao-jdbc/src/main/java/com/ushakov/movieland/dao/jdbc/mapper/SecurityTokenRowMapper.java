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
        return new SecurityToken(uuid.toString()
                , resultSet.getString("nickName")
                , UserRole.valueOf(resultSet.getString("userRole"))
                , resultSet.getInt("id"));
    }
}
