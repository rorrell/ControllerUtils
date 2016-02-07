package utils;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.Node;
import javafx.scene.control.TextField;

/**
 * Creates an ArrayList-based group of Nodes.
 * @author racello
 * @param <S> the type (must be Node or child of Node) of the NodeGroup
 * @see Node
 */
public class NodeGroup<S extends Node> extends ArrayList<Node> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8545137019436225863L;
	
	/**
	 * @param nodes the Node or Nodes to add to this NodeGroup
	 */
	public void addAll(Node...nodes) {
		Arrays.stream(nodes).forEach(n -> this.add(n));
	}

	/**
	 * Disables all the Nodes in this NodeGroup
	 */
	public void disable() {
		this.stream().forEach(n -> n.setDisable(true));
	}
	
	/**
	 * Enables all the Nodes in this NodeGroup
	 */
	public void enable() {
		this.stream().forEach(n -> n.setDisable(false));
	}
	
	/**
	 * Clears any Nodes in this NodeGroup that are TextFields
	 * @see TextField
	 */
	public void clearTextFields() {
		this.stream().filter(n -> n instanceof TextField)
			.forEach(n -> ((TextField)n).clear());
	}
}
