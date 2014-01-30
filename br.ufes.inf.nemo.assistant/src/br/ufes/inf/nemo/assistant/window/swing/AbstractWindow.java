package br.ufes.inf.nemo.assistant.window.swing;

import javax.swing.JDialog;

import br.ufes.inf.nemo.assistant.graph.Node;

public abstract class AbstractWindow extends JDialog {
	
	private static final long serialVersionUID = 1L;
	protected Node myNode;
	protected AbstractWindow instance;
	protected boolean autoGetConceptName = false;
	protected String conceptName;
	
	public void run(Node n){
		this.setVisible(true);
		myNode = n;
		if(autoGetConceptName){
			conceptName = n.getTree().getManagerPatern().getCurrentClass();	
		}
	}
	
	public void killMySelf(){
		this.dispose();
	}
	
	public void autoGetConceptName(boolean bool){
		autoGetConceptName = bool;
	}
}