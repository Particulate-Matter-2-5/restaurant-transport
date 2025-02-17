import { createRouter, createWebHistory } from 'vue-router'
import authApi from '@/api/authApi'

const routes = [
    {
        path: '/',
        redirect: '/food',
    },
    {
        path: '/signup',
        name: 'signup',
        component: () => import('@/views/SignupView.vue'),
    },
    {
        path: '/signin',
        name: 'signin',
        component: () => import('@/views/SigninView.vue'),
    },
    {
        path: '/food',
        name: 'food',
        component: () => import('@/views/FoodView.vue'),
    },
    {
        path: '/order',
        name: 'order',
        component: () => import('@/views/OrderView.vue'),
    },
    {
        path: '/orderforcook',
        name: 'orderforcook',
        component: () => import('@/views/OrderCookView.vue'),
    },
    {
        path: '/ingredient',
        name: 'ingredient',
        component: () => import('@/views/IngredientView.vue'),
    },
    {
        path: '/receipt/:id',
        name: 'receipt',
        component: () => import('@/views/ReceiptView.vue'),
    },
    {
        path: '/receiptforcook/:id',
        name: 'receiptforcook',
        component: () => import('@/views/OrderCookRecipeView.vue'),
    },
    {
        path: '/dashboard',
        name: 'dashboard',
        component: () => import('@/views/DashboardView.vue'),
    },
    {
        path: '/cart',
        name: 'cart',
        component: () => import('@/views/CartView.vue'),
    },
    {
        path: '/payment/success',
        name: 'success',
        component: () => import('@/views/SuccessView.vue'),
    },
    {
        path: '/payment/fail',
        name: 'fail',
        component: () => import('@/views/CancelView.vue'),
    },
    {
        path: '/addingredients',
        name: 'addingredients',
        component: () => import('@/views/AddIngredientsView.vue'),
    },
    {
        path: '/addfood',
        name: 'addfood',
        component: () => import('@/views/AddFoodView.vue'),
    },
    {
        path: '/:notFound',
        name: 'notFound',
        component: () => import('@/views/NotFound.vue'),
    },
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
})

/**
 * @param {jwt token} token
 * @returns เวลาที่ token จะหมดอายุ
 */
const decodeToken = (token) => {
    const payload = JSON.parse(atob(token.split('.')[1]))
    return payload.exp * 1000
}

// navigation guard
router.beforeEach(async (to, _, next) => {
    try {
        // path ที่เข้าได้โดยไม่ต้อง validate token
        if (['signin', 'signup'].includes(to.name)) {
            next()
            return
        }

        // ถ้าไม่มี token ไป signin
        const token = localStorage.getItem('token')
        if (!token) {
            return next({ name: 'signin' })
        }

        // ถ้า token หมดอายุ ลบ token แล้วไป signin
        const tokenExpiry = decodeToken(token)
        if (tokenExpiry < Date.now()) {
            localStorage.removeItem('token')
            return next({ name: 'signin' })
        }

        // validate token ว่าถูกต้องไหม ถ้าไม่ ลบ token แล้วไป signin
        const { data: response } = await authApi.validateToken(token)
        const isAuthenticated = response.success
        if (!isAuthenticated) {
            localStorage.removeItem('token')
            return next({ name: 'signin' })
        }
        
        next()
    } catch (error) {
        console.error('Error validating token:', error)
        localStorage.removeItem('token')
        next({ name: 'signin' })
    }
})

export default router
