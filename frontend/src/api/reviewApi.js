import apiClient from './index'
import userApi from './userApi'

const reviewApi = {
    getReviews() {
        return apiClient.get('/review', {
            headers: { 'Content-Type': 'application/json' },
        })
    },

    createReview(reviewData) {
        return apiClient.post('/review/submit', reviewData, {
            headers: { 'Content-Type': 'application/json' },
        })
    },

    like(likeData) {
        return apiClient.post('/review/like', likeData, {
            headers: { 'Content-Type': 'application/json' },
        })
    },

    isLikedBy(isLikedData) {
        return apiClient.post('/review/isLiked', isLikedData, {
            headers: { 'Content-Type': 'application/json' },
        })
    },
}

export default reviewApi
