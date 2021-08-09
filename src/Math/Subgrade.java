package Math;

public class Subgrade {
	
	public boolean toCalculate;
	
	public double grade;
	
	public double expectedGrade;
	
	public String name;
	
	public boolean isEmpty;
	
	public boolean isExpected;

	/**
	 * Constructors for testing purposes only.
	 */
	public Subgrade(boolean toCalculate, double grade, double expectedGrade, String name) {
		this.toCalculate = toCalculate;
		this.grade = grade;
		this.expectedGrade = expectedGrade;
		this.name = name;
		this.isEmpty = (grade == -1.0);
		this.isExpected = (expectedGrade != -1.0);
	}
	
	public Subgrade(double grade) {
		this(false, grade, -1.0, "");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
