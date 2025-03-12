<template>
    <div class="border-2 rounded-md p-4 w-full mb-4 shadow-md h-fit min-w-48">
        <div class="flex justify-between">
            <p>Order ID: #{{ review.order.id.slice(0, 4) }}</p>
            <div class="flex gap-4 items-center">
                <span>{{ review.likeCount }}</span>
                <fa
                    class="text-3xl cursor-pointer hover:text-red-500 duration-200"
                    :class="isLiked ? 'text-red-500' : 'text-gray-400'"
                    icon="heart"
                    @click="handleLike"
                />
                <button
                    v-if="
                        loginUserId === review.customer.id || role === 'ADMIN'
                    "
                    class="bg-red-500 text-white px-2 py-1 rounded-md"
                    @click="deleteReview(review.id)"
                >
                    Delete
                </button>
            </div>
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

        <!-- Comment Section Toggle -->
        <button
            @click="showComments = !showComments"
            class="mt-4 w-full text-left text-blue-600 font-bold"
        >
            {{
                showComments
                    ? 'Hide Comments'
                    : `View Comments (${comments.length})`
            }}
        </button>

        <!-- Comment Section -->
        <transition name="fade">
            <div v-if="showComments" class="mt-2 border-t pt-2">
                <h3 class="text-lg font-bold">Comments</h3>
                <div
                    v-for="comment in comments"
                    :key="comment.id"
                    class="bg-gray-100 p-2 my-2 rounded"
                >
                    <p class="font-semibold">{{ comment.user.username }}:</p>
                    <p>{{ comment.comment }}</p>
                    <p class="text-sm text-gray-500">
                        {{ formatDate(comment.commentAt) }}
                    </p>
                </div>
                <div class="mt-2 flex gap-2">
                    <input
                        v-model="newComment"
                        placeholder="Write a comment..."
                        class="border rounded p-2 w-full"
                    />
                    <button
                        @click="addComment"
                        class="bg-blue-500 text-white px-4 py-2 rounded"
                    >
                        Post
                    </button>
                </div>
            </div>
        </transition>
    </div>
</template>

<script setup lang="ts">
import { ref, defineProps, onMounted } from 'vue'
import reviewApi from '@/api/reviewApi.js'
import userApi from '@/api/userApi'
import commentApi from '@/api/commentApi'

const props = defineProps<{ review }>()

const isLiked = ref<boolean>(false)
const loginUserId = ref<string>('')
const role = ref<string>('')
const comments = ref([])
const newComment = ref<string>('')
const showComments = ref<boolean>(false) // Toggle state for dropdown

const checkIsLiked = async () => {
    try {
        const { data: user } = await userApi.getUserByJwt()
        const { data: res } = await reviewApi.isLikedBy({
            reviewId: props.review.id,
            userId: user.data.id,
        })
        isLiked.value = res.data.liked
        loginUserId.value = user.data.id
        role.value = user.data.role
    } catch (error) {
        console.error('Error checking like status:', error)
    }
}

const fetchComments = async () => {
    try {
        const { data: res } = await commentApi.getCommentsByReview(
            props.review.id
        )
        comments.value = res.data
    } catch (error) {
        console.error('Error fetching comments:', error)
    }
}

const addComment = async () => {
    if (!newComment.value.trim()) return
    try {
        const { data: user } = await userApi.getUserByJwt()
        const commentData = {
            reviewId: props.review.id,
            userId: user.data.id,
            comment: newComment.value,
        }
        const { data } = await commentApi.addComment(commentData)
        comments.value.push(data)
        newComment.value = ''
    } catch (error) {
        console.error('Error adding comment:', error)
    }
}

const handleLike = async () => {
    try {
        isLiked.value ? props.review.likeCount-- : props.review.likeCount++

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

const deleteReview = async (id: string) => {
    try {
        await reviewApi.deleteReview(id)
        console.log('Review deleted')
        window.location.reload()
    } catch (error) {
        console.error('Error deleting review:', error)
    }
}

const formatDate = (dateString) => {
    return new Date(dateString).toLocaleString()
}

onMounted(() => {
    checkIsLiked()
    fetchComments()
})
</script>

<style scoped>
.star {
    color: grey;
}

.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.3s;
}
.fade-enter,
.fade-leave-to {
    opacity: 0;
}
</style>
