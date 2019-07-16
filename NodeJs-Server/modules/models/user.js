const mongoose = require('mongoose')
const timestamps = require('mongoose-timestamp')
const bcrypt = require('bcrypt')
const Schema = mongoose.Schema

const UserSchema = new Schema({

    fullName: { type: String, required: true },
    phoneNumber: { type: String, required: false, default: null },
    email: { type: String, required: true, unique:true },
    password: { type: String, required: true },
    postAllowed: { type: Boolean, default: false },
    experience: { type: String, required: false, default: null },
    areaOfService: { type: String, required: false, default: null },
    image: { type: String, required: false, default: null },
    foods: [{ type: Schema.Types.ObjectId, ref: 'Food' }],
    favorites: [{ type: Schema.Types.ObjectId, ref: 'Food' }],
    googleId: { type: String, required: true}
})

UserSchema.plugin(timestamps);

UserSchema.pre('save', function (next) { 
    bcrypt.hash(this.password, 10, (error, hash) => {
        this.password = hash
        next() 
    })
})

module.exports = mongoose.model('User' , UserSchema)
