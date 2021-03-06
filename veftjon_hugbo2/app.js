/**
 * Created by Sandeep on 01/06/14.
 */

// Load Our Modules
var express = require('express');
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var questions = require('./routes/questions'); //routes are defined here
var app = express(); //Create the Express app



//connect to our database
//Ideally you will obtain DB details from a config file

var dbName = 'questionDB';
var connectionString = 'mongodb://localhost:27017/' + dbName;

mongoose.connect(connectionString);

//configure body-parser
app.use(bodyParser.json());
//app.use(bodyParser.urlencoded());
app.use(bodyParser.urlencoded({extended: false}));
app.use('/api', questions); //This is our route middleware

module.exports = app;
