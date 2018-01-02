package org.nominis.db;

import org.ethereum.config.SystemProperties;
import org.ethereum.core.Block;
import org.ethereum.core.Transaction;
import org.ethereum.core.TransactionInfo;
import org.ethereum.datasource.inmem.HashMapDB;
import org.ethereum.db.TransactionStore;
import org.ethereum.util.blockchain.SolidityContract;
import org.ethereum.util.blockchain.StandaloneBlockchain;
import org.ethereum.vm.DataWord;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;


import java.util.Arrays;

import static org.junit.Assert.*;

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
        executionResult = tx1Info_.getReceipt().getExecutionResult();
        System.out.println(executionResult);
//        System.out.println(txDb.getStorage());



    }





}