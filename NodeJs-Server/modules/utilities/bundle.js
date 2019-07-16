
module.exports = class bundle {

    static createBundle(models, errors, message) {
        return {
            models: models,
            errors: errors,
            message: message
        }
    }

    static setBundle(req, models, errors, message, bundleName){ 
        if (bundleName) {
            req.session[bundleName] = {
                models: models,
                errors: errors,
                message: message,
            }
        } else {
            req.session.data = {
                models: models,
                errors: errors,
                message: message,
            }
        }
    }

    static getBundle(req,bundleName) {
        if (bundleName) {
            const data = req.session['bundleName']
            req.session.data = null

            if (!data) {
                return {
                    models: null,
                    errors: null,
                    message: null
                }
            } else {
                return data
            }
        } else {
            const data = req.session.data
            req.session.data = null

            if (!data) {
                return {
                    models: null,
                    errors: null,
                    message: null
                }    
            } else {
                return data
            }
        }
    }
}