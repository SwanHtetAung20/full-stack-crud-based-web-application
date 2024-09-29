<template>
  <div class="flex justify-center items-center h-screen">
    <table>
      <thead>
        <tr class="text-center">
          <th class="w-16">No</th>
          <th class="w-40">Name</th>
          <th class="w-60">Email</th>
          <th class="w-40">Phone</th>
          <th class="w-40">Photo</th>
          <th class="w-40">Role</th>
          <th class="w-32">Update</th>
          <th class="w-32">Delete</th>
          <th class="w-32">Change</th>
          <th class="w-32">Logout</th>
        </tr>
      </thead>
      <tbody>
        <tr
          class="text-center"
          v-for="(user, index) in userList"
          :key="user.id"
        >
          <td>{{ index + 1 }}</td>
          <td>{{ user.name ? user.name : "UNDEFINED" }}</td>
          <td>{{ user.email }}</td>
          <td>{{ user.phone ? user.phone : "UNDEFINED" }}</td>
          <td>{{ user.photo ? user.photo : "UNDEFINED" }}</td>
          <td>{{ user.role }}</td>
          <td>
            <button
              class="btn bg-blue-700 transition ease duration-200 hover:scale-125"
            >
              <i class="pi pi-user-edit"></i> Update
            </button>
          </td>
          <td>
            <button
              @click="deleteHandler(user.id)"
              class="btn transition ease duration-200 hover:scale-125 bg-red-700"
            >
              <i class="pi pi-trash"></i> Delete
            </button>
          </td>
          <td>
            <button
              class="btn transition ease duration-200 hover:scale-125 bg-green-700"
            >
              <i class="pi pi-user-edit"></i> Change
            </button>
          </td>
          <td>
            <button
              @click="logout()"
              class="btn transition ease duration-200 hover:scale-125 bg-gray-700"
            >
              <i class="pi pi-sign-out"></i> Logout
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts">
import { storeToRefs } from "pinia";
import { useCounterStore } from "../stores/counter";
import { onMounted } from "vue";
import { useToast } from "vue-toastification";

const main = useCounterStore();
const toast = useToast();
const { userList } = storeToRefs(main);
const { findAll, deleteHandler, updateHandler } = main;

onMounted(async () => {
  await findAll();
});

const { logout } = main;
</script>
