package utils;

import javafx.scene.Node;

/**
 * Builder class for SceneChanger objects.  Supports chaining.
 * @author racello
 * @see SceneChanger
 */
public class SceneChangerBuilder {
	private int mainIndex = -1;
	private int subIndex = -1;
	private String msg = "";
	private Node node = null;

	/**
	 * @param mainIndex the integer representing the primary ID associated with the scene
	 * @return this updated SceneChangerBuilder object, for chaining
	 */
	public SceneChangerBuilder setMainIndex(int mainIndex) {
		this.mainIndex = mainIndex;
		return this;
	}

	/**
	 * @param subIndex the integer representing the secondary ID associated with the scene
	 * @return this updated SceneChangerBuilder object, for chaining
	 */
	public SceneChangerBuilder setSubIndex(int subIndex) {
		this.subIndex = subIndex;
		return this;
	}

	/**
	 * @param message the string representing any desired message to associate with the scene
	 * @return this updated SceneChangerBuilder object, for chaining
	 */
	public SceneChangerBuilder setMsg(String message) {
		this.msg = message;
		return this;
	}

	/**
	 * @param node a node object from which the window can be obtained (in case the target object of the ActionEvent is not a child of the Node class)
	 * @return this update SceneChangerBuilder object, for chaining
	 * @see Node
	 */
	public SceneChangerBuilder setNode(Node node) {
		this.node = node;
		return this;
	}
	
	/**
	 * Terminal method (does not support chaining)
	 * @return new SceneChanger object from any properties set by this builder
	 */
	public SceneChanger build() {
		return new SceneChanger(mainIndex, subIndex, msg, node);
	}
}
