const express = require('express')
const router = express.Router()
const userController = require('../../../controllers/api/v1/userController')
const Authorization = require('../../middlewares/authentication')


router.get('/getMyProfile', Authorization.authorize, userController.getMyProfile.bind(userController))


module.exports = router;