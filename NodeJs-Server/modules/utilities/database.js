const Food = require('../models/food')
const Group = require('../models/group')
const Score = require('../models/score')
const User = require('../models/user')

module.exports = new class Database{
    
    constructor() {
        this.Food = Food
        this.Group = Group
        this.Score = Score
        this.User = User
    }
}