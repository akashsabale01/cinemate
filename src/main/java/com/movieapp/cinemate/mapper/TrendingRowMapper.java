package com.movieapp.cinemate.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.movieapp.cinemate.pojo.Trending;

public class TrendingRowMapper implements RowMapper<Trending> {

	@Override
	public Trending mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Trending(rs.getString(1), rs.getInt(2));
	}
}
