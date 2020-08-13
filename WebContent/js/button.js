function DisableButton( str ){
	button = document.getElementById("button");
	if(str.length >= 140){
		button.disabled = true;
	}else{
		button.disabled = false;
	}
}

function ConfirmDeletePost(){
	if(window.confirm("該当ツイートを削除しますか？")){
		return true;
	}else{
		window.alert("キャンセルしました");
		return false;
	}
}
