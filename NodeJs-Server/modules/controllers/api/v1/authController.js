const Controller = require('../controller')
const bcrypt = require('bcrypt')

class AuthController extends Controller{

    login(req, res) {
        
        req.checkBody('password', 'password is empty').not().isEmpty()
        req.checkBody('email', 'email is empty').not().isEmpty()

        this.Validator.checkValidation(res, req,
            () => { 
                this.Database.User.findOne({ email: req.body.email },this.Transform.User.login, (error, user) => {
                    if (error)
                        return new this.Response(res, [], [error], 500, false, 'Internal Server Error.')

                    else if (user == null) { 
                        return new this.Response(res, [], ['Email'], 400, false, 'Login information is invalid')

                    } else if (user != null) {
                        
                        bcrypt.compare(req.body.password, user.password, (error, isSame) => { 
                            if (!isSame) {
                                return new this.Response(res, [], ['Password'], 422, false, 'Login information is invalid')
                            } else {
                                user = this.Auth.bindTokenToUser(user)
                                console.log(user)
                                let _user = {
                                    name: user.fullName,
                                    email: user.email,
                                    token: user.token,
                                    google: user.googleId,
                                    image: user.image
                                }
                                return new this.Response(res, [_user], [], 200, true, 'Welcome to Tezpishir')
                            }
                        })
                    } else {
                        return new this.Response(res, [], ['error'], 520, false, 'Unknown_Error')
                    }
                })
            },
            () => { 
                return new this.Response(res, [], req.validationErrors().map(error => (error.msg)), 422, false, 'Some fileds are not valid.')
            })
    }


    register(req, res) {

        const fullname = req.google.profile.payload.name
        const email = req.google.profile.payload.email
        const image = req.google.profile.payload.picture
        const googleId = req.google.id
        const password = req.google.profile.password

        this.Database.User.findOne({ email: email }, (error, user) => { 

            if (error)
                return new this.Response(res, [], ['error'], 500, false, 'Database')

            if (user) {
                user = this.Auth.bindTokenToUser(user)
                
                let _user = {
                    id: user._id,
                    name: user.fullName,
                    email: user.email,
                    image:user.image,
                    token: user.token,
                }

                return new this.Response(res, _user, [], 200, true, 'User find successfuly')
            }
            
            this.Database.User({

                fullName: fullname,
                email: email,
                password: password,
                googleId: googleId,
                image: image

            }).save((error, user) => {
                if (error) {
                    if (error.code == 11000)
                        return new this.Response(res, [], ['Email is duplicated'], 404, false, 'User with this Email-Address has registred before.')

                    return new this.Response(res, [], [error], 500, false, 'Error occured when creating new user')

                } else if (user) {
                    user = this.Auth.bindTokenToUser(user)
                    let _user = {
                        id: user._id,
                        name: user.fullName,
                        email: user.email,
                        image: user.image,
                        token: user.token,
                    }
                    return new this.Response(res, _user, [], 200, true, 'User has created successfuly')
                } else {
                    return new this.Response(res, [], ['error'], 520, false, 'Unknown Error')
                }
            })
        })
    }
    
    logout(req, res) {

    }
}

module.exports = new AuthController