const Controller = require('../controller')
const bcrypt = require('bcrypt')

class UserController extends Controller {

    editMyProfile(req, res) {
        
    }

    getMyProfile(req, res) {
        
        return new this.Response(res,  req.user , [], 200,true,"User_Found")
    }

    getUserProfile(req, res) {
        
    }

}

module.exports = new UserController

