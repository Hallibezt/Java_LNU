import controller.Controller;
import model.Registry;
import view.Terminal;

public class Main {

	public static void main(String[] args) {
		Registry reg = new Registry();
		Terminal term = new Terminal();
		Controller c = new Controller(reg, term);
		boolean running = true;
		
		c.boot();
		
		while(running) {
			c.navigate();
			
			// Selected a leaf  || operation
			if(c.getLeafNavigationNode() != null && c.getLeafNavigationNode().getChildren() == null) {
				String leaf = c.getLeafNavigationNode().getTitle();
				
				if(leaf == "exit") {
					running = false;
				}
				//System.out.println(leaf);
				c.performAction(leaf);
			}
		}
	}
}
