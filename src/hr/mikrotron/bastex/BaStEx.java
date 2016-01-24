package hr.mikrotron.bastex;

import java.io.*;
import java.text.DateFormat;
import java.util.Locale;


/**BankStatementExtractor command line version used for text extraction testing
 * @author prexy
 * @version 0.1
 */
public class BaStEx {

	public static final boolean DEBUG=false; //needs to be false for production code !!!
	public static final double EPS=0.0001; //double comparison precision	
	
	public static void main(String[] args) {
		
		new BatchExtract("/home/prexy/Dropbox/MIKROTRON/izvodi");
		
	}
	
//	public static void main(String[] args) {
//		String testFile="testdata/pbztest_big.pdf";
//		try{
//			System.out.println("Početak...\n\n");
//			//String res=new PDFManager(testFile).getText();
//			//writeToFile(testFile + ".out", res);
//			//System.out.println(clearedText(res));
//			BankStatementParser bsp=new BankStatementParser(new PDFManager(testFile).getText());
//			BankStatement bs=new BankStatement();
//			bsp.parse(bs);
//			System.out.println("\n\n\nRezultat: " + bs.getTransactionsCount() + " transakcija na izvodu " 
//					+ bs.getNumber() + " od " + 
//					DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.GERMANY).format(bs.getDate()));
//			Transaction tr = bs.getTransaction(bs.getTransactionsCount()-1);
//			System.out.println("Zadnja transakcija: " + tr.getPayer() + " prema " + tr.getRecipient() + 
//					" u iznosu od " + tr.getAmount() + " kn. Stanje: " + tr.getBalance() + " kn");
//		}
//		catch (Exception e){
//			System.out.println(e.getMessage());
//		}
//	}
	
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
