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

/** 전화번호 형식 체크
 */
export const isPhone = value => {
	return true;
}

/** 이메일 형식 체크
 */
export const isEmail = value => {
	return true;
}

