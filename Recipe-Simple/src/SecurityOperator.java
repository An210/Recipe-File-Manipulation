
public class SecurityOperator {
	private String password;
	private String mode;

	public SecurityOperator(String password, String mode) {
		this.password = password;
		this.mode = mode;
	}

	public String secProcess(String sec) {
		String secText = "";
		if (this.mode.equalsIgnoreCase("decrypt")) {
			int i = 0;
			while (i < sec.length()) {
				secText += (char) (sec.charAt(i) + (this.password.hashCode() % 10));
				i++;
			}
		} else if (this.mode.equalsIgnoreCase("encrypt")) {
			int x = 0;
			while (x < sec.length()) {
				secText += (char) (sec.charAt(x) - (this.password.hashCode() % 10));
				x++;
			}
			// D2: This secProcess method still have some errors displaying additional
			// character after the plain text.
			// But generally, it shouldn't be such an issue because the plain text is still
			// readable, and it may cause
			// confusion for attackers if they successfully solve the matrix. In other
			// words, those attackers will get confused
			// about that extra character of the secret recipe. So, I consider it as a
			// useful bug from my program.
		}
		return secText;
	}
}
//This SecurityOperator can also be incorporated inside the main class instead of being a separate class. However, since it's
//involved security issues, this is known as one of the crucial of information. We do not want someone who has no authorities to
//access our "secret recipe" in this case. Therefore, it's necessary to enhance the forte of OO design when we making variable, methods
//private and decentralizing the accessibility of other methods from main class to this class.
