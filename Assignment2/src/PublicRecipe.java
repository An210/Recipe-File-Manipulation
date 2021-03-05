import java.io.*;

public class PublicRecipe extends Recipe {
	// Similarly to PrivateRecipe, the same mechanism of facilitating polymorphism
	// is applied for PublicRecipe. However, from my point of view PublicRecipe is
	// keeping closer to it's super class in terms of it's functionalities. As a
	// child class achieving different behavior in comparison with Recipe and
	// PrivateRecipe, PublicRecipe makes it way to become one the indispensable
	// components in my design at the moment. Staying simple but efficient, this
	// class continuously devotes to the the variety of recipes. Therefore, keeping
	// these "Recipes" classes distinguishable is completely mandatory.
	// Additionally, insecurity and complexity in data management is remarkable
	// concerns in digital worlds, keeping PublicRecipe separate from it's parents
	// is presumably a vulnerable option.
	private Ingredient ings[];
	private int currentIng;
	private int maxIng;

	public PublicRecipe(int maxIng, int currentIng, String recPath) {
		super(recPath);
		this.maxIng = maxIng;
		this.ings = new Ingredient[this.maxIng];

	}

	public void loadRecipe(String password) throws LoadRecipeException, BrandNewRecipe {
		String recipeContents = "";

		try {
			BufferedReader br = new BufferedReader(new FileReader(super.getRecPath()));
			String line = br.readLine();
			if (line == null) {

				String ingredientContents = "";
				int index = 0;
				while (index < this.currentIng) {
					ingredientContents += "Ingredient: " + this.ings[index].getName() + ", Quantity: "
							+ this.ings[index].getQuant() + "\n";
					index++;
				}
				recipeContents += ingredientContents;
				saveRecipe(recipeContents);
			}
			while (line != null) {
				recipeContents += line + "\n";
				line = br.readLine();
			}
			br.close();

		} catch (Exception e) {
			throw new LoadRecipeException("Error! Unable to detect recipe. Please create a new recipe!");
		}

		throw new BrandNewRecipe(recipeContents);
		// As usual, BrandNewRecipe of PulbicRecipe is kept simplest but still has
		// indispensable impacts on the program.

	}// This loadRecipe is the chief different functionality of PublicRecipe.
		// Contents are being processed as simply as possible without any encryption or
		// decryption
		// requirements. However, the simplicity is also a forte of this class relating
		// to the app's design. Clients are able to be specified, such as free accounts
		// or premium accounts whose access being decentralized appropriately.

	public void addIngredient(String name, int quant) {
		if (this.currentIng < this.maxIng) {
			this.ings[this.currentIng] = new Ingredient(name, quant);
			this.currentIng++;
		}
		int x = 0;
		while (x < this.currentIng) {
			int z = 0;
			while (z < this.currentIng) {
				if (this.ings[x].getName().equalsIgnoreCase(this.ings[z].getName()) && z != x) {
					int increasedQuant = this.ings[z].getQuant();
					Ingredient[] newIngs = new Ingredient[this.currentIng--];
					int i = 0;
					while (i < this.currentIng) {
						newIngs[i] = this.ings[i];
						i++;
					}
					this.ings = newIngs;
					this.ings[z] = this.ings[this.currentIng - 1];
					this.ings[x] = new Ingredient(this.ings[x].getName(), this.ings[x].getQuant() + increasedQuant);
				}
				z++;
			}
			x++;
		}
	}

	public void saveRecipe(String ingredientContents) {
		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter(super.getRecPath()));
			bw.write(ingredientContents);
			bw.close();
		} catch (Exception e) {

		}
	}
	// Other methods here are pretty similar to methods in PrivateRecipe cause they
	// are arguably 'siblings'. Therefore, resemblances are considered as usual
	// nature here.

	public void saveChangesRecipe(String conf, String mutatedIng) throws SaveChangesRecipeException {

		if (conf != null) {
			if (conf.equalsIgnoreCase("yes")) {
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(super.getRecPath()));
					bw.write(mutatedIng);
					bw.close();
				} catch (Exception e) {
					throw new SaveChangesRecipeException("Error! Unble to save changes");
				}
			} else if (conf.equalsIgnoreCase("no")) {
				throw new SaveChangesRecipeException("No changes have been made");
			}
		}

	}
}
