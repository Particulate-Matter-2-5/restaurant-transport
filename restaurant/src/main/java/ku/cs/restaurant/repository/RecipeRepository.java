package ku.cs.restaurant.repository;

import ku.cs.restaurant.entity.Recipe;
import ku.cs.restaurant.entity.RecipeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, RecipeKey> {
}
