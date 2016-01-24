package hr.mikrotron.bastex;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class BatchExtract {

	private String source;
	private SortedSet<String> fileNames = new TreeSet<String>();
	private List<BankStatement> bss = new ArrayList<BankStatement>();
	private double checkBalance = 0;
	
	public BatchExtract (String source){
		this.source = source;
		if (readFiles()){
			parseFiles();
			//TODO export to CSV
		}else{
			write("Nema datoteka za čitanje!");
		}
		
	}
	
	private boolean readFiles(){
		File[] files = new File(source).listFiles();
		if (files==null) return false;	//source does not exist or is empty
		for (File f : files){
			if (f.isFile()) fileNames.add(f.getName());
		}
		return true;
	}
	
	private void parseFiles(){
		try{
			for (String file : fileNames){
				file=fullFileName(file);
				write("Parsing " + file);
				PDFManager pm = new PDFManager(file);
				BankStatementParser bsp=new BankStatementParser(pm.getText());
				BankStatement bs=new BankStatement();
				bsp.parse(bs);
				if (bs.getInputBalance()-checkBalance < BaStEx.EPS){
					checkBalance = bs.getOutputBalance();
					bss.add(bs);
				}else{
					write("Input balance in statement " + bs.getNumber() + " is " + 
							bs.getInputBalance() + " but should be " + checkBalance);
					return;
				}
			}			
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
		
	private String fullFileName(String file){
		return source + "/" + file;
	}
	
	private void write(String line){
		System.out.println(line);
	}
	
	public double getCheckBalance() {
		return checkBalance;
	}

	public void setCheckBalance(double checkBalance) {
		this.checkBalance = checkBalance;
	}
}
