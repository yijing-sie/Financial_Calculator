package application;
	

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Main extends Application {
	private double interest;
	private double principal;
	private double time;
	//0: simple interest, and 1: compound
	private int choose = 0;
	private double amount = 0;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			VBox root = new VBox();
			Label title = new Label();
			title.setText("   Finantial Calculator");
			title.getStyleClass().add("titletheme");
			//the principal amount
			TextField textfieldPrincipal = new TextField();
			textfieldPrincipal.setMinSize(300, 30);			
			Label labelPrincipal = new Label();
			labelPrincipal.setText("Principal ($): ");
			labelPrincipal.setMinSize(300, 30);
			labelPrincipal.getStyleClass().add("labeltheme");
			VBox principalVbox= new VBox();
			principalVbox.getChildren().add(labelPrincipal);
			principalVbox.getChildren().add(textfieldPrincipal);
			principalVbox.getStyleClass().add("vboxtheme");
			
			//the time in month
			TextField textfieldTime = new TextField();
			textfieldTime.setMinSize(300, 30);
			Label labelTime = new Label();
			labelTime.setText("Time (y): ");
			labelTime.setMinSize(300, 30);
			labelTime.getStyleClass().add("labeltheme");
			VBox TimeVbox= new VBox();
			TimeVbox.getChildren().add(labelTime);
			TimeVbox.getChildren().add(textfieldTime);
			TimeVbox.getStyleClass().add("vboxtheme");
			
			//the interest rate in %
			VBox sliderVbox = new VBox();
			Slider interestRateSlider = new Slider();
			interestRateSlider.setMinWidth(300);
			Label sliderlabel = new Label();
			sliderlabel.setText("Interest (%): ");
			sliderlabel.setMinSize(300, 30);			
			sliderlabel.getStyleClass().add("labeltheme");
			sliderVbox.getChildren().add(sliderlabel);
			sliderVbox.getChildren().add(interestRateSlider);

			VBox buttonVbox = new VBox();
			Button switchButton = new Button();
			switchButton.setText("simple/compound interest");
			switchButton.setMinHeight(25);
			Label switchButtonLabel = new Label();
			switchButtonLabel.setText("Simple interest");
			switchButtonLabel.setMaxHeight(150);
			switchButtonLabel.getStyleClass().add("labeltheme");
			VBox switchVbox= new VBox();
			switchVbox.getChildren().add(switchButtonLabel);
			switchVbox.getChildren().add(switchButton);
			Button resultButton = new Button();
			resultButton.setText("Result");
			buttonVbox.getChildren().add(switchVbox);
			buttonVbox.getChildren().add(resultButton);
			buttonVbox.setStyle("-fx-spacing: 10px;");
			
			Label spaceLabel = new Label();
			spaceLabel.getStyleClass().add("-fx-background-color: #000000");
			spaceLabel.setMinHeight(10);
			Label resultLabel = new Label();
			resultLabel.getStyleClass().add("labeltheme");
			resultLabel.setMinSize(150, 30);
			resultLabel.setText("$");
			
			root.getChildren().add(title);	
			root.getChildren().add(principalVbox);			
			root.getChildren().add(TimeVbox);
			root.getChildren().add(sliderVbox);
			root.getChildren().add(buttonVbox);
			root.getChildren().add(spaceLabel);
			root.getChildren().add(resultLabel);
			root.getStyleClass().add("myroottheme");
			
			
			textfieldPrincipal.textProperty().addListener(new ChangeListener<String>() {
				
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					labelPrincipal.setText("Principal: $"+newValue);
					try {
						principal = Double.parseDouble(newValue);
					}catch(NumberFormatException nfe) {
						if (nfe.getMessage().equals("empty String")) {
							principal = 0;
							labelPrincipal.setText("Principal ($): ");
						}
						else labelPrincipal.setText("NUMBER ONLY!");					
					}
				}
			});
			
			textfieldTime.textProperty().addListener(new ChangeListener<String>() {
				
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					labelTime.setText("Time: "+newValue + " year(s)");
					try {
						time = Double.parseDouble(newValue);
					}catch(NumberFormatException nfe) {
						if (nfe.getMessage().equals("empty String")) {
							time = 0;
							labelTime.setText("Time (y): ");
						}
						else labelTime.setText("NUMBER ONLY!");
					}
				}
			});
			
			interestRateSlider.valueProperty().addListener(new ChangeListener<Number>() {
				
				//<? extends Number> : any type that extends Number class or Number class itself
				//observable points to Porperty "slider.valueProperty()"
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					sliderlabel.setText("Interest Rate: " + newValue.intValue() + " %" );
					interest = newValue.intValue()/100.0;
				}
			});
	
			switchButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
				
				@Override
				public void handle(MouseEvent me) {
					if (choose == 0) {
						choose = 1;
						switchButtonLabel.setText("Compound Interest - Annually");
					}
					else {
						switchButtonLabel.setText("Simple Interest");
						choose = 0;
					}

					
				}
			});
			
			resultButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
				
				@Override
				public void handle(MouseEvent me) {
					calculate();
					resultLabel.setText(" $" + amount);
				}
			});
			
			
						
			Scene scene = new Scene(root,400,450);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void calculate() {
		switch (choose) {
		case 0: amount = principal*(1+interest*time);
			break;
		case 1: amount = principal*Math.pow((1+interest),time);
			break;
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
