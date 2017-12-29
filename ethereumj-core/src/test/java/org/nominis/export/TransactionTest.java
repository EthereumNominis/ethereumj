package org.nominis.export;

import org.apache.commons.codec.binary.Hex;
import org.ethereum.core.Account;
import org.ethereum.core.Transaction;
import org.junit.Test;

import java.util.ArrayList;

public class TransactionTest {

    /**
     *Testing the simple randomTransaction method from nominis.export createTransactions
     */
    @Test
    public void SingleTransaction(){
        System.out.println(createTransactions.randomTransaction().toString());

    }
    /**
     *
     */
    @Test
    public void multipleTransactions(){
        accountGenerator aGen = new accountGenerator(5);
        ArrayList<Account> accounts = aGen.getGeneratedAccounts(12);
        Transaction[] randomTransactions = createTransactions.getTransactions(accounts,10);
        
        int counter = 0;
        for(Transaction tRandom : randomTransactions){
            System.out.println("Transaction "+counter);
            System.out.println(tRandom.toString());
            System.out.println("Raw transaction "+counter+" data: 0x{}" + Hex.encodeHexString(tRandom.getEncodedRaw()));
            counter++;
        }

    }
}
