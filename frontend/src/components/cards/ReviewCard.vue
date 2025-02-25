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

        <p>
            Review Date:
            {{
                review.createdAt.split('T')[0] +
                ' | ' +
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
        <p class="bg-gray-200 py-4 px-2 rounded-md h-fit">
            {{ review.comment }}
        </p>
    </div>
</template>

<script setup lang="ts">
import { ref, defineProps, onMounted } from 'vue'
import reviewApi from '@/api/reviewApi.js'
import userApi from '@/api/userApi'

const props = defineProps<{ review }>()

const isLiked = ref<boolean>(false)

const checkIsLiked = async () => {
    try {
        const { data: user } = await userApi.getUserByJwt()
        const { data: res } = await reviewApi.isLikedBy({
            reviewId: props.review.id,
            userId: user.data.id,
        })
        console.log(res)
        isLiked.value = res.success
        console.log('isLiked status:', isLiked.value)
    } catch (error) {
        console.error('Error checking like status:', error)
    }
}

const handleLike = async () => {
    try {
        isLiked.value = !isLiked.value
        const { data: user } = await userApi.getUserByJwt()
        const { data: res } = await reviewApi.like({
            reviewId: props.review.id,
            userId: user.data.id,
            isLiked: isLiked.value.toString(),
        })

        console.log('Like status updated:', res)
    } catch (error) {
        console.error('Error updating like status:', error)
    }
}

onMounted(() => {
    checkIsLiked()
})
</script>

<style scoped>
.star {
    color: grey;
}
</style>
