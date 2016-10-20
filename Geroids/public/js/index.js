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

function canvasApp() {
    if (!canvasSupport) {
        return;
    }
    var theCanvas = document.getElementById("canvas");
    var context = theCanvas.getContext("2d");

    // resize the canvas to fill browser window dynamically
    window.addEventListener('resize', resizeCanvas, false);

    function resizeCanvas() {
        canvas.width = ((window.innerWidth) / 100) * 62.5;
        console.log(window.innerWidth);
        canvas.height = window.innerHeight;
        console.log(window.innerHeight);

        /**
         * Your drawings need to be inside this function otherwise they will be reset when 
         * you resize the browser window and the canvas goes will be cleared.
         */
        drawStuff();
    }
    resizeCanvas();

    function drawStuff() {
        // do your drawing stuff here
        cW2 = canvas.width / 2;
        cH2 = canvas.height / 2;
        context.fillStyle = "black";
        context.fillRect(0, 0, cW2, cH2);
        context.fillStyle = "white";
        context.fillRect(cW2, 0, cW2, cH2);
        context.fillStyle = "green";
        context.fillRect(0, cH2, cW2, cH2);
        context.fillStyle = "red";
        context.fillRect(cW2, cH2, cW2, cH2);
        context.fillStyle = "white";
        context.font = "30px arial";
        context.fillText("Hello Wooorld!", 10, 80);

        var helloWorldImage = new Image();
        helloWorldImage.onload = function() {

            context.drawImage(helloWorldImage, 100, 0, helloWorldImage.width / 3, helloWorldImage.height / 3);
        }
        helloWorldImage.src = "images/logo_black.png";
    }
}


var ws = new WebSocket("ws://127.0.0.1:8080/");

ws.onopen = function() {
    alert("Opened!");
    ws.send("Hello Server");
};

ws.onmessage = function(evt) {
    alert("Message: " + evt.data);
};

ws.onclose = function() {
    alert("Closed!");
};

ws.onerror = function(err) {
    alert("Error: " + err);
};