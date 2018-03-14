var mongoose=require('mongoose');
var Schema=mongoose.Schema;

var questionSchema = new Schema({
  title: String,
  releaseYear: String,
  director: String,
  genre: String
});

module.exports = mongoose.model('Question', questionSchema);