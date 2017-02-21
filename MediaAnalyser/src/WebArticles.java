import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class WebArticles {
	
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
	static String file = "C:/Users/claire/Desktop/BNZ_WEB.pdf";

	
	//turn PDF file into text
	static String getText(File pdfFile) throws IOException {
		    
		PDDocument doc = PDDocument.load(pdfFile);
	    return new PDFTextStripper().getText(doc);
	}
	
	
	//write PDF file as new text file
	static void writePDFAsText() {
				
		try {
			String text = getText(new File(file));
		    FileWriter fw = new FileWriter("pdfTextWeb.txt");
		    fw.write(text);
		    fw.close();
		} catch (IOException e) {
		    System.out.println("IO Exception - unable to create or write file");
		}
	}
	
	
	//record all article IDs
	static void recordAllIDs() {

		try {
			String line = "";
			String id = "";

			FileReader fr = new FileReader("pdfTextWeb.txt");
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
	
	
	//open URLs
	static void openURL(String url) {

		try {
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
			keywordSearch(url);
		} catch (IOException e) {
			System.out.println("Unable to open URL");
		}	
	}

	
	//open URLs for each article
	static void openWebArticles() {

		try {
			String line = "";

			FileReader fr = new FileReader("pdfTextWeb.txt");
			BufferedReader br = new BufferedReader(fr);

			while ((line = br.readLine()) != null) {

				if (line.length() < 4) {
					continue;
				}

				if (line.contains("www.")) {
					String url = line;
					openURL(url);
				}
			}

			br.close();

		} catch (FileNotFoundException e) {
			System.out.println("Text file not found");
		} catch (IOException e) {
			System.out.println("IO Exception");
		}
	}
	

	//search each web article for keywords and add to appropriate lists
	static void keywordSearch(String url) {
		
		try {
		    // Create a URL for the desired page
			URL urlToOpen = new URL(url); 
			
			// Create new file to write output to
			FileWriter fw = new FileWriter("webText.txt");
			
			String article = "";

			// Read all the text returned by the server
			//stop writing over current text in file and then strip out HTML
			BufferedReader br = new BufferedReader(new InputStreamReader(urlToOpen.openStream()));
			String line = "";
			
			while ((line = br.readLine()) != null) {
				article = article + line;
			}
			
			fw.write(article);
			
			br.close();
			fw.close();
			
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL");
		
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
		
		openWebArticles();
		
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
