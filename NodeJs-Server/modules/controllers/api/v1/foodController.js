const Controller = require('../controller')

class FoodController extends Controller {

    index(req, res) {
        res.json('Welcome to admin Api version 1')
    }

    sleep(millis) {
        return new Promise(resolve => setTimeout(resolve, millis));
    }

    list(req, res) {

        this.sleep(2000);

        let pageNo = req.query.no
        let count = req.query.count

        if (pageNo < 0 || count === 0) {
            return new this.Response(res, [], ["Page No or Count are Not Accepted."], 404, false, 'Params Problem')
        }

        var query = { 'skip': count * (pageNo - 1), 'limit': Number(count) }

        this.Database.Food.count({ 'group': req.params.groupId }, (err, totalCount) => {

            if (err) {
                return new this.Response(res, [], ["Error On Get Count."], 404, false, 'Error Fetching Data')
            }

            this.Validator.checkValidation(res, req,
                () => {
                    this.Database.Food.find({ 'group': req.params.groupId }, this.Transform.Food.semi, query)
                        .populate('owner', ['fullName'])
                        .exec((error, foods) => {
                            if (error) {
                                console.log(error)
                                return new this.Response(res, [], [], 520, false, 'Database problem')
                            }

                            foods.map((food) => {
                                let scoreAvrage = 0
                                food.score.map((score) => {
                                    scoreAvrage += score.score
                                })
                                food.scoreAvrage = (scoreAvrage > 0) ? (scoreAvrage / food.score.count) : 0
                            })

                            var foodsObject = []
                            foods.map((food) => {
                                let object = {
                                    'id': food._id,
                                    'name': food.name,
                                    'time': food.time,
                                    'owner': food.owner,
                                    'image': food.image,
                                    'score': food.scoreAvrage
                                }
                                foodsObject.push(object)
                            })

                            let foodResponse = { 'foods': foodsObject, 'leftOver': totalCount }
                            console.log(foodResponse)
                            return new this.Response(res, foodResponse, [], 200, true, 'Foods has been listed')
                        })
                },
                () => {
                    return new this.Response(res, [], req.validationErrors().map(error => (error.msg)), 422, true, 'Request was not valid')
                }
            )
        })
    }


    single(req, res) {

        //params validation
        req.checkParams('id', 'id format is not valid.').isMongoId()

        this.Validator.checkValidation(res, req,
            () => {
                this.Database.Food.findById(req.params.id, this.Transform.Food.full, (error, food) => {

                    if (error)
                        return new this.Response(res, [], [], 520, false, 'Database problem')

                    return new this.Response(res, {'food':food}, [], 200, true, 'Foods has been listed')
                })
            },
            () => {
                return new this.Response(res, [], req.validationErrors().map(error => (error.msg)), 422, true, 'Request was not valid')
            })
    }

    store(req, res) {

        //validation
        req.checkBody('name', 'name is empty').not().isEmpty()
        req.checkBody('ingredients', 'ingredients is empty').not().isEmpty()
        req.checkBody('recipes', 'recipes is empty').not().isEmpty()
        req.checkBody('time', 'time should be numberic').isDecimal();
        req.checkBody('image', 'Image is empty').not().isEmpty();
        req.checkBody('groupId', 'groupId is empty').not().isEmpty();

        this.Validator.escapeAndTrim(req, ['name', 'ingredients', 'recipes', 'time', 'image'])

        this.Validator.checkValidation(res, req,
            () => {
                let _group = this.Database.Group.findById(req.body.groupId, (error, group) => {

                    if (error) {

                        return new this.Response(res, [], [error], 404, false, 'Bad Request')

                    } else if (group) {

                        let food = new this.Database.Food({
                            name: req.body.name,
                            ingredients: req.body.ingredients,
                            recipes: req.body.recipes,
                            image: req.body.image,
                            time: req.body.time,
                            group: group._id

                        }).save((error, food) => {
                            if (error)
                                return new this.Response(res, [], [error], 500, false, 'Database error')

                            group.foods.push(food._id)
                            group.save()

                            return new this.Response(res, [], [], 200, true, 'Food has been saved succesfuly.')
                        })
                    } else {
                        return new this.Response(res, [], [], 404, true, 'There is no any group with that groupId')
                    }
                })
            },
            () => {
                return new this.Response(res, [], req.validationErrors().map(error => (error.msg)), 422, false, 'Some fileds are not valid.')
            })
    }

