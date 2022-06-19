package com.bshp.system.repository;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.bshp.common.util.CommonUtil;
import com.bshp.system.vo.AuthVo;

import reactor.core.publisher.Flux;

@Repository
public class AuthRepository {

private final DatabaseClient client;
	
	public AuthRepository(DatabaseClient databaseClient) {
		this.client = databaseClient;
	}	
	
	/**
	 * 사용자 권한 조회
	 * @param adminId
	 * @return
	 */
	public Flux<AuthVo> selectAdminNoAuthList(String adminNo){
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ADMIN_NO, MENU_NO FROM AUTH WHERE 1=1 AND ADMIN_NO = ").append(adminNo);
		
		return client.sql(() -> sql.toString()).fetch().all().map(auth -> CommonUtil.convertMapToVo(auth, AuthVo.class));
	}
	
	/**
	 * 사용자 권한 조회
	 * @param adminId
	 * @return
	 */
	public Flux<AuthVo> selectAdminIdAuthList(String adminId){
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ADMIN_NO, MENU_NO FROM AUTH WHERE ADMIN_NO = (SELECT ADMIN_NO FROM ADMIN WHERE ADMIN_ID = '").append(adminId).append("')");
		
		return client.sql(() -> sql.toString()).fetch().all().map(auth -> CommonUtil.convertMapToVo(auth, AuthVo.class));
	}
}
