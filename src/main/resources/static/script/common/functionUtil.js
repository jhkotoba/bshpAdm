export const createAside = menuList => {	
	
	// 세션스토로지 초기화
	sessionStorage.removeItem("menu");
	
	let menu = '';
	menuList.sort((a, b) => {
	    if(a.menuLv < b.menuLv) return -1;
	    if(a.menuLv > b.menuLv) return 1;
	    if(a.menuSeq < b.menuSeq) return -1;
	    if(a.menuSeq > b.menuSeq) return 1;
	}).forEach(menu => {
			
		
		
		
		
	});
	
}