import axios from 'axios'
import VueAxios from 'vue-axios'
import { createApp } from 'vue';
import App from './App.vue';
import router from '@/router/index.js';
import ApiPlugins from '@/plugins/api.js'
import configsys from '@/plugins/configsys.js'
import network from '@/plugins/network.js'
// Настройка Axios для отправки AJAX-запросов
// window.axios = axios;
// window.axios.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
// axios.defaults.baseURL = 'http://localhost:9000';

// Инициализация Vue и подключение маршрутизации
const app = createApp(App);
app.use(ApiPlugins);
app.use(network);
app.use(configsys);
app.use(router);
// app.use(VueAxios, axios);
app.mount('#app');

