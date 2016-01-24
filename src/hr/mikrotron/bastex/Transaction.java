package hr.mikrotron.bastex;

import java.util.Date;

/**Holds information about single transaction as well as balance
 * AFTER transaction is conducted. Once instantiated, transaction 
 * object contains only read-only data, i.e. transaction data 
 * cannot be changed
 * @author prexy
 * @version 0.1
 */
public class Transaction {

	private String transactionNumber;
	private String additionalTransactionNumber;
	private String IBAN;
	private String payer;
	private String recipient;
	private String payerReferenceModel;
	private String recipientReferenceModel;
	private String payerReferenceNumber;
	private String recipientReferenceNumber;
	private String description;
	private String assignementCode;
	private double exchangeRate;
	private Date currencyDate;
	private Date executionDate;
	private double amount;
	private double balance;
	
	public Transaction(String transactionNumber, String additionalTransactionNumber,
			String IBAN, String payer, String recipient, String payerReferenceModel,
			String recipientReferenceModel, String payerReferenceNumber, 
			String recipientReferenceNumber, String description, String assignementCode, 
			double exchangeRate, Date currencyDate, Date executionDate, double amount, double balance){
		
		this.transactionNumber=transactionNumber;
		this.additionalTransactionNumber=additionalTransactionNumber;
		this.IBAN=IBAN;
		this.payer=payer;
		this.recipient=recipient;
		this.payerReferenceModel=payerReferenceModel;
		this.recipientReferenceModel=recipientReferenceModel;
		this.payerReferenceNumber=payerReferenceNumber;
		this.recipientReferenceNumber=recipientReferenceNumber;
		this.description=description;
		this.assignementCode=assignementCode;
		this.exchangeRate=exchangeRate;
		this.currencyDate=currencyDate;
		this.executionDate=executionDate;
		this.amount=amount;
		this.balance=balance;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public String getAdditionalTransactionNumber() {
		return additionalTransactionNumber;
	}

	public String getIBAN() {
		return IBAN;
	}

	public String getPayer() {
		return payer;
	}

	public String getRecipient() {
		return recipient;
	}

	public String getPayerReferenceModel() {
		return payerReferenceModel;
	}

	public String getRecipientReferenceModel() {
		return recipientReferenceModel;
	}

	public String getPayerReferenceNumber() {
		return payerReferenceNumber;
	}

	public String getRecipientReferenceNumber() {
		return recipientReferenceNumber;
	}

	public String getDescription() {
		return description;
	}

	public String getAssignementCode() {
		return assignementCode;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public Date getCurrencyDate() {
		return currencyDate;
	}

	public Date getExecutionDate() {
		return executionDate;
	}

	public double getAmount() {
		return amount;
	}

	public double getBalance() {
		return balance;
	}

}
