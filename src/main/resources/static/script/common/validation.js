/** 빈값 체크
 */
export const isEmpty = value => {
	
	if(value === null || value === undefined){
		return true;
	}else if((typeof value === "string" && value.trim() === "")){
		return true;
	}else if(typeof value === "object" && !Object.key(value).length){
		return true;
	}else{
		return false;
	}
}

/** 비밀번호 체크(8~20자리, 영문,숫자 특수문자 조합)
 */
export const isPassword = value => /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,20}$/.test(String(value));

/** 전화번호 형식 체크
 */
export const isPhone = value => /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/.test(String(value));

/** 이메일 형식 체크
 */
export const isEmail = value => /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i.test(String(value));

