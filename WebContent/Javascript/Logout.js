document.getElementById('name').innerHTML = sessionStorage.getItem("name");

function logOut(){
  	let config = {
       	method: 'GET',
   	};
   	fetch("./Logout", config)
   	.then(function(res){
       	return res.json();
   	})
   	.then(function(data){
       	if(data.redirect != null && data.redirect != undefined){
       		sessionStorage.clear();
       		window.location.href = data.redirect;
       	}
   	})
}

function getAvatar(){
	let config = {
		method: 'GET',
	};
	fetch("./UpdateUserConfig", config)
	.then(function(res){
		return res.json();
	})
	.then(function(data){
		if(data.message != null && data.url != 'unknown'){
			document.getElementById("userAvatar").src = data.url;
			if(document.getElementById('profileAvatar')){
				document.getElementById("profileAvatar").src = data.url;
			}
		}
		if(document.getElementById('profile_Username') && document.getElementById('profile_Name')){
			document.getElementById("profile_Username").innerText = data.username;
			document.getElementById("profile_Name").innerText = data.name + " " + data.lastName;
		}
	})
}

document.getElementById("button2").addEventListener("click", logOut);