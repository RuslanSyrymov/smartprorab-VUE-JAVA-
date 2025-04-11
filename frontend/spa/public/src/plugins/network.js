import network from '../api/index.js'

export default {
    install: (app) => {
        app.config.globalProperties.$network = network;
    }
}