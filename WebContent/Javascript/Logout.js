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

document.getElementById("button2").addEventListener("click", logOut);