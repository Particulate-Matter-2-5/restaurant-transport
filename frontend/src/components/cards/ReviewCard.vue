<template>
  <div class="border-2 rounded-md p-4 w-full mb-4 shadow-md h-fit min-w-48">
    <div class="flex justify-between">
      <p>Order ID: #{{ review.order.id.slice(0, 4) }}</p>
      <fa
          class="text-3xl cursor-pointer hover:text-red-500 duration-200"
          :class="isLiked ? 'text-red-500' : 'text-gray-400'"
          icon="heart"
          @click="handleLike"
      />
    </div>
    <p>Customer Name: {{ review.customer.username }}</p>

    <p>Review Date:
      {{
        review.createdAt.split('T')[0] + " | " +
        review.createdAt.split('T')[1].slice(0, 5)
      }}
    </p>
    <fa
        v-for="i in 5"
        :key="i"
        icon="star"
        :class="i <= review.rating ? 'text-yellow-500' : 'text-gray-300'"
        class="text-lg"
    />
    <p class="bg-gray-200 py-4 px-2 rounded-md h-fit">{{ review.comment }}</p>
  </div>
</template>

<script setup lang="ts">
import { ref, defineProps } from 'vue'
import reviewApi from "@/api/reviewApi.js";

const props = defineProps<{review: Review}>()

const isLiked = ref<boolean>(false)

const handleLike = async () => {
  try {
    isLiked.value = !isLiked.value;

    const { data: res } = await reviewApi.like({
      id: props.review.id,
      isLiked: isLiked.value.toString()
    });

    console.log("Like status updated:", res);
  } catch (error) {
    console.error("Error updating like status:", error);
  }
};
</script>

<style scoped>
.star {
  color: grey;
}
</style>
