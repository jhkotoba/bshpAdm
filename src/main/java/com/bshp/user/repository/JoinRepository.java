package com.bshp.user.repository;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.bshp.user.vo.AdminRequestVo;

import reactor.core.publisher.Mono;

@Repository
public class JoinRepository{
	
	private final DatabaseClient client;
	
	public JoinRepository(DatabaseClient databaseClient) {
		this.client = databaseClient;
	}
	
	/**
	 * 관리자 회원 등록
	 * @param join
	 * @return
	 */ 
	public Mono<Integer> insertAdmin(AdminRequestVo join){
		
		StringBuilder qry = new StringBuilder("INSERT INTO ADMIN(ADMIN_ID, PASSWORD, PHONE, EMAIL, USE_YN, ADMIN_ST_CD, INS_NO, INS_DTTM, UPT_NO, UPT_DTTM)VALUES(");
		qry.append("'").append(join.getAdminId()).append("'");
		qry.append(",'").append(join.getPassword()).append("'");
		qry.append(",'").append(join.getPhone()).append("'");
		qry.append(",'").append(join.getEmail()).append("'");
		qry.append(",'").append("N").append("'");
		qry.append(",'").append("JOIN_REQUEST").append("'");
		qry.append(",'").append("0").append("'");
		qry.append(",").append("NOW()");
		qry.append(",'").append("0").append("'");
		qry.append(",").append("NOW()").append(")");
		
		return client.sql(qry.toString()).fetch().rowsUpdated();
		
	}
	
}
	