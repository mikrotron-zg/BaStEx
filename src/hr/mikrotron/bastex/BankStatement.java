package hr.mikrotron.bastex;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**Holds data extracted form PDF bank statement
 * @author prexy
 * @version 0.1
 */
public class BankStatement {

	final static double EPS=0.0001; //double comparison precision
	private int number;
	private Date date;
	private String currency;
	private double inputBalance;
	private double outputBalance;
	private double debitTransactionsTotal; //ukupni dugovni promet
	private double creditTransacionsTotal; //ukupni potražni promet
	private List<Transaction> transactions = new ArrayList<Transaction>();
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getInputBalance() {
		return inputBalance;
	}
	public void setInputBalance(double inputBalance) {
		this.inputBalance = inputBalance;
	}
	public double getOutputBalance() {
		return outputBalance;
	}
	public void setOutputBalance(double outputBalance) {
		this.outputBalance = outputBalance;
	}
	public double getDebitTransactionsTotal() {
		return debitTransactionsTotal;
	}
	public void setDebitTransactionsTotal(double debitTransactionsTotal) {
		this.debitTransactionsTotal = debitTransactionsTotal;
	}
	public double getCreditTransacionsTotal() {
		return creditTransacionsTotal;
	}
	public void setCreditTransacionsTotal(double creditTransacionsTotal) {
		this.creditTransacionsTotal = creditTransacionsTotal;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public Transaction getTransaction(int index){
		try{
			return transactions.get(index);
		}
		catch (IndexOutOfBoundsException iob){
			return null;
		}
	}
	
	public boolean addTransaction(Transaction transaction){
		if (this.isComplete()){
			return false;	//don't add transaction, bank statement is complete!
		}else{
			transactions.add(transaction);
			return true;
		}
	}
	
	public int getTransactionsCount(){
		return transactions.size();
	}
	
	/**Checks if balance, credit, debit and total of transactions fits bank statement data
	 * @return true if bank statement and transactions data correspond to each other
	 */
	public boolean isComplete(){
		if (transactions.size()==0) return false;	// bank statement must have transactions!
		return balanceCheck() && totalCheck() && creditCheck() && debitCheck();
	}
	
	/**Checks if sum of all transactions equals bank statement's input and output balance
	 * @return true if transactions sum checks out with statement's balance data
	 */
	private boolean totalCheck(){
		double total=0;
		for (Transaction t : transactions) total += t.getAmount();
		if (Math.abs(total - (outputBalance-inputBalance)) <= EPS) return true; else return false;
	}
	
	/**Checks if sum of debit (outgoing) transactions equals bank statement's 
	 * debit transactions total 
	 * @return true if it checks out
	 */
	private boolean debitCheck(){
		double debit=0;
		for (Transaction t : transactions){
			if (t.getAmount()<0) debit += t.getAmount();
		}
		if (Math.abs(debit - debitTransactionsTotal) <= EPS) return true; else return false;
	}
	
	/**Checks if sum of credit (incoming) transactions equals bank statement's 
	 * credit transactions total
	 * @return true if it checks out
	 */
	private boolean creditCheck(){
		double credit=0;
		for (Transaction t : transactions){
			if (t.getAmount()>0) credit += t.getAmount();
		}
		if (Math.abs(credit - creditTransacionsTotal) <= EPS) return true; else return false;
	}
	
	/**Checks if last transaction's balance equals bank statement's output balance
	 * @return true if it checks out
	 */
	private boolean balanceCheck(){
		if (Math.abs(transactions.get(transactions.size()-1).getBalance() - outputBalance) <= EPS) 
			return true;
			else return false;
	}
}
