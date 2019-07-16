
module.exports = class response{

    constructor(res, models, errors, statusCode, success, message) {

        setTimeout(function () {
            return (
                res.status(statusCode).json({
                    models: models,
                    errors: errors,
                    message: message,
                    success: success
                })
            )
        }, 1500);

    }
}