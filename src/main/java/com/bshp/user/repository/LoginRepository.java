package com.bshp.user.repository;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.bshp.common.util.CommonUtil;
import com.bshp.user.exception.LoginException;
import com.bshp.user.vo.PrivateAdminVo;

import reactor.core.publisher.Mono;

@Repository
public class LoginRepository{
	
	private final DatabaseClient client;
	
	public LoginRepository(DatabaseClient databaseClient) {
		this.client = databaseClient;
	}
	
	/**
	 * 로그인 처리하기위한 관리자조회
	 * @param userId
	 * @return
	 */
	public Mono<PrivateAdminVo> getLoginUser(String userId){
		
		StringBuilder qry = new StringBuilder("SELECT ADMIN_NO AS adminNo, ADMIN_ID AS adminId, PASSWORD AS password FROM ADMIN WHERE 1=1");
		qry.append(" AND ADMIN_ID = '").append(userId).append("'");
		
		return client.sql(() -> qry.toString()).fetch().one()			
			.map(user -> CommonUtil.convertMapToVo(user, PrivateAdminVo.class))
			// 미조회 예외
			.switchIfEmpty(Mono.error(new LoginException(LoginException.reason.ID_NOT_FOUND)));
	}
}
	