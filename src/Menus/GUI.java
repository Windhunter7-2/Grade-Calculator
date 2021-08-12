package Menus;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import External.MainDirectoryInfo;
import Files.ReadingWriting;
import Math.Grade;


public class GUI extends Application {
	
	private String baseFolder;
	
	private ReadingWriting fileHandler;
	
	private void save (ArrayList<Grade> grades)
	{
		//TO DO
	}
	
	/** 
	 * Will call fileHandler to load the class file,
	 * pass it onto Parsing's stringToGrade, then pass it
	 * to the Main GUI.
	 * 
	 * @param s The current stage to be passed to main GUI.
	 */
	private void loadClass(Stage s) {
		
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open class file");
		chooser.showOpenDialog(s); // Returns a File object
		
		mainGUI(null, s);
	}
	
	private void startGUI ()
	{
		//baseFolder = MainDirectoryInfo.getMainDirectory() + "Grade-Calculator/";
		//fileHandler = new ReadingWriting(baseFolder);
		
		// ---------------
		// getMainDirectory produces exception since directory not found
		// Use JavaFX's file chooser to choose a directory?
		// ---------------
		
		// Creates the scene
		Stage s = new Stage();
        s.setTitle("Start GUI");
        VBox vb = new VBox();
        
        //********** Example GUI
        //HBox hb = new HBox(new Text(""));
        //TextField waitField = new TextField("0");
        //hb.getChildren().addAll(waitField);
        //Button submit = new Button("Submit Instruction");
        //submit.setOnMouseClicked(event -> s.hide());
        //vb.getChildren().addAll(hb, submit);
        //********** End of Example
        
        // Buttons Created
        Button createClass = new Button("Create New Class");
        Button loadClass = new Button("Update/View Existing Class");
        createClass.setOnMouseClicked(event -> createClassGUI(s) );
        loadClass.setOnMouseClicked(event -> loadClass(s) ); 
        
        // Should create some private helper methods for buttons
        
        // All Scenes added to VBox and Scene Set
        vb.getChildren().addAll(createClass, loadClass);
        s.setScene(new Scene(vb, 520, 620));
        s.show();
		
	}
	
	/**
	 * Helper method that will read a file using fileHandler
	 * and use Parsing's stringToGrades making an ArrayList of grades.
	 * It will then call fileHandler to pass whatever info was
	 * in the loaded file and pass it to Parsing's stringToInt() 
	 * 
	 * @param s Stage to be passed to main GUI.
	 */
	private void loadCutOffs(Stage s) {
		mainGUI(null, s);
	}
	
	/**
	 * Helper method that will produce an empty array list of grades,
	 * for the class, then initialize Algorithm's gradeCutoffs for the default values.
	 * Then, it will call Parsing's intToString and pass it to writeToFile.
	 * Then read that same file, pass to gradesToString(), then pass to writeToFile()
	 * and finally call main GUI.
	 * 
	 * @param s Stage to be passed to main GUI.
	 */
	private void newCutOffs(Stage s) {
		mainGUI(null, s);
	}
	
	private void createClassGUI (Stage s)
	{
		// Rather than recreating a stage,
		// we should pass the stage as a param
		// and set the scene instead
		
		// Creates the Stage
		s.setTitle("Create Class GUI");
		VBox vb = new VBox();
		
		Button loadCuts = new Button("Load Grade Cutoffs");
		Button newCuts = new Button("Create New Cuttoffs");
		
		loadCuts.setOnMouseClicked(event -> loadCutOffs(s) );
		newCuts.setOnMouseClicked(event -> newCutOffs(s) );
		
		// All Scenes added to VBox and Scene Set
		vb.getChildren().addAll(loadCuts, newCuts);
        s.setScene(new Scene(vb, 520, 620));
        s.show();
		
	}
	
	private void mainGUI (ArrayList<Grade> grades, Stage s)
	{
		//TO DO
	}
	
	@Override
	/**
	 * Method to run the GUI.
	 * 
	 * @param arg0 A stage we will not be using.
	 * @throws any relevant exception that may come up.
	 */
	public void start(Stage arg0) throws Exception {
		GUI runProgram = new GUI();
		runProgram.startGUI();
	}

	/**
	 * The main method. Runs the program.
	 * For JavaFX, this is a fallback method.
	 * 
	 * @param args Commandline arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
