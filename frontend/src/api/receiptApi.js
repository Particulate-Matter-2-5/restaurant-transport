import apiClient from './index'

const receiptApi = {
    getReceiptById(id) {
        return apiClient.get(`/order/${id}/receipt`, {
            headers: { 'Content-Type': 'application/json' },
        })
    },

    getUserById(id) {
        return apiClient.get(`/order/${id}/user`, {
            headers: { 'Content-Type': 'application/json' },
        })
    },

    getFoodById(id) {
        return apiClient.get(`/order/${id}/food`, {
            headers: { 'Content-Type': 'application/json' },
        })
    },
}

export default receiptApi
