const User = require('../models/user')
const JWT = require('jsonwebtoken')
const GoogleTokenStrategy = require('passport-google-id-token');
const fs = require('fs')
const path = require('path')

module.exports = new class Auth{ 

    setPassportSetting(passport) {

        const passwordGenerator = (length) => {
            let charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789",
                retVal = "";
            for (var i = 0, n = charset.length; i < length; ++i) {
                retVal += charset.charAt(Math.floor(Math.random() * n));
            }
            return retVal; 
        }

        passport.serializeUser(function (user, done) {
            done(null, user);
        });

        passport.deserializeUser(function (user, done) {
            done(null, user);
        });

        passport.use(new GoogleTokenStrategy({
            clientID: config.auth_data.googleAuth.clientId,
            passReqToCallback: true
        },
            function (req, parsedToken, googleId, done) {
                
                req.google = {
                    id: googleId,
                    profile: { ...parsedToken, password:passwordGenerator(10) }
                }   
                return done(null,req)
            }
        ));
    }

    bindTokenToUser(user) { 
        if (user.token)
            return user
        else {

            let private_key = fs.readFileSync(path.resolve('modules/protectedFiles/private.key'), 'utf8')
            let i = 'tezpishir';          // Issuer 
            let s = 'tezpishir@gmail.com';        // Subject 
            let a = 'tezpishir.com'; // Audience
            let signOptions = {
                issuer: i,
                subject: s,
                audience: a,
                expiresIn: "12h",
                algorithm: 'RS256'
            };
            let token = JWT.sign({ id: user._id }, private_key, signOptions) 
            
            let _user = {
                ...user._doc,
                token: token
            }
            console.log(console.log(_user.token))
            
            return _user
        }
    }




}
