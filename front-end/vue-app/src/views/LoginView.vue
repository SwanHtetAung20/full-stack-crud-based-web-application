<template>
  <div class="w-full h-screen flex justify-center items-center bg-gray-600">
    <div class="w-full max-w-xs">
      <form
        @submit.prevent="loginHandler"
        class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4"
      >
        <div class="mb-4">
          <label
            class="block text-gray-700 text-sm font-bold mb-2"
            for="username"
          >
            UserEmail
          </label>
          <input
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:shadow-outline"
            id="username"
            type="text"
            placeholder="Useremail"
            v-model="email"
          />
        </div>
        <div class="mb-6">
          <label
            class="block text-gray-700 text-sm font-bold mb-2"
            for="password"
          >
            Password
          </label>
          <input
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:shadow-outline"
            id="password"
            type="password"
            placeholder="******************"
            v-model="password"
          />
          <!-- <p class="text-red-500 text-xs italic">Please choose a password.</p> -->
        </div>
        <div class="flex items-center justify-between">
          <button
            class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
            type="submit"
          >
            Sign In
          </button>
          <a
            class="inline-block align-baseline font-bold text-sm text-blue-500 hover:text-blue-800"
            href="#"
          >
            Forgot Password?
          </a>
        </div>
      </form>
      <p class="text-center text-gray-500 text-xs">
        &copy;2020 Acme Corp. All rights reserved.
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useToast } from "vue-toastification";
import axios from "axios";
import { useCounterStore } from "../stores/counter";
import { storeToRefs } from "pinia";
const email = ref<string>("");
const password = ref<string>("");
const toast = useToast();
const main = useCounterStore();

const { login } = main;

const loginHandler = async () => {
  if (email.value === "" || password.value === "") {
    toast.error("Fields cannot be empty.!");
    return;
  }
  await login(email.value, password.value);
};
</script>
