<template>
  <div>
    <h1>LOGIN</h1>
    <form>
      <input v-model="form_data.username" placeholder="username"/>
      <br/>
      <br/>
      <input v-model="form_data.password" placeholder="password" type="password"/>
      <br/>
      <br/>
      <button type="button" @click="log">Login</button>
    </form>
  </div>
</template>
<script>


import auth from "@/api/auth.js";
import router from "@/router/index.js";
import axios from "axios";
import configsys from "@/config/index.js";


export default {
  data: () => {
    return {
      form_data: {
        username: "",
        password: "",
      },
      token: null,
    };
  },
  methods: {

    log() {
      // auth.login(this.form_data).then(resp => {
      //   if (!resp) {
      //     console.log("false")
      //   } else {
      //     router.push('/')
      //   }
      // })
      axios.post(configsys.api + configsys.apiAuth.signIn, this.form_data).then(response => {
        if(response.data.error === undefined && response.data.accessToken !== undefined){

          // used for simple auth
          localStorage.setItem(configsys.localStorageKeys.accessToken, response.data.accessToken);
          // localStorage.setItem(configsys.localStorageKeys.role,response.data.role);
          this.token = response.data;
          router.push("/");
        }

      });
      // axios
      //     .post('http://localhost:8080/auth/sign-in', this.form_data)
      //     .then(response => (this.token = response.data))
      // await instance.get(`test2/reg`);
      // const response = await axios.post(`/auth/sign-in`, this.form_data);
      // await this.$auth.login(this.form_data)
      // this.token = response.data;
    },
  },
};
</script>