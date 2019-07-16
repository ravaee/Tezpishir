const express = require('express')
const router = express.Router()

const food = require('./food')
const group = require('./group')
const auth = require('./auth')
const user = require('./user')

router.use('/food', food)
router.use('/group', group)
router.use('/auth', auth)
router.use('/user', user)


module.exports = router