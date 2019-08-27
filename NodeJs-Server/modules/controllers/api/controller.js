const Validator = require('../../utilities/validator')
const Response = require('../../utilities/response')
const Database = require('../../utilities/database')
const Transform = require('../../transforms/v1/transform')
const Config = require('../../utilities/config')
const Auth = require('../../utilities/auth')


module.exports = class controller{
    
    constructor() {
        this.Validator = Validator
        this.Response = Response
        this.Database = Database 
        this.Transform = Transform
        this.Config = Config
        this.Auth = Auth
    }


}