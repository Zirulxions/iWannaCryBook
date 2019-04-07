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
	var formData = new FormData();
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

$("button1").addEventListener("click", upText);
$("button3").addEventListener("click", upImage);
$("button4").addEventListener("click", upVideo);

function getPublication() {
	fetch("./PublicationServlet", {method: 'GET'})
	.then(function(response){
		return response.json();
	})
	.then(function(data){
		console.log(data);
		var item1, item2, item3, item4, item5, item6, item7, item8;
		for(var i = 0; i < data.postCounter; i++){
			//Create elements.
			item1 = document.createElement("div");
			item1.setAttribute("class", "card");
			document.getElementById("postView").appendChild(item1);
			item2 = document.createElement("div");
			item2.setAttribute("class", "card-stacked");
			item1.appendChild(item2);
			item3 = document.createElement("div");
			item3.setAttribute("class", "card-content");
			item2.appendChild(item3);
			if(data.postUrl[i].trim() != 'unknown'){
				item4 = document.createElement("img");
				item4.setAttribute("class", "responsive-img");
				item4.src = data.postUrl[i];
				item3.appendChild(item4);
			}
			item5 = document.createElement("h5");
			item5.innerText = data.postText[i];
			item3.appendChild(item5);
			item6 = document.createElement("div");
			item6.setAttribute("class", "input-field col s12");
			item3.appendChild(item6);
			item3.appendChild(document.createElement("br"));
			item3.appendChild(document.createElement("br"));
			item3.appendChild(document.createElement("br"));
			item7 = document.createElement("input");
			item7.setAttribute("id", "comment"+i);
			item7.setAttribute("class", "validate");
			item7.setAttribute("type", "text");
			item6.appendChild(item7);
			item8 = document.createElement("label");
			item8.setAttribute("for", "comment");
			item8.setAttribute("class", "active");
			item8.innerText = "Comment Here!";
			item6.appendChild(item8);
			//Lets See What Happens...
		}
	})
}
