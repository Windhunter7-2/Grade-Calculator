package Math;

import java.util.ArrayList;

public class Algorithm {

	public int gradeCutoffs [] = new int[11];

	//private double remainingPercent;

	private boolean areExpected;

	private double addKnown (ArrayList<Grade> grades)
	{
		double outval = 0.0;
		for(Grade g : grades) 
		{
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
			if (g.numGrades() > 0) 
			{
				totalProp = (double)numKnown / g.numGrades();
			}
			outval += g.percent * totalProp * gradeAverage;
		}
		return outval;
	}

	private double addExpected (ArrayList<Grade> grades)
	{
		double outval = 0.0;
		for(Grade g : grades) 
		{
			int numExpected = 0;
			double gradeAverage = 0.0;
			double totalProp = 0;
			for (Subgrade s : g.subgradeList) 
			{
				if (!s.toCalculate && s.isExpected && s.isEmpty)
				{
					numExpected++;
					gradeAverage += s.expectedGrade;
				}
			}
			if (numExpected > 0) 
			{
				gradeAverage /= numExpected;
			}
			if (g.numGrades() > 0) 
			{
				totalProp = (double)numExpected / g.numGrades();
			}
			outval += g.percent * totalProp * gradeAverage;
		}
		return outval;
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
			for (Subgrade s : g.subgradeList) 
			{
				if(s.isEmpty) 
				{
					outval += g.percent / g.numGrades();
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
			String x1 = String.format("0%%: %.2f", gradeNeeded(grades, cutoffProp, 0));
			String x2 = String.format("100%%: %.2f", gradeNeeded(grades, cutoffProp, percentRemaining(grades)));
			String x3 = "\n";
			if (areExpected) {
				x3 = x3 + String.format("Expected%%: %.2f", gradeNeeded(grades, cutoffProp, addExpected(grades) * percentRemaining(grades)));
			}
			System.out.println("Cutoff: " + i + "%\n" 
			+ x1 + "\n"
			+ x2
			+ x3 + "\n");
		}
	}

	/**
	 * Testing only.
	 * @param args unused.
	 */
	public static void main(String[] args) {
		Algorithm a = new Algorithm();
		a.areExpected = true;
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
