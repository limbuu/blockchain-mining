package blockchain;


public class BlockChainClient {

	public static final int difficulty = 2;
	public static void main(String[] args) {

		BlockChain blockChain = new BlockChain();
	
		Transaction first_transaction = new Transaction("FM-New York City","FM-Nepal","FM-NYC should give FM-Nepal $1000 by May, 2018 ",1000L);
		Transaction second_transaction = new Transaction("FM-San Fransisco","Fm-India","FM-San Fransisco is liable to give Fm-India Rs1000 by 2020 ",1000L);
		
		Block first_block = new Block(1,first_transaction,blockChain.chain.get(0).getHash());
		System.out.println("First block is added.....");
		blockChain.addNewBlock(first_block);
		System.out.println("Starting to mine first block.....");
		blockChain.chain.get(1).mineBlock(difficulty);
		
		Block second_block = new Block(2,second_transaction, first_block.getHash());
		System.out.println("Second block is added.....");
		blockChain.addNewBlock(second_block);
		System.out.println("Starting to mine the second block.....");
		blockChain.chain.get(2).mineBlock(difficulty);
		

		System.out.println("Check the validity :\n"+blockChain.isChainValid(blockChain));
			
		System.out.println("Unique hash of blocks in the blockchain are : \n");
		for(int i=0; i<blockChain.chain.size() ; i++) {
			System.out.println(blockChain.chain.get(i).getHash());
			
		}
	
	}
}
