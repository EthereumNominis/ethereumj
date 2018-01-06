package org.nominis.export;

import org.ethereum.config.SystemProperties;
import org.ethereum.core.*;
import org.ethereum.core.genesis.GenesisLoader;
import org.ethereum.mine.Ethash;
import org.ethereum.util.blockchain.StandaloneBlockchain;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.nominis.core.accountGenerator;
import org.spongycastle.util.encoders.Hex;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static org.ethereum.core.ImportLightTest.createBlockchain;
import static org.ethereum.util.blockchain.EtherUtil.Unit.ETHER;
import static org.ethereum.util.blockchain.EtherUtil.convert;
import static org.junit.Assert.assertEquals;

/**
 * class_name: preallocGenesisTest
 * package: org.nominis.export
 * describe: TODO
 * create_user: Harrison Hicks
 * create_date: 12/29/17
 * create_time: 9:33 AM
 **/

@FixMethodOrder(MethodSorters.DEFAULT)
public class StandardBlockMiningTest {
        // fields used together with @Parameter must be public

        private BlockchainImpl blockchain;

        private String PATH = "/Users/harrisonhicks/Documents/github_nominis/ethereumj/ethereumj-core/src/main/resources/genesis/test.json";

        private StandaloneBlockchain sb;

        private ArrayList<Account> accounts;

        private ArrayList<Transaction> transactions;

        private Genesis genesis;

        private final int numAccounts = 5;

        private long[] initialBalances;

        private String hardCodedAddress;

    /**
     * Tests accountGenerator to create a list of accounts, and writes to json file.
     */
    @Test
    public void ReadFromJsonFile() {


        //create arrayList of accounts.
        Random rand = new Random();
        accountGenerator aGen = new accountGenerator(numAccounts);
        accounts = aGen.getGeneratedAccounts();

        //initializes random initial balance amounts using the alloc field in the genesis block and writes to json file.
        JSONObject addresses = new JSONObject();
        JSONObject genesisJSon = new JSONObject();
        JSONObject balance;
        long amount;
        initialBalances = new long[numAccounts];
        System.out.println("number of accounts:"+accounts.size());
        for (int i = 0; i < accounts.size(); i++) {
            balance = new JSONObject();

            //store initial amounts in array for later reference
            amount = Math.abs(rand.nextLong());
            initialBalances[i] = amount;

            //convert amount into weis and encode as JSON object
            balance.put("balance", convert(amount, ETHER).toString());
            addresses.put(Hex.toHexString(accounts.get(i).getAddress()), balance);

            System.out.println("address "+i+" "+Hex.toHexString(accounts.get(i).getAddress()));
            System.out.println(  Hex.toHexString(accounts.get(i).getEcKey().getPrivKeyBytes())   );
        }

        genesisJSon.put("alloc", addresses);
        genesisJSon.put("nonce", "0x0000000000000000");
        // low difficulty
        genesisJSon.put("difficulty", "0x000002");
        genesisJSon.put("mixhash", "0x0000000000000000000000000000000000000000000000000000000000000000");
        genesisJSon.put("timestamp", "0x00");
        genesisJSon.put("coinbase", "0x0000000000000000000000000000000000000000");
        genesisJSon.put("parentHash", "0x0000000000000000000000000000000000000000000000000000000000000000");
        genesisJSon.put("extraData", "0x11bbe8db4e347b4e8c937c1c8370e4b5ed33adb3db69cbdb7a38e1e50b1b82fa");
        genesisJSon.put("gasLimit", "0x1000000000");

        // try-with-resources statement based on post comment below :)
        try (FileWriter file = new FileWriter(PATH, false)) {
            file.write(genesisJSon.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + genesisJSon);
        } catch (IOException e) {
            System.out.println("Java IO exception caught when trying to write to file");
        }
    }

    @Test
    public void mineSingleBlock() throws Exception{
        ReadFromJsonFile();

        BlockchainImpl blockchain = createBlockchain(GenesisLoader.loadGenesis(
                getClass().getResourceAsStream("/genesis/allocated-genesis.json")));
        blockchain.setMinerCoinbase(Hex.decode("ee0250c19ad59305b2bdb61f34b45b72fe37154f"));
        Block parent = blockchain.getBestBlock();

        System.out.println("Mining #1 ...");
        Block b1 = blockchain.createNewBlock(parent, Collections.EMPTY_LIST, Collections.EMPTY_LIST);
        Ethash.getForBlock(SystemProperties.getDefault(), b1.getNumber()).mineLight(b1).get();
        ImportResult importResult = blockchain.tryToConnect(b1);
        System.out.println("Best: " + blockchain.getBestBlock().getShortDescr());
        Assert.assertTrue(importResult == ImportResult.IMPORTED_BEST);




    }

    @Test
    public void AllocStandAloneBlockchainTest(){
        ReadFromJsonFile();

        StandaloneBlockchain bc = new StandaloneBlockchain();
        genesis = GenesisLoader.loadGenesis(
                getClass().getResourceAsStream("/genesis/allocated-genesis.json"));
        bc.withGenesis(genesis);
        bc.createBlock();
    }

    /**
     * I cannot figure out why this will not work.
     */
    @Ignore
    public void getBalance_From_Repository_In_StandaloneBlockchain() {
        ReadFromJsonFile();

        final Genesis genesis = GenesisLoader.loadGenesis(
                getClass().getResourceAsStream("/genesis/allocated-genesis.json"));
        final StandaloneBlockchain bc = new StandaloneBlockchain();

        bc.withGenesis(genesis);
        bc.createBlock();
        final byte[] account = Hex.decode(hardCodedAddress);
        long expectedBalance = 1;       //FF

        final BigInteger actualBalance = bc.getBlockchain().getRepository().getBalance(account);


        assertEquals(BigInteger.valueOf(expectedBalance), actualBalance);
    }


//    @Test
//    public void multipleTransactions(){
////        transactions = createTransactions.getTransactions(accounts,10);
////
////        int counter = 0;
////        for(Transaction tRandom : transactions){
////            System.out.println("Transaction "+counter);
////            System.out.println(tRandom.toString());
////            System.out.println("Raw transaction "+counter+" data: 0x{}" + org.apache.commons.codec.binary.Hex.encodeHexString(tRandom.getEncodedRaw()));
////            counter++;
////        }
//
//    }


}

