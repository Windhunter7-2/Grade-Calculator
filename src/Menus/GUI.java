package Menus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.function.UnaryOperator;
import External.MainDirectoryInfo;
import Files.Parsing;
import Files.ReadingWriting;
import Math.Algorithm;
import Math.Grade;
import Math.Subgrade;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
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
	
	/**
	 * Helper global for mainGUI(ArrayList<Grade>, Stage), but can also be applied in other uses very easily.
	 * This is the size to make all the bold text on the main GUI.
	 */
	private static final int mainGUI_textSize = 16;
	
	/**
	 * Helper global for mainGUI(ArrayList<Grade>, Stage), but can also be applied in other uses very easily.
	 * This is the font itself for all the bold text on the main GUI.
	 */
	private static final Font mainGUI_bold = Font.font(Font.getDefault().getName(), FontWeight.BOLD,
			FontPosture.REGULAR, mainGUI_textSize);
	
	/**
	 * Helper method for mainGUI(ArrayList<Grade>, Stage), but can also be applied in other uses very easily.
	 * Creates a generic TextField for use in the main GUI, where the initial text is set to initialText, and
	 * it sets the format of the TextField to [0-9]*% only, and anything else will not work in the textbox.
	 * @param initialText The initial text to put in the TextField
	 * @return The created TextField
	 */
	private TextField mainGUI_genericField(String initialText)
	{
		//Create Textbox
		TextField gen_textField = new TextField(initialText)
		{
			//Make It Impossible to Add Non-Numbers
			@Override
			public void replaceText(int start, int end, String text)
			{
				//If Invalid Text, Ignore This Call!
				if ( !text.matches("[^0-9.%]") )
					super.replaceText(start,  end,  text);
			}
			@Override
			public void replaceSelection(String text)
			{
				//If Invalid Text, Ignore This Call!
				if ( !text.matches("^[0-9.%]") )
					super.replaceSelection(text);
			}
		};
		gen_textField.setMaxWidth(mainGUI_textSize * 4.2);	//Set Width Equal to Approx. (Width of Single Character[AKA 1.4]) * 3
		
		//Check Validity of Some Stuff
		class Validity
		{
			public boolean dotUsed;
			public boolean percentUsed;
		}
		Validity validity = new Validity();
		validity.dotUsed = false;
		validity.percentUsed = false;
		
		//Make It So Only Valid Numbers Occur, & % Cannot Be Deleted
		UnaryOperator<TextFormatter.Change> filter = c ->
		{
			//Make Numbers Valid (AKA Ignore More Than 1 Dot)
			String curText = c.getControlNewText();
			if ( c.getText().equals(".") )
			{
				if (validity.dotUsed == false)	//If Dot Not Used Yet
					validity.dotUsed = true;
				else
				{
					String temp = curText.replaceFirst("[.]", "9");
					if ( !temp.contains(".") )	//If Dot Previously Used but Not Currently There, Reset
						validity.dotUsed = false;
					else	//If Attempting 2 Or More Dots
						return null;
				}
			}
			
			//Proofread %'s
			String curChar = c.getText();
			if ( curChar.equals("%") )	//If % Typed by User
			{
				if ( c.getCaretPosition() != curText.length() )	//If % Is Not the Last Character, Don't Allow
					return null;
				else	//If % Is Last Character, Allow, But Restrict to Max of 1
				{
					if (validity.percentUsed == false)	//If % Not Used Yet
						validity.percentUsed = true;
					else
					{
						String temp = curText.replaceFirst("[%]", "9");
						if ( !temp.contains("%") )	//If % Previously Used But Not Currently There, Reset
							validity.percentUsed = false;
						else	//If Attempting 2 Or More %'s
							return null;
					}
				}
			}
			return c;
		};
		gen_textField.setTextFormatter( new TextFormatter<String>(filter) );
		
		//Set Tabbing to Be at the Beginning of the TextField
		return gen_textField;
	}
	
	/**
	 * Helper method for mainGUI(ArrayList<Grade>, Stage). Constructs the GUI form of a single grade cutoff,
	 * given its letter grade (e.g. A+) and the percent needed for that cutoff.
	 * @param letterGrade The letter grade for that cutoff (i.e. A+, A, A-, B+, ...)
	 * @param percentNeeded The percent needed to achieve that grade cutoff (e.g. 97 for an A+, 93 for an A, ...)
	 * @return The VBox containing the GUI form of a single grade cutoff
	 */
	private VBox mainGUI_grCut_Ea (String letterGrade, int percentNeeded)
	{
		//Create Letter Grade Symbol
		Text letterGrade_T = new Text(letterGrade);
		letterGrade_T.setFont(mainGUI_bold);	//Bold
		letterGrade_T.setUnderline(true);	//Underline
		
		//Create Textbox
		String percentNeeded_S = ("" + percentNeeded + "%");
		TextField percentNeeded_T = mainGUI_genericField(percentNeeded_S);
		percentNeeded_T.setAlignment(Pos.CENTER);
		percentNeeded_T.setTranslateY(5);
		
		//Add Items To VBox and Return
		VBox gradeCutoff = new VBox();
		gradeCutoff.getChildren().addAll(letterGrade_T, percentNeeded_T);
		gradeCutoff.setAlignment(Pos.CENTER);
		return gradeCutoff;
	}
	
	/**
	 * Helper method for mainGUI(ArrayList<Grade>, Stage). Does the actual construction itself of the
	 * "Grade Cutoffs" section of the main GUI.
	 * @param algorithmInst A reference to the currently defined grade cutoffs
	 * @return The HBox with all the grade cutoffs in it
	 */
	private HBox mainGUI_grCutoff(Algorithm algorithmInst)
	{
		//Create Array of Letters Themselves
		String [] letters = new String[11];
		for (int i = 0; i < 3; i++)
		{
			String curSymbol = "+";	//A+, B+, C+
			if (i == 1)
				curSymbol = "";	//A, B, C
			if (i == 2)
				curSymbol = "-";	//A-, B-, C-
			letters[i] = ("A" + curSymbol);
			letters[i+3] = ("B" + curSymbol);
			letters[i+6] = ("C" + curSymbol);
		}
		letters[9] = "D";
		letters[10] = "F";
		
		//Create HBox
		HBox allGradeCutoffs = new HBox();
		for (int i = 0; i < 11; i++)
		{
			VBox curGradeCutoff = mainGUI_grCut_Ea (letters[i], algorithmInst.gradeCutoffs[i]);
			allGradeCutoffs.getChildren().add(curGradeCutoff);
		}
		allGradeCutoffs.setSpacing(20);	//Offset Each VBox So They're Not "Scrunched" Together
		return allGradeCutoffs;
	}
	
	/**
	 * Helper method for mainGUI(ArrayList<Grade>, Stage). Converts a Subgrade to HBox (GUI) format.
	 * @param subgrade The Subgrade to display the contents of to the GUI
	 * @return An HBox with the Subgrade contents in that HBox
	 */
	private HBox mainGUI_subgrades (Subgrade subgrade)
	{
		//Create Checkbox & Title
		CheckBox checkbox = new CheckBox();
		if (!subgrade.toCalculate)	//If To Calculate, Do *Not* Mark the Checkbox
			checkbox.setSelected(true);
		checkbox.setTranslateX(50);
		TextField title = new TextField(subgrade.name);
		title.setMaxWidth(mainGUI_textSize * 15);	//Set Width Equal to Approx. (Width of % TextField) * 3
		title.setTranslateX(300);
		
		//Create Known Grade Section
		Text knownTxt = new Text("Known Grade:");
		knownTxt.setFont(mainGUI_bold);
		knownTxt.setTranslateX(320);
		TextField knownField = mainGUI_genericField("");
		if (!subgrade.isEmpty)
		{
			int temp1 = (int) (subgrade.grade * 100);
			double temp2 = (subgrade.grade * 100.0);
			knownField.setText(temp2 + "%");
			if ( ((temp1/temp2) == 1.0 ) || (temp2 == 0.0) )
				knownField.setText(temp1 + "%");
		}
		knownField.setTranslateX(340);
		
		//Create Expected Grade Section
		Text expectedTxt = new Text("Expected Grade:");
		expectedTxt.setFont(mainGUI_bold);
		expectedTxt.setTranslateX(360);
		TextField expectedField = mainGUI_genericField("");
		if (subgrade.isExpected)
		{
			int temp1 = (int) (subgrade.expectedGrade * 100);
			double temp2 = (subgrade.expectedGrade * 100.0);
			expectedField.setText(temp2 + "%");
			if ( ((temp1/temp2) == 1.0 ) || (temp2 == 0.0) )
				expectedField.setText(temp1 + "%");
		}
		expectedField.setTranslateX(380);
		
		//Create HBox
		HBox hbox = new HBox();
		hbox.getChildren().addAll(checkbox, title, knownTxt, knownField, expectedTxt, expectedField);
		return hbox;
	}
	
	/**
	 * Helper method for mainGUI(ArrayList<Grade>, Stage). Converts a pre-formatted Subgrade HBox to a Subgrade.
	 * @param subgradeHBox The HBox to convert to a Subgrade
	 * @return The Subgrade from the conversion
	 */
	private Subgrade mainGUI_getSub (HBox subgradeHBox)
	{
		boolean toCalculate = !(( (CheckBox) subgradeHBox.getChildren().get(0) ).isSelected());
		String gradeContents = ( (TextField) subgradeHBox.getChildren().get(3) ).getText().replaceAll("%", "");
		double grade = -1.0;	//If Empty Box
		if ( !gradeContents.isBlank() )
			grade = (Double.parseDouble(gradeContents) / 100.0);
		String expectedContents = ( (TextField) subgradeHBox.getChildren().get(5) ).getText().replaceAll("%", "");
		double expectedGrade = -1.0;	//If Empty Box
		if ( !expectedContents.isBlank() )
			expectedGrade = (Double.parseDouble(expectedContents) / 100.0);
		String name = ( (TextField) subgradeHBox.getChildren().get(1) ).getText();
		Subgrade subgrade = new Subgrade(toCalculate, grade, expectedGrade, name);
		return subgrade;
	}
	
	/**
	 * Helper global for mainGUI(ArrayList<Grade>, Stage). This is the list of each Grade's number of Subgrades, in the
	 * main GUI.
	 */
	private ArrayList<Integer> mainGUI_curPlaceholders = new ArrayList<Integer>();
	
	/**
	 * Helper global for mainGUI(ArrayList<Grade>, Stage). This is the list of Grade VBoxes to add to the global main GUI.
	 */
	private ArrayList<VBox> mainGUI_curGrades = new ArrayList<VBox>();
	
	/**
	 * Helper global for mainGUI(ArrayList<Grade>, Stage). This is how far apart to have each box on the main GUI,
	 * to make it look significantly nicer.
	 */
	private final int mainGUI_spacing = 10;
	
	/**
	 * Helper method for mainGUI(ArrayList<Grade>, Stage). This is what happens to a particular Grade if the
	 * "Add New" button is clicked on.
	 * @param grades_VBox The pre-formatted Grade's VBox to add a new HBox (Subgrade) to
	 * @param mainName The main part of the initial name template for the new Subgrade
	 * @param placeHolderNum What to number this new Subgrade (In relation to other Subgrades, to identify it)
	 * @return The VBox containing the additional HBox (Subgrade)
	 */
	private VBox mainGUI_bttn_addSubgrade(VBox grades_VBox, String mainName, int placeHolderNum)
	{
		Subgrade blankSubgrade = new Subgrade( true, -1.0, -1.0, (mainName + " " + placeHolderNum) );
		HBox newHBox = mainGUI_subgrades(blankSubgrade);
		grades_VBox.getChildren().add(newHBox);
		return grades_VBox;
	}
	
	/**
	 * Helper method for mainGUI(ArrayList<Grade>, Stage). This is the GUI for a single Grade:
	 * Given a Grade, this will output a VBox containing the GUI for that Grade, as well as its Subgrades.
	 * @param isWording Whether or not it is the very first Grade
	 * @param grade The Grade to convert to GUI format
	 * @return The VBox containing the converted GUI from Grade form
	 */
	private VBox mainGUI_grades (boolean isWording, Grade grade)
	{
		//Create "Submitted" Column (If and Only if First Occurrence)
		HBox titleArea = new HBox();
		Text submitted = new Text("");
		int initOffset = 100;	//If No Text, Increase Indent
		if (isWording)
		{
			submitted.setText("Submitted");
			initOffset = 20;
		}
		submitted.setFont(mainGUI_bold);
		submitted.setUnderline(true);
		submitted.setTranslateX(20);
		
		//Create Textboxes
		TextField title = new TextField(grade.name);
		title.setMaxWidth(mainGUI_textSize * 15);	//Set Width Equal to Approx. (Width of % TextField) * 3
		title.setTranslateX(initOffset + 160);
		Text weightTxt = new Text("Weight:");
		weightTxt.setFont(mainGUI_bold);
		weightTxt.setTranslateX(initOffset + 180);
		TextField weightField = mainGUI_genericField("");
		if (grade.percent >= 0.0)
		{
			int temp1 = (int) (grade.percent * 100);
			double temp2 = (grade.percent * 100.0);
			weightField.setText(temp2 + "%");
			if ( ((temp1/temp2) == 1.0 ) || (temp2 == 0.0) )
				weightField.setText(temp1 + "%");
		}
		weightField.setTranslateX(initOffset + 200);
		
		//Create Button
		VBox grades = new VBox();
		grades.setSpacing(mainGUI_spacing);
		Button addNewSubgrade = new Button("Add New");
		mainGUI_curGrades.add(grades);
		mainGUI_curPlaceholders.add( grade.numGrades() );
		int grade_index = (mainGUI_curGrades.size() - 1);
		int placeHolder_index = (mainGUI_curPlaceholders.size() - 1);
		addNewSubgrade.setOnAction( e -> {
			int placeHolder = (mainGUI_curPlaceholders.get(placeHolder_index) + 1);
			mainGUI_curPlaceholders.set(placeHolder_index, placeHolder);
			VBox addSub_curVBox = mainGUI_curGrades.get(grade_index);
			VBox addSub_newVBox = mainGUI_bttn_addSubgrade(addSub_curVBox, grade.name, placeHolder);
			mainGUI_curGrades.set(grade_index, addSub_newVBox);
		} );
		addNewSubgrade.setTranslateX(initOffset + 220);
		mainGUI_curGrades.get(grade_index).getChildren().addAll(titleArea);
		
		//Add Subgrades
		for (int i = 0; i < grade.subgradeList.size(); i++)
		{
			HBox curSubgrade = mainGUI_subgrades( grade.subgradeList.get(i) );
			mainGUI_curGrades.get(grade_index).getChildren().add(curSubgrade);
		}
		titleArea.getChildren().addAll(submitted, title, weightTxt, weightField, addNewSubgrade);
		return mainGUI_curGrades.get(grade_index);
	}
	
	/**
	 * Helper method for mainGUI(ArrayList<Grade>, Stage). Gets the contents of a given pre-formatted VBox for
	 * a particular Grade, and returns the GUI contents as a Grade.
	 * @param gradeVBox The pre-formatted VBox for a Grade
	 * @return The conversion of the GUI boxes to Grade format
	 */
	private Grade mainGUI_getGrade(VBox gradeVBox)
	{
		//Get Main Components
		HBox mainHBox = (HBox) gradeVBox.getChildren().get(0);
		String percent_S = ((TextField) mainHBox.getChildren().get(3)).getText();
		percent_S = percent_S.replaceAll("%", "");
		double percent = -1.0;
		if ( !percent_S.isBlank() )
			percent = (Double.parseDouble(percent_S) / 100.0);
		ArrayList<Subgrade> subgrades = new ArrayList<Subgrade>();
		String name = ((TextField) mainHBox.getChildren().get(1)).getText();
		
		//Get Subgrades
		int numSubgrades = (gradeVBox.getChildren().size() - 1);
		for (int i = 0; i < numSubgrades; i++)
		{
			int counter = (i + 1);
			HBox curHBox = (HBox) gradeVBox.getChildren().get(counter);
			Subgrade curSubgrade = mainGUI_getSub(curHBox);
			subgrades.add(curSubgrade);
		}
		Grade grade = new Grade(percent, subgrades, name);
		return grade;
	}
	
	/**
	 * Helper global for mainGUI(ArrayList<Grade>, Stage). This is the main VBox for the main GUI:
	 * This is the VBox that contains all the VBoxes of Grades, and of those Grades' Subgrades.
	 */
	private VBox mainGUI_mainVBox = new VBox();
	
	/**
	 * Helper method for mainGUI(ArrayList<Grade>, Stage). This is what happens when the "Add New Assignment Type"
	 * button is clicked on.
	 */
	private void mainGUI_bttn_addGrade()
	{
		double percent = -1.0;
		ArrayList<Subgrade> subgradeList = new ArrayList<Subgrade>();
		String name = "New Assignment Type";
		Grade newGrade = new Grade(percent, subgradeList, name);
		VBox newVBox = mainGUI_grades(false, newGrade);
		mainGUI_mainVBox.getChildren().add(newVBox);
		return;
	}
	
	/**
	 * Helper method for mainGUI(ArrayList<Grade>, Stage). This imports the given list into the main GUI. Returns the
	 * value of the extra credit in that list, if applicable; otherwise, -1 is returned to signify non-existent (Yet)
	 * extra credit.
	 * Important: It is assumed that this method is only called once, in the initial creation of the main GUI.
	 * @param grades The original list of grades to import into the main GUI
	 * @return The value of the extra credit in the original list (If applicable; otherwise, -1 is returned)
	 */
	private double mainGUI_mainGrades(ArrayList<Grade> grades)
	{
		//Add First Grades Section (If None, Add Blank)
		mainGUI_mainVBox.setSpacing(mainGUI_spacing);
		VBox curGrade = new VBox();
		if (grades.size() > 0)
		{
			curGrade = mainGUI_grades( true, grades.get(0) );
		}
		else
		{
			double percent = -1.0;
			ArrayList<Subgrade> subgradeList = new ArrayList<Subgrade>();
			String name = "New Assignment Type";
			Grade newGrade = new Grade(percent, subgradeList, name);
			curGrade = mainGUI_grades(true, newGrade);
		}
		mainGUI_mainVBox.getChildren().add(curGrade);
		
		//Add Other Grades Sections
		for (int i = 1; i < grades.size(); i++)
		{
			//Return Extra Credit Amount
			String extraCreditNm = grades.get(i).name;
			if ( (i == (grades.size()-1)) && (extraCreditNm.equals("Extra Credit")) )
				return grades.get(i).percent;
			
			//Normal Other Grades
			curGrade = mainGUI_grades( false, grades.get(i) );
			mainGUI_mainVBox.getChildren().add(curGrade);
		}
		return -1.0;
	}
	
	/**
	 * Helper method for mainGUI(ArrayList<Grade>, Stage). This is a sub-panel that has the "Add New Assignment Type"
	 * button, as well as the "Extra Credit" GUI details.
	 * @param extraCreditAmt The amount of "Extra Credit" in the original list of grades; negative means not pre-existing
	 * @return The VBox with the "Add New Assignment Type" and "Extra Credit" GUI details
	 */
	private VBox mainGUI_mainPanel(double extraCreditAmt)
	{
		//Add "New Assignment Type" Button
		Button newAssType = new Button("Add New Assignment Type");
		newAssType.setTranslateX(260);
		newAssType.setOnAction( e -> mainGUI_bttn_addGrade() );
		
		//Add Extra Credit Textbox
		Text extraCreditTxt = new Text("Extra Credit:");
		extraCreditTxt.setFont(mainGUI_bold);
		String extraCreditFill = "";	//If No Extra Credit Yet
		if (extraCreditAmt >= 0.0)	//If There Is Extra Credit Currently
			extraCreditFill = (extraCreditAmt + "%");
		TextField extraCreditField = mainGUI_genericField(extraCreditFill);
		extraCreditTxt.setTranslateX(260);
		extraCreditField.setTranslateX(280);
		
		//Add Stuff to VBox
		HBox extraCredit = new HBox();
		extraCredit.getChildren().addAll(extraCreditTxt, extraCreditField);
		VBox bttn_xCredit_holder = new VBox();
		bttn_xCredit_holder.getChildren().addAll(newAssType, extraCredit);
		bttn_xCredit_holder.setSpacing(20);
		return bttn_xCredit_holder;
	}
	
	/**
	 * Helper method for mainGUI(ArrayList<Grade>, Stage). Gets all Grades and Subgrades, and returns as an ArrayList
	 * of Grades. Extra Credit is a special case that's added as the last "Grade" in the list.
	 * @param extraCreditVBox The VBox that contains the "Extra Credit" TextField in it
	 * @return The ArrayList of all grades gotten
	 */
	private ArrayList<Grade> mainGUI_getAllGrades(VBox extraCreditVBox)
	{
		//Get Grades (& Subgrades)
		ArrayList<Grade> grades = new ArrayList<Grade>();
		for (int i = 0; i < mainGUI_mainVBox.getChildren().size(); i++)
		{
			VBox curVBox = (VBox) mainGUI_mainVBox.getChildren().get(i);
			Grade curGrade = mainGUI_getGrade(curVBox);
			grades.add(curGrade);
		}
		
		//Get Extra Credit Percent
		HBox extraCreditHBox = (HBox) extraCreditVBox.getChildren().get(1);
		TextField extraCreditField = (TextField) extraCreditHBox.getChildren().get(1);
		String extraCreditTxt = extraCreditField.getText();
		extraCreditTxt = extraCreditTxt.replaceAll("%", "");
		double extraCredit = 0.0;
		if ( !extraCreditTxt.isBlank() )
			extraCredit = (Double.parseDouble(extraCreditTxt) / 100.0);
		ArrayList<Subgrade> empty = new ArrayList<Subgrade>();
		Grade extraCreditGrade = new Grade(extraCredit, empty, "Extra Credit");
		grades.add(extraCreditGrade);
		return grades;
	}
	
	/**
	 * This is the main GUI of the program. This is the GUI where the user enters the data for the various grades,
	 * and then saves them via one of the two main buttons. (Followed by viewing if they click that button)
	 * @param grades The initial list of all grades to use
	 * @param s The main Stage
	 */
	private void mainGUI (ArrayList<Grade> grades, Stage s)
	{
		//Get the Grade Cutoffs
		String numbers = fileHandler.readFromFile(className, 1);
		Parsing parser = new Parsing();
		parser.stringToInt(numbers);
		Algorithm algorithm = parser.algorithm;
		
		//Construct Grade Cutoffs Panel
		s.setTitle("Class: " + className);
		VBox panel_gradeCutoffs = new VBox();
		Text gradeCutoffsTxt = new Text("Grade Cutoffs:");
		gradeCutoffsTxt.setFont(mainGUI_bold);
		gradeCutoffsTxt.setUnderline(true);
		gradeCutoffsTxt.setTranslateX(20);
		HBox gradeCutoffs = mainGUI_grCutoff(algorithm);
		panel_gradeCutoffs.getChildren().addAll(gradeCutoffsTxt, gradeCutoffs);
		panel_gradeCutoffs.setSpacing(20);
		panel_gradeCutoffs.setStyle("-fx-border-color: black; -fx-border-radius: 20 20 20 20;"
				+ " -fx-padding: 30; -fx-border-width: 2px, 0px;");
		
		//Construct Main Panel (The Big One in the Center)
		VBox gradesSection = new VBox();
		double extraCreditAmt = mainGUI_mainGrades(grades);	//iff >= 0, Extra Credit % Returned; Else, 0 Returned
		VBox extraCreditPanel = mainGUI_mainPanel(extraCreditAmt);
		gradesSection.getChildren().addAll(mainGUI_mainVBox, extraCreditPanel);
		gradesSection.setSpacing(50);
		gradesSection.setStyle("-fx-border-color: black; -fx-border-radius: 20 20 20 20;"
				+ " -fx-padding: 30; -fx-border-width: 2px, 0px;");
		
		//Main Buttons (AKA Save & Exit, Save & View, and Exit)
		Text buttons_blank1 = new Text(" ");
		buttons_blank1.setStyle("-fx-font: " + mainGUI_textSize + " arial");
		Button save_view = new Button("Save and View");
		Button save_exit = new Button("Save and Exit");
		Button exit = new Button("Exit");
		Scale scale = new Scale();	//Create Scale Changes
		scale.setX(1.5);
		scale.setY(1.5);
		save_view.getTransforms().add(scale);	//Apply Scale Changes
		save_exit.getTransforms().add(scale);	//Apply Scale Changes
		exit.getTransforms().add(scale);	//Apply Scale Changes
		HBox mainButtons = new HBox();
		mainButtons.setSpacing(70);
		mainButtons.getChildren().addAll(buttons_blank1, save_view, save_exit, exit);
		
		//Create Border for Main Buttons
		VBox mainButtons_border = new VBox();	//Container for the Border Itself
		mainButtons_border.setTranslateX(500);
		Text buttons_blank2 = new Text(" ");
		buttons_blank2.setStyle("-fx-font: " + (mainGUI_textSize * 2) + " arial");
		mainButtons_border.getChildren().addAll(buttons_blank2, mainButtons);
		String borderLocation = (baseFolder + "/Settings/Graphics/MainGUI_MainButtons_BG.png");
		FileInputStream borderImg = null;
		try {
			borderImg = new FileInputStream(borderLocation);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		if (borderImg != null)
		{
			mainButtons_border.setMinHeight(128);
			mainButtons_border.setMinWidth(512);
			Image border = new Image(borderImg);
			BackgroundPosition border_bgLoc = new BackgroundPosition(null, 10, false, null, 0, false);
			BackgroundImage border_bgImg = new BackgroundImage(border, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
					border_bgLoc, BackgroundSize.DEFAULT);
			Background border_bg = new Background(border_bgImg);
			mainButtons_border.setBackground(border_bg);
		}
		VBox mainButtons_V = new VBox();	//Container for the Border to Apply Properly
		mainButtons_V.getChildren().addAll(mainButtons_border);
		
		//Main Button Actions
		save_view.setOnAction(e -> {
			ArrayList<Grade> newGrades = mainGUI_getAllGrades(extraCreditPanel);
			save(newGrades);
			algorithm.runAlgorithm(newGrades);
		});
		save_exit.setOnAction(e -> {
			ArrayList<Grade> newGrades = mainGUI_getAllGrades(extraCreditPanel);
			save(newGrades);
			System.exit(0);
		});
		exit.setOnAction(e -> { System.exit(0); });
		
		//Blank Spaces at Top and Bottom (To Make Look Slightly Nicer)
		Text blank1 = new Text(" ");
		Text blank2 = new Text(" ");
		blank1.setStyle("-fx-font: " + (mainGUI_textSize / 2) + " arial");
		blank2.setStyle("-fx-font: " + (mainGUI_textSize / 2) + " arial");
		
		//Create the Main Panel Itself
		VBox overallPane = new VBox();	//The Overall Pane
		VBox mainPanel = new VBox();	//A Nested VBox to Support Scrolling Well
		Scene scene = new Scene(overallPane, 700, 500);
		s.setScene(scene);	//The Scene Itself
		ScrollPane mainScroller = new ScrollPane();	//Scrollbar
		mainScroller.setContent(mainPanel);
		mainPanel.setSpacing(50);	//To Separate the Sections Clearly
		s.setMaximized(true);
		s.centerOnScreen();
		s.resizableProperty().setValue(Boolean.FALSE);	//Force Maximized (To Prevent Weird Skewing)
		double maxCoords = s.getWidth();
		mainPanel.setTranslateX(maxCoords / 5.0);	//Math to Put in Center of Screen
		mainPanel.getChildren().addAll(blank1, panel_gradeCutoffs, gradesSection, mainButtons_V,
				blank2);	//The Additions Themselves
		overallPane.getChildren().addAll(mainPanel, mainScroller);
		mainScroller.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		mainScroller.setVisible(true);
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
