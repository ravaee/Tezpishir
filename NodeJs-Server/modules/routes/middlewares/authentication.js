const Response = require('../../utilities/response')
const JWT = require('jsonwebtoken')
const User = require('../../models/user')
const fs = require('fs')
var path = require('path')

module.exports = new class Auth{

    sessionChecker(req, res, next) {
        if (req.session.user && req.cookies.user) {
            res.redirect('/dashboard')
        } else {
            next()
        }
    }

    sessionAuthorize(req, res, next) {
        if (req.session.user && req.cookies.user) {
            req.user = req.session.user
            next()
            return
        } else {
            res.redirect('/login')
        }
    }

    authorize(req,res,next) {
        let token = req.headers['x-access-token']
        if (token) {  
            const public_key = fs.readFileSync(path.resolve('modules/protectedFiles/public.pem'), 'utf8')
            
            var i = 'tezpishir';          
            var s = 'tezpishir@gmail.com';        
            var a = 'tezpishir.com'; 

            let verifyOptions = {
                issuer: i,
                subject: s,
                audience: a,
                expiresIn: "12h",
                algorithm: ['RS256']
            }

            return JWT.verify(token, public_key, verifyOptions, (error, decode) => { 

                if (error)
                    return new Response(res, [], error, 401, false, "JsonWebToken Verifing Problem")
                
                User.findById(decode.id, (error, user) => { 
                    if (error)
                        return new Response(res, [], ["Internal_Error"], 401, false, "Error occured while access to data please try again later.")
                    
                    if (user) {
                        req.user = user
                        next()
                        return

                    } else {
                        return new Response(res, [], ["Forbidden"], 401, false, "Access Denied To This Information")
                    }
                })
            })
        } else {
            return new Response(res,[],["Unauthorized"],401,false,"Access Denied To This Information")
        }
    }
}