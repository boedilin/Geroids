var promise = require('bluebird');

var options = {
    // Initialization Options
    promiseLib: promise
};

var pgp = require('pg-promise')(options);
// postgres://username:password@host:port/databasename
var connectionString = 'postgres://eqeskeyyomthxo:mxucs6UbhfrGIp0osw1fyQ2dBW@ec2-54-221-225-242.compute-1.amazonaws.com:5432/db2n32jagimtsb';
var db = pgp(connectionString);

function getHighscore(request, response, next) {
    db.any('select * from highscore order by score desc')
        .then(function(data) {
            response.status(200)
                .jsonp({
                    status: 'success',
                    data: data,
                    message: 'Retrieved HighscoreList'
                });
        })
        .catch(function(err) {
            return next(err);
        });
}

function createScore(request, response, next) {
    response.header("Access-Control-Allow-Origin", "*");
    response.header("Access-Control-Allow-Headers", "X-Requested-With");
    db.none('insert into highscore(nickname, score, date)' +
            'values(${nickname}, ${score}, ${date})',
            request.body)
        .then(function() {
            response.status(200)
                .jsonp({
                    status: 'success',
                    message: 'Inserted one result'
                });
        })
        .catch(function(err) {
            return next(err);
        });
}

module.exports = {
    createScore: createScore,
    getHighscore: getHighscore
};