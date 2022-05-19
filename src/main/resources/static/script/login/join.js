import { constant } from "/static/script/common/constant.js";
import { onlyEnTextAndNumber, onlyNumber, phoneFormat } from "/static/script/common/stringUtil.js";
import { postFetch } from "/static/script/common/fetchUtil.js";

//암호화 객체 생성
const crypt = new JSEncrypt();
window.addEventListener('DOMContentLoaded', function(){
	
	// 암호화 공개키 세팅
	crypt.setPublicKey(constant.aes256.publicKey);
	
	// 취소
	cancel.addEventListener('click', () => window.history.back());
	
	// 가입신청
	request.addEventListener('click', joinRequest);
	
	// 아이디 문자
	adminId.addEventListener('keyup', e => e.target.value = onlyEnTextAndNumber(e.target.value));
	
	// 핸드폰번호 포멧
	phone.addEventListener('keyup', e => e.target.value = onlyNumber(e.target.value));
	phone.addEventListener('focusin', e => e.target.value = onlyNumber(e.target.value));
	phone.addEventListener('focusout', e => e.target.value = phoneFormat(e.target.value));
});

/**
 * 가입신청
 */
function joinRequest(){
	
	let adminId = crypt.encrypt(document.getElementById("adminId").value);
	let password = crypt.encrypt(document.getElementById("passwd").value);
	let phone = crypt.encrypt(document.getElementById("phone").value);
	let email = crypt.encrypt(document.getElementById("email").value);
	
	postFetch({
		url: '/join/joinRequest',
		body: {adminId, password, phone, email}
	}).then(data => {
		console.log(data);
	}).catch(function(error){
		console.error(error);
		alert("회원신청에 실패하였습니다.");
	});
}