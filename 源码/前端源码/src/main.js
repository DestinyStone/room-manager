import Vue from 'vue'
import App from './App.vue'
import 'element-ui/lib/theme-chalk/index.css';
import ElementUI from 'element-ui';
import VueRouter from 'vue-router';
import router from "@/router/index"
import basicContainer from './components/basic-container/main'
import Axios from "@/api/request"

Vue.config.productionTip = false
Vue.use(VueRouter)
Vue.use(ElementUI);

window.axios = Axios;

Vue.use(window.AVUE, {
  size: 'small',
  tableSize: 'small',
})

Vue.component('basicContainer', basicContainer)

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
