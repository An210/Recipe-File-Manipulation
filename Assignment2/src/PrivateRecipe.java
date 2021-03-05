import java.io.*;

public class PrivateRecipe extends Recipe {
	// Since PrivateRecipe is arguably a child class of Recipe, and PrivateRecipe
	// also has it's own functionalities which can be differentiated from it's super
	// class. Therefore, having PrivateRecipe as a child class using inheritance and
	// polymorphism concepts is a crucial part of my program's OO design. Besides,
	// As I believe in near future my application is going to be extended
	// considerably, so the increment of flexibility regarding having the parent
	// type reference and to keep adding new child classes in convenient manner are
	// also my top concerns. Moreover, decent design also takes chief
	// responsibilities in maintaining data efficiently and keeping operations
	// organized.
	// On the other hand, PrivateRecipe can be a stand alone part, but this is
	// certainly going to lead to discrete structure of the OS, especially when it's
	// data is broaden massively. The lack of connections designs renders
	// consequently painful data controlling.

	private SecurityOperator secOp;
	private String password;
	private Ingredient ings[];
	private int currentIng;
	private int maxIng;
	// Object member variables are explicitly declared being private, non-static and
	// there are no = (equal) signs.
	// On the other hand, not private object member variables will still work
	// but this lead to unwanted access from other classes and potential issues of
	// the program's consistency and security. Besides, I can still declare other
	// variables from below methods as
	// object members variables and the program will still be operated, however this
	// will leads to redundancy of accessibility
	// from other methods. Because, some information should only belongs to one
	// block, so I need to select just only crucial
	// variables which should last for the entire lifetime of the object which can
	// be accessed by other methods.

	public PrivateRecipe(int maxIng, int currentIng, String recPath) {
		super(recPath);
		this.secOp = new SecurityOperator(this.password, null);
		this.password = "";
		this.currentIng = currentIng;
		this.maxIng = maxIng;
		this.ings = new Ingredient[this.maxIng];
		// All member variables, arrays, etc. are explicitly initialized in the
		// constructor because the responsibility of
		// the constructor is to construct the program. Therefore, it's necessary to
		// ensure that no object member variables
		// and other crucial information are uninitialized after the constructor
		// finished. Alternatively, some of these can potentially
		// be uninitialized and the program is not prevented from usual execution,
		// nevertheless this will lead to the sporadical program
		// and potential vulnerabilities.

	}

	public void loadRecipe(String password) throws LoadRecipeException, BrandNewRecipe {
		String recipeContents = "";
		boolean firstEnter = false;
		if (super.getRecPath() != null) {
			this.password = password;
		}
		this.secOp = new SecurityOperator(this.password, "decrypt");
		// secOp takes an important value in the diversity of Recipes, since it's the
		// noticeable reason of how PrivateRecipe can be outstanding comparing to it's
		// parent and sibling. And one more time, the advantage from 'child' keeping
		// it's secOp function securely since PrivateRecipe has highest authority of
		// directly access Security Operator.
		try {
			BufferedReader br = new BufferedReader(new FileReader(super.getRecPath()));
			String line = br.readLine();
			if (line == null) {
				firstEnter = true;
				String ingredientContents = "";
				int index = 0;
				while (index < this.currentIng) {
					ingredientContents += "Ingredient: " + this.ings[index].getName() + ", Quantity: "
							+ this.ings[index].getQuant() + "\n";
					index++;
				}
				recipeContents += ingredientContents;
				saveRecipe(recipeContents);
				// This saveRecipe method is uniquely created to support the automatic saving of
				// initial
				// input from user. If this method is removed
				// the app will still executes but it loses one of the most useful feature
				// related to first data entry. As evidence, the
				// OS will display the first input from user but just only for one time.
				// Alternatively, I can also combine saveChangesRecipe()
				// and saveRecipe(String ingredientContents) together, but this will require
				// user to save the input automatically
				// or manually (by click on the button).
				// - If there is only automatic saving the user is more likely to encounter
				// mistakes while enter the input
				// leading to high chance of losing unsaved data
				// - If there is only manual saving, after enter the first input, the user may
				// expect the app to save it automatically,
				// by which they might lose their initial data by not clicking on the button to
				// save manually.
			}
			while (line != null) {
				recipeContents += line + "\n";
				line = br.readLine();
			}
			br.close();

		} catch (Exception e) {
			throw new LoadRecipeException("Error! Unable to detect recipe. Please create a new recipe!");
		}
		if (firstEnter == true) {
			throw new BrandNewRecipe(recipeContents);
		} else {
			throw new BrandNewRecipe(this.secOp.secProcess(recipeContents));
		} // This exception are used to contain recipe's contents. Since exceptions are
			// being utilized in my app for the sake of communication, this BrandNewRecipe
			// exception is one of the most vital piece of data.

	}

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
		// This method is used as radical foundation for an interaction between an user
		// and the system, it operate information in terms of
		// the ingredient class's information. BackEnd is beneficial from this method
		// and PrivateRecipe is able to gain essential infor via BackEnd.
	}

	public void saveRecipe(String ingredientContents) {
		try {
			this.secOp = new SecurityOperator(this.password, "encrypt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(super.getRecPath()));
			bw.write(this.secOp.secProcess(ingredientContents));
			bw.close();
		} catch (Exception e) {
			// No exception is caught here since if there is any error, it presumably
			// belongs to internal data administration. Alternatively, exception can also be
			// presented to the user, however I prefer keeping it confidential to both avoid
			// user's confusion and achieve conveniences in fixing issues.

		}

	}
	// As a consequence of abstract methods from the super class, the child classes
	// in general or PrivateRecipe to be specific are beneficial and protected from
	// indirectional pathways of developing process. Consolidating the foundation of
	// nurturing is conducted from the support of abstract methods.

	public void saveChangesRecipe(String conf, String mutatedIng) throws SaveChangesRecipeException {
		this.secOp = new SecurityOperator(this.password, "encrypt");
		if (conf != null) {
			if (conf.equalsIgnoreCase("yes")) {
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(super.getRecPath()));
					bw.write(this.secOp.secProcess(mutatedIng));
					bw.close();
				} catch (Exception e) {
					throw new SaveChangesRecipeException("Error! Unble to save changes");
				}
			} else if (conf.equalsIgnoreCase("no")) {
				throw new SaveChangesRecipeException("No changes have been made");
			} // SaveChangesRecipeException is another brutal piece of information in order to
				// support user's control over the app. Byy providing confirmed text, this
				// exception assists user to overcome the difficulties while processing changes
				// of their recipe
		} // As mentioned, this method is for the convenience of user's interaction. But
			// being 1 part of the 'pattern', I believe this is the mutual developing phase
			// of the whole 'family'. Since this is forced by the parent. not having it is
			// not an option, but facilitating the sake to evolve is what should be taken
			// under the consideration. Difference is great, but keeping connections or
			// similarities between classes is also important.

	}
	// Other exceptions are for the conveniences when there are unprecedented error
	// from the OS. Keeping users satisfaction as the app's first priority is the
	// core here.
}
