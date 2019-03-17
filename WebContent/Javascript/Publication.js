function $(id) {
	return document.getElementById(id);
}

var option;

function upText(){
	option = 1;
	doFetch();
}

function upImage(){
	option = 2;
	doFetch();
}

function upVideo(){
	option = 3;
	doFetch();
}

function doFetch(){
	var formData = FormData();
	switch(option){
	case 1:
		formData.append("option", option);
		formData.append("upTextText", $("upTextText").value);
		break;
	case 2:
		formData.append("option", option);
		if($("upImageText").value.trim != null || $("upImageText").value.trim != ""){
			formData.append("upImageText", $("upImageText").value);
		}
		formData.append("upImageFile", $("upImageFile").files[0]);
		break;
		
	case 3:
		formData.append("option", option);
		if($("upVideoText").value.trim != null || $("upVideoText").value.trim != ""){
			formData.append("upVideoText", $("upVideoText").value);
		}
		formData.append("upVideoFile", $("upVideoFile").files[0]);
		break;
	default:
		alert("Something is wrong. Reload the Page.");
		break;
	}
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