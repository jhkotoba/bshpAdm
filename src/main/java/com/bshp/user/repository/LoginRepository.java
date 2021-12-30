package com.bshp.user.repository;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.bshp.common.CommonUtil;
import com.bshp.user.vo.UserVo;

import reactor.core.publisher.Mono;

@Repository
public class LoginRepository{
	
	private final DatabaseClient client;
	
	public LoginRepository(DatabaseClient databaseClient) {
		this.client = databaseClient;
	}
	
	public Mono<UserVo> getUser(String userId){
		
		StringBuilder qry = new StringBuilder("SELECT USER_ID, USER_NO, PASSWORD FROM ADMIN WHERE 1=1");
		qry.append(" AND USER_ID = '").append(userId).append("'");		
		
		return client.sql(() -> qry.toString()).fetch().one()			
			.map(user -> CommonUtil.converterMapToVo(user, UserVo.class))
			.onErrorResume(error -> Mono.error(new RuntimeException(error.getMessage(), error)));
	}

}
	