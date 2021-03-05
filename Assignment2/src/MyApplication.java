public class MyApplication {
	private BackEnd backEnd;
	private UserInterface userInterface;

	public MyApplication() {
		this.backEnd = new BackEnd();
		this.userInterface = new GraphicalUI(this.backEnd);
		this.userInterface = new ConsoleUI(this.backEnd);
	}

	public static void main(String[] args) {
		MyApplication myApp = new MyApplication();
	}
}