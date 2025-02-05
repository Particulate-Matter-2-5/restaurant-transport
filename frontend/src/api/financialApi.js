import apiClient from './index'

const financialApi = {
    getAll() {
        return apiClient.get('/financial', {
            headers: { 'Content-Type': 'application/json' },
        })
    },
}

export default financialApi
