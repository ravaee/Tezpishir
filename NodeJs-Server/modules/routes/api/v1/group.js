const express = require('express')
const router = express.Router()
const groupController = require('../../../controllers/api/v1/groupController')
const Authorization = require('../../middlewares/authentication')

//default route
router.get('/', groupController.index.bind(groupController))

//get a single food
router.get('/find/:id', groupController.single.bind(groupController))

//get list of foods
router.get('/list', Authorization.authorize, groupController.list.bind(groupController))

//store single food
router.post('/store', groupController.store.bind(groupController))

//update single food by id
router.put('/change/:id', groupController.update.bind(groupController))

//remove a single food by id
router.delete('/remove/:id', groupController.delete.bind(groupController))

module.exports = router;