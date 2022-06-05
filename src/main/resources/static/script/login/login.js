import { constant } from "/static/script/common/constant.js";
import { postFetch } from "/static/script/common/fetchUtil.js";

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
function loginProcess(){
	let adminId = crypt.encrypt(document.getElementById("adminId").value);
	let password = crypt.encrypt(document.getElementById("password").value);
	
	postFetch({
		url: '/login/loginProcess',
		body: {adminId, password}
	}).then(data => {
		if(data.resultCode == 'SUCCESS'){
			window.location.href = "/";	
		}else{
			alert(data.resultMessage);
		}
	}).catch(function(error){
		console.error(error);
	});
}