    update(req, res) {

        //params validation
        req.checkParams('id', 'id format is not valid.').isMongoId()

        //validation
        req.checkBody('name', 'name is empty').not().isEmpty()
        req.checkBody('ingredients', 'ingredients is empty').not().isEmpty()
        req.checkBody('recipes', 'recipes is empty').not().isEmpty()
        req.checkBody('time', 'time should be numberic').isDecimal();
        req.checkBody('image', 'Image is empty').not().isEmpty();
        req.checkBody('groupId', 'groupId is empty').not().isEmpty();


        this.Validator.escapeAndTrim(req, ['name', 'ingredients', 'recipes', 'time', 'image'])

        this.Validator.checkValidation(res, req,

            () => {
                let findNewGroup = this.Database.Group.findById(req.body.groupId, (error, newGroup) => {

                    if (error) {

                        return new this.Response(res, [], [error], 500, false, 'Database error')

                    } else if (newGroup) {

                        this.Database.Food.findById(req.params.id).populate('group').exec((error, food) => {

                            if (error) {

                                return new this.Response(res, [], [error], 500, false, 'Database error')

                            } else if (food) {

                                let group = food.group
                                let pos = group.foods.indexOf(food._id)
                                group.foods.splice(pos, 1)
                                newGroup.foods[pos] = food._id

                                newGroup.updateOne(newGroup, (error, doc) => {
                                    if (doc) {
                                        group.save()
                                    } else {
                                        return new this.Response(res, [], [error], 500, false, 'Internal Server Error')
                                    }
                                })

                                food.updateOne({
                                    name: req.body.name,
                                    ingredients: req.body.ingredients,
                                    recipes: req.body.recipes,
                                    image: req.body.image,
                                    time: req.body.time,
                                    group: newGroup._id
                                },

                                    (error, food) => {
                                        if (error) {
                                            return new this.Response(res, [], [error], 500, false, 'Database error')
                                        } else if (food) {
                                            return new this.Response(res, [food], [], 200, true, 'Food has been updated succesfuly.')
                                        } else {
                                            return new this.Response(res, [], [error], 500, false, 'Internal Server Error')
                                        }
                                    })

                            } else {

                                return new this.Response(res, [], [], 400, true, 'The food with that foodId has not found.')
                            }
                        })

                    } else {

                        return new this.Response(res, [], [], 400, true, 'The group with that groupid has not found.')
                    }
                })
            },
            () => {
                return new this.Response(res, [], req.validationErrors().map(error => (error.msg)), 422, false, 'Some fileds are not valid.')
            })
    }

    delete(req, res) {

        req.checkParams('id', 'id format is not valid.').isMongoId()

        this.Validator.checkValidation(res, req,
            () => {

                this.Database.Food.findById(req.params.id).populate('group').exec((error, food) => {
                    if (error) {

                        return new this.Response(res, [], [error], 500, false, 'Database error')

                    } else if (food) {

                        let group = food.group
                        let pos = group.foods.indexOf(food._id)
                        group.foods.splice(pos, 1)
                        group.save();

                        food.remove((error, food) => {
                            if (error)
                                return new this.Response(res, [], [error], 500, false, 'Database error')
                            else if (food) {
                                return new this.Response(res, [food], [], 200, true, 'Food has been deleted successfuly')
                            } else {
                                return new this.Response(res, [], [error], 500, false, 'Internal Server Error')
                            }
                        })

                    } else {

                        return new this.Response(res, [], [], 400, false, 'The food with that foodId has not found.')

                    }
                })
            },
            () => {
                return new this.Response(res, [], req.validationErrors().map(error => (error.msg)), 422, false, 'Some fileds are not valid.')
            })
    }

}

module.exports = new FoodController