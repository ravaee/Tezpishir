const express = require('express')
const router = express.Router()
const foodController = require('../../../controllers/api/v1/foodController')
const Authorization = require('../../middlewares/authentication')

//default route
router.get('/', foodController.index.bind(foodController))

//get a single food
router.get('/find/:id', foodController.single.bind(foodController))

//get list of foods
router.get('/list/:groupId', foodController.list.bind(foodController))

//store single food
router.post('/store', foodController.store.bind(foodController))

//update single food by id
router.put('/change/:id', foodController.update.bind(foodController))

//remove a single food by id
router.delete('/remove/:id', foodController.delete.bind(foodController))

module.exports = router;