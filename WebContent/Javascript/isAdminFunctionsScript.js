function $(id) {
	return document.getElementById(id);
}

var ulNav, liNav, aNav, iNav;
var rowDiv, cols12Div, cardDiv, cardStackDiv, cardContentDiv, inputFieldDiv, inputFieldDiv2, cardActionDiv;
var headH3, inputF1, label1, inputF2, label2, inputF3, label3, inputF4, label4, btnButton, ibtnButton;
var optionHelp = 0;

function alertingAdminMode(){
	alert("ADMIN MODE ON");
	
	ulNav = document.createElement("ul");
	ulNav.setAttribute("id","nav-mobile");
	ulNav.setAttribute("class","left");
	$("navHelper").appendChild(ulNav);
	liNav = document.createElement("li");
	ulNav.appendChild(liNav);
	aNav = document.createElement("a");
	aNav.setAttribute("class","btn-floating");
	aNav.setAttribute("id","adminPanel");
	aNav.setAttribute("onclick","showHideAdmPanel();")
	liNav.appendChild(aNav);
	iNav = document.createElement("i");
	iNav.setAttribute("class","material-icons");
	iNav.innerHTML = "warning";
	aNav.appendChild(iNav);
	for(var count = 1; count <= 2; count++){
		rowDiv = document.createElement("div");
		rowDiv.setAttribute("class","row");
		$("admFuncPanel").appendChild(rowDiv)
		cols12Div = document.createElement("div");
		cols12Div.setAttribute("class","col s12 m12");
		rowDiv.appendChild(cols12Div);
		cardDiv = document.createElement("div");
		cardDiv.setAttribute("class","card horizontal");
		cols12Div.appendChild(cardDiv);
		cardStackDiv = document.createElement("div");
		cardStackDiv.setAttribute("class","card-stacked");
		cardDiv.appendChild(cardStackDiv);
		cardContentDiv = document.createElement("div");
		cardContentDiv.setAttribute("class","card-content");
		cardStackDiv.appendChild(cardContentDiv);
		headH3 = document.createElement("h3");
		headH3.setAttribute("class","header");
		if(count == 1){
			headH3.innerHTML = "Ban or Unban an user.";
			
		} else if (count == 2){
			headH3.innerHTML = "Update User Password";
		}
		cardContentDiv.appendChild(headH3);
		inputFieldDiv = document.createElement("div");
		inputFieldDiv.setAttribute("class","input-field col s12");
		cardContentDiv.appendChild(inputFieldDiv);
		if(count == 1){
			inputF1 = document.createElement("input");
			inputF1.setAttribute("id","idToBanUnban");
			inputF1.setAttribute("type","text");
			inputF1.setAttribute("class","validate");
			inputFieldDiv.appendChild(inputF1);
			label1 = document.createElement("label");
			label1.setAttribute("for","idToBanUnban");
			label1.setAttribute("class","");//maybe "";
			label1.innerHTML = "Ban / Unban User";
			inputFieldDiv.appendChild(label1);
		} else if(count = 2){
			inputF1 = document.createElement("input");
			inputF1.setAttribute("id","idUserPwChange");
			inputF1.setAttribute("type","text");
			inputF1.setAttribute("class","validate");
			inputFieldDiv.appendChild(inputF1);
			label1 = document.createElement("label");
			label1.setAttribute("for","idUserPwChange");
			label1.setAttribute("class","");//maybe "";
			label1.innerHTML = "User to change Password by Username";
			inputFieldDiv.appendChild(label1);
			inputF2 = document.createElement("input");
			inputF2.setAttribute("id","pwChanged");
			inputF2.setAttribute("type","password");
			inputF2.setAttribute("class","validate");
			inputFieldDiv2 = document.createElement("div");
			inputFieldDiv2.setAttribute("class","input-field col s12");
			cardContentDiv.appendChild(inputFieldDiv2);
			inputFieldDiv2.appendChild(inputF2);
			label2 = document.createElement("label");
			label2.setAttribute("for","pwChanged");
			label2.setAttribute("class","");//maybe "";
			label2.innerHTML = "New Password here";
			inputFieldDiv2.appendChild(label2);
			
		}
		cardActionDiv = document.createElement("div");
		cardActionDiv.setAttribute("class","card-action");
		cardStackDiv.appendChild(cardActionDiv);
		btnButton = document.createElement("button");
		btnButton.setAttribute("class","btn waves-effect waves light");
		btnButton.setAttribute("type","submit");
		if(count == 1){
			btnButton.setAttribute("onclick","optionHelp = 1; updateConfig();")
			btnButton.setAttribute("id","updateBanStatus");
			btnButton.innerHTML = "Ban/Unban User";
		} else if(count == 2){
			btnButton.setAttribute("onclick","optionHelp = 2; updateConfig();")
			btnButton.setAttribute("id","updateUsPassword");
			btnButton.innerHTML = "Change User's Password";
		}
		cardActionDiv.appendChild(btnButton);
		ibtnButton = document.createElement("i");
		ibtnButton.setAttribute("class","material-icons right");
		ibtnButton.innerHTML = "send";
		btnButton.appendChild(ibtnButton);
	}
}

function updateConfig(){
	let body = {
		bannedUser: $("idToBanUnban").value,
		usernameEdit: $("idUserPwChange").value,
		passwordEdit: $("pwChanged").value,
		option: optionHelp
	};
	fetch("./AdminFunctions",{method:"put", body: JSON.stringify(body)})
	.then(function (response){
		return response.json();
	})
	.then(function (data){
		alert(data.message);
	})
}

function showHideAdmPanel(){
	if($("admFuncPanel").style.display == "none"){
		$("admFuncPanel").style.display = "block";
	} else if ($("admFuncPanel").style.display == "block"){
		$("admFuncPanel").style.display = "none";
	}
}

window.load = alertingAdminMode();