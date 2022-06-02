import { constant } from "/static/script/common/constant.js";
import { fmtOnlyEnTextAndNumber, fmtOnlyNumber, fmtPhone } from "/static/script/common/stringUtil.js";
import { isEmpty, isPhone, isEmail } from "/static/script/common/validationUtil.js";
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
	
	// 빈값 체크
	for(var key in params){
    	if(isEmpty(params[key])){
			return false;
		}
	}
	
	// 전화번호 체크
	if(isPhone(params.phone) == false) return false; 
	
	// 이메일 체크
	if(isEmail(params.email) == false) return false;
	
	return true;
}

/**
 * 가입신청
 */
function joinRequest(){
	
	
	
	// 전송할 파라미터
	let params = {
		adminId : document.getElementById("adminId").value,
		password : document.getElementById("passwd").value,
		phone : document.getElementById("phone").value,
		email : document.getElementById("email").value
	}
	
	// 유효성 검사
	if(joinValidation(params) == false){
		return;
	}
	
	// 암호화
	params.adminId = crypt.encrypt(params.adminId);
	params.password = crypt.encrypt(params.password);
	
	// 회원신청
	postFetch({
		url: '/join/joinRequest',
		body: params
	}).then(data => {
		console.log(data);
		switch(data.resultCode){
		case "0000" :
			break;
		case "9999" :
			break;
		default :
			break;
		}
	}).catch(function(error){
		console.error(error);
		alert("회원신청에 실패하였습니다.");
	});
}