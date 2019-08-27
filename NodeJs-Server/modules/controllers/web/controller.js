const Validator = require('../../utilities/validator')
const Database = require('../../utilities/database')
const Transform = require('../../transforms/v1/transform')
const Config = require('../../utilities/config')
const Auth = require('../../utilities/auth')
const Bundle = require('../../utilities/bundle')
const File = require('../../utilities/files')

module.exports = class controller{
    
    constructor() {
        this.Validator = Validator
        this.Bundle = Bundle
        this.Database = Database 
        this.Transform = Transform
        this.Config = Config
        this.Auth = Auth
        this.File = File
    }


}