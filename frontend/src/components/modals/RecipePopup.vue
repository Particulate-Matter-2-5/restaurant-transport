<template>
    <div
        class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50"
    >
        <div class="bg-white p-6 rounded-lg shadow-lg w-96">
            <h2 class="text-xl font-bold mb-4">Recipe</h2>
            <ul v-if="recipe.length > 0">
                <li
                    v-for="ingredient in recipe"
                    :key="ingredient.id.ingredientId"
                    class="flex justify-between"
                >
                    <span>{{ ingredient.ingredient.name }}</span>
                    <span class="text-gray-500">Qty: {{ ingredient.qty }}</span>
                </li>
            </ul>
            <p v-else>Loading...</p>
            <button
                class="mt-4 bg-red-500 text-white px-4 py-2 rounded-md"
                @click="$emit('close')"
            >
                Close
            </button>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import recipeApi from '@/api/recipeApi.js'

const props = defineProps({
    foodId: {
        type: String,
        required: true,
    },
})

const recipe = ref([])

const fetchRecipe = async () => {
    try {
        const { data } = await recipeApi.getRecipeByFoodId(props.foodId)
        recipe.value = data.data
    } catch (error) {
        console.error('Error fetching recipe:', error)
    }
}

watch(() => props.foodId, fetchRecipe)

onMounted(fetchRecipe)
</script>
