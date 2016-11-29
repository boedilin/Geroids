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

function goLeft() {

}

function shoot() {

}

function goRight() {

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

    var drawInterval = setInterval(function() {
        for (key in map) {
            if (map[key]) {
                ws.send(key);
            }
        }
        drawStuff();
    }, 25);

    /**
     * how fast the pressed key can be sent: (85ms)
     *      var previousTime;
     *      console.log(new Date().getTime() - previousTime);
            previousTime = new Date().getTime();
     */

    // resize the canvas to fill browser window dynamically
    window.addEventListener('resize', resizeCanvas, false);
    //window.addEventListener("keydown", eventKeyPressed, true);

    function resizeCanvas() {
        if (window.innerWidth <= 800) {
            canvas.width = window.innerWidth;
            canvas.height = window.innerHeight*0.9;
        } else {
            canvas.width = ((window.innerWidth) / 100) * 62.5;
            canvas.height = window.innerHeight;
        }

        /**
         * Your drawings need to be inside this function otherwise they will be
         * reset when you resize the browser window and the canvas goes will be
         * cleared.
         */
        canvasXFactor = canvas.width / 1000;
        canvasYFactor = canvas.height / 1000;

        drawStuff();
    }
    resizeCanvas();

    /*
        function eventKeyPressed(event) {
            console.log(event.key);
            //ws.send(event.key);
        }*/

    function drawStuff() {
        // do your drawing stuff here
        context.fillStyle = "black";
        context.fillRect(0, 0, canvas.width, canvas.height);

        drawGeroids(gamefield.Geroids);
        drawFigure(gamefield.Figure);
        drawProjectiles(gamefield.Projectiles);
        drawScore(gamefield.Score);
        if (gamefield.Gameover) {
            drawGameover();
            clearInterval(drawInterval);
        }
    }

    function drawFigure(figureJSON) {
        context.drawImage(spaceShip, figureJSON.position.xStart * canvasXFactor, figureJSON.position.yStart * canvasYFactor, figureJSON.position.xLength * canvasXFactor, figureJSON.position.yLength * canvasYFactor);
    }

    function drawGeroids(GeroidJSON) {

        for (i in GeroidJSON) {
            if (GeroidJSON[i].position.xStart % 5 == 0) {
                context.drawImage(geroid1, GeroidJSON[i].position.xStart * canvasXFactor, GeroidJSON[i].position.yStart * canvasYFactor, GeroidJSON[i].position.xLength * canvasXFactor, GeroidJSON[i].position.yLength * canvasYFactor);
            }
            if (GeroidJSON[i].position.xStart % 5 == 1) {
                context.drawImage(geroid2, GeroidJSON[i].position.xStart * canvasXFactor, GeroidJSON[i].position.yStart * canvasYFactor, GeroidJSON[i].position.xLength * canvasXFactor, GeroidJSON[i].position.yLength * canvasYFactor);
            }
            if (GeroidJSON[i].position.xStart % 5 == 2) {
                context.drawImage(geroid3, GeroidJSON[i].position.xStart * canvasXFactor, GeroidJSON[i].position.yStart * canvasYFactor, GeroidJSON[i].position.xLength * canvasXFactor, GeroidJSON[i].position.yLength * canvasYFactor);
            }
            if (GeroidJSON[i].position.xStart % 5 == 3) {
                context.drawImage(geroid4, GeroidJSON[i].position.xStart * canvasXFactor, GeroidJSON[i].position.yStart * canvasYFactor, GeroidJSON[i].position.xLength * canvasXFactor, GeroidJSON[i].position.yLength * canvasYFactor);
            }
            if (GeroidJSON[i].position.xStart % 5 == 4) {
                context.drawImage(geroid5, GeroidJSON[i].position.xStart * canvasXFactor, GeroidJSON[i].position.yStart * canvasYFactor, GeroidJSON[i].position.xLength * canvasXFactor, GeroidJSON[i].position.yLength * canvasYFactor);
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
}



ws.onopen = function() {
    console.log("Websocket opened!");
    if (localStorage.getItem('name') != null) {
        ws.send(localStorage.getItem('name'));
    }
};

/**
 * Upon Receiving a message from the server, this method tries to extract name attribute from string and send the message to the corresponding drawing method.
 */

ws.onmessage = function(evt) {
    gamefield = JSON.parse(evt.data);
    if (firstTime) {
        firstTime = false;
        canvasApp();
    }
};

ws.onclose = function() {
    console.log("Closed!");
};

ws.onerror = function(err) {
    console.log("Error: " + err);
};