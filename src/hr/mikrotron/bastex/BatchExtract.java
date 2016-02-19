package hr.mikrotron.bastex;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;


/**Batch processing of multiple bank statement files
 * @author prexy
 * @version 0.1
 */
public class BatchExtract {

	private String source;
	private SortedSet<String> fileNames = new TreeSet<String>();
	private List<BankStatement> bss = new ArrayList<BankStatement>();
	private double checkBalance = 0;
	
	
	/**Runs batch extract for multiple bank statement PDF files and
	 * exports data to .csv file
	 * @param source path to bank statement files
	 */
	public BatchExtract (String source){
		this.source = source;
		if (readFiles()){
			parseFiles();
			Export.toCSV(bss, source + "/izvodi.csv");
		}else{
			System.out.println("Nema datoteka za čitanje!");
		}
		
	}
	
	private boolean readFiles(){
		File[] files = new File(source).listFiles();
		if (files==null) return false;	//source does not exist or is empty
		for (File f : files){
			if (f.isFile() && isBankStatementPDF(f)) fileNames.add(f.getName());
		}
		return true;
	}
	
	private void parseFiles(){
		try{
			for (String file : fileNames){
				file=fullFileName(file);
				System.out.println("Parsing " + file);
				PDFManager pm = new PDFManager(file);
				BankStatementParser bsp=new BankStatementParser(pm.getText());
				BankStatement bs=new BankStatement();
				bsp.parse(bs);
				if (bs.getInputBalance()-checkBalance < BaStEx.EPS || bss.size()==0){
					checkBalance = bs.getOutputBalance();
					bss.add(bs);
				}else{
					System.out.println("Input balance in statement " + bs.getNumber() + " is " + 
							bs.getInputBalance() + " but should be " + checkBalance);
					return;
				}
			}			
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	/**Determines if file is expected PDF bank statement
	 * @param f file to check
	 * @return true if file meets all conditions
	 */
	private boolean isBankStatementPDF(File f){
		if (f.getName().toLowerCase().endsWith(".pdf") && 	//TODO should relay on mime type not the extension
			f.getName().substring(10,11).equals("-") &&		//first 10 characters are account number
			f.getName().substring(19,26).equals("_HR_MER")) return true;
		return false;
	}
	
	private String fullFileName(String file){
		return source + "/" + file;
	}
	
}
