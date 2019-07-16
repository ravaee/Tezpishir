const Food = require('./foodTransform')
const Group = require('./groupTransform')
const Score = require('./scoreTransform')
const User = require('./userTransform')

module.exports = new class Transform{
    constructor() {
        this.Food = Food
        this.Group = Group
        this.Score = Score
        this.User = User
    }
}