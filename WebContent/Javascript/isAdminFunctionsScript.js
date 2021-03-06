function $(id) {
	return document.getElementById(id);
}

var ulNav, liNav, aNav, iNav;
var rowDiv, cols12Div, cardDiv, cardStackDiv, cardContentDiv, inputFieldDiv, inputFieldDiv2, cardActionDiv;
var headH3, inputF1, label1, inputF2, label2, inputF3, label3, inputF4, label4, btnButton, ibtnButton, iMatIcon;
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
			label1.setAttribute("class","");
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
			label1.setAttribute("class","");
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
			label2.setAttribute("class","");
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
			btnButton.setAttribute("onclick","optionHelp = 1; updateConfig();");
			btnButton.setAttribute("id","updateBanStatus");
			btnButton.innerHTML = "Ban/Unban User";
		} else if(count == 2){
			btnButton.setAttribute("onclick","optionHelp = 2; updateConfig();");
			btnButton.setAttribute("id","updateUsPassword");
			btnButton.innerHTML = "Change User's Password";
		}
		cardActionDiv.appendChild(btnButton);
		ibtnButton = document.createElement("i");
		ibtnButton.setAttribute("class","material-icons right");
		ibtnButton.innerHTML = "send";
		btnButton.appendChild(ibtnButton);
	}
	getAdminStats();
	placeSearchBar();
}


function placeSearchBar(){
	rowDiv = document.createElement("div");
	rowDiv.setAttribute("class","row");
	$("admFuncPanel").appendChild(rowDiv);
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
	headH3.innerHTML = "Search Things by Content";
	cardContentDiv.appendChild(headH3);
	inputFieldDiv = document.createElement("div");
	inputFieldDiv.setAttribute("class","input-field col s12");
	cardContentDiv.appendChild(inputFieldDiv);
	inputF1 = document.createElement("input");
	inputF1.setAttribute("id","searchInput");
	inputF1.setAttribute("type","text");
	inputF1.setAttribute("class","validate");
	inputFieldDiv.appendChild(inputF1);
	label1 = document.createElement("label");
	label1.setAttribute("for","searchInput");
	label1.setAttribute("class","");
	label1.innerHTML = "Search Label";
	inputFieldDiv.appendChild(label1);
	cardActionDiv = document.createElement("div");
	cardActionDiv.setAttribute("class","card-action");
	cardStackDiv.appendChild(cardActionDiv);
	btnButton = document.createElement("button");
	btnButton.setAttribute("class","btn waves-effect waves light");
	btnButton.setAttribute("type","submit");
	btnButton.setAttribute("onclick","doSearch()");
	btnButton.setAttribute("id","searchNowButton");
	btnButton.innerHTML = "Search Now";
	cardActionDiv.appendChild(btnButton);
	ibtnButton = document.createElement("i");
	ibtnButton.setAttribute("class","material-icons right");
	ibtnButton.innerHTML = "send";
	btnButton.appendChild(ibtnButton);
}


function updateConfig(){
	let body = {
		bannedUser: $("idToBanUnban").value,
		usernameEdit: $("idUserPwChange").value,
		passwordEdit: $("pwChanged").value,
		option: optionHelp
	};
	fetch("./AdminFunctions",{method:"PUT", body: JSON.stringify(body)})
	.then(function (response){
		return response.json();
	})
	.then(function (data){
		alert(data.message);
	})
}

var rDiv, cs12Div, cardPanelDiv, table, tableHead, tableBody, tableRow, tableH, tableD1, TableD2;

function getAdminStats(){
	fetch("./AdminStats", {method: 'GET'})
	.then(function(response){
		return response.json();
	})
	.then(function(data){
		console.log(data);
		rDiv = document.createElement("div");
		rDiv.setAttribute("class","row");
		$("admFuncPanel").appendChild(rDiv);
		cs12Div = document.createElement("div");
		cs12Div.setAttribute("class","col s12 m12");
		rDiv.appendChild(cs12Div);
		cardPanelDiv = document.createElement("div");
		cardPanelDiv.setAttribute("class","card-panel");
		cs12Div.appendChild(cardPanelDiv);
		table = document.createElement("table");
		table.setAttribute("class","centered highlight responsive-table")
		cardPanelDiv.appendChild(table);
		tableHead = document.createElement("thead");
		table.appendChild(tableHead);
		tableRow = document.createElement("tr");
		tableHead.appendChild(tableRow);
		tableH = document.createElement("th");
		tableH.setAttribute("style","text-align: center;");
		tableH.innerHTML = "Posts/Users";
		tableHead.appendChild(tableH);
		tableH = document.createElement("th");
		tableH.setAttribute("style","text-align: center;");
		tableH.innerHTML = "Quanity";
		tableHead.appendChild(tableH);
		tableBody = document.createElement("tbody");
		table.appendChild(tableBody);
		for(var i = 1; i <= 5; i++){
			tableRow = document.createElement("tr");
			tableBody.appendChild(tableRow);
			tableD1 = document.createElement("td");
			tableD2 = document.createElement("td");
			switch(i){
			case 1:
				tableD1.innerHTML = "Posts By Text";
				tableD2.innerHTML = data.postsByText;
				break;
			case 2:
				tableD1.innerHTML = "Posts By Image";
				tableD2.innerHTML = data.postsByImage;
				break;
			case 3:
				tableD1.innerHTML = "Posts By Video";
				tableD2.innerHTML = data.postsByVideo;
				break;
			case 4:
				tableD1.innerHTML = "Male Users";
				tableD2.innerHTML = data.maleUsers;
				break;
			case 5:
				tableD1.innerHTML = "Female Users";
				tableD2.innerHTML = data.femaleUsers;
				break;
			default:
				break;
			}
			tableRow.appendChild(tableD1);
			tableRow.appendChild(tableD2);
		}
	})
}

