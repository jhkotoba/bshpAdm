package com.bshp.user.repository;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.bshp.user.vo.JoinRequestVo;

import reactor.core.publisher.Mono;

@Repository
public class JoinRepository{
	
	private final DatabaseClient client;
	
	public JoinRepository(DatabaseClient databaseClient) {
		this.client = databaseClient;
	}
	
	
	public Mono<Integer> insertAdmin(JoinRequestVo join){
		
		StringBuilder qry = new StringBuilder("INSERT INTO ADMIN(ADMIN_ID, PASSWORD, PHONE, EMAIL, USE_YN, INS_DTTM, UPT_DTTM)VALUES(");
		qry.append("'").append(join.getAdminId()).append("'");
		qry.append(",'").append(join.getPassword()).append("'");
		qry.append(",'").append("01011111111").append("'");
		qry.append(",'").append("jhkotoba@gmail.com").append("'");
		qry.append(",'").append("Y").append("'");
		qry.append(",").append("NOW()");
		qry.append(",").append("NOW()").append(")");
		
		return client.sql(qry.toString()).fetch().rowsUpdated();
		
	}
	
}
	