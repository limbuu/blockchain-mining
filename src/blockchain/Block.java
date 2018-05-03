package blockchain;


import java.util.Date;



public class Block {

	private int index;
	private String previous_hash;
	private Transaction transaction;
	private String hash;
	private int nonce; //proof of work (POW) or mining
	private Long timeStamp;
	public Block() {
		super();
	}
		
	public Block(int index, Transaction transaction, String previous_hash) {
		super();
		this.index = index;
		this.previous_hash = previous_hash;
		this.transaction = transaction;
		this.hash = calculateHash();
		this.timeStamp = new Date().getTime();
	}


	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getPrevious_hash() {
		return previous_hash;
	}

	public void setPrevious_hash(String previous_hash) {
		this.previous_hash = previous_hash;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public String getHash() {
		return calculateHash();
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}
	
	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	/*public  String calculateHash() {
	return ""+(this.index+this.previous_hash+this.transaction+this.hash+this.nonce+this.createdDate).hashCode();
	}*/
	
	public  String calculateHash() {
		String calculatedHash = StringUtil.applySha256(previous_hash + transaction + nonce + timeStamp);
	    return calculatedHash;	
	}
	
	public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace("\0","0");
		System.out.println("Target :"+target);
		while(!hash.substring( 0, difficulty).equals(target)) {
			System.out.println("Hash Substring :"+hash.substring( 0, difficulty));
			nonce ++;
			System.out.println("Nonce :"+nonce);
			hash = calculateHash();
			System.out.println("Hash :"+hash);
		}
		System.out.println("Block is sucessfully mined!! : " + hash);
	}
	
	
	
	
}
	
	
