
import java.util.Scanner;

public class ConsoleUI extends UserInterface {
	private BackEnd backEnd;

	private int maxIng;
	private String type;
	private Scanner sc;

	public ConsoleUI(BackEnd backEnd) {
		this.backEnd = backEnd;
		this.sc = new Scanner(System.in);
		this.maxIng = 0;
		getIngredient();

	}

	public void getIngredient() {
		System.out.print("[1] Add" + "\n" + "[2] Save changes" + "\n" + ">>");
		Integer message = this.sc.nextInt();
		while (message != null) {
			if (message == 1) {
				addToReicpeList();
			} else if (message == 2) {
				saveChangesRecipe();
			}
			System.out.print("[1] Add" + "\n" + "[2] Save changes" + "\n" + ">>");
			message = this.sc.nextInt();
		} // getIngredient() holds responsibilities in displaying the menu by which input
			// from an user regarding designated instructions. And this function uses the
			// while loop instead of other conditional statements such as if statement, in
			// order to generate a iterative process preventing reinventing the wheel. In
			// other words, user can continuously give out instructions without reboot the
			// application. Therefore, while loop in this scenario assists with the usage
			// flexibility when the user interacts with the OS.
	}

	public void addToReicpeList() {
		System.out.print("Enter type of recipe recipe full path: " + "\n" + ">>");
		String recipeFullPath = this.sc.next();
		if (recipeFullPath != null) {
			System.out.print("Enter type of recipe (Private or Public): " + "\n" + ">>");
			this.type = this.sc.next();
			if (this.type != null) {
				System.out.print("Enter your maximum ingredients: " + "\n" + ">>");
				this.maxIng = this.sc.nextInt();
				this.backEnd.abbreviateRecPath(recipeFullPath);
				this.backEnd.addToRecipe(this.maxIng, this.type, recipeFullPath);
				System.out.println(">>>>>" + this.backEnd.getRecPath() + "<<<<<");
				loadRecipe();
			}
		} // After the instruction is executed, information relating to recipes,
			// ingredients, etc. is requested. 'this.sc.nextInt()' is utilized to ensure the
			// toleration and error prevention features are implemented. Since it requires
			// the user to enter the corresponding type of value (integer in this case).
			// Alternatively, 'this.sc.next()' can potentially replace 'this.sc.nextInt()'
			// nevertheless it generates possibilities of system failure involving
			// incompatible input.
	}

	public void loadRecipe() {
		try {
			this.backEnd.newRecipeCheck();
		} catch (CheckNewException e) {
			addIngredientInfo();
		} catch (Exception e) {
			System.out.print("File scanning error!");
		}

		try {
			if (this.type.equalsIgnoreCase("Private")) {
				System.out.print("Enter your password" + "\n" + ">>");
				String password = this.sc.next();
				this.backEnd.loadChosenRecipe(password);

			} else if (this.type.equalsIgnoreCase("Public")) {
				this.backEnd.loadChosenRecipe("");
			}
		} catch (BrandNewRecipe e) {
			System.out.println(e.getMessage() + "\n");
		} catch (LoadRecipeException e) {
			System.out.println(e.getMessage() + "\n");
		} catch (Exception e) {
			System.out.println("Unexpected error!" + "\n");
		} // This method using conditional statements to determine which recipe is being
			// represented to the user in the similar mechanism with GUI.
	}

	public void addIngredientInfo() {
		int i = 0;

		while (i < this.maxIng) {
			System.out.print("Enter name of ingredient " + i + "\n" + ">>");
			String name = this.sc.next();
			System.out.print("Enter quantity of ingredient " + i + "\n" + ">>");
			int quant = this.sc.nextInt();
			this.backEnd.addIngredient(name, quant);
			i++;
		}
	}// Besides of 'addToReicpeList()', this function aims to retrieve more insights
		// of ingredient information. Potentially, this 'addIngredientInfo()' can be
		// integrated in the 'addToReicpeList()', nevertheless it'd better to separate
		// those two in my opinion. Since it's good to consolidate with basic foundation
		// from 'addToReicpeList()', henceforth requests more explicit information of
		// ingredients in this function.

	public void saveChangesRecipe() {
		System.out.print("Please enter content: " + "\n" + ">>");
		String mutatedIng = this.sc.next();
		System.out.print("Do you want to mutate your ingredient: [Yes or No]" + "\n" + ">>");
		String conf = this.sc.next();
		try {
			this.backEnd.saveChangesRecipe(conf, mutatedIng);
			System.out.println("");
		} catch (SaveChangesRecipeException e) {
			System.out.println(e.getMessage() + "\n");
		} // Requests of confirmation of saving changes from user's perspective is
			// conducted via this method. Some minor changes are added, typically
			// 'System.out.println("")' supports aesthetic app's appearance. Cutting to the
			// essence, these changes can be eliminated with no impacts to the chief
			// features, however increasing user's satisfaction is one of the main priorities.

	}

}
