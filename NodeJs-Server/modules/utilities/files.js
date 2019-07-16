const fs = require('fs')
const path = require('path')

module.exports = new class File{

    deleteFile(_path) {
        fs.unlink(path.resolve(_path), (err) => {
            if (err) throw err;
            console.log('successfully deleted /tmp/hello');
        });
    }
}