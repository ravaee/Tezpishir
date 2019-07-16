const express = require('express')
const router = express.Router()
const Startup = require('../middlewares/startup')
const home = require('./home')
const auth = require('./auth')
const group = require('./group')
const food = require('./food')

Startup(router)

router.use('/', home)
router.use('/', auth)
router.use('/group', group)
router.use('/food', food)

module.exports = router