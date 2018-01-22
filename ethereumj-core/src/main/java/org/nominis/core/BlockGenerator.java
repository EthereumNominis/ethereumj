package org.nominis.core;


import org.ethereum.core.Block;
import org.ethereum.core.BlockHeader;
import org.ethereum.core.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
* class_name: BlockGenerator
* package: org.nominis.core
* describe:
 * TODO: Generate a specified/general block for testing purpose
* create_user: Yaojin Sun
* create_date: 1/5/18
* create_time: 4:53 PM
**/
public class BlockGenerator {

    private static List<Block> Blocks;
    private static ArrayList<Transaction> transactions;

    public BlockGenerator(){
        transactions = new ArrayList<Transaction>();
        Blocks = new ArrayList<Block>();
    }


    public List<Block> queueBlocks(int blockSize, ArrayList<Transaction> txs) {
        transactions = txs;
        byte[] emptyArray =  new byte[0];
        byte[] recentHash = emptyArray;
        int i = 0;
        ArrayList<Transaction> blockTxs;
        BlockHeader blockHeader;
        while(!transactions.isEmpty()) {
            blockHeader = new BlockHeader(recentHash, emptyArray, emptyArray, emptyArray, emptyArray,
                    i, emptyArray, 0L, 0L, emptyArray, emptyArray, emptyArray);
            blockTxs = new ArrayList<Transaction>();

            while (blockTxs.size() <= blockSize && !transactions.isEmpty()) {
                    blockTxs.add(transactions.remove(transactions.size()-1));
            }

            recentHash = blockHeader.getHash();
            org.ethereum.core.Block block = new Block(blockHeader, blockTxs, new ArrayList<BlockHeader>());
            Blocks.add(block);
            i++;
        }

        return Blocks;
    }


}
