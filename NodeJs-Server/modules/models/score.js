const mongoose = require('mongoose')
const timestamps = require('mongoose-timestamp')
const Schema = mongoose.Schema

const ScoreSchema = new Schema({

    score: { type: Number, required: false },
    user: { type: Schema.Types.ObjectId, ref: 'User'},
    food: { type: Schema.Types.ObjectId, ref: 'Food' }
    
})

ScoreSchema.plugin(timestamps);

module.exports = mongoose.model('Score',ScoreSchema)