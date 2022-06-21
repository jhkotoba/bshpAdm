/** 영문만 반환
 */
export const fmtOnlyEnText = (value) => {
	return value.replace(/[^A-Za-z]/ig, '');
}

/** 숫자만 반환
 */
export const fmtOnlyNumber = (value) => {
	return value.replace(/[^0-9]/g, '');
}

/** 영문 숫자만 반환
 */
export const fmtOnlyEnTextAndNumber = (value) => {
	return value.replace(/[^A-Za-z0-9+]/g, '');
}

/** 전화번호 포맷
 */
export const fmtPhone = (value) => {
	return value.replace(/[^0-9]/g, '')
		.replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, '$1-$2-$3');
}
