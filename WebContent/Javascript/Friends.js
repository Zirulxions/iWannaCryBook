function $(id) {
	return document.getElementById(id);
}
function addFriend(){
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

function getFriendList(){
	fetch("./Friends",{method: 'GET'})
	.then(function(response){
		return response.json();
	})
	.then(function(data){
		console.log(data);
		var item1;
		if(data.status == 200){
			$("h61").innerText = "Friend List:";
			var x = 1;
			for(i = 0; i < data.friendCounter; i++){
				item1 = document.createElement("h6");
				item1.setAttribute("style","display:inline-block");
				if(i == 0){
					item1.innerText = "(" + x + ") " + data.friendsUserName[i] + " ";
				} else {
					item1.innerText = ", (" + x + ") " + data.friendsUserName[i] + " ";
				}
				$("friendList").appendChild(item1);
				x++;
			}
		}
	})
}

$("buttonFriends").addEventListener("click", addFriend);
