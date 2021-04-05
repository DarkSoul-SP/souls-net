import Vue from 'vue'
import Vuetify from 'vuetify'
import '@babel/polyfill'
import 'api/resource'
import router from 'router/router'
import App from 'pages/App.vue'
import store from 'store/store'
import { connect } from "./util/ws";
import 'vuetify/dist/vuetify.min.css'
import * as Sentry from '@sentry/vue'

Sentry.init({
    Vue,
    dsn: "https://fbde7d01bbea4be8ab4c36daf00e0f63@o396287.ingest.sentry.io/5700773",
    logErrors: true,
    release: "soulsnet@1.0.0",
    environment: 'development'
})

Sentry.setUser({
    id: profile && profile.id,
    username: profile && profile.name,
    email: profile && profile.email
})

if (profile) {
    connect();
}

Vue.use(Vuetify)

new Vue({
    el: '#app',
    store,
    router,
    render: a => a(App)
})
