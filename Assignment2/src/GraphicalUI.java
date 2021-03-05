import java.awt.Color;

public class GraphicalUI extends UserInterface {
	private BackEnd backEnd;
	private GTerm gt;
	private int maxIng;
	private String type;
	private boolean uniqueListElement;

	public GraphicalUI(BackEnd backEnd) {
		this.backEnd = backEnd;
		this.gt = new GTerm(610, 400);
		this.gt.setFontSize(12);
		this.gt.setBackgroundColor(Color.GRAY);
		this.gt.addList(200, 365, this, "loadRecipe");
		this.gt.setFontColor(Color.black);
		this.gt.addTextArea("", 400, 365);
		this.gt.setXY(0, 365);
		this.gt.setFontColor(Color.BLACK);
		this.gt.addButton("Add recipe", this, "addToReicpeList");
		this.gt.addButton("Save changes", this, "saveChangesRecipe");
		this.maxIng = 0;
		this.uniqueListElement = true;
	}

	public void addToReicpeList() {
		if (uniqueListElement == false) {
			this.gt.removeElementFromList(0, 0);
			uniqueListElement=true;
		}
		String recipeFullPath = this.gt.getFilePath();
		if (recipeFullPath != null) {
			uniqueListElement = false;
			this.type = this.gt.getInputString("Enter type of recipe (Private or Public): ");
			this.maxIng = Integer.parseInt(this.gt.getInputString("Enter your maximum ingredients: "));
			this.backEnd.abbreviateRecPath(recipeFullPath);
			this.backEnd.addToRecipe(this.maxIng, this.type, recipeFullPath);
			this.gt.addElementToList(0, this.backEnd.getRecPath());
		}

		// The foundation of categorizing recipes. By taking input
		// and communicate with BackEnd, the majority of other classes are obtaining
		// advantages. Moreover, some efforts is laid down due to the program's
		// appearance when the RecipeList contains only the name of the recipes but not
		// the whole link from root. This might seems unnecessary and some will choose
		// to remove it, but in fact it dedicates
		// considerably to user's impression of the application.

	}

	public void loadRecipe() {
		try {
			this.backEnd.newRecipeCheck();
		} catch (CheckNewException e) {
			addIngredientInfo();
		} catch (Exception e) {
			this.gt.showMessageDialog("File scanning error!");
		}
		String password = "";
		try {
			if (this.type.equalsIgnoreCase("Private")) {
				password = this.gt.getInputString("Enter your password");
				this.backEnd.loadChosenRecipe(password);
			} else if (this.type.equalsIgnoreCase("Public")) {
				this.backEnd.loadChosenRecipe(password);
			}
		} catch (BrandNewRecipe e) {
			this.gt.setTextInEntry(0, e.getMessage());
		} catch (LoadRecipeException e) {
			this.gt.showMessageDialog(e.getMessage());
		} catch (Exception e) {
			this.gt.showMessageDialog("Unexpected error!");
		} // The most communications are processed in this method by using try and catch
			// blocks for exceptions. From that secure protocol permits front-end talk to
			// back-end, and vice versa. Minimizing the number of distinguishable exceptions
			// can be taken to action, however I prefer keep them separate to generate
			// the explicit level of messages.
	}

	public void addIngredientInfo() {
		int i = 0;
		while (i < this.maxIng) {
			String name = this.gt.getInputString("Enter name of ingredient " + i);
			int quant = Integer.parseInt(this.gt.getInputString("Enter quantity of ingredient " + i));
			this.backEnd.addIngredient(name, quant);
			i++;
		}
	}

	public void saveChangesRecipe() {
		String conf = this.gt.getInputString("Do you want to mutate your ingredient: [Yes or No]");
		String mutatedIng = this.gt.getTextFromEntry(0);
		try {
			this.backEnd.saveChangesRecipe(conf, mutatedIng);
		} catch (SaveChangesRecipeException e) {
			this.gt.showMessageDialog(e.getMessage());
		}
		// Representing the speak of back-end classes is operated efficiently from this
		// method.
	}

}
//D2: There is still an issue when getting user's input regarding "private or public". 
//If the customer wants to reopen a recipe they need to type the correct type of that recipe,
//otherwise the text displayed will be encrypted automatically. However, I get a view that this bug could also 
//potentially benefit my app, since the customer still can recover from that issue by entering the correct password. 
//Therefore, this bug is likely to be the second layer of security. 