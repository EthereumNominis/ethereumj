package org.nominis.core;

import com.typesafe.config.ConfigException;
import org.ethereum.core.Account;
import org.ethereum.core.Transaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.ethereum.util.blockchain.EtherUtil.Unit.ETHER;
import static org.ethereum.util.blockchain.EtherUtil.convert;
import static org.nominis.export.TestUtils.randomBytes;
import static org.ethereum.util.ByteUtil.toHexString;


public class createTransactions {

    public static List<Account> accounts;
    public static Random rand;


    public static ArrayList<Transaction> getTransactions(ArrayList<Account> accs, int numTx) {

        accounts = accs;
        rand = new Random();
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        try {
            for (int i = 0; i < numTx; i++) {
                transactions.add(randomValidTx());
            }
        }
        catch(NullPointerException e)
        {
            return null;
        }
            return transactions;
    }

    private static Transaction randomTransaction() {
        try{
            Transaction transaction = Transaction.createDefault(toHexString(randomAccount().getAddress()), new BigInteger(randomBytes(2)), new BigInteger(randomBytes(1)));
            transaction.sign(randomAccount().getEcKey());
            return transaction;
        }
        catch(NullPointerException e) {
            return null;
        }

    }

    private static Transaction randomValidTx()throws NullPointerException{
        Account a = randomAccount();
        BigInteger amount;

        /**
         * Find a random BigInteger between the current balance and zero. If the current balance is zero, just return this.
         */
        try{
            do {
                amount = convert(Math.abs((long) rand.nextInt(1000)), ETHER);
            } while (amount.compareTo(a.getBalance()) >= 0);
        }
        catch(NullPointerException e){
            amount = new BigInteger(0, rand);
        }

        if(accounts != null){
            //creates the transaction
            Transaction transaction = Transaction.createDefault(toHexString(randomAccount().getAddress()), amount, new BigInteger(randomBytes(1)));
            return transaction;
        }
        else{
            System.out.println("Accounts null");
            return null;
        }
    }

    private static Account randomAccount() throws NullPointerException {
        try{
            return accounts.get(rand.nextInt(accounts.size()));
        }
        catch(NullPointerException e) {
            System.out.println("Accounts field is null");
            return null;
        }
    }
}
