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
          <td>
            <div v-if="user.photo">
              <img
                :src="'data:image/**;base64,' + user.photo"
                alt="Profile Image"
                :style="{
                  height: '100px',
                  width: '100px',
                  borderRadius: '10px',
                }"
              />
            </div>
            <div v-else>UNDEFINED</div>
          </td>
          <td>{{ user.role }}</td>
          <td>
            <button
              @click="triggerModal(user)"
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
              @click="triggerFile(user.id)"
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
  <BaseModel :modalActive="modalActive" @close-modal="triggerModal">
    <form
      @submit.prevent="update"
      class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4"
    >
      <input type="hidden" v-model="selectedUser.id" />
      <div class="mb-4">
        <label
          class="block text-gray-700 text-sm font-bold mb-2"
          for="username"
        >
          User Name
        </label>
        <input
          class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:shadow-outline"
          id="username"
          type="text"
          placeholder="Username"
          v-model="selectedUser.name"
        />
      </div>
      <div class="mb-4">
        <label
          class="block text-gray-700 text-sm font-bold mb-2"
          for="useremail"
        >
          UserEmail
        </label>
        <input
          class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:shadow-outline"
          id="useremail"
          type="text"
          placeholder="Useremail"
          v-model="selectedUser.email"
        />
      </div>
      <div class="mb-6">
        <label class="block text-gray-700 text-sm font-bold mb-2" for="phone">
          Phone
        </label>
        <input
          class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:shadow-outline"
          id="phone"
          type="number"
          placeholder="Userphone"
          v-model="selectedUser.phone"
        />
        <!-- <p class="text-red-500 text-xs italic">Please choose a password.</p> -->
      </div>
      <div class="flex items-center justify-between">
        <button
          class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
          type="submit"
        >
          <i class="pi pi-user-edit"></i> Update
        </button>
      </div>
    </form>
  </BaseModel>
  <input
    type="file"
    id="fileInput"
    class="hidden"
    @change="fileHandlerUpload"
  />
</template>

<script setup lang="ts">
import { storeToRefs } from "pinia";
import { useCounterStore, type USER } from "../stores/counter";
import { onMounted, ref } from "vue";
import { useToast } from "vue-toastification";
import BaseModel from "../components/BaseModel.vue";

const main = useCounterStore();
const toast = useToast();
const modalActive = ref<boolean>(false);
const userId = ref<string>("");
const { userList } = storeToRefs(main);
const selectedUser = ref<USER>({
  id: "",
  name: "",
  email: "",
  phone: "",
});
const { findAll, deleteHandler, updateHandler, upload } = main;
const { logout } = main;

onMounted(async () => {
  await findAll();
});

const triggerModal = (user?: USER) => {
  if (user) {
    selectedUser.value = {
      id: user?.id,
      name: user?.name,
      email: user?.email,
      phone: user?.phone,
    };
  }
  modalActive.value = !modalActive.value;
};

const update = async () => {
  if (!selectedUser.value || !selectedUser.value.id) {
    toast.error("Something wrong. Please try again.");
    return;
  }
  await updateHandler(selectedUser.value.id, selectedUser.value);
  triggerModal();
};

const triggerFile = (id: string) => {
  const file = document.getElementById("fileInput") as HTMLElement;
  userId.value = id;
  file.click();
};

const fileHandlerUpload = async (event: Event) => {
  const target = event.target as HTMLElement;
  const file = target.files?.[0];
  if (file) {
    upload(userId.value, file);
  }
};
</script>
