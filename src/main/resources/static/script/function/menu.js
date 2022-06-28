/** 메뉴 태그 생성
 */
export const createAsideMenu = menuList => {
	
	let levelList = [...new Set(menuList.map(menu => menu.menuLv))].sort();
	let maxlevel = levelList[levelList.length-1];
	
	// 메뉴 레벨별 순서별 정렬
	let resultList = null;
	for(let i=0; i<maxlevel; i++){
		if(i === 0){
			 resultList = menuList.filter(menu => menu.menuLv === (i+1))
			 	.sort((a, b) => a.menuSeq - b.menuSeq)
		}else{
			menuList.filter(menu => menu.menuLv === (i+1))
				.forEach(menu => {
					for(let j=0; j<resultList.length; j++){
						if(resultList[j].menuNo == menu.groupNo){
							resultList.splice((j+1), 0, menu);
						}
					}
				});
		}		
	}
	
	// 메뉴 태그 생성
	let html = '<ul>';
	for(let i=0; i<resultList.length; i++){
		
		let item = resultList[i];
		let next = resultList[i+1];
		
		html += '<li>' + resultList[i].menuNm;
		
		if(item.menuLv != next?.menuLv){
			if(item.menuLv < next?.menuLv){
				html += '<ul>';
			}else{
				html += '</li></ul>';
				if(next == undefined){
					html += '</li>';
				}else if(item.menuLv > next.menuLv){
					html += '</li>';
				}
			}
		}else{
			html += '</li>';
		}	
	}
	html += '</ul>';
	return html;
}

