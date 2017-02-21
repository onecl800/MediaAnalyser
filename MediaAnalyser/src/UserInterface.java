import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UserInterface extends Application {

	private Desktop desktop = Desktop.getDesktop();
	private ArrayList<Text> fileNames = new ArrayList<Text>();
	
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Media Filter");	
		
		final Text colHeader1 = new Text("Files");
		final Text colHeader2 = new Text("Article Type");
		final Text colHeader3 = new Text("Search terms:");
		
		final FileChooser fc = new FileChooser();
		final Button openButton = new Button("Open PDF files");
		final Button okButton = new Button("Filter");
		Text fileName1 = new Text();
		Text fileName2 = new Text();
		Text fileName3 = new Text();
		Text fileName4 = new Text();
		fileNames.add(fileName1);
		fileNames.add(fileName2);
		fileNames.add(fileName3);
		fileNames.add(fileName4);
		
		final ToggleGroup group1 = new ToggleGroup();
		final ToggleGroup group2 = new ToggleGroup();
		final ToggleGroup group3 = new ToggleGroup();
		final ToggleGroup group4 = new ToggleGroup();
		
		RadioButton rb11 = new RadioButton("Print");
		rb11.setToggleGroup(group1);
		RadioButton rb12 = new RadioButton("Web");
		rb12.setToggleGroup(group1);
		RadioButton rb21 = new RadioButton("Print");
		rb21.setToggleGroup(group2);
		RadioButton rb22 = new RadioButton("Web");
		rb22.setToggleGroup(group2);
		RadioButton rb31 = new RadioButton("Print");
		rb31.setToggleGroup(group3);
		RadioButton rb32 = new RadioButton("Web");
		rb32.setToggleGroup(group3);
		RadioButton rb41 = new RadioButton("Print");
		rb41.setToggleGroup(group4);
		RadioButton rb42 = new RadioButton("Web");
		rb42.setToggleGroup(group4);
		
		TextField search1 = new TextField();
		TextField search2 = new TextField();
		TextField search3 = new TextField();
		TextField search4 = new TextField();
		TextField search5 = new TextField();
		TextField search6 = new TextField();
					
		
		openButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				List<File> list = fc.showOpenMultipleDialog(stage);
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						for (int j = i; j < fileNames.size(); j++) {
							//Text fileName = new Text();
							fileNames.get(j).setText(list.get(i).toString());
							//fileNames.add(fileName);
						}
					}		
				}				
			}
		});
		
		
		okButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//get file name, radio button and call main method of 
				//corresponding class
				
				//consider where to write all the output - excel file?
				
			}
		});
		
		
		
		final GridPane pane = new GridPane();
		
		GridPane.setConstraints(openButton, 0, 0);
		GridPane.setConstraints(fileName1, 0, 2);
		GridPane.setConstraints(fileName2, 0, 3);
		GridPane.setConstraints(fileName3, 0, 4);
		GridPane.setConstraints(fileName4, 0, 5);
		GridPane.setConstraints(rb11, 1, 2);
		GridPane.setConstraints(rb12, 2, 2);
		GridPane.setConstraints(rb21, 1, 3);
		GridPane.setConstraints(rb22, 2, 3);
		GridPane.setConstraints(rb31, 1, 4);
		GridPane.setConstraints(rb32, 2, 4);
		GridPane.setConstraints(rb41, 1, 5);
		GridPane.setConstraints(rb42, 2, 5);
		GridPane.setConstraints(search1, 3, 2);
		GridPane.setConstraints(search2, 3, 3);
		GridPane.setConstraints(search3, 3, 4);
		GridPane.setConstraints(search4, 3, 5);
		GridPane.setConstraints(search5, 3, 6);
		GridPane.setConstraints(search6, 3, 7);
		GridPane.setConstraints(okButton, 0, 10);
		GridPane.setConstraints(colHeader1, 0, 1);
		GridPane.setConstraints(colHeader2, 1, 1);
		GridPane.setConstraints(colHeader3, 3, 1);
		
		pane.setHgap(10);
		pane.setVgap(5);
		pane.getChildren().addAll(colHeader1, colHeader2, colHeader3, openButton, okButton, fileName1, fileName2, fileName3, fileName4, rb11, rb12, rb21, rb22, rb31, rb32, rb41, rb42, search1, search2, search3, search4, search5, search6);
		
		final Pane rootGroup = new HBox(50);
		rootGroup.getChildren().addAll(pane);
		rootGroup.setPadding(new Insets(50,500,150,50));
		
		stage.setScene(new Scene(rootGroup));
		stage.show();
	}

	
	public static void main(String[] args) {
		launch(args);
	}
	
}

