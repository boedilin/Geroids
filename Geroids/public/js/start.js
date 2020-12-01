var audioStory = new Audio('../music/login.mp3');

function myFunction() {
	localStorage.setItem("name",document.getElementById("name").value);
}

function startMusic() {
	audioStory.play();
}