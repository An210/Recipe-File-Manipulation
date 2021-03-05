import java.io.*;

public class BackEnd {
	private Recipe[] recs;
	private int currentRec;
	private RecipePath recPath;
	// Object member variables are explicitly declared being private, non-static and
	// there are no = (equal) signs.

	public BackEnd() {
		this.recs = new Recipe[10];
		this.currentRec = 0;
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

	private void addToPublicRecipe(int maxIng, String recPath) {
		PublicRecipe addedPublicRecipe = null;
		if (this.currentRec < this.recs.length) {
			addedPublicRecipe = new PublicRecipe(maxIng, 0, recPath);
			this.recs[this.currentRec] = addedPublicRecipe;
			this.currentRec++;
		}
	}

	public void addToRecipe(int maxIng, String type, String recPath) {
		if (type.equalsIgnoreCase("Private")) {
			addToPrivateRecipe(maxIng, recPath);
		} else if (type.equalsIgnoreCase("Public"))
			addToPublicRecipe(maxIng, recPath);
	}
	// This addToRecipe method of BackEnd plays a crucial role in improving security
	// of information being processed from the back-end side. Both addToPublicRecipe
	// and addToPrivateRecipe are protected from data leaking by 'private'. Despite
	// this addToRecipe can be omitted and GraphicalUI can still using directly
	// addToPrivateRecipe and addToPublicRecipe to transfer infor. Keeping these 2
	// methods 'public' is not the best optimization.

	private void addToPrivateRecipe(int maxIng, String recPath) {
		PrivateRecipe addedPrivateRecipe = null;
		if (this.currentRec < this.recs.length) {
			addedPrivateRecipe = new PrivateRecipe(maxIng, 0, recPath);
			this.recs[this.currentRec] = addedPrivateRecipe;
			this.currentRec++;
		}
	}

	public void abbreviateRecPath(String recipeFullPath) {
		this.recPath = new RecipePath(recipeFullPath);
	}

	public RecipePath getRecPath() {
		return this.recPath;
	}

	public void addIngredient(String name, int quant) {
		this.recs[currentRec - 1].addIngredient(name, quant);
	}

	public void loadChosenRecipe(String password) throws LoadRecipeException, BrandNewRecipe {
		this.recs[this.currentRec - 1].loadRecipe(password);
	}

	public void newRecipeCheck() throws CheckNewException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(this.recPath.getRecipePath()));
		String line = br.readLine();
		if (line == null) {
			throw new CheckNewException();
		}
	}

	public void saveChangesRecipe(String conf, String mutatedIng) throws SaveChangesRecipeException {
		this.recs[this.currentRec - 1].saveChangesRecipe(conf, mutatedIng);
	}
	// These methods are conveniently communicational protocol between GraphicalUI
	// to back-end classes via BackEnd. Preventing users from access unauthorized
	// pieces of information is dramatically important regarding both user's
	// conveniences and security issues of the app. Other classes methods can
	// possibly be used by GraphicalUI, but this method will cause culprits to
	// proram's devastation. Therefore, indirect messages are mandatory. And
	// Exception is main way of communication between different classes of my
	// program.

}
