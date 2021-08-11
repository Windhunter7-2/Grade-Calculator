package Menus;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;

import External.MainDirectoryInfo;
import Files.Parsing;
import Files.ReadingWriting;
import Math.Algorithm;
import Math.Grade;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {
	
	private String baseFolder;
	
	/**
	 * The name of the current class.
	 */
	private String className;
	
	private ReadingWriting fileHandler;
	
	private void save (ArrayList<Grade> grades)
	{
		//TO DO
	}
	
	private void startGUI ()
	{
		//Instantiate baseFolder and fileHandler
		MainDirectoryInfo md = new MainDirectoryInfo();
		baseFolder = (md.getMainDirectory() + "Grade-Calculator/");
		fileHandler = new ReadingWriting(baseFolder);
		
		//TO DO
	}
	
	private void createClassGUI ()
	{
		//TO DO
	}
	
	private void mainGUI (ArrayList<Grade> grades, Stage s)
	{
		//TO DO
		/*
		 * This is the main GUI of the program (See “GUI Diagrams” for details on layout and additional buttons).
		 * It first does fileHandler.readFromFile() and passing that to Parsing.stringToInt().
		 */
		
		//Get the Grade Cutoffs
		String numbers = fileHandler.readFromFile(className, 1);
		Parsing parser = new Parsing();
		parser.stringToInt(numbers);
		Algorithm algorithm = parser.algorithm;
		
		//Construct Grade Cutoffs Panel
		s.setTitle("Class: " + className);
		
		//Testing, Making Sure JavaFX Started Properly (For Me, I Mean)
		Button test = new Button("Example");
		VBox test2 = new VBox();
		test2.getChildren().add(test);
		Scene scene = new Scene(test2, 700, 500);
		s.setScene(scene);
		
		/*
		 * Must be O(n), where n is the number of grades in total (Including sub-grades)
		 * 
		 * It then constructs
		 * the GUI, based on the information stored in Algorithm.gradeCutoffs and in the ArrayList grades, which
		 * holds the details to the grades themselves. (Look at the “Grades” class to see the similarities between
		 * the GUI and the variables in the class) Something of extra note here is that Subgrade.isEmpty is mainly
		 * intended for the GUI code, in that, if this is false, don’t do anything special, but if it’s true, leave
		 * the textbox blank for that respective Subgrade. Do the same thing with Subgrade.isExpected for the
		 * “Expected” grade(s), as well. If the “Save & View” button is pressed, it will call save() and then
		 * Algorithm.runAlgorithm(), using as the parameter a reconstructed ArrayList<Grade>, based on what’s put
		 * into the textboxes. If the “Save & Exit” button is pressed, it calls save() in the exact same manner,
		 * but will then, instead of running the algorithm, just call System.exit().
		 */
	}

	/**
	 * Initializes the program. 
	 * @param nullStage A dummy parameter
	 */
	public void start(Stage nullStage)
	{
		GUI start = new GUI();
		start.startGUI();
		
		start.className = "EXE 777";	//"EXE" for "Example Class"
		Stage s = new Stage();
		s.show();
		ArrayList<Grade> test = new ArrayList<Grade>();
		start.mainGUI(test, s);
	}
	
	/**
	 * The main method. Runs the program.
	 * @param args Commandline arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
