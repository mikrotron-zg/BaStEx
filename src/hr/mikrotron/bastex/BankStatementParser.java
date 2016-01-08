package hr.mikrotron.bastex;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**Parser for PDF bank statement extracted as plain text
 * @author prexy
 * @version 0.1
 */
public class BankStatementParser {

	private String rawRecord;
	private ArrayList<String> records=new ArrayList<String>();	//all lines that contain text
	private ArrayList<Integer> gaps=new ArrayList<Integer>();	//empty lines BEFORE the line with text
	private NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
	
	/**Constructor
	 * @param rawRecord output of PDFManager's getText
	 */
	public BankStatementParser(String rawRecord){
		this.rawRecord=rawRecord;
		extractRecords();
	}
	
	public void parse(BankStatement bankStatement) throws ParseException{
		parseBankStatementData(bankStatement);
		//TODO parseTransactions(bankStatement);
	}
	
	private void parseBankStatementData(BankStatement bs) throws ParseException{
		bs.setNumber(recordAsInt(findRecord("Izvadak br.:")+1));
		bs.setDate(recordAsDate(findRecord("Datum izvatka:")+1));
		bs.setInputBalance(recordAsDouble(findRecord("Stanje prethodnog izvatka:")+1));
		bs.setDebitTransactionsTotal(recordAsDouble(findRecord("Ukupni dugovni promet na dan:")+1));
		bs.setCreditTransacionsTotal(recordAsDouble(findRecord("Ukupni potražni promet na dan:")+1));
		bs.setOutputBalance(recordAsDouble(findRecord("Novo stanje:")+1));
	}
	
	/**Extracts lines containing text to <i>records</i> ArrayList, at the same time
	 * recording number of empty lines BEFORE each line of text and saving it to
	 * <i>gaps</i> ArrayList
	 */
	private void extractRecords(){
		int gapCounter=0;
		String strings[]=rawRecord.split("\n");
		for (int i=0 ; i<strings.length ; i++){
			if (strings[i].trim().isEmpty()){
				gapCounter++;
			}else{
				records.add(strings[i]);
				gaps.add(gapCounter);
				gapCounter=0;
			}
		}
		printResult();//debug only
	}
	
	private void printResult(){
		for (int i=0 ; i<records.size() ; i++){
			System.out.println("<" + i + ">   " + records.get(i) + "    [" + gaps.get(i) + "]");
		}
	}
	
	private int findRecord(String searchString){
		for (int i=0 ; i<records.size() ; i++){
			if (records.get(i).indexOf(searchString)>=0) return i;
		}
		return -1;
	}
	
	private double recordAsDouble(int recordIndex) throws ParseException, IndexOutOfBoundsException{
		return nf.parse(records.get(recordIndex).trim()).doubleValue();
	}
	
	private int recordAsInt(int recordIndex) throws ParseException, IndexOutOfBoundsException{
		return nf.parse(records.get(recordIndex).trim()).intValue();
	}
	
	private Date recordAsDate(int recordIndex) throws ParseException{
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		return df.parse(records.get(recordIndex).trim());
	}
}
