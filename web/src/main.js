// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'

import Axios from "axios"
import VueAxios from "vue-axios"

// import ElementUI from 'element-ui'
// import 'element-ui/lib/theme-chalk/index.css'
import {Container, Header, Main, Button, Menu, MenuItem,
  Card, Table, TableColumn, Pagination,
  Input, Loading, Upload } from 'element-ui';

Vue.prototype.$ELEMENT = {size: 'normal', zIndex: 3000};

Vue.use(Container)
Vue.use(Header)
Vue.use(Main)
Vue.use(Button)
Vue.use(Menu)
Vue.use(MenuItem)
Vue.use(Card)
Vue.use(Table)
Vue.use(TableColumn)
Vue.use(Pagination)
Vue.use(Input)
Vue.use(Loading)
Vue.use(Upload)

Vue.use(VueAxios, Axios)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  components: { App },
  template: '<App/>'
})
