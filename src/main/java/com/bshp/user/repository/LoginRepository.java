package com.bshp.user.repository;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.bshp.common.util.CommonUtil;
import com.bshp.user.vo.PrivateUserVo;

import reactor.core.publisher.Mono;

@Repository
public class LoginRepository{
	
	private final DatabaseClient client;
	
	public LoginRepository(DatabaseClient databaseClient) {
		this.client = databaseClient;
	}
	
	/**
	 * 로그인 처리하기위한 회원조회
	 * @param userId
	 * @return
	 */
	public Mono<PrivateUserVo> getLoginUser(String userId){
		
		StringBuilder qry = new StringBuilder("SELECT USER_ID, USER_NO, PASSWORD FROM ADMIN WHERE 1=1");
		qry.append(" AND USER_ID = '").append(userId).append("'");		
		
		return client.sql(() -> qry.toString()).fetch().one()			
			.map(user -> CommonUtil.convertMapToVo(user, PrivateUserVo.class))
			.onErrorResume(error -> Mono.error(new RuntimeException(error.getMessage(), error)));
	}

}
	