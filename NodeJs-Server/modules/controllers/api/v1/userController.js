const Controller = require('../controller')
const bcrypt = require('bcrypt')

class UserController extends Controller {

    editMyProfile(req, res) {

        if(req.body.id != req.user._id){
            return new this.Response(res, [], req.validationErrors().map(error => (error.msg)), 422, true, 'Request was not valid')
        }

        req.checkBody('fullName', 'fullName is empty').not().isEmpty()
        req.checkBody('experience', 'experience is empty').not().isEmpty()
        req.checkBody('areaOfService', 'areaOfService is empty').not().isEmpty();

        this.Validator.escapeAndTrim(req, ['areaOfService', 'experience', 'fullName'])
        this.Validator.checkValidation(res, req,
            () => { 
                
                this.Database.User.findByIdAndUpdate(req.body.id,{

                    uploadImage: (req.file) ? req.file["filename"] : req.user.uploadImage,
                    fullName: req.body.fullName,
                    experience: req.body.experience,
                    phoneNumber: req.body.phoneNumber,
                    areaOfService: req.body.areaOfService,

                }, (error, user) => {
                    if (error) {

                        return new this.Response(res, [], error, 500, true, "There was an error during submit your data")

                    } else if (user) {
                        
                        return new this.Response(res, user, [], 200, false, "Profile Updated.")

                    } else {

                        return new this.Response(res, [], ["Unknown Request"], 520, true, "There was an error during submit your data")
                    }
                })
                
            },
            () => {  
                return new this.Response(res, [], req.validationErrors().map(error => (error.msg)), 422, true, 'Request was not valid')
            })
    }

    getMyProfile(req, res) {

        return new this.Response(res,  req.user , [], 200,true,"User_Found")
    }

    getUserProfile(req, res) {
        
    }

}

module.exports = new UserController

