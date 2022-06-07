package com.bshp.system.repository;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.bshp.common.util.CommonUtil;
import com.bshp.system.vo.MenuVo;

import reactor.core.publisher.Flux;

@Repository
public class MenuRepository {

private final DatabaseClient client;
	
	public MenuRepository(DatabaseClient databaseClient) {
		this.client = databaseClient;
	}	
	
	/**
	 * 메뉴목록 조회
	 * @param adminId
	 * @return
	 */
	public Flux<MenuVo> selectMenuList(){
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT         ");
		sql.append("	MENU_NO    ");
		sql.append("	, MENU_NM  ");
		sql.append("	, MENU_URL ");
		sql.append("	, MENU_LV  ");
		sql.append("	, MENU_SEQ ");
		sql.append("	, GROUP_NO ");
		sql.append("	, DISP_YN  ");
		sql.append("	, USE_YN   ");
		sql.append("	, INS_NO   ");
		sql.append("	, DATE_FORMAT(INS_DTTM, '%Y-%m-%d %H:%i:%S') AS INS_DTTM ");
		sql.append("	, UPT_NO   ");
		sql.append("	, DATE_FORMAT(UPT_DTTM, '%Y-%m-%d %H:%i:%S') AS UPT_DTTM ");
		sql.append("FROM MENU");
		
		return client.sql(() -> sql.toString()).fetch().all().map(menu -> CommonUtil.convertMapToVo(menu, MenuVo.class));
	}
}
