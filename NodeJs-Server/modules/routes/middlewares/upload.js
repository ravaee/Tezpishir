const multer = require('multer')
const path = require('path')
module.exports = {
    setFood : multer.diskStorage({

        destination: function (req, file, cb) {
            cb(null, './modules/public/uploads/images/food')
        },
        filename: function (req, file, cb) {
            cb(null, Date.now() + path.extname(file.originalname))
        }
    }),
    setGroup : multer.diskStorage({

        destination: function (req, file, cb) {
            cb(null, './modules/public/uploads/images/group')
        },
        filename: function (req, file, cb) {
            cb(null, Date.now() + path.extname(file.originalname))
        }
    })
}



