public interface RecipeIngredientRepository
        extends JpaRepository<RecipeIngredient, Long> {

    List<RecipeIngredient> findByMenuItemId(Long menuItemId);

    boolean existsByMenuItemId(Long menuItemId);

    @Query("SELECT COALESCE(SUM(r.quantityRequired), 0) FROM RecipeIngredient r WHERE r.ingredient.id = :ingredientId")
    Double getTotalQuantityByIngredientId(Long ingredientId);
}
