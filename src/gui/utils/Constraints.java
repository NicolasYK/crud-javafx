package gui.utils;

import javafx.scene.control.TextField;

public class Constraints {
	
	
	public static void checkIsNumber(TextField text) {
		text.textProperty().addListener((obs, CtValue, NewValue) -> {
			if(NewValue != null && !NewValue.matches("\\d*")) {
				text.setText(CtValue);
			}
		});
	}

}
