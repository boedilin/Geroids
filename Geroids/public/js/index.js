var ws = new WebSocket("ws://" + location.host);
//load: This is the named event for which we are adding a listener. Events for existing objects like window are already defined.
//eventWindowLoaded: Call this function when the event occurs. In our code, we will then call the canvasApp() function, which will start our main application execution.
//false: This sets the function to capture this type of event before it propagates lower in the DOM tree of objects. We will always set this to false.
window.addEventListener("load", eventWindowLoaded, false);

function eventWindowLoaded() {
    canvasApp();
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
    var counter = 0;
    ws.onmessage = function(evt) {
       // console.log(counter++);
        drawStuff(JSON.parse(evt.data));
    };
    var theCanvas = document.getElementById("canvas");
    var context = theCanvas.getContext("2d");
    var canvasXFactor = theCanvas.width/1000;
    var canvasYFactor = theCanvas.height/1000;

    var map = {}; // You could also use an array
    onkeydown = onkeyup = function(e) {
        //e = e || event; // to deal with IE
        if (e.keyCode == keySpace || e.keyCode == keyA || e.keyCode == keyD) {
            map[e.keyCode] = e.type == 'keydown';
            //console.log(e.type);
            //console.log(map);
        }
    }

    setInterval(function() {
        for (key in map) {
            if (map[key]) {
                ws.send(key);
                //console.log(key);
            }
        }
    }, 50);

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
        canvas.width = ((window.innerWidth) / 100) * 62.5;
       // console.log(window.innerWidth);
        canvas.height = window.innerHeight;
        //console.log(window.innerHeight);

        /**
         * Your drawings need to be inside this function otherwise they will be
         * reset when you resize the browser window and the canvas goes will be
         * cleared.
         */
        canvasXFactor = canvas.width/1000;
        canvasYFactor = canvas.height/1000;
        
        drawStuff();
    }
    resizeCanvas();

    /*
        function eventKeyPressed(event) {
            console.log(event.key);
            //ws.send(event.key);
        }*/

    function drawStuff(gamefield) {
        // do your drawing stuff here
        cW2 = canvas.width / 2;
        cH2 = canvas.height / 2;
        context.fillStyle = "black";
        context.fillRect(0, 0, cW2, cH2);
        context.fillStyle = "black";
        context.fillRect(cW2, 0, cW2, cH2);
        context.fillStyle = "black";
        context.fillRect(0, cH2, cW2, cH2);
        context.fillStyle = "black";
        context.fillRect(cW2, cH2, cW2, cH2);
        context.fillStyle = "black";

        drawGeroids(gamefield.Geroids);
        drawFigure(gamefield.Figure);
        drawProjectiles(gamefield.Projectiles);
    }
    
    function drawFigure(figureJSON) {
        context.fillStyle = "green";
        context.fillRect(figureJSON.position.xStart*canvasXFactor, figureJSON.position.yStart*canvasYFactor, figureJSON.position.xLength*canvasXFactor, figureJSON.position.yLength*canvasYFactor);
    }

    function drawGeroids(GeroidJSON) {

        for (i in GeroidJSON) {
            context.fillStyle = "red";
            context.fillRect(GeroidJSON[i].position.xStart*canvasXFactor, GeroidJSON[i].position.yStart*canvasYFactor, GeroidJSON[i].position.xLength*canvasXFactor, GeroidJSON[i].position.yLength*canvasYFactor);
        }
    }

    function drawProjectiles(ProjectileJSON) {
    	
        for (i in ProjectileJSON) {
            context.fillStyle = "yellow";
            context.fillRect(ProjectileJSON[i].position.xStart*canvasXFactor, ProjectileJSON[i].position.yStart*canvasYFactor, ProjectileJSON[i].position.xLength*canvasXFactor, ProjectileJSON[i].position.yLength*canvasYFactor);
        }
    }
}

// kann generisch gemacht werden, da der Websocketserver der selbe ist, wie der
// Server, der das HTML liefert:"ws://" + location.host

ws.onopen = function() {
    console.log("Websocket opened!");
    ws.send("Hello Server");
};

/**
 * Upon Receiving a message from the server, this method tries to extract name attribute from string and send the message to the corresponding drawing method.
 */


ws.onclose = function() {
    console.log("Closed!");
};

ws.onerror = function(err) {
    console.log("Error: " + err);
};