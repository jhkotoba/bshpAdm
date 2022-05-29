package com.bshp.user.repository;

import java.util.Map;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public class AdminRepository{
	
	private final DatabaseClient client;
	
	public AdminRepository(DatabaseClient databaseClient) {
		this.client = databaseClient;
	}
	
	/**
	 * 로그인 처리하기위한 관리자조회
	 * @param userId
	 * @return
	 */
	public Mono<Map<String, Object>> isAdmin(String adminId){
		
		StringBuilder qry = new StringBuilder("SELECT COUNT(1) AS count FROM ADMIN WHERE 1=1");
		qry.append(" AND ADMIN_ID = '").append(adminId).append("'");
		
		return client.sql(() -> qry.toString()).fetch().one();
	}
}
	