////脸部编辑操作模块
//脸部编辑
function faceChange(i) {
	expressDataParse(face,"facePart",faceData["face"+i].frontSide,true);
	if(faceTag.color!=""){
		roleA.selectAll(".faceColor").attr("fill",faceTag.color);
	}
}
//头发编辑
function hairChange(i) {
	hairDataParse(frontHair,middleHair,backHair,"hairPart",hairData["hair"+i].frontSide,true);
	if(hairTag.color!=""){
		roleA.selectAll(".hairColor").attr("fill",hairTag.color);
	}
}
//眼睛编辑
function eyeChange(i) {
	expressDataParse(eye,"eyePart",eyeData["eye"+i].frontSide,true);
}
//眉毛编辑
function eyebrowChange(i){
	expressDataParse(eyebrow,"eyebrowPart",eyebrowData["eyebrow"+i].frontSide,true);
}
//嘴部编辑
function mouthChange(i){
	expressDataParse(mouth,"mouthPart",mouthData["mouth"+i].frontSide,true);
}
