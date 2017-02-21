import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

//doesn't work for text in image

public class PaperArticles {
	
	//keywords to search for 
	static String keyword1 = "BNZ";
	static String keyword2 = "Bank of New Zealand";
	static String keyword3 = "Westpac";
	static String keyword4 = "ANZ";
	static String keyword5 = "ASB";
	static String keyword6 = "Kiwibank";
	
	
	//array list of all article IDs in the PDF file
	static ArrayList<String> allArticleIDs = new ArrayList<String>();
	static ArrayList<String> idsWithKeywords = new ArrayList<String>();
	static ArrayList<String> idsWithOutKeywords = new ArrayList<String>();
	
	
	//PDF file to run program on
	static String file = "C:/Users/claire/Desktop/20161128030024593.pdf";
	
	
	
	//turn PDF file into text
	static String getText(File pdfFile) throws IOException {
	    
		PDDocument doc = PDDocument.load(pdfFile);
	    return new PDFTextStripper().getText(doc);
	}
	
	
	//write PDF file as new text file
	static void writePDFAsText() {
				
		try {
			String text = getText(new File(file));
		    FileWriter fw = new FileWriter("pdftext.txt");
		    fw.write(text);
		    fw.close();
		} catch (IOException e) {
		    System.out.println("IO Exception - unable to create or write file");
		}
	}

		
	//record the IDs of each article
	static void recordAllIDs() {
		
		try {
			String line = "";
			String id = "";
			
			FileReader fr = new FileReader("pdftext.txt");
			BufferedReader br = new BufferedReader(fr);
			
			while ((line = br.readLine()) != null) {
				
				if (line.length() < 2) {
					continue;
				}
				
				if (line.substring(0, 2).equals("ID")) {
					id = line.substring(3, 12);
					
					if (!allArticleIDs.contains(id)) {
						allArticleIDs.add(id);
					}
				}		
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Text file not found");
		} catch (IOException e) {
			System.out.println("IO Exception");
		}
	}
	
	
	//record IDs of each article if it contains the keyword
	static void recordKeywordIDs() {
		
		try {
			String line = "";
			String id = "";
			String article = line;
			
			FileReader fr = new FileReader("pdftext.txt");
			BufferedReader br = new BufferedReader(fr);
			
			while ((line = br.readLine()) != null) {

				if (line.length() < 2) {
					continue;
				}
				
				if (line.substring(0, 2).equals("ID")) {
					
					id = line.substring(3, 12);
					
					if (article.contains(keyword1) || article.contains(keyword2) || article.contains(keyword3) || 
							article.contains(keyword4) || article.contains(keyword5) || article.contains(keyword6)) {
						
						if (!idsWithKeywords.contains(id) && !idsWithOutKeywords.contains(id)) {
							idsWithKeywords.add(id);
						} else if (!idsWithKeywords.contains(id) && idsWithOutKeywords.contains(id)) {
							idsWithOutKeywords.remove(id);
							idsWithKeywords.add(id);	
						} else if (idsWithKeywords.contains(id) && idsWithOutKeywords.contains(id)) {
							idsWithOutKeywords.remove(id);
						}
		
					} else {
						
						if (!idsWithOutKeywords.contains(id) && !idsWithKeywords.contains(id)) {
							idsWithOutKeywords.add(id);
						} else if (idsWithOutKeywords.contains(id) && idsWithKeywords.contains(id)) {
							idsWithOutKeywords.remove(id);
						}
					}
					
					article = "";
					
				} else if (!line.substring(0, 2).equals("ID")) {
					article += line;
				}
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Text file not found");
		} catch (IOException e) {
			System.out.println("IO Exception");
		}
	}
	
	
	//print array lists
	static void printLists(ArrayList<String> list) {
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	
	
		
	
	
	public static void main(String[] args) {
		
		writePDFAsText();

		recordAllIDs();

		recordKeywordIDs();
		
		
		System.out.println("List of all article IDs:");
		printLists(allArticleIDs);
		System.out.println();

		
		System.out.println("List of article IDs containing the keywords:");
		printLists(idsWithKeywords);
		System.out.println();
		
		System.out.println("List of article IDs NOT containing the keywords:");
		printLists(idsWithOutKeywords);	
		System.out.println();
	
	}


}
