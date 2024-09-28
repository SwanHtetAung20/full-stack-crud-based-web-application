import { createApp } from "vue";
import { createPinia } from "pinia";
import "./assets/style.css";
import Toast from "vue-toastification";
import "vue-toastification/dist/index.css";
import "primeicons/primeicons.css";

import App from "./App.vue";
import router from "./router";

const app = createApp(App);

app.use(createPinia());
app.use(Toast);
app.use(router);

app.mount("#app");
