package Math;

public class Subgrade {
	
	public boolean toCalculate;
	
	public double grade;
	
	public double expectedGrade;
	
	public String name;
	
	public boolean isEmpty;
	
	public boolean isExpected;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public Subgrade(boolean toCalculate, double grade, double expectedGrade, String name) {
		super();
		this.toCalculate = toCalculate;
		this.grade = grade;
		this.expectedGrade = expectedGrade;
		this.name = name;
		this.isEmpty = (grade < 0);
		this.isExpected = (expectedGrade >= 0 && isEmpty);
	}
}