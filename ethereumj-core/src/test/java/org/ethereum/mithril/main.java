package org.ethereum.mithril;

import org.apache.commons.codec.binary.Hex;
import org.ethereum.core.Account;
import org.ethereum.core.Transaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class main {

    public static void main(String[] args) {
        accountGenerator aGen = new accountGenerator(10);
        Random rand = new Random();

        /**
         * BigInteger test
         */
        BigInteger amount = new BigInteger(12,rand);
        System.out.println(amount);



        /**
         * Controls upper bound of randomly generated starting balance.
         */
        int numBits = 12;

        Account testAccount = new Account();
        testAccount.init();
        System.out.println(Hex.encodeHexString(testAccount.getAddress()));



        ArrayList<Account> accounts = aGen.getGeneratedAccounts(numBits);
        if(accounts.size() == 0) {
            System.out.println("account size equal zero, problem with getGeneratedAccount function");
            throw new NullPointerException();
        }

        int counter = 0;

        /**
         * This iterates through the recently created accounts and list the balance (should be zero) the address number and private key.
         */

        for (Account x : accounts) {
            System.out.println("Account " + counter);

            try{
                System.out.println(x.getBalance());
            }catch(NullPointerException e ){
                System.out.println("Balance = 0");}

            System.out.println("Hex Address: "+Hex.encodeHexString(x.getAddress()));
            System.out.println("Private Key: "+x.getEcKey().getPrivKey());
            counter++;
        }

        /**
         * Create a transaction generator object and generate transactions by inserting number of
         */
        txGenerator txGen = new txGenerator(accounts);

        Transaction[] randomTransactions = txGen.generateRandomTxArray(5);
        Transaction[] randomValidTransactions = txGen.generateValidTxArray(5);
        counter = 0;

        for(Transaction tRandom : randomTransactions){
            System.out.println("Transaction "+counter);
            System.out.println(tRandom.toString());
            System.out.println("Raw transaction "+counter+" data: 0x{}" + Hex.encodeHexString(tRandom.getEncodedRaw()));
            counter++;
        }
        counter = 0;

        for(Transaction tValid : randomValidTransactions){
            System.out.println("Transactions"+counter);
            System.out.println(tValid.toString());
            System.out.println("Raw transaction "+counter+" data: 0x{}" + Hex.encodeHexString(tValid.getEncodedRaw()));
            counter++;
        }

    }
}