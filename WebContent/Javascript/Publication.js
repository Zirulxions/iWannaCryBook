function $(id) {
	return document.getElementById(id);
}

var option, commentDefiner, likeDefiner, typeLike;
var postsIdentity = [];
var item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11, like, dislike, likeIcon, dislikeIcon;
var m , n;

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
		for(var i = 0; i < data.postCounter; i++){
			item1 = document.createElement("div");
			item1.setAttribute("class", "card");
			document.getElementById("postView").appendChild(item1);
			item2 = document.createElement("div");
			item2.setAttribute("class", "card-stacked");
			item1.appendChild(item2);
			item3 = document.createElement("div");
			item3.setAttribute("class", "card-content");
			item3.setAttribute("id", "content" + data.postId[i]);
			item2.appendChild(item3);
			if(data.postUrl[i].trim() != 'unknown' && data.postType[i] == 2){
				item4 = document.createElement("img");
				item4.setAttribute("id", "url" + data.postId[i]);
				item4.setAttribute("class", "responsive-img");
				item4.src = data.postUrl[i];
				item3.appendChild(item4);
			} else if (data.postUrl[i].trim() != 'unknown' && data.postType[i] == 3){
				item4 = document.createElement("video");
				item4.setAttribute("id", "url" + data.postId[i]);
				item4.controls = true;
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
			item7.setAttribute("id", "comment" + data.postId[i]);
			item7.setAttribute("class", "validate");
			item7.setAttribute("type", "text");
			item6.appendChild(item7);
			item8 = document.createElement("label");
			item8.setAttribute("for", "comment" + data.postId[i]);
			item8.setAttribute("class", "");
			item8.innerText = "Comment Here!";
			item6.appendChild(item8);
			item9 = document.createElement("div");
			item9.setAttribute("class", "card-action");
			item2.appendChild(item9);
			item10 = document.createElement("button");
			item10.setAttribute("onclick", "commentDefiner = " + data.postId[i] + "; doComment();");
			item10.setAttribute("class", "btn waves-effect waves-light");
			item10.setAttribute("id", "actionButton" + data.postId[i]);
			item10.innerHTML = "Comment!";
			item9.appendChild(item10);
			item11 = document.createElement("i");
			item11.setAttribute("class", "material-icons right");
			item11.innerHTML = "send";
			item10.appendChild(item11);
			like = document.createElement("button");
			like.setAttribute("onclick", "typeLike = 1; likeDefiner = " + data.postId[i] + "; doLike();");
			like.setAttribute("class", "btn-floating btn-medium waves-effect waves-light light-blue darken-4");
			like.setAttribute("id","buttonlike" + data.postId[i]);
			item9.appendChild(like);
			likeIcon = document.createElement("i");
			likeIcon.setAttribute("class","material-icons");
			likeIcon.innerHTML = "thumb_up";
			like.appendChild(likeIcon);
			dislike = document.createElement("button");
			dislike.setAttribute("onclick", "typeLike = 2; likeDefiner = " + data.postId[i] + "; doLike();");
			dislike.setAttribute("class", "btn-floating btn-medium waves-effect waves-light red darken-4");
			dislike.setAttribute("id","buttondislike" + data.postId[i]);
			item9.appendChild(dislike);
			dislikeIcon = document.createElement("i");
			dislikeIcon.setAttribute("class","material-icons");
			dislikeIcon.innerHTML = "thumb_down";
			dislike.appendChild(dislikeIcon);
			postsIdentity.push(data.postId[i]);
		}
		getComment();
		getLikes();
	})
}

function doComment(){
	var buttonIdentity = "actionButton" + commentDefiner;
	var commentIdentity = "comment" + commentDefiner;
	var urlIdentity;
	var commentUrl = "url" + commentDefiner;
	var urlConfig;
	if($("url" + commentDefiner)){
		urlConfig = $(commentUrl).src;
	} else {
		urlConfig = "unknown";
	}
	let body = {
		commentText: $(commentIdentity).value,
		commentUrl: urlConfig,
		postId: commentDefiner
	};
	let config = {
		method: 'POST',
		body: JSON.stringify(body)
	};
	fetch(("./Comments"), config)
		.then(function(response){
			return response.json();
		})
		.then(function(data){
			console.log(data);
		})
}

var iComment1, iComment2, iComment3;

function getComment(){
	console.log(postsIdentity + " Has Length: " + postsIdentity.length);
	fetch("./Comments?arrid=" + postsIdentity, {method: 'GET', header: {'Content-Type':'application/x-www-form-urlencoded'}})
		.then(function(response){
			return response.json();
		})
		.then(function(data){
			console.log(data);
			for(var x = 0; x < data.postId.length; x++){
				iComment1 = document.createElement("h6");
				iComment1.innerHTML = data.userUsername[x] + ": " + data.commentText[x];
				$("content" + data.postId[x]).appendChild(iComment1);
			}
		})	
}

function doLike(){
	var likeButtonIdentity = "buttonlike" + likeDefiner;
	let body = {
		postId: likeDefiner,
		typeLike: typeLike
	};
	console.log(body);
	let config = {
		method: 'POST',
		body: JSON.stringify(body),
	};
	fetch(("./Likes"), config)
	.then(function(response){
		return response.json();
	})
	.then(function(data){
		console.log(data);
	})
}

function getLikes(){
	fetch("./Likes?arrid=" + postsIdentity, {method: 'GET', header: {'Content-Type':'application/x-www-form-urlencoded'}})
		.then(function(response){
			return response.json();
		})
		.then(function(data){
			console.log(data);
		})
}


//var uri = encodeURIComponent(JSON.stringify(postsIdentity))
//console.log(uri);
//var body = {
//		postsIdentity: JSON.stringify(postsIdentity),
//};
//console.log("OMAIGA: " + JSON.stringify(body));
//var config = {
//	method: 'GET',
	//body: JSON.stringify(body),
//	array: JSON.stringify(body)
//};
//console.log(config);

// /endpoint?arrid=" + uri
