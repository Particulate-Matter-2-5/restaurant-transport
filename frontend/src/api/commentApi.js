import apiClient from './index'

const commentApi = {
    getCommentsByReview(reviewId) {
        return apiClient.get(`/comment/${reviewId}`, {
            headers: { 'Content-Type': 'application/json' },
        })
    },

    addComment(comment) {
        return apiClient.post('/comment', comment, {
            headers: { 'Content-Type': 'application/json' },
        })
    },
}

export default commentApi
