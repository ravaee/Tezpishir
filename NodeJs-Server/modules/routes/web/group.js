const express = require('express')
const router = express.Router()
const groupController = require('../../controllers/web/groupController')
const Authorization = require('../middlewares/authentication')
const uploadMid = require('../middlewares/upload.js')
const multer = require('multer')
const upload = multer({ storage: uploadMid.setGroup });

router.get('/list', Authorization.sessionAuthorize, groupController.list.bind(groupController))

router.get('/create', Authorization.sessionAuthorize, groupController._create.bind(groupController))

router.post('/create', Authorization.sessionAuthorize, upload.single('photo'), groupController.create.bind(groupController))

router.get('/remove/:id', Authorization.sessionAuthorize ,groupController.remove.bind(groupController))

module.exports = router
 