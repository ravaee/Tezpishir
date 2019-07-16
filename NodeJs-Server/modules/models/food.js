const mongoose = require('mongoose')
const timestamps = require('mongoose-timestamp')
const Schema = mongoose.Schema
const Group = require('./group')

const FoodSchema = new Schema({

    name: { type: String, required: true },
    ingredients: { type: String, required: true },
    recipes: { type: String, required: true },
    time: { type: Number, required: true },
    image: { type: String, required: true },
    group: { type: Schema.Types.ObjectId, ref: 'Group' },
    score: [{ type: Schema.Types.ObjectId, required: false }],
    owner: { type: Schema.Types.ObjectId, ref: 'User' },

})

FoodSchema.plugin(timestamps);

// FoodSchema.pre('remove', (next) => {
//     console.log('dare mishe')
//     Group.update({'_id':this.group},  { $pull: { foods: this._id } }, { multi: true }).exec(next);
// })

module.exports = mongoose.model('Food', FoodSchema)
