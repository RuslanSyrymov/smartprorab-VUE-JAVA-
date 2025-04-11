import network from '../api/index.js'
import configsys from '../config/index.js';

export const auth = {

    async login(creds) {

        return new Promise((resolve) =>{
            network.post(configsys.apiAuth.signIn,creds).then(response =>{

                let succeded = false;

                if(response.data.error === undefined && response.data.accessToken !== undefined){

                    // used for simple auth
                    localStorage.setItem(configsys.localStorageKeys.accessToken, response.data.accessToken);
                    // localStorage.setItem(configsys.localStorageKeys.role,response.data.role);
                    succeded = true;
                }

                resolve(succeded)
            });
        });
    },

    logout() {
        network.post(configsys.apiRoutes.logout,'').then(() => {
            localStorage.removeItem(configsys.localStorageKeys.accessToken)
            localStorage.removeItem(configsys.localStorageKeys.role)
        });
    },

    verifyAuth(){
        return new Promise((resolve) => {

            network.post(configsys.apiRoutes.verify, '').then(response => {
                resolve(response.data);
            });
        });
    }
}

export default auth;