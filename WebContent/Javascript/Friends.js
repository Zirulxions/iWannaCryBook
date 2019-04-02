function $(id) {
	return document.getElementById(id);
}
function addFriend(){
	alert("test");
	let body = {
		userFriend: $("addFriends").value,
	};
	let config = {
		method: 'POST',
		body: JSON.stringify(body)
	};
	fetch("./Friends", config)
	.then(function(response){
		return response.json();
	})
	.then(function(data){
		console.log(data);
		if(data.status == 200){
			alert(data.message);
			window.location.reload();
		}
	})
}

$("buttonFriends").addEventListener("click", addFriend);
