function $(id) {
	return document.getElementById(id);
}

var option;

function UpAvatar(){
	option = 1;
	doFetch();
}

function UpPassword(){
	option = 2;
	doFetch();
}

function UpEmail() {
	option = 3;
	doFetch();
}

function UpName() {
	option = 4;
	doFetch();
}

function UpOthers() {
	option = 5;
	doFetch();
}

function doFetch(){
	var formData = new FormData();
	switch(option){
		case 1:
			formData.append("option", option);
			formData.append("file", $("file").files[0]);
			break;
		case 2:
			formData.append("option", option);
			formData.append("oldPass", $("oldPass").value);
			formData.append("newPass", $("newPass").value);
			break;
		case 3:
			formData.append("option", option);
			formData.append("oldEmail", $("oldEmail").value);
			formData.append("newEmail", $("newEmail").value);
			break;
		case 4:
			formData.append("option", option);
			formData.append("newName", $("newName").value);
			formData.append("newLName", $("newLName").value);
			break;
		default:
			alert("Corrupted Data. Reload the Page Please.");
			break;
	}
	let config = {
			method: 'POST',
			body: formData,
			header: {'Content-Type':'multipart/form-data'},
		}
	fetch("./UpdateUserConfig", config)
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

$("changeAvatar").addEventListener("click", UpAvatar);
$("changePassword").addEventListener("click", UpPassword);
$("changeEmail").addEventListener("click", UpEmail);
$("changeName").addEventListener("click", UpName);