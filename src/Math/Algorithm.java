package Math;

import java.util.ArrayList;

public class Algorithm {
	
	public int gradeCutoffs [] = new int[11];
	
	private double remainingPercent;
	
	private boolean areExpected;
	
	private double addKnown (ArrayList<Grade> grades)
	{
		double outval = 0.0;
		for(Grade g : grades) {
			int numKnown = 0;
			double gradeAverage = 0.0;
			for (Subgrade s : g.subgradeList) {
				if (!s.isEmpty) {
					System.out.println(s.grade);
					numKnown++;
					gradeAverage += s.grade;
				}
			}
			gradeAverage /= numKnown;
			outval += g.percent * gradeAverage;
		}
		return outval;
	}
	
	private double addExpected (ArrayList<Grade> grades)
	{
		//TODO Cole
		return 0.0;
	}
	
	private double addToCalculate (ArrayList<Grade> grades)
	{
		//TODO Cole
		return 0.0;
	}
	
	//TODO Finalize
	public void runAlgorithm (ArrayList<Grade> grades)
	{
		System.out.println(addKnown(grades));
	}
	
	/**
	 * Testing only.
	 * @param args unused.
	 */
	public static void main(String[] args) {
		Algorithm a = new Algorithm();
		a.gradeCutoffs = new int[]{97, 93, 90, 87, 83, 80, 77, 73, 70, 60, 0};
		ArrayList<Grade> g = new ArrayList<Grade>();

		ArrayList<Subgrade> s1 = new ArrayList<Subgrade>();
		s1.add(new Subgrade(false, 0.8, -1.0, "Homework1"));
		s1.add(new Subgrade(false, 0.9, -1.0, "Homework2"));
		s1.add(new Subgrade(false, -1.0, 0.85, "Homework3"));
		s1.add(new Subgrade(true, -1.0, -1.0, "Homework4"));
		g.add(new Grade(0.7, s1, "Homeworks"));
		
		ArrayList<Subgrade> s2 = new ArrayList<Subgrade>();
		s2.add(new Subgrade(false, 0.82, -1.0, "Midterm"));
		s2.add(new Subgrade(true, -1.0, -1.0, "FinalExam"));
		g.add(new Grade(0.3, s2, "Tests"));
		a.runAlgorithm(g);
	}
}
