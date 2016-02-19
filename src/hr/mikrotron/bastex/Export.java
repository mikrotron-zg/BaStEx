package hr.mikrotron.bastex;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.List;

/**Exports extracted bank statements
 * @author prexy
 * @version 0.1
 */
public abstract class Export {

	private static StringBuilder sb = new StringBuilder();
	private static final char d=';'; //delimiter - not using comma because it is sometimes part of data
	private static int atn=0; //is there any additional transaction number?
	
	/**Exports transactions to CSV file
	 * @param bss list of bank statements
	 * @param outputFile full name for output file
	 */
	public static void toCSV(List<BankStatement> bss, String outputFile){
		sb.append(CSVheader());
		for (BankStatement bs : bss){
			for (Transaction t : bs.getTransactions()){
				sb.append(CSVtransaction(t));
				writeToFile(outputFile, sb.toString());
				if (t.getAdditionalTransactionNumber().length()>0) atn++;
			}
		}
		//Additional transaction number occurs very rarely and it is not being extracted at the moment
		System.out.println("Additional transaction number: " + atn);
		System.out.println("Extraction done!");
	}
	
	/**CSV column header
	 * @return single line CSV column header
	 */
	private static String CSVheader(){
		return "Broj transakcije" + d + "IBAN" + d + "Platitelj" + d +
				"Primatelj" + d + "Poziv na broj platitelja" + d +
				"Poziv na broj primatelja" + d + "Opis" + d + "Šifra" + d +
				"Datum valute" + d + "Datum izvršenja" + d + "Iznos" + d + "Stanje" + "\n";
	} 
	
	/**Builds CSV string for single transaction
	 * @param t transaction
	 * @return CSV string
	 */
	private static String CSVtransaction(Transaction t){
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		return t.getTransactionNumber() + d + t.getIBAN() + d + t.getPayer() + d +
				t.getRecipient() + d + t.getPayerReferenceModel() + " " + t.getPayerReferenceNumber() + d +
				t.getRecipientReferenceModel() + " " + t.getRecipientReferenceNumber() + d +
				t.getDescription() + d + t.getAssignementCode() + d + 
				sdf.format(t.getCurrencyDate()) + d + sdf.format(t.getExecutionDate()) + d +
				t.getAmount() + d + t.getBalance() + "\n";
	}
		
	/**Writes output file 
	 * @param fileName full file name
	 * @param content file content
	 */
	private static void writeToFile(String fileName, String content){
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
		new FileOutputStream(fileName), "utf-8"))) {
			writer.write(content);
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

}
