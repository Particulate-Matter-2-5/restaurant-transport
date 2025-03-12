package ku.cs.restaurant.service;

import ku.cs.restaurant.dto.recipe.IngredientQtyRequest;
import ku.cs.restaurant.entity.Food;
import ku.cs.restaurant.entity.Recipe;
import ku.cs.restaurant.entity.RecipeKey;
import ku.cs.restaurant.repository.FoodRepository;
import ku.cs.restaurant.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;
    private final FoodRepository foodRepository;

    public RecipeService(RecipeRepository recipeRepository, FoodRepository foodRepository, IngredientService ingredientService) {
        this.recipeRepository = recipeRepository;
        this.ingredientService = ingredientService;
        this.foodRepository = foodRepository;
    }

    public void createRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public void createRecipes(IngredientQtyRequest ingredients, Food createdFood) {
        ingredients.getIngredientMap().forEach(ingredient -> {
            ingredientService.findIngredientById(ingredient.getId()).ifPresentOrElse(
                    optionalIngredient -> {
                        Recipe recipe = new Recipe();
                        recipe.setId(new RecipeKey(createdFood.getId(), ingredient.getId()));
                        recipe.setQty(ingredient.getQuantity());
                        recipe.setIngredient(optionalIngredient);
                        recipe.setFood(createdFood);
                        this.createRecipe(recipe);
                    },
                    () -> {
                        throw new RuntimeException("Ingredient with ID " + ingredient.getId() + " not found.");
                    }
            );
        });
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipeById(RecipeKey id) {
        return recipeRepository.findById(id);
    }


    public Optional<Recipe> updateRecipe(RecipeKey id, int qty) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            recipe.setQty(qty);
            recipeRepository.save(recipe);
            return Optional.of(recipe);
        }
        return Optional.empty();
    }

    public void deleteRecipe(RecipeKey id) {
        recipeRepository.deleteById(id);
    }

    public List<Recipe> getRecipeByFoodId(UUID foodId) {
        Optional<Food> recordOptional = foodRepository.findById(foodId);
        if (recordOptional.isPresent()) {
            Food record = recordOptional.get();
            return recipeRepository.findByFood(record);
        }
        return Collections.emptyList();
    }
}
