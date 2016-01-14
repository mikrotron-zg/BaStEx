package hr.mikrotron.bastex;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**Holds data extracted form PDF bank statement
 * @author prexy
 * @version 0.1
 */
public class BankStatement {

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
	
	
}
