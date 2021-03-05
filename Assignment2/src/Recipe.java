
public abstract class Recipe implements Changeable {
	// Recipe class is made to be abstract class since I believe Recipe is the vital
	// core of my application.
	// Therefore, I believe "Recipe" is necessarily been retained uniquely in my
	// entire program. As evidence,
	// if this application is a collaborative workspace and I am working on Recipe
	// class, not using abstract when instantiate Recipe class is also an "usable"
	// option. To be more explicit, some of my coworkers may either incidentally or
	// intentionally damage my codes without even accessing my codes visually, so
	// 'abstract' assists me to prevent those harmful scenarios. By which the
	// security and irrespective level of my program are also evolved.
	private String recPath;

	public Recipe(String recPath) {
		this.recPath = recPath;
	}

	public String getRecPath() {
		return this.recPath;
	}

	public abstract void addIngredient(String name, int quant);

	public abstract void saveRecipe(String ingredientContents) throws Exception;

	public abstract void loadRecipe(String password) throws LoadRecipeException, BrandNewRecipe;
	// Abstract methods generate concrete structure which bend child classes into
	// invoked patterns, and assists their development efficiently. In addition,
	// they also provides the OS
	// with massive versatilities by effectively allowing fake bodies. One goal of
	// having a decent design is also to keep the program close to the reality, not
	// having stern methods of raising children is likely to end up with negative
	// results. Following the analogy, putting not abstract methods when they need
	// to be contribute adversely to the evolutionary patterns of the application in
	// general.
}
