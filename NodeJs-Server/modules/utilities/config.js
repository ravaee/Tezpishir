const path = require('path')

module.exports = {
    port: 3000,
    auth_data: { 
        googleAuth: {
            clientID: '624101718624-1sm3potngau4se1tokrhu9tabjbflo18.apps.googleusercontent.com',
            clientSecret: '3yp5qEye7ZsXoNxdkM_G-NWU',
            callbackURL:'http://localhost:8000/api/v1/auth/google/callback'
        }
    },
    jwt_secret:'salam'
}