import { constant } from "/static/script/common/constant.js";
import { fmtOnlyEnTextAndNumber, fmtOnlyNumber, fmtPhone } from "/static/script/common/string.js";
import { isEmpty, isPassword, isPhone, isEmail } from "/static/script/common/validation.js";
import { postFetch } from "/static/script/common/fetch.js";

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
	adminId.addEventListener('keyup', e => e.target.value = fmtOnlyEnTextAndNumber(e.target.value));
	
	// 핸드폰번호 포멧
	phone.addEventListener('keyup', e => e.target.value = fmtOnlyNumber(e.target.value));
	phone.addEventListener('focusin', e => e.target.value = fmtOnlyNumber(e.target.value));
	phone.addEventListener('focusout', e => e.target.value = fmtPhone(e.target.value));
});

/**
 * 유효성 검사
 */
function joinValidation(params){
	
	// 타겟명
	let paramName = {
		adminId : "아이디", password : "비밀번호", phone : "전화번호", email : "이메일"
	}
		
	// 빈값 체크
	for(let key in params){
    	if(isEmpty(params[key])){
			alert(paramName[key] + "을(를) 입력해주십시오.");
			document.getElementById(key).focus();
			return false;
		}
	}
	
	// 비밀번호 확인
	if(params.password !== params.confirm){
		alert("비밀번호가 일치하지 않습니다.");
		document.getElementById("password").focus();
		return false;	
	}
	
	// 비밀번호 체크
	if(isPassword(params.password) == false){
		alert("비밀번호가 올바르지 않습니다. \n (8~20자리, 영문, 숫자 포합되어야 합니다.)");
		document.getElementById("password").focus();
		return false;
	}
	
	// 전화번호 체크
	if(isPhone(params.phone) == false) {
		alert("전화번호가 올바르지 않습니다.");
		document.getElementById("phone").focus();
		return false;	
	} 
	
	// 이메일 체크
	if(isEmail(params.email) == false){
		alert("이메일이 올바르지 않습니다.");
		document.getElementById("email").focus();
		return false;
	} 
	
	return true;
}

/**
 * 가입신청
 */
function joinRequest(){
	
	// 전송할 파라미터
	let params = {
		adminId: document.getElementById("adminId").value,
		password: document.getElementById("password").value,
		confirm: document.getElementById("confirm").value,
		phone: document.getElementById("phone").value,
		email: document.getElementById("email").value
	}
	
	// 유효성 검사
	if(joinValidation(params) == false){
		return;
	}
	
	// 암호화
	params.adminId = crypt.encrypt(params.adminId);
	params.password = crypt.encrypt(params.password);
	// 전화번호 하이픈 제거
	params.phone = fmtOnlyNumber(params.phone);
	
	// 회원신청
	postFetch({
		url: '/join/joinRequest',
		body: params
	}).then(data => {
		console.log("data:", data);
		if(data.resultCode === "SUCCESS"){
			alert(data.message);
			window.location.replace('/login');
		}else{
			alert(data.message);
		}
	}).catch(function(error){
		console.error("error:", error);
	});
}