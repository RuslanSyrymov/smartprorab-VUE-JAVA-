import auth from '../api/auth.js'

export default {
    install: (app) => {
        app.config.globalProperties.$auth = auth;
    }
}