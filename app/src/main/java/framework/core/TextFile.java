package framework.core;

import java.util.*;

public class TextFile {
	private ArrayList<String> Lines = new ArrayList<String>();
	
	public TextFile(String filename) {
		Scanner scanner = ResourceManager.get().getFile(filename);
		String line = null;
		
		while(true) {
			try {
				line = scanner.nextLine();
			} catch (NoSuchElementException e) {
				scanner.close();
				break;
			}
			
			if(line.length() > 0 && line.charAt(0) != '#') {
				Lines.add(line);
			}
		}
	}
	
	public ArrayList<String> GetLines() {
		ArrayList<String> ReversedLines = new ArrayList<String>();
		for(int i = Lines.size() - 1; i >= 0; --i) {
			ReversedLines.add(Lines.get(i));
		} return ReversedLines;
	}
}
