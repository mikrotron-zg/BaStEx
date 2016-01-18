package hr.mikrotron.bastex;

import java.io.*;

/**BankStatementExtractor command line version used for text extraction testing
 * @author prexy
 * @version 0.1
 */
public class BaStEx {

	public static final boolean DEBUG=true; //needs to be false for production code !!!
	
	public static void main(String[] args) {
		String testFile="testdata/pbztest_big.pdf";
		try{
			System.out.println("Početak...\n\n");
			//String res=new PDFManager(testFile).getText();
			//writeToFile(testFile + ".out", res);
			//System.out.println(clearedText(res));
			BankStatementParser bsp=new BankStatementParser(new PDFManager(testFile).getText());
			BankStatement bs=new BankStatement();
			bsp.parse(bs);
			System.out.println("\n\n\nRezultat: " + bs.getDebitTransactionsTotal());
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	static void writeToFile(String fileName, String content){
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
		new FileOutputStream(fileName), "utf-8"))) {
			writer.write(content);
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	static String clearedText (String text){
		String strings[]=text.split("\n");
		StringBuilder sb=new StringBuilder();
		for (int i=0 ; i<strings.length ; i++){
			if (!strings[i].trim().isEmpty()) sb.append(strings[i].trim()+"\n");
		}
		return sb.toString();
	}
	
}
