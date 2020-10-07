package view;
import java.util.ArrayList;

public class NavigationNode {

	private String title = "";
	private NavigationNode parent;
	private ArrayList<NavigationNode> children;
	
	public NavigationNode(String title, NavigationNode parent, ArrayList<NavigationNode> children) {
		this.title = title;
		this.parent = parent;
		this.children = children;
	}
	public void addChild(NavigationNode child) {
		children.add(child);
	}
	public String getTitle() {
		return this.title;
	}
	public ArrayList<NavigationNode> getChildren() {
		return this.children;
	}
	public NavigationNode getParent() {
		return this.parent;
	}
}
