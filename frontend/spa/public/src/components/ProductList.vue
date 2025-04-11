<template>
  <div>
    <h1>Product List</h1>
    <ul>
<!--      <li v-for="product in products" :key="product.id">-->
<!--        <strong>{{ product.name }}</strong> - {{ product.price }}₽-->
<!--        <router-link :to="`/products/${product.id}/edit`">Edit</router-link>-->
<!--        <button @click="deleteProduct(product.id)" class="delete-btn">Delete</button>-->
<!--      </li>-->
      <button @click="test" class="delete-btn">test</button>
    </ul>
    <router-link to="/products/create" class="create-btn">Create New Product</router-link>
  </div>
</template>

<script>



import network from "@/api/index.js";
import configsys from "@/config/index.js";

export default {
  data() {
    return {
      products: [],
    };
  },

  methods: {
    test(){
      return new Promise((resolve) => {

        network.get('test2/reg').then(response => {
          resolve(response.data);
        });
      });
    },
    // test() {
    //   auth.login(this.form_data).then(resp => {
    //     if(!resp){
    //       console.log("false")
    //     }else{
    //       router.push('/')
    //     }
    //   })
    // },


  },
};
</script>

<style scoped>
.create-btn, .delete-btn {
  margin-left: 10px;
  padding: 5px 10px;
  border: none;
  cursor: pointer;
}
.delete-btn {
  background-color: red;
  color: white;
}
.create-btn {
  display: block;
  margin-top: 20px;
  background-color: green;
  color: white;
  text-align: center;
  text-decoration: none;
  padding: 8px 12px;
}
</style>