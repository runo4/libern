function ShowLength( str ) {
	obj = document.getElementById("inputlength");
	obj.innerHTML = 140 - str.length;
	if(str.length >= 130){
		obj.style.color = "red";
	}else{
		obj.style.color = "#aac6c9";
	}
}