// kann generisch gemacht werden, da der Websocketserver der selbe ist, wie der
// Server, der das HTML liefert:"ws://" + location.host
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

function goLeft() {

}

function shoot() {

}

function goRight() {

}

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
    sendScore = true;
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

    var map = {}; // You could also use an array
    onkeydown = onkeyup = function(e) {
        //e = e || event; // to deal with IE
        if (e.keyCode == keySpace || e.keyCode == keyA || e.keyCode == keyD) {
            map[e.keyCode] = e.type == 'keydown';
        }
    }

    /**
     * how fast the pressed key can be sent: (85ms)
     *      var previousTime;
     *      console.log(new Date().getTime() - previousTime);
            previousTime = new Date().getTime();
     */

    // resize the canvas to fill browser window dynamically
    window.addEventListener('resize', resizeCanvas, false);

    function resizeCanvas() {
        if (window.innerWidth <= 800) {
            canvas.width = window.innerWidth;
            canvas.height = window.innerHeight * 0.9;
        } else {
            canvas.width = ((window.innerWidth) / 100) * 62.5;
            canvas.height = window.innerHeight;
        }

        canvasXFactor = canvas.width / 1000;
        canvasYFactor = canvas.height / 1000;

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
        // do your drawing stuff here
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
//        context.fillStyle="#FF0000";
//    	context.fillRect((27+figureJSON.position.xStart) * canvasXFactor, (3+figureJSON.position.yStart) * canvasYFactor,2,2);
//    	context.fillStyle="#FF0000";
//    	context.fillRect((33+figureJSON.position.xStart) * canvasXFactor, (3+figureJSON.position.yStart) * canvasYFactor,2,2);
//    	context.fillStyle="#FF0000";
//    	context.fillRect((15+figureJSON.position.xStart) * canvasXFactor, (16+figureJSON.position.yStart) * canvasYFactor,2,2);
//    	context.fillStyle="#FF0000";
//    	context.fillRect((46+figureJSON.position.xStart) * canvasXFactor, (16+figureJSON.position.yStart) * canvasYFactor,2,2);
//    	context.fillStyle="#FF0000";
//     	context.fillRect((2+figureJSON.position.xStart) * canvasXFactor, (54+figureJSON.position.yStart) * canvasYFactor,2,2);
//     	context.fillStyle="#FF0000";
//     	context.fillRect((58+figureJSON.position.xStart) * canvasXFactor, (54+figureJSON.position.yStart) * canvasYFactor,2,2);
//     	context.fillStyle="#FF0000";
//     	context.fillRect((2+figureJSON.position.xStart) * canvasXFactor, (72+figureJSON.position.yStart) * canvasYFactor,2,2);
//     	context.fillStyle="#FF0000";
//     	context.fillRect((58+figureJSON.position.xStart) * canvasXFactor, (72+figureJSON.position.yStart) * canvasYFactor,2,2);
     	
    }

    function drawGeroids(GeroidJSON) {

        for (i in GeroidJSON) {
            if (GeroidJSON[i].id == 1) {
                context.drawImage(geroid1, GeroidJSON[i].position.xStart * canvasXFactor, GeroidJSON[i].position.yStart * canvasYFactor, 100 * canvasXFactor, 100 * canvasYFactor);
//                context.fillStyle="#FF0000";
//            	context.fillRect((23+GeroidJSON[i].position.xStart) * canvasXFactor, (27+GeroidJSON[i].position.yStart) * canvasYFactor,5,5);
//            	context.fillStyle="#FF0000";
//            	context.fillRect((GeroidJSON[i].position.xStart+79) * canvasXFactor, (43+GeroidJSON[i].position.yStart) * canvasYFactor,5,5);
//            	context.fillStyle="#FF0000";
//            	context.fillRect((63+GeroidJSON[i].position.xStart) * canvasXFactor, (100+GeroidJSON[i].position.yStart) * canvasYFactor,5,5);
//            	context.fillStyle="#FF0000";
//            	context.fillRect((11+GeroidJSON[i].position.xStart) * canvasXFactor, (81+GeroidJSON[i].position.yStart) * canvasYFactor,5,5); 
            }
            if (GeroidJSON[i].id == 2) {
                context.drawImage(geroid2, GeroidJSON[i].position.xStart * canvasXFactor, GeroidJSON[i].position.yStart * canvasYFactor, 100 * canvasXFactor, 100 * canvasYFactor);
//                context.fillStyle="#FF0000";
//            	context.fillRect((69+GeroidJSON[i].position.xStart) * canvasXFactor, (28+GeroidJSON[i].position.yStart) * canvasYFactor,5,5);
//            	context.fillStyle="#FF0000";
//            	context.fillRect((17+GeroidJSON[i].position.xStart) * canvasXFactor, (47+GeroidJSON[i].position.yStart) * canvasYFactor,5,5);
//            	context.fillStyle="#FF0000";
//            	context.fillRect((36+GeroidJSON[i].position.xStart) * canvasXFactor, (100+GeroidJSON[i].position.yStart) * canvasYFactor,5,5);
//            	context.fillStyle="#FF0000";
//            	context.fillRect((88+GeroidJSON[i].position.xStart) * canvasXFactor, (81+GeroidJSON[i].position.yStart) * canvasYFactor,5,5); 
            }
            if (GeroidJSON[i].id == 3) {
                context.drawImage(geroid3, GeroidJSON[i].position.xStart * canvasXFactor, GeroidJSON[i].position.yStart * canvasYFactor, 100 * canvasXFactor, 100 * canvasYFactor);
//                context.fillStyle=21+GeroidJSON[i].position.xStart) * canvasXFactor, (32+GeroidJSON[i].position.yStart) * canvasYFactor,5,5);
//            	context.fillStyle="#FF0000";
//            	context.fillRect((81+GeroidJSON[i].position.xStart) * canvasXFactor, (70+GeroidJSON[i].position.yStart) * canvasYFactor,5,5);
//            	context.fillStyle="#FF0000";
//            	context.fillRect(("#FF0000";
//            	context.fillRect((29+GeroidJSON[i].position.xStart) * canvasXFactor, (100+GeroidJSON[i].position.yStart) * canvasYFactor,5,5); 
                }
            if (GeroidJSON[i].id == 4) {
                context.drawImage(geroid4, GeroidJSON[i].position.xStart * canvasXFactor, GeroidJSON[i].position.yStart * canvasYFactor, 100 * canvasXFactor, 100 * canvasYFactor);
//                context.fillStyle="#FF0000";
//            	context.fillRect((59+GeroidJSON[i].position.xStart) * canvasXFactor, (25+GeroidJSON[i].position.yStart) * canvasYFactor,5,5);
//            	context.fillStyle="#FF0000";
//            	context.fillRect((28+GeroidJSON[i].position.xStart) * canvasXFactor, (68+GeroidJSON[i].position.yStart) * canvasYFactor,5,5);
//            	context.fillStyle="#FF0000";
//            	context.fillRect((71+GeroidJSON[i].position.xStart) * canvasXFactor, (100+GeroidJSON[i].position.yStart) * canvasYFactor,5,5); 
            }
            if (GeroidJSON[i].id == 5) {
                context.drawImage(geroid5, GeroidJSON[i].position.xStart * canvasXFactor, GeroidJSON[i].position.yStart * canvasYFactor, 100 * canvasXFactor, 100 * canvasYFactor);
//                context.fillStyle="#FF0000";
//            	context.fillRect((31+GeroidJSON[i].position.xStart) * canvasXFactor, (35+GeroidJSON[i].position.yStart) * canvasYFactor,5,5);
//            	context.fillStyle="#FF0000";
//            	context.fillRect((4+GeroidJSON[i].position.xStart) * canvasXFactor, (95+GeroidJSON[i].position.yStart) * canvasYFactor,5,5);
//            	context.fillStyle="#FF0000";
//            	context.fillRect((94+GeroidJSON[i].position.xStart) * canvasXFactor, (56+GeroidJSON[i].position.yStart) * canvasYFactor,5,5); 
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
    audioGameOver.pause();
    audioGame.play();
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