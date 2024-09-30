<template>
  <Teleport to="body">
    <div
      v-show="modalActive"
      class="absolute w-full bg-black bg-opacity-35 h-screen top-0 left-0 flex justify-center"
    >
      <div class="p-4 bg-white self-start mt-32 max-w-screen-md">
        <slot />

        <button
          @click="$emit('close-modal')"
          class="text-white mt-8 bg-blue-700 py-2 px-6"
        >
          Close
        </button>
      </div>
    </div>
  </Teleport>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from "vue";

defineProps<{
  modalActive: boolean;
}>();

defineEmits(["close-modal"]);
</script>