var ul, li, img, span, p, a, mi;
var commentIden, postIden, switchOpt;

function doSearch(){
	var formData = new FormData();
	formData.append("dataSearch", $("searchInput").value);
	let config = {
			method: 'POST',
			body: formData,
			header: {'Content-Type':'multipart/form-data'},
		}
	console.log($("searchInput").value);
	fetch("./AdminStats", config)
	.then(function (response){
		return response.json();
	})
	.then(function (data){
		console.log(data);
		rowDiv = document.createElement("div");
		rowDiv.setAttribute("class","row");
		$("admFuncPanel").appendChild(rowDiv);
		cs12Div = document.createElement("div");
		cs12Div.setAttribute("class","col s12");
		rDiv.appendChild(cs12Div);
		headH3 = document.createElement("h3");
		headH3.setAttribute("class","header white-text");
		headH3.innerHTML = "Comment / Post List";
		cs12Div.appendChild(headH3);
		ul = document.createElement("ul");
		ul.setAttribute("class","collection");
		cs12Div.appendChild(ul);
		for(let i = 0; i < data.commentContent.length; i++){
			li = document.createElement("li");
			li.setAttribute("class","collection-item avatar");
			ul.appendChild(li);
			img = document.createElement("img");
			if(data.commentAvatar[i] == "unknown"){
				img.setAttribute("src","Resources/huevito.jpg");
			} else {
				img.setAttribute("src", data.commentAvatar[i]);
			}
			img.setAttribute("alt","");
			img.setAttribute("class","circle");
			li.appendChild(img);
			span = document.createElement("span");
			span.setAttribute("class","title");
			span.innerHTML = "Comment Owner: " + data.commentUsername[i];
			li.appendChild(span);
			p = document.createElement("p");
			p.innerHTML = "Comment Content: " + data.commentContent[i];
			li.appendChild(p);
			a = document.createElement("a");
			a.setAttribute("href","#!");
			a.setAttribute("onclick","commentIden = " + data.commentId[i] + "; switchOpt = 1; deleteByContent()");
			a.setAttribute("class","secondary-content");
			li.appendChild(a);
			mi = document.createElement("i");
			mi.setAttribute("class","material-icons");
			mi.innerHTML = "delete_forever";
			a.appendChild(mi);
		}
		for(let i = 0; i < data.postContent.length; i++){
			li = document.createElement("li");
			li.setAttribute("class","collection-item avatar");
			ul.appendChild(li);
			img = document.createElement("img");
			if(data.postAvatar[i] == "unknown"){
				img.setAttribute("src","Resources/huevito.jpg");
			} else {
				img.setAttribute("src", data.postAvatar[i]);
			}
			img.setAttribute("alt","");
			img.setAttribute("class","circle");
			li.appendChild(img);
			span = document.createElement("span");
			span.setAttribute("class","title");
			span.innerHTML = "Post Owner: " + data.postUsername[i];
			li.appendChild(span);
			p = document.createElement("p");
			p.innerHTML = "Post Content: " + data.postContent[i];
			li.appendChild(p);
			a = document.createElement("a");
			a.setAttribute("href","#!");
			a.setAttribute("onclick","postIden = " + data.postId[i] + "; switchOpt = 2; deleteByContent()");
			a.setAttribute("class","secondary-content");
			li.appendChild(a);
			mi = document.createElement("i");
			mi.setAttribute("class","material-icons");
			mi.innerHTML = "delete_forever";
			a.appendChild(mi);
		}
	})
}

//remember: switchOpt, 1 = Comment, 2 = Post
function deleteByContent(){
	let body ={
		option: switchOpt,
		post: postIden,
		comment: commentIden
	};
	let config = {
		method: 'DELETE',
		body: JSON.stringify(body)
	}
	fetch("./AdminStats", config)
	.then(function(response){
		return response.json();
	})
	.then(function (data){
		alert(data.message);
		if(data.status == 200){
			window.location.reload();
		}
	})
}

function deletePostComment(){
	alert("Deleting ");
}

function showHideAdmPanel(){
	if($("admFuncPanel").style.display == "none"){
		$("admFuncPanel").style.display = "block";
		//alert($("admFuncPanel").getAttribute("id"));
	} else if ($("admFuncPanel").style.display == "block"){
		$("admFuncPanel").style.display = "none";
	}
}

function sendEmail(){
	let body = {
		EmailTo: "andrea"
	};
	fetch("./SendEmail",{method:"POST", body: JSON.stringify(body)})
	.then(function (response){
		return response.json();
	})
	.then(function (data){
		alert(data.message);
	})
}

window.load = alertingAdminMode();