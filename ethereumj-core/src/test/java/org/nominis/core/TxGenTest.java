package org.nominis.core;

import org.ethereum.core.Account;
import org.ethereum.core.Transaction;
import org.junit.Test;

import java.util.ArrayList;

public class TxGenTest {


    public static accountGenerator agen;
    public static ArrayList<Account> accounts;
    public static TransactionGenerator txGen;

    @Test
    public void generateTransaction(){
        agen = new accountGenerator(100);
        accounts = agen.getGeneratedAccounts();
        txGen = new TransactionGenerator(accounts);

        ArrayList<Transaction> txs = txGen.generateSignedTransactions(100, true);

        for(Transaction t : txs){
            System.out.println(t.toString());
        }
    }

    @Test
    public void JSONTest(){
        agen = new accountGenerator(100);
        accounts = agen.getGeneratedAccounts();
        txGen = new TransactionGenerator(accounts);
        txGen.generateSignedTransactions(100, true);
        System.out.println(txGen.getTxListJSON());
    }








}
