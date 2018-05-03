package blockchain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockChain {
	
	public List<Block> chain = new ArrayList<>();
	
	public BlockChain() {
		this.chain.add(createGenesisBlock());
	}

	public List<Block> getChain() {
		return chain;
	}
	
	public Block createGenesisBlock() {
		Block genesisBlock = new Block();
		genesisBlock.setIndex(0);
		genesisBlock.setPrevious_hash("0");
		Transaction transaction = new Transaction("source", "destination","genesis block contract", 1000L);
		genesisBlock.setTransaction(transaction);
		genesisBlock.setHash("0");
		return genesisBlock;
		
	}
	public Block getLatestBlock() {
		return chain.get(chain.size()-1);
	}
	
	public void addNewBlock(Block block) {
		block.setPrevious_hash(getLatestBlock().getHash());
		block.setHash(block.calculateHash());
		chain.add(block);
	}
	
	public boolean isChainValid(BlockChain blockChain) {
		
		for(int i=1; i< blockChain.getChain().size() ; i++ ) {
			
			Block previousBlock = blockChain.getChain().get(i-1);
			Block currentBlock = blockChain.getChain().get(i);
			
			if(!currentBlock.getHash().equals(currentBlock.calculateHash())) {
				System.out.println("Current Block hash do not match!");
				return false;
			}
			
			if(!previousBlock.getHash().equals(currentBlock.getPrevious_hash())) {
				
				System.out.println("Previous Block hash do not match!");
				return false;
			}	
		}
		return true;
		
	}
	
	
	

}
