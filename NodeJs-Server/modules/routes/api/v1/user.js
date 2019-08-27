const express = require('express')
const router = express.Router()
const userController = require('../../../controllers/api/v1/userController')
const Authorization = require('../../middlewares/authentication')
const uploadMid = require('../../middlewares/upload')
const multer = require('multer')
const upload = multer({ storage: uploadMid.setUser });



router.get('/getMyProfile', Authorization.authorize, userController.getMyProfile.bind(userController))
router.post('/editMyProfile', Authorization.authorize,upload.single('file'), userController.editMyProfile.bind(userController))


module.exports = router;