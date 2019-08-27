const cookieParser = require('cookie-parser')
const session = require('express-session')
const morgan = require('morgan')
const User = require('../../models/user')

module.exports = function (app) {

    app.use(morgan('dev'))
    app.use(cookieParser())
    app.use(session({
        key: 'user',
        secret: '564653456fsd3fd76f3',
        resave: false,
        saveUninitialized: false,
        cookie: {
            expires: 600000
        }
    }));

    //try to clear cookies if there is cookies in browser but not sat as session in server side 
    app.use((req, res, next) => {
        if (req.cookies.user && !req.session.user) {
            res.clearCookie('user')
        }
        next()
    })

    //check if there is no admin make an admin to our project and add it to database as a user
    // app.use((req, res, next) => {

    //     User.findOne({ email: 'adminadmin' }, (error, user) => {

    //         if (error)
    //             return res.send('Internal Error')

    //         if (user)
    //             return next()

    //         let us = User({
    //             fullName: 'admin',
    //             email: 'adminadmin',
    //             password: 'Mmgrdd211!',
    //             googleId: '---',
    //             image: '---'

    //         }).save((error, user) => {
    //             if (error) {
    //                 return res.send('Internal Error')
    //             } else if (user) {
    //                 next()
    //             } else {
    //                 return res.send('Internal Error')
    //             }
    //         })
    //     })
    // })
}