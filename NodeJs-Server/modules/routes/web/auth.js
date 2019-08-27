const express = require('express')
const router = express.Router()
const AuthController = require('../../controllers/web/authController')
const Authorization = require('../middlewares/authentication')

router.get('/login', Authorization.sessionChecker, AuthController.loginGet.bind(AuthController))

router.post('/login', AuthController.loginPost.bind(AuthController))  
  
router.get('/logout', Authorization.sessionAuthorize, AuthController.logout.bind(AuthController))


module.exports = router