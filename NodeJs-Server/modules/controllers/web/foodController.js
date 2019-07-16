const path = require('path')
const Controller = require('./controller')

class FoodController extends Controller {
    
    _create(req, res) {

        var bundle = this.Bundle.getBundle(req)

        this.Database.Group.find((error, groups) => { 
            if (error)
                bundle = this.Bundle.createBundle(null, error, "Error occured when fetch groups.")
            else if (groups) {
                if (!bundle.errors) {
                    bundle = this.Bundle.createBundle(groups, null, null)    
                } else {
                    bundle = this.Bundle.createBundle(groups, bundle.errors, bundle.message)    
                }
            }
            else
                bundle = this.Bundle.createBundle(null, ["Unknown Error"], "Error occured when fetch groups.")
            
            return res.render(path.resolve('modules/views/pages/food/create'), bundle)
        })
    }

    create(req, res) {

        req.checkBody('name', 'name is empty').not().isEmpty()
        req.checkBody('time', 'time is empty').not().isEmpty()
        req.checkBody('ingredients', 'ingredients is empty').not().isEmpty()
        req.checkBody('recipes', 'recipes is empty').not().isEmpty()
        req.checkBody('group_id', 'group_id is empty').not().isEmpty()       
        req.checkBody('user_id', 'user_id is empty').not().isEmpty() 
        
        this.Validator.escapeAndTrim(req, ['name','time','group_id'])
        this.Validator.checkValidation(res, req,
            () => {
                if (req.file) {
                    let group = this.Database.Group.findById(req.body.group_id, (error, group) => { 
                        if (error) {

                            this.Bundle.setBundle(req, null, error, "Check the Errors Bellow.")
                            return res.redirect('./create')

                        } else if (group) {

                            this.Database.Food({

                                image: req.file["filename"],
                                name: req.body.name,
                                owner: req.body.user_id,
                                time: req.body.time,
                                group: req.body.group_id,
                                ingredients: req.body.ingredients,
                                recipes: req.body.recipes,

                            }).save((error, food) => {
                                if (error) {

                                    this.Bundle.setBundle(req, null, error, "Check the Errors Bellow.")
                                    return res.redirect('./create')

                                } else if (food) {

                                    group.foods.push(food._id)
                                    group.save()
                                    this.Bundle.setBundle(req, null, error, "Food has been created successfuly.")
                                    return res.redirect('./list')

                                } else {

                                    this.Bundle.setBundle(req, null, ["Unknown Errors"], "Check the Errors Bellow.")
                                }
                            })
                            
                        } else {
                            this.Bundle.setBundle(req, null, ["Unknown Errors"], "Check the Errors Bellow.")
                            return res.redirect('./create')
                        }
                    })

                } else {
                    this.Bundle.setBundle(req, null, ["No Picture choosed."], "Check the Errors Bellow.")
                    return res.redirect('./create')
                }
            },
            () => {
                this.Bundle.setBundle(req, null, req.validationErrors().map(error => (error.msg)), "Check the Errors Bellow.")
                return res.redirect('./create')
            })
    }

    remove(req, res) {

        this.Database.Food.findById(req.params.id).populate('group').exec((error, food) => {
            if (error) {
                this.Bundle.setBundle(req, null, error, "Check the Errors Bellow.")

            } else if (food) {

                let group = food.group
                let pos = group.foods.indexOf(food._id)
                group.foods.splice(pos, 1)
                group.save();

                food.remove((error, food) => {
                    if (error)
                        this.Bundle.setBundle(req, null, ["database Error"], "Check the Errors Bellow.")
                    else if (food) {
                        this.File.deleteFile('modules/public/uploads/images/food/' + food.image)
                        this.Bundle.setBundle(req, null, null, "Food Has Been Deleted Successfuly.")
                    } else {
                        this.Bundle.setBundle(req, null, ["Unknown Error."], "Check the Errors Bellow.")
                    }
                })
            } else {
                this.Bundle.setBundle(req, null, ["Error has occured while get data."], "Check the Errors Bellow.")

            }
            return res.redirect('../list')
        })
    }

    list(req, res) {

        const bundle = this.Bundle.getBundle(req)

        this.Database.Food.find({}, ['name', 'time', 'image', 'group', 'score', 'owner']).populate('group').populate('owner').exec((error, foods) => {
            
            if (error)
                return res.render(path.resolve('modules/views/pages/food/list'), this.Bundle.createBundle(null, error, "Check the Errors Bellow."))

            if (foods)
                return res.render(path.resolve('modules/views/pages/food/list'), this.Bundle.createBundle(foods, bundle.errors, bundle.message))

            else
                return res.render(path.resolve('modules/views/pages/food/list'), this.Bundle.createBundle(null, ["Error has occured while get data."], "Check the Errors Bellow."))
        })
    }

    details(req, res) {

        this.Database.Food.findById(req.params.id).populate('group').populate('owner').exec((error, food) => {

            if (error)
                return res.render(path.resolve('modules/views/pages/food/details'), this.Bundle.createBundle(null, error, "Check the Errors Bellow."))

            if (food)
                return res.render(path.resolve('modules/views/pages/food/details'), this.Bundle.createBundle(food, null, null))

            else
                return res.render(path.resolve('modules/views/pages/food/details'), this.Bundle.createBundle(null, ["Error has occured while get data."], "Check the Errors Bellow."))
        })
    }

}

module.exports = new FoodController