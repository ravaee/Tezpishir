const express = require('express')
const router = express.Router()
const homeController = require('../../controllers/web/homeController')
const Authorization = require('../middlewares/authentication')

router.get('/', homeController.index.bind(homeController))

router.get('/dashboard', Authorization.sessionAuthorize, homeController.adminDashboard.bind(homeController))

module.exports = router