
public interface Changeable {
	public abstract void saveChangesRecipe(String conf, String mutatedIng) throws SaveChangesRecipeException;
	// This Changeable is padded in order to create conveniently mechanism when
	// users interact with the application. By having extra capabilities or
	// functionalities, the app's sake is thriven with new colors. As a result, it
	// can achieve more goals not only from back end site but also front-end
	// concerns, especially from user center design concepts. Conversely, some may
	// argues that the OS will still work fine without this interface, however fine
	// seems to be not enough. Keeping the app constantly developed forces the
	// application to be extended, therefore additional features offered by
	// interfaces is inevitable.
}
