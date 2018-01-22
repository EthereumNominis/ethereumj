package org.nominis.core;

import org.ethereum.core.Account;
import org.ethereum.core.Block;
import org.ethereum.core.Transaction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BlockGeneratorTest {

    private accountGenerator aGen;
    private TransactionGenerator txGen;
    private ArrayList<Account> accounts;
    private BlockGenerator blockGen;



    @Test
    public void generateBlocks(){
        aGen = new accountGenerator(100);
        accounts = aGen.getGeneratedAccounts();
        txGen = new TransactionGenerator(accounts);

        ArrayList<Transaction> txs = txGen.generateSignedTransactions(100, true);
        blockGen = new BlockGenerator();
        List<Block> blocks = blockGen.queueBlocks(10,txs);
        for( Block b : blocks){
            System.out.println(b.toString());
        }

    }

}
