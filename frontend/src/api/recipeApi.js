import apiClient from './index'


const recipeApi = {
    getRecipeByFoodId(foodId) {
        return apiClient.get(`/recipe/${foodId}`)
    },
}
export default recipeApi

