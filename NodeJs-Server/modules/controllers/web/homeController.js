const path = require('path')

class HomeController {

    index(req, res) {

        res.render(path.resolve('modules/views/pages/home/index'), { title: 'Tezpishir' });
    }

    adminDashboard(req, res) {
        
        res.render(path.resolve('modules/views/pages/home/adminPanel.ejs'))
    }
}

module.exports = new HomeController