package org.apache.lucene.analsys.ko.morph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.lucene.analysis.ko.morph.WordEntry;
import org.apache.lucene.analysis.ko.utils.DictionaryUtil;

import junit.framework.TestCase;

public class DictionaryTest extends TestCase {

	public void testNewTerm() throws Exception {
		
		InputStream is = DictionaryTest.class.getResourceAsStream("/new_term.txt");
		BufferedReader reader= new BufferedReader(new InputStreamReader(is));  
		String line = reader.readLine();
		
		HashSet<String> newTerms = new HashSet<String>();
		
		// select terms which don't exist in the dictionary
		while(line!=null && line.length()>0) {
			String[] terms = line.split("[ ]+");
			for(String term : terms) {
				WordEntry entry = DictionaryUtil.getAllNoun(term);
				if(entry==null) {
					newTerms.add(term);
				}
			}
			
			line = reader.readLine();
		}
		
		// print out terms to file
		FileWriter writer = new FileWriter(new File("notFound.txt"));
		
		Iterator<String> iter = newTerms.iterator();
		while(iter.hasNext()) {
			writer.append(iter.next());
			writer.append("\n");
		}
		
		writer.close();
	}
	
	public void testBuildNewTerm() throws Exception {
		InputStream is = DictionaryTest.class.getResourceAsStream("/source.txt");
		BufferedReader reader= new BufferedReader(new InputStreamReader(is));  
		String line = reader.readLine();
		
		HashSet<String> newTerms = new HashSet<String>();
		
		// select terms which don't exist in the dictionary
		while(line!=null && line.length()>0) {
			line = line.replaceAll("\\([^\\)]+\\)", "");
			if(line.length()>0 && line.compareTo("가")>=0) newTerms.add(line);
			
			line = reader.readLine();
		}
		
		// print out terms to file
		FileWriter writer = new FileWriter(new File("new_term_n.txt"));
		
		Iterator<String> iter = newTerms.iterator();
		while(iter.hasNext()) {
			writer.append(iter.next());
			writer.append("\n");
		}
		
		writer.close();
	}

}
