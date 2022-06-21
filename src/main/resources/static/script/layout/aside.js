import { postFetch } from "/static/script/common/fetch.js";
import { createAsideMenu } from "/static/script/function/menu.js";

// 세션스토로지에서 메뉴 가져오기
let menu = sessionStorage.getItem("asideMenu");

// aside 자식노드 비우기
while(aside.hasChildNodes()){ aside.removeChild( aside.firstChild );}

if(menu){
	aside.innerHTML = menu;
}else{
	// 사용자 메뉴조회
	postFetch({url: '/system/getAdminMenuList', body: {}})
		.then(response => {
			// 메뉴 태그 생성, 세션스토로지 저장
			menu = createAsideMenu(response.data);
			sessionStorage.setItem("asideMenu", menu);
			aside.innerHTML = menu;
		});
}