const Response = require('./response')

module.exports = new class validator{
    
    checkValidation(res, req, onSuccess, onFailed) {

        let errors = req.validationErrors()

        if (errors) {
            
            return onFailed()
        }
        return onSuccess()
    
    }

    escapeAndTrim(req, items) {

        items.forEach(item => {
            req.sanitize(item).escape();    
            req.sanitize(item).trim();
        });
    }


}
