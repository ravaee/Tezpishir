const path = require('path')
const User = require('../../models/user')
const bcrypt = require('bcrypt')

class AuthController {

    loginPost(req, res) {
        var username = req.body.username,
            password = req.body.password

        User.findOne({ email: username }).then(function (user) {
            if (!user) {
                return res.redirect('/login')
            }
            bcrypt.compare(req.body.password, user.password, (error, isSame) => {

                if (!isSame) {
                    return res.redirect('/login')
                } else {
                    req.session.user = user
                    req.cookies.user_sid = user
                    return res.redirect('/dashboard')
                }
            })
        })
    }

    loginGet(req, res) {
        res.render(path.resolve('modules/views/pages/auth/login.ejs'))
    }

    logout(req,res) {
        res.clearCookie('user')
        res.redirect('/login')
    }
}

module.exports = new AuthController