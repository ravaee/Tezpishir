const Controller = require('../controller')

class GroupController extends Controller {

    index(req, res) {
        res.json('Welcome to admin Api version 1')
    }

    list(req, res) {

        this.Validator.checkValidation(res, req,
            () => {
                this.Database.Group.find({}, this.Transform.Group.full).populate('foods').exec((error, groups) => {

                    if (error) {

                        return new this.Response(res, [], [], 520, false, 'Database problem')
                    
                    } else if (groups) { 
                        
                        let _groups = []

                        groups.map((group) => { 
                            console.log(group.foods)
                            let obj = {'image':group.image, 'name': group.name, 'count': group.foods.length, 'id':group._id }
                            _groups.push(obj)
                        })

                        return new this.Response(res, _groups, [], 200, true, 'Groups has been listed')

                    } else {
                        
                        return new this.Response(res, [], [], 200, true, 'Unknown Error')
                    }
                    
                })
            },
            () => {
                return new this.Response(res, [], req.validationErrors().map(error => (error.msg)), 422, true, 'Request was not valid')
            })
    }

    single(req, res) {

        //params validation
        req.checkParams('id', 'id format is not valid.').isMongoId()

        this.Validator.checkValidation(res, req,
            () => {
                this.Database.Group.findById(req.params.id, this.Transform.Group.full, (error, group) => {

                    if (error)
                        return new this.Response(res, [], [], 520, false, 'Database problem')

                    return new this.Response(res, group, [], 200, true, 'Group has found')
                })
            },
            () => {
                return new this.Response(res, [], req.validationErrors().map(error => (error.msg)), 422, true, 'Request was not valid')
            })
    }

    store(req, res) {

        //validation
        req.checkBody('name', 'name is empty').not().isEmpty()
        req.checkBody('image', 'Image is empty').not().isEmpty();

        this.Validator.escapeAndTrim(req, ['name', 'image'])

        this.Validator.checkValidation(res, req,
            () => {
                //store
                let group = new this.Database.Group({
                    name: req.body.name,
                    image: req.body.image,

                }).save(error => {
                    if (error)
                        return new this.Response(res, [], [error], 500, false, 'Database error')

                    return new this.Response(res, [], [], 200, true, 'Group has been saved succesfuly.')
                })
            },
            () => {
                new this.Response(res, [], req.validationErrors().map(error => (error.msg)), 422, false, 'Some fileds are not valid.')
            })
    }

    update(req, res) {

        //params validation
        req.checkParams('id', 'id format is not valid.').isMongoId()

        //validation
        req.checkBody('name', 'name is empty').not().isEmpty()
        req.checkBody('image', 'Image is empty').not().isEmpty();

        this.Validator.escapeAndTrim(req, ['name', 'image'])

        this.Validator.checkValidation(res, req,
            () => {
                this.Database.Group.findByIdAndUpdate(req.params.id,
                    {
                        name: req.body.name,
                        image: req.body.image,
                    },
                    (error, group) => {
                        if (error)
                            return new this.Response(res, [], [error], 500, false, 'Database error')

                        return new this.Response(res, [], [], 200, true, 'Group has been updated succesfuly.')
                    })
            },
            () => {
                new this.Response(res, [], req.validationErrors().map(error => (error.msg)), 422, false, 'Some fileds are not valid.')
            })
    }

    delete(req, res) {

        req.checkParams('id', 'id format is not valid.').isMongoId()

        this.Validator.checkValidation(res, req,
            () => {
                this.Database.Group.findByIdAndRemove(req.params.id, (error, group) => {
                    if (error)
                        return new this.Response(res, [], [error], 500, false, 'Database error')
                    return new this.Response(res, [], [], 200, true, 'Group has been deleted successfuly')
                })
            },
            () => {
                new this.Response(res, [], req.validationErrors().map(error => (error.msg)), 422, false, 'Some fileds are not valid.')
            })
    }

}

module.exports = new GroupController