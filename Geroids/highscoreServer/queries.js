var promise = require('bluebird');

var options = {
    // Initialization Options
    promiseLib: promise
};

var pgp = require('pg-promise')(options);
// postgres://username:password@host:port/databasename
var connectionString = 'postgres://pyfpusooyxpymo:bV-dN-Z_hGvZ63rZDB-NkFqHb0@ec2-54-163-238-215.compute-1.amazonaws.com:5432/ddpq5suo07un0';
var db = pgp(connectionString);

function getHighscore(request, response, next) {
    db.any('select * from highscore order by score desc')
        .then(function(data) {
            response.status(200)
                .json({
                    status: 'success',
                    data: data,
                    message: 'Retrieved collection'
                });
        })
        .catch(function(err) {
            return next(err);
        });
}

function createScore(request, response, next) {
    db.none('insert into highscore(nickanme, score, date)' +
            'values(${nickname}, ${score}, ${date})',
            request.body)
        .then(function() {
            response.status(200)
                .json({
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