<template>
    <div
        class="flex justify-between items-center flex-col w-60 h-72 rounded-lg shadow-md bg-white"
    >
        <div class="w-full h-3/5 relative">
            <img
                :src="props.foodsData.imagePath"
                alt="Food Image"
                class="w-full h-full object-cover rounded-t-lg"
            />
            <div
                class="absolute inset-0 bg-gradient-to-b from-transparent to-white opacity-20 bottom-0"
            ></div>
        </div>
        <div
            class="flex flex-col items-center justify-center h-2/5 w-full gap-1"
        >
            <span class="font-bold">{{ props.foodsData.name }}</span>
            <span class="text-yellow-500"
                >{{
                    props.foodsData.price + props.foodsData.price * 0.07
                }}
                ฿</span
            >
            <div class="flex gap-2">
                <!-- ปุ่มเพิ่มลงตะกร้า -->
                <button
                    class="px-2 py-1 rounded-md bg-yellow-300 shadow-md hover:bg-yellow-500 duration-100"
                    :class="{
                        'opacity-20 cursor-not-allowed':
                            foodStore.getCartItemCount(props.foodsData.id) >=
                            props.foodsData.max,
                    }"
                    @click="addToCart(props.foodsData)"
                    :disabled="
                        foodStore.getCartItemCount(props.foodsData.id) >=
                        props.foodsData.max
                    "
                >
                    Add to Cart
                </button>

                <!-- ปุ่มเปิด recipe -->
                <button
                    class="px-2 py-1 rounded-md bg-blue-300 shadow-md hover:bg-blue-500 duration-100"
                    @click="showRecipe(props.foodsData.id)"
                >
                    Recipe
                </button>
            </div>
        </div>

        <!-- Popup Recipe -->
        <RecipePopup
            v-if="showRecipePopup"
            :foodId="selectedFoodId"
            @close="showRecipePopup = false"
        />
    </div>
</template>

<script setup>
import { ref } from 'vue'
import { foodsStore } from '@/stores/cart'
import RecipePopup from '@/components/RecipePopup.vue' // ต้องสร้างไฟล์นี้

const props = defineProps({
    foodsData: {
        type: Object,
        required: true,
    },
})

const foodStore = foodsStore()
const showRecipePopup = ref(false)
const selectedFoodId = ref(null)

const addToCart = (food) => {
    foodStore.addToCart(food)
}

// ฟังก์ชันเปิด popup
const showRecipe = (foodId) => {
    selectedFoodId.value = foodId
    showRecipePopup.value = true
}
</script>
