const express = require('express')
const app = require('express')()
const bodyParser = require('body-parser')
const mongoose = require('mongoose')
const expressValidator = require('express-validator')
const passport = require('passport')
const path = require('path')
global.config = require('./modules/utilities/config')

// Connect to DB
mongoose.connect('mongodb://127.0.0.1/test', { useNewUrlParser: true })
mongoose.Promise = global.Promise 
mongoose.set('useFindAndModify', false);
app.set('view engine', 'ejs');
app.set('views', __dirname + 'modules/views');

app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json({ type: 'application/json' }))
app.use(expressValidator())
app.use(passport.initialize());
app.use(express.static(path.resolve('modules/public')))
// app.use(express.static(path.join(__dirname, 'public')))


const webRouter = require('./modules/routes/web')
const apiRouter = require('./modules/routes/api')

app.use('/api', apiRouter)
app.use('/', webRouter)

app.listen(config.port, () => { 
    console.log(`server running at port ${config.port}`)
})