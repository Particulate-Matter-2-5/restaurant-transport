package ku.cs.restaurant.repository;

import ku.cs.restaurant.entity.Food;
import ku.cs.restaurant.entity.Recipe;
import ku.cs.restaurant.entity.RecipeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, RecipeKey> {
    List<Recipe> findByFood(Food food);
}
