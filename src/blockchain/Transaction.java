package blockchain;

public class Transaction {
	private String source;
	private String destination;
	private String contract;
	private Long Amount;
	public Transaction() {
		super();
	}
	public Transaction(String source, String destination, String contract, Long amount) {
		super();
		this.source = source;
		this.destination = destination;
		this.contract = contract;
		Amount = amount;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public String getContract() {
		return contract;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}
	public Long getAmount() {
		return Amount;
	}
	public void setAmount(Long amount) {
		Amount = amount;
	}
	
	
}
