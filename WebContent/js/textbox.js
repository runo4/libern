function coloeSet(argObj){
 // ==============================================
 //	入力済み時の背景色設定
 // ==============================================
    if(argObj.value==""){
        argObj.style.backgroundColor = "";
    }else{
        argObj.style.backgroundColor = "rgba(170, 198, 201, 0.20)";
    }
}

function colorReset(argObj){
    argObj.style.backgroundColor = "";
}