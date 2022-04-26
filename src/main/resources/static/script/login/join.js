import { constant } from "/static/script/common/constant.js";
import { postFetch } from "/static/script/common/fetchUtil.js";

//암호화 객체 생성
const crypt = new JSEncrypt();
window.addEventListener('DOMContentLoaded', function(event){
	
	// 암호화 공개키 세팅
	crypt.setPublicKey(constant.aes256.publicKey);
	
	// 취소
	cancel.addEventListener('click', e => window.history.back());
	
	// 가입신청
	request.addEventListener('click', joinRequest);
});

/**
 * 가입신청
 */
function joinRequest(event){
	
	let adminId = crypt.encrypt(document.getElementById("adminId").value);
	let password = crypt.encrypt(document.getElementById("passwd").value);
	
	postFetch({
		url: '/join/joinRequest',
		body: {adminId, password}
	}).then(data => {
		console.log(data);
	}).catch(function(error){
		console.error(error);
		alert("회원신청에 실패하였습니다.");
	});
}