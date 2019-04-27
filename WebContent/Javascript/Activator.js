var tabs = document.querySelectorAll('.tabs')
for (var i = 0; i < tabs.length; i++){
	M.Tabs.init(tabs[i]);
}

function activator(){
	fetch("./AdminFunctions",{method:'GET'})
	.then(function(response){
		return response.json();
	})
	.then(function(data){
		console.log(data);
		if(data.htmlScript == "noadm"){
			return;
		} else {
			var scr;
			scr = document.createElement("script");
			scr.setAttribute("type","text/javascript");
			scr.src = data.htmlScript;
			document.getElementById("scripts").appendChild(scr);
		}
	})
}

//window.load = activator();

//$(document).ready(function(){
//	$('.tabs').tabs();
//});