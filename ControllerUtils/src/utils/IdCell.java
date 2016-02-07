package utils;

import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

/**
 * A custom TableCell for displaying custom content to be displayed in an HBox.
 * This class extends a TableCell with an Integer as its second type parameter, 
 * and the integer supplied will be assigned to each supplied node as custom user data.
 * @author racello
 * @param <S> the type of object being displayed in the table containing this TableCell
 * @see #IdCell(NodeGroup)
 * @see Node#setUserData(Object)
 */
public class IdCell<S> extends TableCell<S, Integer> {
	
	private NodeGroup<? extends Node> nodes;
	
	/**
	 * @param nodes the group of nodes to add to the HBox that is the container inside the cell
	 * @see NodeGroup
	 * @see HBox
	 */
	public IdCell(NodeGroup<? extends Node> nodes) {
		this.nodes = nodes;
	}
	
	@Override
	protected void updateItem(Integer item, boolean empty) {
		super.updateItem(item, empty);

		if (item == null || empty) setText(null);
		else {
			HBox box = new HBox();
			box.setSpacing(5);
			nodes.stream().forEach(n -> n.setUserData(item));
			box.getChildren().addAll(nodes);
			setGraphic(box);
		}
	}
}
