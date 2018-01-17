package org.nominis.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.ethereum.core.Account;
import org.ethereum.core.Transaction;
import org.ethereum.crypto.ECKey;
import org.ethereum.crypto.HashUtil;
import org.ethereum.util.ByteUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.spongycastle.util.BigIntegers;
import org.spongycastle.util.encoders.Hex;

import java.io.File;
import java.io.FileWriter;
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
    public JSONArray txListJSON;

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
    public ArrayList<Transaction> generateSignedTransactions(int numTransactions, Boolean test)throws NullPointerException{
        this.txListJSON = new JSONArray();
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


                txListJSON.add(this.txToJSON(tx));
            }
            return txs;
        }catch(NullPointerException e) {
            System.out.println("Accounts NULL, must instantiate accounts. txs empty");
            return new ArrayList<Transaction>();

        }
    }
    //write transactions to Json
    private JSONObject txToJSON(Transaction tx){
        JSONObject TxItem = new JSONObject();
        //amount
        if(tx.getValue()!=null){
            TxItem.put("value","0x"+Hex.toHexString(tx.getValue()));
        }
        else{
            TxItem.put("value","0");
        }
        // data
        if (tx.getData() != null){
            TxItem.put("data", "0x" + Hex.toHexString(tx.getData()));
        }else{
            TxItem.put("data","0");
        }
        // gasLimit
        if (tx.getGasLimit() != null){
            TxItem.put("gasLimit", "0x" + Hex.toHexString(tx.getGasLimit()));
        }else{
            TxItem.put("gasLimit",null);
        }
        //gasPrice
        if (tx.getGasPrice() != null){
            TxItem.put("gasPrice", "0x" + Hex.toHexString(tx.getGasPrice()));
        }else{
            TxItem.put("gasPrice",null);
        }
        //nonce
        if (tx.getNonce() != null){
            TxItem.put("gasNonce", "0x" + Hex.toHexString(tx.getNonce()));
        }else{
            TxItem.put("gasNonce",null);
        }
        //signature-r
        TxItem.put("r", "0x" + ByteUtil.toHexString(BigIntegers.asUnsignedByteArray(tx.getSignature().r)));
        //signature-s
        TxItem.put("s", "0x" + ByteUtil.toHexString(BigIntegers.asUnsignedByteArray(tx.getSignature().s)));
        //signature-v
        TxItem.put("v",  tx.getSignature().v);
        //receiver
        TxItem.put("to", "0x" + Hex.toHexString(tx.getReceiveAddress()));

        return TxItem;

    }

    public JSONArray getTxListJSON(){
        if(this.txListJSON!=null) {
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonParser jp = new JsonParser();
                JsonElement je = jp.parse(txListJSON.toJSONString());
                String prettyJson = gson.toJson(je);
                String basepath = new File("").getAbsolutePath();
                String fileName = basepath.concat("/src/main/java/org/nominis/export/mithril/resources/Nominis_Test_Transactions.json");
                FileWriter JsonFile = new FileWriter(fileName);
                JsonFile.write(prettyJson);
                JsonFile.flush();
                JsonFile.close();
            } catch (Exception e) {
                System.out.println("exception caught");
                e.printStackTrace();
            }
            return txListJSON;
        }
        else{
            System.out.println("JSON ARRAY NULL, RETURN EMPTY JSON ARRAY");
            return new JSONArray();
        }
    }



}
