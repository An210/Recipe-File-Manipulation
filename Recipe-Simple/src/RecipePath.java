
public class RecipePath {
	private String recipeFullPath;

	public RecipePath(String recipeFullPath) {
		this.recipeFullPath = recipeFullPath;
	}

	public String getRecipePath() {
		return this.recipeFullPath;
	}

	public String toString() {
		String recipePath = "";
		if (this.recipeFullPath.lastIndexOf("/") >= 0)
			recipePath = this.recipeFullPath.substring(this.recipeFullPath.lastIndexOf("/") + 1);
		else if (this.recipeFullPath.lastIndexOf("\\") >= 0)
			recipePath = this.recipeFullPath.substring(this.recipeFullPath.lastIndexOf("\\") + 1);
		return recipePath;
	}
}
//As demonstrated in the main class, this RecipePath class can be mutated as a method into the main class. But it is better to 
//have a separate class like this, since it is not good to cram into main class. And it's easier to maintain and test proper code.