import { createRouter, createWebHistory } from 'vue-router'
import authApi from '@/api/authApi'

const routes = [
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

// navigation guard
router.beforeEach(async (to, _, next) => {
    try {
        // Allow navigation to 'signin' and 'signup' routes without token validation
        if (to.name === 'signin' || to.name === 'signup') {
            next()
            return
        }

        const token = localStorage.getItem('token')

        // Check if the token exists
        if (!token) {
            return next({ name: 'signin' })
        }

        const { data: response } = await authApi.validateToken(token)

        const isAuthenticated = response.success

        // Redirect to 'signin' if the token is not valid
        if (!isAuthenticated) {
            localStorage.removeItem('token') // ลบ token เก่า
            return next({ name: 'signin' })
        }

        next()
    } catch (error) {
        console.error('Error validating token:', error)
        localStorage.removeItem('token') // ลบ token เมื่อเกิด error
        next({ name: 'signin' })
    }
})

export default router
