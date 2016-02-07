package utils;

import java.util.List;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * Creates an ArrayList-based group of Buttons.
 * @author racello
 */
public class ButtonGroup extends NodeGroup<Button> {

	private static final long serialVersionUID = -5560231116550043518L;
	
	private EventHandler<ActionEvent> handler;
	
	/**
	 * Adds a Button with a custom EventHandler.
	 * @param id the string identifying the Button
	 * @param text the string representing the Button's display text
	 * @param handler the ActionEvent handler for this Button
	 * @return this ButtonGroup instance with the new Button added
	 */
	public ButtonGroup addButton(String id, String text, EventHandler<ActionEvent> handler) {
		Button btn = new Button(text);
		btn.setId(id);
		btn.setOnAction(handler);
		this.add(btn);
		return this;
	}

	/**
	 * Adds a Button using the ActionEvent handler specified for this ButtonGroup instance.
	 * @param id the string identifying the Button
	 * @param text the string representing the Button's display text
	 * @return this ButtonGroup instance with the new Button added
	 */
	public ButtonGroup addButton(String id, String text) {
		return addButton(id, text, this.handler);
	}
	
	/**
	 * Set the ActionEvent handler belonging to this ButtonGroup instance (functions as the default handler for new buttons).
	 * If a default handler is desired, this method should be called at the beginning of the chain. 
	 * @param handler the ActionEvent handler for this ButtonGroup instance
	 * @return this ButtonGroup instance with the updated handler
	 */
	public ButtonGroup setHandler(EventHandler<ActionEvent> handler) {
		this.handler = handler;
		return this;
	}
	
	/**
	 * Get a List containing the ids of the Button objects contained in this ButtonGroup.
	 * @return the string list of Button ids
	 */
	public List<String> getIdList() {
		return this.stream().map(Node::getId).collect(Collectors.toList());
	}
}
