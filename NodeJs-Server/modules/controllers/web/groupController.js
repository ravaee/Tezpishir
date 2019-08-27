const path = require('path')
const Controller = require('./controller')

class GroupController extends Controller{

    _create(req, res) {
        return res.render(path.resolve('modules/views/pages/group/create'),this.Bundle.getBundle(req))
    }

    create(req, res) {

        req.checkBody('name', 'name is empty').not().isEmpty()
        this.Validator.escapeAndTrim(req, ['name'])
        this.Validator.checkValidation(res, req,
            () => { 
                if (req.file) {
                    this.Database.Group({
                        image: req.file["filename"],
                        name: req.body.name
                    }).save((error, group) => {
                        if (error)
                            this.Bundle.setBundle(req, null, error, "Check the Errors Bellow.")

                        else if (group) {

                            this.Bundle.setBundle(req,null, error, "Group has been created successfuly.")
                            return res.redirect('./list')
                        } else {
                       
                            this.Bundle.setBundle(req, null, ["Unknown Errors"], "Check the Errors Bellow.")
                        }
                        return res.redirect('./create')
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
         
        this.Database.Group.findByIdAndRemove(req.params.id, (error, doc) => { 
            if (error) {
                this.Bundle.setBundle(req, null, error, "Check the Errors Bellow.")
                
            } else if (doc) {
                this.Bundle.setBundle(req, null, null, "Group Has Been Deleted Successfuly.")
                
            } else {
                this.Bundle.setBundle(req, null, ["Error has occured while get data."], "Check the Errors Bellow.")               
                 
            }
            return res.redirect('../list')
        })
    }

    list(req, res) {

        const bundle = this.Bundle.getBundle(req)

        this.Database.Group.find((error, groups) => { 
            if(error)
                return res.render(path.resolve('modules/views/pages/group/list'), this.Bundle.createBundle(null, error, "Check the Errors Bellow."))
            
            if (groups)
                return res.render(path.resolve('modules/views/pages/group/list'), this.Bundle.createBundle(groups, bundle.errors, bundle.message))
            
            else
                return res.render(path.resolve('modules/views/pages/group/list'), this.Bundle.createBundle(null, ["Error has occured while get data."], "Check the Errors Bellow."))
        })
    }

}

module.exports = new GroupController