import axios from 'axios';
import configsys from '../config/index.js';

const API = configsys.api;

const network = {

    /**
     * Post function to cover the await solution
     * @param url
     * @returns {Promise<axios.AxiosResponse<any>>}
     */
    post(url,body){
        let header = this.getHeader();
        return axios.post(API+url,body,header).then(response => {
            return response;
        });
    },

    /**
     * Get function as async
     * @param url
     * @param params
     * @returns {Promise<axios.AxiosResponse<any>>}
     */
    get(url){
        let header = this.getHeader();
        return axios.get(API+url,header).then(response => {
            return response;
        });
    },

    getHeader(){
        return { headers: {'Authorization':'Bearer ' + localStorage.getItem(configsys.localStorageKeys.accessToken) }};
    }
}

export default network;