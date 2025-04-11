import configsys from '../config/index.js'

export default {
    install: (app) => {
        app.config.globalProperties.$configsys = configsys;
    }
}