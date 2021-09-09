package Math;

import java.util.ArrayList;

public class Grade {
	
	public double percent;
	
	public ArrayList<Subgrade> subgradeList;
	
	public String name;
	
	public Grade(double percent, ArrayList<Subgrade> subgradeList, String name) {
		this.percent = percent;
		this.subgradeList = subgradeList;
		this.name = name;
	}
	
	public static void main(String[] args) {

	}

	public int numGrades() {
		return subgradeList.size();
	}

}
