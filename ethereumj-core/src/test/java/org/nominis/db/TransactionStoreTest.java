package org.nominis.db;

import org.ethereum.config.SystemProperties;
import org.ethereum.core.Block;
import org.ethereum.core.Transaction;
import org.ethereum.core.TransactionInfo;
import org.ethereum.crypto.ECKey;
import org.ethereum.datasource.inmem.HashMapDB;
import org.ethereum.datasource.leveldb.LevelDbDataSource;
import org.ethereum.db.TransactionStore;
import org.ethereum.util.blockchain.SolidityContract;
import org.ethereum.util.blockchain.StandaloneBlockchain;
import org.junit.AfterClass;
import org.junit.Test;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;

import static org.ethereum.util.blockchain.EtherUtil.Unit.ETHER;
import static org.ethereum.util.blockchain.EtherUtil.convert;

public class TransactionStoreTest {

    @AfterClass
    public static void cleanup() {

        SystemProperties.resetToDefault();
    }



    @Test
    public void simpleTest(){

        String contractSrc =    "contract Adder {" +
                                "  function add(int a, int b) returns (int) {return a + b;}" +
                                "}";


        HashMapDB<byte[]> txDb = new HashMapDB<>();
        StandaloneBlockchain bc = new StandaloneBlockchain();
        bc.getBlockchain().withTransactionStore(new TransactionStore(txDb));
        SolidityContract contract = bc.submitNewContract(contractSrc);
        bc.createBlock();
        contract.callFunction("add", 555, 222);
        Block b2 = bc.createBlock();
        contract.callFunction("add", 333, 333);
        Block b3 = bc.createBlock();
        Transaction tx1 = b2.getTransactionsList().get(0);
        TransactionInfo tx1Info = bc.getBlockchain().getTransactionInfo(tx1.getHash());
        byte[] executionResult = tx1Info.getReceipt().getExecutionResult();

        //System.out.println(Arrays.toString(executionResult));
        //System.out.println(Arrays.toString(new DataWord(777).getData()));
        //Assert.assertArrayEquals(new DataWord(777).getData(), executionResult);

        TransactionStore txStore = new TransactionStore(txDb);
        TransactionInfo tx1Info_ = txStore.get(tx1.getHash()).get(0);
//        byte[] detailedInfo = tx1Info_.getReceipt().getTransaction().getReceiveAddress();
//        System.out.println(executionResult);
//        System.out.println(txDb.getStorage());



    }





    @Test
    public void TransactionTrackTest(){

        ECKey Account1 = new ECKey();
        System.out.println(  "Account 1: " + Hex.toHexString(Account1.getAddress())   );
        ECKey Account2 = new ECKey();
        System.out.println(  "Account 2: " + Hex.toHexString(Account2.getAddress())   );
        ECKey Account3 = new ECKey();
        System.out.println(  "Account 3: " + Hex.toHexString(Account3.getAddress())   );




        StandaloneBlockchain sb = new StandaloneBlockchain();
        HashMapDB<byte[]> txDb = new HashMapDB<>();

        /**
         * Init balance
        **/

        sb.withAccountBalance(Account1.getAddress(), convert(100000000, ETHER));
        sb.withAccountBalance(Account2.getAddress(), convert(110000000, ETHER));
        sb.withAccountBalance(Account3.getAddress(), convert(120000000, ETHER));

        /**
         * submit Transactions
        **/
        Transaction tx1 = sb.createTransaction(Account1, 0, Account2.getAddress(), BigInteger.ONE,"tx1".getBytes()) ;

        sb.submitTransaction(tx1);


//        System.out.println(      Hex.toHexString(tx1.getHash())      );
//        System.out.println(      Hex.toHexString(tx1.getEncoded())      );
//        System.out.println(   tx1.getHash().length    );
//        System.out.println(   tx1.getEncoded().length    );
//        System.out.println(   tx1.getReceiveAddress().length   );

        /**
         * Transaction Storage using leveldb
        **/

        LevelDbDataSource transactionDB = new LevelDbDataSource("TransactionDB");
        transactionDB.init();
        transactionDB.put(tx1.getHash(),tx1.getEncoded());




    }





}