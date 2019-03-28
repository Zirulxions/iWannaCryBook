function $(id) {
	return document.getElementById(id);
}
function addFriend(){
	doFetch();
	alert("hello");
}
function doFetch(){
	var formData = new FormData();
	formData.append("addFriends", $("addFriends").value);
	let config = {
			method: 'POST',
			body: formData,
			header: {'Content-Type':'multipart/form-data'},
		}
	fetch("./PublicationServlet", config)
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
