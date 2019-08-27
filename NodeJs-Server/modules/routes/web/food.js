const express = require('express')
const router = express.Router()
const foodController = require('../../controllers/web/foodController')
const Authorization = require('../middlewares/authentication')
const uploadMid = require('../middlewares/upload')
const multer = require('multer')
const upload = multer({ storage: uploadMid.setFood });

router.get('/list', Authorization.sessionAuthorize, foodController.list.bind(foodController))

router.get('/create', Authorization.sessionAuthorize, foodController._create.bind(foodController))

router.post('/create', Authorization.sessionAuthorize, upload.single('photo'), foodController.create.bind(foodController))

router.get('/remove/:id', Authorization.sessionAuthorize, foodController.remove.bind(foodController))

router.get('/details/:id', Authorization.sessionAuthorize, foodController.details.bind(foodController))

module.exports = router
