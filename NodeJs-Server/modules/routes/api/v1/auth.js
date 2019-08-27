const express = require('express')
const router = express.Router()
const authController = require('../../../controllers/api/v1/authController')
const auth = require('../../../utilities/auth')
const passport = require('passport')

auth.setPassportSetting(passport)

//router.post('/SignInWithGoogle', passport.authenticate('google-token'), authController.register.bind(authController))
router.post('/SignInWithGoogle', passport.authenticate('google-id-token'), authController.register.bind(authController))
router.post('/login', authController.login.bind(authController))



module.exports = router