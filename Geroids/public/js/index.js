var ws = new WebSocket("ws://" + location.host);
var gamefield;
var counter = 0;
var spaceShip = new Image();
var geroid1 = new Image();
var geroid2 = new Image();
var geroid3 = new Image();
var geroid4 = new Image();
var geroid5 = new Image();
var gameover = new Image();
var audioGame = new Audio('../music/gameMusic.mp3');
var audioGameOver = new Audio('../music/gameOverMusic.mp3');
var audioGameOverTheme = new Audio('../music/gameOverTheme.wav');
spaceShip.src = "../images/ship.png";
geroid1.src = "../images/element1.png";
geroid2.src = "../images/element2.png";
geroid3.src = "../images/element3.png";
geroid4.src = "../images/element4.png";
geroid5.src = "../images/element5.png";
gameover.src = "../images/gameover.png";
var leftButton = document.getElementById("leftButton");
var shootButton = document.getElementById("shootButton");
var rightButton = document.getElementById("rightButton");
var firstTime = true;
var requestId;
var sendedScore = false;

function sendScore(score) {
    console.log("try to post score");
    $.ajax({
        url: "https://radiant-beyond-79689.herokuapp.com/api/score",
        dataType: "json",
        crossDomain: true,
        type: "POST",
        data: {
            "nickname": localStorage.getItem("name"),
            "score": score,
            "date": new Date().toLocaleDateString()
        }
    });
    sendedScore = true;
}

function canvasSupport() {
    return Modernizr.canvas;
}

const keyA = 65;
const keyD = 68;
const keySpace = 32;

function canvasApp() {
    if (!canvasSupport) {
        return;
    }

    var theCanvas = document.getElementById("canvas");
    var context = theCanvas.getContext("2d");
    var canvasXFactor = theCanvas.width / 1000;
    var canvasYFactor = theCanvas.height / 1000;

    var map = {};
    onkeydown = onkeyup = function(e) {
        if (e.keyCode == keySpace || e.keyCode == keyA || e.keyCode == keyD) {
            map[e.keyCode] = e.type == 'keydown';
        }
    }

    window.addEventListener('resize', resizeCanvas, false);

    function resizeCanvas() {
        if (window.innerWidth <= 800) {
            canvas.width = window.innerWidth;
            canvas.height = window.innerHeight;
        } else {
            canvas.width = ((window.innerWidth) / 100) * 62.5;
            canvas.height = window.innerHeight;
        }

        canvasXFactor = canvas.width / 1000;
        canvasYFactor = canvas.height / 1000;

        cancelAnimationFrame(requestId);
        drawStuff();

    }
    resizeCanvas();

    function drawStuff() {
        requestId = requestAnimationFrame(drawStuff);
        for (key in map) {
            if (map[key]) {
                ws.send(key);
            }
        }
        context.fillStyle = "black";
        context.fillRect(0, 0, canvas.width, canvas.height);

        drawGeroids(gamefield.Geroids);
        drawFigure(gamefield.Figure);
        drawProjectiles(gamefield.Projectiles);
        drawScore(gamefield.Score);
        if (gamefield.Gameover) {
            if (!sendedScore && localStorage.getItem("name")) {
                sendScore(gamefield.Score);
            }
            drawGameover();
            cancelAnimationFrame(requestId);
            showGameoverButtons();
            audioGame.pause();
            audioGameOverTheme.play();
            setTimeout(function (){

                audioGameOver.play();

            	}, 1500);
        }
    }

    function drawFigure(figureJSON) {
        context.drawImage(spaceShip, figureJSON.position.xStart * canvasXFactor, figureJSON.position.yStart * canvasYFactor, figureJSON.position.xLength * canvasXFactor, figureJSON.position.yLength * canvasYFactor);  	
    }

    function drawGeroids(GeroidJSON) {

        for (i in GeroidJSON) {
            if (GeroidJSON[i].id == 1) {
                context.drawImage(geroid1, GeroidJSON[i].position.xStart * canvasXFactor, GeroidJSON[i].position.yStart * canvasYFactor, 100 * canvasXFactor, 100 * canvasYFactor);
           }
            if (GeroidJSON[i].id == 2) {
                context.drawImage(geroid2, GeroidJSON[i].position.xStart * canvasXFactor, GeroidJSON[i].position.yStart * canvasYFactor, 100 * canvasXFactor, 100 * canvasYFactor);
            }
            if (GeroidJSON[i].id == 3) {
                context.drawImage(geroid3, GeroidJSON[i].position.xStart * canvasXFactor, GeroidJSON[i].position.yStart * canvasYFactor, 100 * canvasXFactor, 100 * canvasYFactor);
              }
            if (GeroidJSON[i].id == 4) {
                context.drawImage(geroid4, GeroidJSON[i].position.xStart * canvasXFactor, GeroidJSON[i].position.yStart * canvasYFactor, 100 * canvasXFactor, 100 * canvasYFactor);
            }
            if (GeroidJSON[i].id == 5) {
                context.drawImage(geroid5, GeroidJSON[i].position.xStart * canvasXFactor, GeroidJSON[i].position.yStart * canvasYFactor, 100 * canvasXFactor, 100 * canvasYFactor);
          }
        }
    }

    function drawProjectiles(ProjectileJSON) {

        for (i in ProjectileJSON) {
            context.fillStyle = "white";
            context.fillRect(ProjectileJSON[i].position.xStart * canvasXFactor, ProjectileJSON[i].position.yStart * canvasYFactor, ProjectileJSON[i].position.xLength * canvasXFactor, ProjectileJSON[i].position.yLength * canvasYFactor);
        }
    }

    function drawScore(Score) {
        context.font = "15px MineCrafter_3_Regular";
        context.fillStyle = "white";
        context.fillText("Score " + Score.toString(), 10, 25);
    }

    function drawGameover() {
        context.drawImage(gameover, 0, canvas.height / 3, canvas.width, canvas.height / 4);
    }

    function showGameoverButtons() {
        document.getElementById("gameoverbuttons").style.display = "flex";
    }
}

ws.onopen = function() {
    console.log("Websocket opened!");
    if (localStorage.getItem('name') != null) {
        ws.send(localStorage.getItem('name'));
    }
    setTimeout(function() {
        canvasApp();
    }, 100);
};

ws.onmessage = function(evt) {
    gamefield = JSON.parse(evt.data);
};

ws.onclose = function() {
    console.log("Closed!");
};

ws.onerror = function(err) {
    console.log("Error: " + err);
};

function startMusic() {
    audioGameOver.pause();
    audioGame.play();
}