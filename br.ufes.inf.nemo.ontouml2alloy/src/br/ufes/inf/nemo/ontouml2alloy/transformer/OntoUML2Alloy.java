package br.ufes.inf.nemo.ontouml2alloy.transformer;

/**
 * Copyright 2011 NEMO (http://nemo.inf.ufes.br/en)
 *
 * This file is part of OntoUML2Alloy (OntoUML to Alloy Transformation).
 *
 * OntoUML2Alloy is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * OntoUML2Alloy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OntoUML2Alloy; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import RefOntoUML.Association;
import RefOntoUML.Classifier;
import RefOntoUML.Derivation;
import RefOntoUML.PackageableElement;
import br.ufes.inf.nemo.alloy.AlloyFactory;
import br.ufes.inf.nemo.alloy.AlloyPackage;
import br.ufes.inf.nemo.alloy.impl.AlloyPackageImpl;
import br.ufes.inf.nemo.alloy.util.AlloyResourceFactoryImpl;
import br.ufes.inf.nemo.common.ontoumlparser.OntoUMLParser;
import br.ufes.inf.nemo.ontouml2alloy.options.OntoUMLOptions;
import br.ufes.inf.nemo.ontouml2alloy.util.AlloyLibraryFiles;
import br.ufes.inf.nemo.ontouml2alloy.util.AlloyThemeFile;

/**
 *	This class is used to execute the transformation of OntoUML to Alloy. 
 *  
 * 	@authors Tiago Sales, John Guerson and Lucas Thom 
 */

public class OntoUML2Alloy {
		
	/** Destination directory path. */
	public static String dirPath;	
	
	/** Alloy specification absolute path. */
	public static String alsPath;
		
	/** 
	 *  Provide the ontouml model elements.
	 *  It is also used for associate the elements of the ontouml model 
	 *  with their modified names (i.e. without special characters: #, !, @, $, %, and etc...). 
	 */
	public static OntoUMLParser ontoparser;
	
	/** 
	 * Performs the transformation of ontouml elements. 
	 */
	public static Transformer transformer;
	
	/** 
	 * Alloy model resource. 
	 */
	public static Resource alsresource;
	
	/**
	 * 
	 * This method performs the transformation of the RefOntoUML Model 
	 * into an Alloy specification (.als). The Alloy libraries used 
	 * by this transformation are generated into the same folder of the Alloy file, as well as
	 * the standard Alloy theme file. In order to open the Analyzer standalone, we need to copy the Analyzer 
	 * into the folder as well.
	 * 
	 * @param refmodel : The root of .refontouml model (RefOntoUML.Model).
	 * @param alloyPath: The absolute path of alloy model.
	 * @param RelatorRuleFlag: True if the relator constraint should be transformed. 
	 * @param WeakSupplementationRuleFlag:  True if the weak supplementation rule should be transformed.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String Transformation (OntoUMLParser refparser, String alloyPath, OntoUMLOptions opt) throws Exception 
	{
		alsPath = alloyPath;
		
		File f = new File(alsPath);
		if (!f.exists()) f.createNewFile();
		if (f.exists()) f.delete(); f.createNewFile();
		f.deleteOnExit();
		
		// initialize dirPath
		dirPath = alsPath.substring(0,alsPath.lastIndexOf(File.separator)+1);
				
		// generate alloy standard theme file
		AlloyThemeFile.generateAlloyThemeFile(dirPath);		

		// generate alloy library files
		AlloyLibraryFiles.generateLibraryFiles(dirPath);				
				
		// Here the transformation begins...
		start(refparser,opt);
		
		return transformer.module.toString();
	}	
	
	private static void start(OntoUMLParser refparser, OntoUMLOptions opt)
	{
		ontoparser = refparser;
		
		AlloyFactory factory = AlloyFactory.eINSTANCE;
		
		transformer = new Transformer(ontoparser, factory, opt);		
		
		for (PackageableElement pe : ontoparser.getAllInstances(PackageableElement.class))
		{			
			if (pe instanceof Classifier) 
			{
				// Classifier
				transformer.transformClassifier( (Classifier)pe );			
			}
		}
		
		// Associations
		for (Association a : ontoparser.getAllInstances(Association.class))
		{
			if (a instanceof Derivation)
				transformer.transformDerivations((Derivation) a);
			else
				transformer.transformAssociations(a);
		}
				
		transformer.populateWithAttributeRules();
		
		transformer.finalAdditions();
		
		createAlsResource();
		
		alsresource.getContents().add(transformer.module);
		
		saveAlsResourceToFile();
	}
	
	/** 
	 * Init Alloy Resource 'alsresource'. 
	 */
	private static void createAlsResource() 
	{
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new AlloyResourceFactoryImpl() );
		resourceSet.getPackageRegistry().put(AlloyPackage.eNS_URI,AlloyPackage.eINSTANCE);
		AlloyPackageImpl.init();
		alsresource = resourceSet.createResource(URI.createURI("models/out.xmi"));
	}
	
	/** 
	 * Save 'alsresource' content into a file (.als). 
	 */
	private static void saveAlsResourceToFile() 
	{		
		try{			
			FileWriter fstream = new FileWriter(OntoUML2Alloy.alsPath);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(alsresource.getContents().get(0).toString());			
			out.close();
		  }catch (Exception e){
			  System.err.println("Error: " + e.getMessage());
		  }		
	}	
}