<script setup>
import { ref, onMounted, computed } from 'vue'
import Sidebar from '@/components/Sidebar.vue'
import OrderCard from '@/components/OrderCard.vue'
import RecipePopup from '@/components/RecipePopup.vue'
import orderApi from '@/api/orderApi'
import userApi from '@/api/userApi'
import router from '@/router'

const orders = ref([])
const role = ref('')
const user = ref({})
const searchQuery = ref('')
const showRecipePopup = ref(false)
const selectedOrder = ref(null)

const fetchOrders = async () => {
    try {
        const { data: orderResponse } = await orderApi.getOrders()
        const { data: userResponse } = await userApi.getUserByJwt()

        user.value = userResponse.data
        role.value = userResponse.data.role
        const loggedInUserId = user.value.id

        console.log(orderResponse)
        console.log('---')
        console.log(userResponse)
        console.log(loggedInUserId)

        // Filter orders based on user role
        orders.value =
            role.value === 'COOK'
                ? orderResponse.data
                : orderResponse.data.filter(
                      (order) => order.user.id === loggedInUserId
                  )

        console.log('Filtered orders for display:', orders.value)
    } catch (error) {
        console.error('Error fetching orders:', error)
    }
}

onMounted(() => {
    fetchOrders()
})

const filteredOrders = computed(() => {
    return orders.value.filter((order) => {
        const matchesStatus = role.value === 'COOK' && order.status === 'COMPLETE'
        const matchesSearch = searchQuery.value
            ? order.user.toLowerCase().includes(searchQuery.value.toLowerCase())
            : true
        return matchesStatus && matchesSearch
    })
})

const handleOrderSuccess = async (orderId) => {
    const order = orders.value.find((o) => o.id === orderId)
    if (order) {
        try {
            await orderApi.createOrder({ ...order, status: 'Success' })
            order.status = 'Success'
        } catch (error) {
            console.error('Error updating order:', error)
        }
    }
}

const handleViewDetail = (orderId) => {
    router.push({ name: 'receipt', params: { id: orderId } })
}

const handleRecipeClick = (order) => {
    selectedOrder.value = order
    showRecipePopup.value = true
}

const closeRecipePopup = () => {
    showRecipePopup.value = false
    selectedOrder.value = null
}
</script>

<template>
    <div class="flex">
        <aside class="fixed">
            <Sidebar />
        </aside>
        <main
            class="ml-[14rem] w-full py-4 px-8 flex flex-col gap-4 bg-gray-50 h-screen"
        >
            <!-- Orders heading -->
            <section class="w-full">
                <span class="font-bold text-3xl">Order</span>
            </section>

            <!-- Order list -->
            <section class="mt-4">
                <OrderCard
                    v-for="(order, i) in filteredOrders"
                    :index="i + 1"
                    :key="order.id"
                    :order="order"
                    @mark-success="handleOrderSuccess"
                    @view-detail="handleViewDetail"
                >
                    <button @click="handleRecipeClick(order)" class="ml-4 px-2 py-1 bg-blue-500 text-white rounded">
                        Recipe
                    </button>
                </OrderCard>
            </section>
        </main>
    </div>

</template>