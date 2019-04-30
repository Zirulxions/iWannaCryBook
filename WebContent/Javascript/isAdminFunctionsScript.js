function $(id) {
	return document.getElementById(id);
}

var ulNav, liNav, aNav, iNav;

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
	liNav.appendChild(aNav);
	iNav = document.createElement("i");
	iNav.setAttribute("class","material-icons");
	iNav.innerHTML = "warning";
	aNav.appendChild(iNav);
	
	fetch("./AdminFunctions", {method:'PUT'})
	.then(function(response){
		return response.json();
	})
	.then(function(data){
		console.log(data);
	})
}

window.load = alertingAdminMode();