package utils;

import javafx.scene.Node;

/**
 * Object containing parameters relevant to changing scenes
 * @author racello
 * @see SceneUtility#changeScene(javafx.event.ActionEvent, String, String, SceneChanger)
 */
public class SceneChanger {
	private int mainIndex;
	private int subIndex;
	private String msg;
	private Node node;
	
	/**
	 * Can use SceneChangerBuilder if only setting some of the fields belonging to this class
	 * @param mainIndex the integer representing the primary ID associated with the scene
	 * @param subIndex the integer representing the secondary ID associated with the scene
	 * @param msg the string representing any desired message to associate with the scene
	 * @param node a node object from which the window can be obtained (in case the target object of the ActionEvent is not a child of the Node class)
	 * @see SceneChangerBuilder
	 * @see Node
	 */
	public SceneChanger(int mainIndex, int subIndex, String msg, Node node) {
		this.mainIndex = mainIndex;
		this.subIndex = subIndex;
		this.msg = msg;
		this.node = node;
	}

	/**
	 * @return the integer representing the primary ID associated with the scene
	 */
	public int getMainIndex() {
		return mainIndex;
	}

	/**
	 * @param mainIndex the integer representing the primary ID associated with the scene
	 */
	public void setMainIndex(int mainIndex) {
		this.mainIndex = mainIndex;
	}

	/**
	 * @return the integer representing the secondary ID associated with the scene
	 */
	public int getSubIndex() {
		return subIndex;
	}

	/**
	 * @param subIndex the integer representing the secondary ID associated with the scene
	 */
	public void setSubIndex(int subIndex) {
		this.subIndex = subIndex;
	}

	/**
	 * @return the string representing any desired message to associate with the scene
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the string representing any desired message to associate with the scene
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return a node object from which the window can be obtained (in case the target object of the ActionEvent is not a child of the Node class)
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * @param node a node object from which the window can be obtained (in case the target object of the ActionEvent is not a child of the Node class)
	 * @see Node
	 */
	public void setNode(Node node) {
		this.node = node;
	}
	
	

}
