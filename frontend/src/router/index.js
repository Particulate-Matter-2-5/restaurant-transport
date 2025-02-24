import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/',
        name: 'home',
        beforeEnter: async (__, _, next) => {
            try {
                const role = await getUserRole()
                if (role === 'COOK') {
                    next({ name: 'orderforcook' })
                } else {
                    next({ name: 'food' })
                }
            } catch (error) {
                console.error('Error getting user role:', error)
                next({ name: 'signin' })
            }
        },
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
        path: '/:pathMatch(.*)*', // Catch-all for undefined routes
        name: 'notFound',
        component: () => import('@/views/NotFound.vue'),
    },
    {
        path: '/order/:id/review',
        name: 'ReviewView',
        component: () => import('@/views/ReviewView.vue'),
    },
    {
        path: '/reviewlistview',
        name: 'ReviewListView',
        component: () => import('@/views/ReviewListView.vue'),
    }
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
})

/**
 * @param {string} token
 * @returns {number} token expiration time in milliseconds
 */
const decodeToken = (token) => {
    try {
        const payload = JSON.parse(atob(token.split('.')[1]))
        return payload.exp * 1000
    } catch (error) {
        console.error('Error decoding token:', error)
        return 0
    }
}

// Get user role from the server
const getUserRole = async () => {
    const { data: res } = await userApi.getUserByJwt()
    return res.data.role
}

// Navigation guard
router.beforeEach(async (to, _, next) => {
    try {
        // Skip token validation for sign-in and sign-up pages
        if (['signin', 'signup'].includes(to.name)) {
            next()
            return
        }

        const token = localStorage.getItem('token')

        // If no token, redirect to sign-in page
        if (!token) {
            return next({ name: 'signin' })
        }

        const tokenExpiry = decodeToken(token)

        // If token has expired, remove it and redirect to sign-in page
        if (tokenExpiry < Date.now()) {
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
