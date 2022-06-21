/** 메뉴 태그 생성
 */
export const createAsideMenu = menuList => {
	
	let menu = document.createElement('div');
	let ul = document.createElement('ul');
	
	menuList.sort((a, b) => {		
	    if(a.menuLv < b.menuLv) return -1;
	    if(a.menuLv > b.menuLv) return 1;
	    if(a.menuSeq < b.menuSeq) return -1;
	    if(a.menuSeq > b.menuSeq) return 1;
	}).forEach((menu, index) => {
		let prev = menuList[index-1];
		let next = menuList[index+1];
		
		let li = document.createElement('li');
		li.textContent = menu.menuNm;
		ul.appendChild(li);
		
	});
	
	menu.appendChild(ul);
	return menu.innerHTML;
}