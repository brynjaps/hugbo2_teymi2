var mongoose=require('mongoose');
var Schema=mongoose.Schema;

var questionSchema = new Schema({
  questionstitle: String
});

module.exports = mongoose.model('Question', questionSchema);