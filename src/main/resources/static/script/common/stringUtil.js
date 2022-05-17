/** 전화번호 포맷
 */
export const phoneFormat = (number) => {
	if(number){
		number = String(number);
		if(number.length > 13){
			return number;
		}else{
			return number.replace(/[^0-9]/, '')
				.replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);	
		}
	}else{
		return '';
	}
}