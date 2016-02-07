package utils;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;

/**
 * A custom TableCell for displaying a URL as a clickable Hyperlink that opens the URL in a browser.
 * @author racello
 * @param <S> the type of object being displayed in the table containing this TableCell
 * @see URL
 */
public class HyperlinkCell<S> extends TableCell<S, URL> {

	@Override
	protected void updateItem(URL item, boolean empty) {
		super.updateItem(item, empty);
		if(item != null && !empty) {
			Hyperlink link = new Hyperlink(item.toString());
				link.setOnAction(e -> {
					try {
						Desktop.getDesktop().browse(item.toURI());
					}
					catch(IOException | URISyntaxException ex) {
						ex.printStackTrace();
					}
				});
			setGraphic(link);
		}
	}

}
