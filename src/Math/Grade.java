package Math;

import java.util.ArrayList;

public class Grade {
	
	//public int numGrades;
	
	public double percent;
	
	public ArrayList<Subgrade> subgradeList;
	
	public String name;
	
	//Constructors for testing only
	public Grade(double percent, ArrayList<Subgrade> subgradeList, String name) {
		this.percent = percent;
		this.subgradeList = subgradeList;
		//this.numGrades = subgradeList.size();
		this.name = name;
	}
	
	public int numGrades() { //May be a substitution for numGrades variable, assuming numGrades is always supposed to be the total number of subgrades.
		return subgradeList.size();
	}
	public static void main(String[] args) {

	}

}
