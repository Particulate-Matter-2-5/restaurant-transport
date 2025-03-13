<template>
    <div class="comment-section">
      <div v-for="comment in comments" :key="comment.id" class="comment">
        <p><strong>{{ comment.user.username }}:</strong> {{ comment.text }}</p>
      </div>
      <div v-if="loginUserId">
        <textarea v-model="newComment" placeholder="Add a comment..."></textarea>
        <button @click="addComment">Submit</button>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, defineProps } from 'vue'
  import commentApi from '@/api/commentApi.js'
  import userApi from '@/api/userApi.js'
  
  const props = defineProps({
    reviewId: String
  })
  
  const comments = ref([])
  const newComment = ref('')
  const loginUserId = ref('')
  
  const fetchComments = async () => {
    try {
      const { data } = await commentApi.getCommentsByReviewId(props.reviewId)
      comments.value = data
    } catch (error) {
      console.error('Error fetching comments:', error)
    }
  }
  
  const addComment = async () => {
    if (!newComment.value.trim()) return
  
    try {
      const { data: user } = await userApi.getUserByJwt()
      const { data } = await commentApi.addComment({
        reviewId: props.reviewId,
        userId: user.data.id,
        text: newComment.value
      })
      comments.value.push(data)
      newComment.value = ''
    } catch (error) {
      console.error('Error adding comment:', error)
    }
  }
  
  onMounted(async () => {
    const { data: user } = await userApi.getUserByJwt()
    loginUserId.value = user.data.id
    await fetchComments()
  })
  </script>
  
  <style scoped>
  .comment-section {
    margin-top: 1rem;
  }
  .comment {
    margin-bottom: 0.5rem;
  }
  textarea {
    width: 100%;
    margin-top: 0.5rem;
  }
  button {
    margin-top: 0.5rem;
  }
  </style>