package org.nominis.core;

import org.ethereum.core.Account;
import org.ethereum.core.Transaction;
import org.ethereum.crypto.ECKey;
import org.ethereum.crypto.HashUtil;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.ethereum.util.ByteUtil.toHexString;
import static org.ethereum.util.blockchain.EtherUtil.Unit.ETHER;
import static org.ethereum.util.blockchain.EtherUtil.convert;
import static org.nominis.export.TestUtils.randomBytes;

/**
* class_name: TransactionGenerator
* package: org.nominis.core
* describe:
 * TODO: Generate specified/general transactions for testing purpose
* create_user: Yaojin Sun
* create_date: 1/5/18
* create_time: 4:55 PM
**/




/**
* harrisonhicks
* 1/10/18
**/
public class TransactionGenerator{

    public static ArrayList<Account> accounts;
    public static Random rand = new Random();
    public String fixedGasLimit;
    public String fixedGasPrice;

    public TransactionGenerator(ArrayList<Account> accountList ){
        this.accounts = accountList;
    }

    //creates transaction with private key of reciever.
    //METHOD ONLY APPLICABLE IN TEST SCENARIO
    private Transaction createTxTest(BigInteger amount, String ReceiverPrivKey, String gasP, String gasAmount ){
        //converts from string to hash code, then to key object.
        byte[] ToPrivKey = ReceiverPrivKey.getBytes();
        ECKey ReceiverEcKey = ECKey.fromPrivate(ToPrivKey);
        byte[] gasPrice = Hex.decode(gasP);
        byte[] gas = Hex.decode(gasAmount);

        return new Transaction(null, gasPrice, gas, ReceiverEcKey.getAddress(),
                amount.toByteArray(),
                null);
    }

    private Transaction createTx(BigInteger amount, String ReceiverPubKey, String gasP, String gasAmount ){
        //converts from string to hash code, then to key object.
        ECKey ReceiverEcKey = ECKey.fromPublicOnly(ReceiverPubKey.getBytes());
        byte[] gasPrice = Hex.decode(gasP);
        byte[] gas = Hex.decode(gasAmount);

        return new Transaction(null, gasPrice, gas, ReceiverEcKey.getAddress(),
                amount.toByteArray(),
                null);
    }

    private Transaction signTransaction(String SenderPrivateKey, Transaction tx){
        try {
            byte[] senderPrivKey = HashUtil.sha3(SenderPrivateKey.getBytes());
            tx.sign(ECKey.fromPrivate(senderPrivKey));
            return tx;
        }
        catch(ECKey.MissingPrivateKeyException e){
            System.out.println("Missing Private Key, cannot sign transaction "+tx.toString());
            return tx;
        }
    }

    // test flag if you are using it in a test setting, otherwise test should be false
    public ArrayList<Transaction> generateTransactions(int numTransactions, Boolean test)throws NullPointerException{

        ArrayList<Transaction> txs = new ArrayList<Transaction>();
        Transaction tx;
        Account from;
        Account to;
        BigInteger amount;

        //check to make sure gas limits and prices have been instantiated.
        if(fixedGasLimit == null)fixedGasLimit = "1234567890";
        if(fixedGasPrice == null)fixedGasPrice = "1234567890";

        //create random transactions, signing each and adding to 'txs'
        try {
            for (int i = 0; i < numTransactions; i++) {

                //generate random BigInteger 'amount'
                try {
                    amount = convert(Math.abs((long) rand.nextInt(1000)), ETHER);
                } catch (NullPointerException e) {
                    amount = new BigInteger(0, rand);
                }

                //select random sender account
                to = accounts.get(rand.nextInt(accounts.size()));

                //Create Receiver Key
                String ReceiverKey;
                if (test) {
                    ReceiverKey = to.getEcKey().getPrivKey().toString();
                } else {
                    ReceiverKey = to.getEcKey().getPubKey().toString();
                }

                //create transaction
                tx = createTxTest(amount, ReceiverKey, fixedGasPrice, fixedGasLimit);

                //sign using random sender's key
                from = accounts.get(rand.nextInt(accounts.size()));
                tx.sign(from.getEcKey());

                //add to arraylist
                txs.add(tx);
            }
            return txs;
        }catch(NullPointerException e) {
            System.out.println("Accounts NULL, must instantiate accounts. txs empty");
            return new ArrayList<Transaction>();

        }
    }



}
