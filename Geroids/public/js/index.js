//load: This is the named event for which we are adding a listener. Events for existing objects like window are already defined.
//eventWindowLoaded: Call this function when the event occurs. In our code, we will then call the canvasApp() function, which will start our main application execution.
//false: This sets the function to capture this type of event before it propagates lower in the DOM tree of objects. We will always set this to false.
window.addEventListener("load", eventWindowLoaded, false);

var figurePreviousXPos = -100
var figurePreviousYPos = -100
// syntax Geroids: [x,y,size]
var previousGeroidsArray = [[-100,-100, 100],[-100,-100, 100]];
var previousProjectilesArray = [[-100,-100,20],[-100,-100,20]];

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
    window.addEventListener("keydown", eventKeyPressed, true);

    function resizeCanvas() {
        canvas.width = ((window.innerWidth) / 100) * 62.5;
        console.log(window.innerWidth);
        canvas.height = window.innerHeight;
        console.log(window.innerHeight);

        /**
		 * Your drawings need to be inside this function otherwise they will be
		 * reset when you resize the browser window and the canvas goes will be
		 * cleared.
		 */
        drawStuff();
    }
    resizeCanvas();

    function eventKeyPressed(event) {
        ws.send(event.key);
    }

    function drawStuff() {
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

        context.font = "30px arial";
        // context.fillText("Hello Wooorld!", 10, 80);

        var helloWorldImage = new Image();
        helloWorldImage.onload = function() {

            context.drawImage(helloWorldImage, 100, 0, helloWorldImage.width / 3, helloWorldImage.height / 3);
        }
        helloWorldImage.src = "images/logo_black.png";
    }
}

// kann generisch gemacht werden, da der Websocketserver der selbe ist, wie der
// Server, der das HTML liefert:"ws://" + location.host
var ws = new WebSocket("ws://" + location.host);


ws.onopen = function() {
    console.log("Websocket opened!");
    ws.send("Hello Serverr");
};


/**
 * Upon Receiving a message from the server, this method tries to extract name attribute from string and send the message to the corresponding drawing method.
 */
ws.onmessage = function(evt) {
	//console.log("unparsed data: " + evt.data);
    var receivedJSON = JSON.parse(evt.data);
    
    if(receivedJSON.name == "figure"){
    	drawFigure(receivedJSON);
    } else if(receivedJSON.name == 'geroidList'){
    	drawGeroids(receivedJSON);
    	
    } else if(receivedJSON.name == 'projectileList'){
    	drawProjectiles(receivedJSON);
    	
    } else {
    	console.log("received name: " + receivedJSON.name);
    }

    
};

function drawFigure(figureJSON){
	//console.log(figureJSON);
	//console.log("start drawing figure");
	var myCanvas = document.getElementById("canvas");
	var myContext = myCanvas.getContext("2d");
	myContext.fillStyle = "black";

	myContext.fillRect(figurePreviousXPos,figurePreviousYPos, figureJSON.position.xLength,figureJSON.position.yLength);


	figurePreviousXPos = figureJSON.position.xStart;
	figurePreviousYPos = figureJSON.position.yStart;
	

  // console.log(figureJSON);
    
    myContext.fillStyle = "green";
    myContext.fillRect(figureJSON.position.xStart,figureJSON.position.yStart,figureJSON.position.xLength,figureJSON.position.xLength); 
  
	//console.log("finished drawing figure");

};

function drawGeroids(GeroidJSON){
	//console.log(GeroidJSON);
	//console.log("start drawing geroids");

	var myCanvas = document.getElementById("canvas");
	var myContext = myCanvas.getContext("2d");
	
	
	// Overwrite Position of the previous array, specified in previousGeroidsArray
	for(k in previousGeroidsArray){
		for(l in previousGeroidsArray[k]){
			myContext.fillStyle = "black";
			myContext.fillRect(previousGeroidsArray[k][0],previousGeroidsArray[k][1], previousGeroidsArray[k][2],previousGeroidsArray[k][2]);
		}
	}
	
	
	
	// draw new Position of geroids
	
	for(i in GeroidJSON.geroids){
		   myContext.fillStyle = "red";
		  // console.log(GeroidJSON.geroids[i].xStart);
		   
		   myContext.fillRect(GeroidJSON.geroids[i].position.xStart,GeroidJSON.geroids[i].position.yStart,GeroidJSON.geroids[i].position.xLength,GeroidJSON.geroids[i].position.yLength); 
		   //console.log(i);
	}
	
	
	// Writes the positions of current geroids in previousGeroidsArray.

	
	
	for(m in GeroidJSON.geroids){
		//console.log("m" + m);
		//console.log(GeroidJSON.geroids[m].position.xStart);
		previousGeroidsArray[m] = [];
		previousGeroidsArray[m][0] = GeroidJSON.geroids[m].position.xStart;
		previousGeroidsArray[m][1] = GeroidJSON.geroids[m].position.yStart;
		previousGeroidsArray[m][2] = GeroidJSON.geroids[m].position.yLength;
	}
	//console.log("finished drawing figure");

}


function drawProjectiles(ProjectileJSON){
	console.log(ProjectileJSON);
	//console.log("start drawing projectiles");

	var myCanvas = document.getElementById("canvas");
	var myContext = myCanvas.getContext("2d");
	
	
	// Overwrite Position of the previous array, specified in previousGeroidsArray
	for(k in previousProjectilesArray){
		for(l in previousProjectilesArray[k]){
			myContext.fillStyle = "black";
			myContext.fillRect(previousProjectilesArray[k][0],previousProjectilesArray[k][1], previousProjectilesArray[k][2],previousProjectilesArray[k][2]);
		}
	}
	
	
	
	// draw new Position of projectiles
	
	for(i in ProjectileJSON.projectiles){
		   myContext.fillStyle = "red";
		  // console.log(GeroidJSON.geroids[i].xStart);
		   
		   myContext.fillRect(ProjectileJSON.projectiles[i].position.xStart,ProjectileJSON.projectiles[i].position.yStart,ProjectileJSON.projectiles[i].position.xLength,ProjectileJSON.projectiles[i].position.yLength); 
		   //console.log(i);
	}
	
	
	// Writes the positions of current projectiles in previousGeroidsArray.

	
	
	for(m in ProjectileJSON.projectiles){
		//console.log("m" + m);
		//console.log(ProjectileJSON.projectiles[m].position.xStart);
		previousProjectilesArray[m] = [];
		previousProjectilesArray[m][0] = ProjectileJSON.projectiles[m].position.xStart;
		previousProjectilesArray[m][1] = ProjectileJSON.projectiles[m].position.yStart;
		previousProjectilesArray[m][2] = ProjectileJSON.projectiles[m].position.yLength;
	}
	//console.log("finished drawing figure");

}


ws.onclose = function() {
    console.log("Closed!");
};

ws.onerror = function(err) {
    console.log("Error: " + err);
};