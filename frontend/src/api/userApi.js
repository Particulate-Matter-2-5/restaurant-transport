import apiClient from './index'

const userApi = {
    getUserByJwt() {
        return apiClient.get('/user/jwt', {
            headers: {
                'Content-Type': 'application/json',
                Authorization: `${localStorage.getItem('token')}`,
            },
        })
    },
}

export default userApi
