package Math;

import java.util.ArrayList;

public class Algorithm {

	public int gradeCutoffs [] = new int[11];

	//private double remainingPercent;

	//private boolean areExpected;

	private double addKnown (ArrayList<Grade> grades)
	{
		double outval = 0.0;
		for(Grade g : grades) 
		{
			if (g.numGrades() > 0) {
				int numKnown = 0;
				double gradeAverage = 0;
				double totalProp = 0;
				for (Subgrade s : g.subgradeList) 
				{
					if (!s.isEmpty) 
					{
						numKnown++;
						gradeAverage += s.grade;
					}
				}
				if (numKnown > 0) 
				{
					gradeAverage /= numKnown;
				}
				totalProp = (double)numKnown / g.numGrades();			
				outval += g.percent * totalProp * gradeAverage;
			} else { 
				
			}
		}
		return outval;
	}

	private double addExpected (ArrayList<Grade> grades)
	{
		double countA = 0.0;
		double countB = 0.0;
		for(Grade g : grades) 
		{	
			double curWeight = g.numGrades() > 0 ? g.percent / g.numGrades() : 0;
			for (Subgrade s : g.subgradeList) 
			{
				if (!s.toCalculate && s.isEmpty)
				{
					if (s.isExpected) {
						countB += curWeight * s.expectedGrade;
					}
					countA += curWeight;
				}
			}
		}
		return countA > 0 ? percentRemaining(grades) * countB / countA : 0;
	}

	private double addToCalculate (ArrayList<Grade> grades)
	{
		double outval = 0.0;
		for(Grade g : grades) 
		{
			int numToCalc = 0;
			double totalProp = 0;
			for (Subgrade s : g.subgradeList) 
			{
				if (s.toCalculate)
				{
					numToCalc++;
				}
			}
			if (g.numGrades() > 0) 
			{
				totalProp = (double) numToCalc / g.numGrades();
			}			
			outval += g.percent * totalProp;
		}
		return outval;
	}

	/**
	 * Calculates the percent of assignments yet to be graded.
	 * @param grades
	 * @return
	 */
	private double percentRemaining(ArrayList<Grade> grades)
	{
		double outval = 0.0;
		for (Grade g : grades) 
		{
			if (g.numGrades() == 0) {
				outval += g.percent;
			} else {
				for (Subgrade s : g.subgradeList) 
				{	
					if(s.isEmpty && !s.toCalculate) 
					{
						outval += g.percent / g.numGrades();
					}
				}
			}
		}
		return outval;
	}

	private double gradeNeeded(ArrayList<Grade> grades, double cutoff, double x)
	{
		return (cutoff - addKnown(grades) - x) / addToCalculate(grades);
	}

	//TODO Finalize
	public void runAlgorithm (ArrayList<Grade> grades)
	{
		for(int i : gradeCutoffs) {
			double cutoffProp = (double) i / 100.0;
			String x1 = String.format("0%%: %.3f", gradeNeeded(grades, cutoffProp, 0));
			String x2 = String.format("100%%: %.3f", gradeNeeded(grades, cutoffProp, percentRemaining(grades)));
			String x3 = String.format("Expected%%: %.3f", gradeNeeded(grades, cutoffProp, addExpected(grades)));

			System.out.println("Cutoff: " + i + "%\n" 
			+ x1 + "\n"
			+ x2 + "\n"
			+ x3 + "\n");
		}
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
		g.add(new Grade(0.3, s2, "Tests"));
		a.runAlgorithm(g);
	}
}
