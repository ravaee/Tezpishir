const mongoose = require('mongoose')
const timestamps = require('mongoose-timestamp')
const Schema = mongoose.Schema

const GroupSchema = new Schema({

    name: { type: String, required: true },
    image: { type: String, required: true },
    foods: [{ type: Schema.Types.ObjectId, ref: 'Food' }],

}) 

GroupSchema.pre('remove', (next) => { 
    this.model('foods').remove({ group: this._id }, (error) => { 
        next(error) 
    })
})

GroupSchema.plugin(timestamps);

module.exports = mongoose.model('Group', GroupSchema)
