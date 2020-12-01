var express = require("express");
var path = require("path");
var http = require("http");
var morgan = require("morgan");
var app = express();
var db = require('./queries');
var bodyParser = require('body-parser');

app.use(morgan("dev"));
app.use(bodyParser.urlencoded({ extended: false }));

app.use('/', express.static(__dirname + '/public'));

//Retrieve highscore
app.get("/api/highscore", db.getHighscore);

//Create a score
app.post("/api/score", db.createScore);

app.use(function(request, response) {
    response.statusCode = 404;
    response.end("404!");
});
app.set("port", process.env.PORT || 3000);

http.createServer(app).listen(app.get("port"), function() {
    console.log("highscoreserver started on port " + app.get("port"));
});