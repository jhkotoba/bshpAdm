import { constant } from "/static/script/common/constant.js";
import { postFetch } from "/static/script/common/fetch.js";
import { createAsideMenu } from "/static/script/function/menu.js";

// 암호화 객체 생성
const crypt = new JSEncrypt();
window.addEventListener('DOMContentLoaded', function(){
	
	// 암호화 공개키 세팅
	crypt.setPublicKey(constant.aes256.publicKey);
	
	// 로그인 버튼
	login.addEventListener('click', loginProcess);
	password.addEventListener('keyup', event => event.keyCode === 13 ? loginProcess(event) : null)
});

/**
 * 로그인 처리
 */
async function loginProcess(){
	let adminId = crypt.encrypt(document.getElementById("adminId").value);
	let password = crypt.encrypt(document.getElementById("password").value);
	
	// 로그인 처리
	let loginRes = await postFetch({url: '/login/loginProcess', body: {adminId, password}});
	if(loginRes.resultCode !== 'SUCCESS'){
		alert(loginRes.resultMessage);
		return false;
	}
	
	// 사용자 메뉴조회
	let menuRes = await postFetch({url: '/system/getAdminMenuList', body: {}});
	if(menuRes.resultCode !== 'SUCCESS'){
		alert(menuRes.resultMessage);
		return false;
	}
	
	// 세션스토로지 초기화
	sessionStorage.removeItem("asideMenu");	
	// 메뉴 태그 생성, 세션스토로지 저장
	sessionStorage.setItem("asideMenu", createAsideMenu(menuRes.data));
	
	// 메인 페이지 이동
	window.location.href = "/";
}