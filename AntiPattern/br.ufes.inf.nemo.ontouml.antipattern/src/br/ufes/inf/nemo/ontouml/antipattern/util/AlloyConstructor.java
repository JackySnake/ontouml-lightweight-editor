package br.ufes.inf.nemo.ontouml.antipattern.util;

public class AlloyConstructor {
	public static int PRED = 0;
	public static int ASSERT = 1;
	
	public static String AlloyParagraph(String parName, String rules, int type) {
		String command;
		
		if(type==1)
			command = "assert ";
		else
			command = "pred ";
		command += parName + " {\n\t" + rules + "\n}\n";
		return command;
	}
	
	public static String RunCheckCommand (String executableName, String scope, String world, int type){
		String command;
		
		if(type==1)
			command = "check ";
		else
			command = "run ";
		
		command += executableName + " for " + scope + " but " + world + " World";
		return command;
	}
}
