package org.nominis.export;

import org.ethereum.core.*;
import org.ethereum.core.genesis.GenesisLoader;
import org.ethereum.util.blockchain.StandaloneBlockchain;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.spongycastle.util.encoders.Hex;


import static org.ethereum.util.FastByteComparisons.equal;
import static org.ethereum.util.blockchain.EtherUtil.Unit.ETHER;
import static org.ethereum.util.blockchain.EtherUtil.convert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * class_name: preallocGenesisTest
 * package: org.nominis.export
 * describe: TODO
 * create_user: Harrison Hicks
 * create_date: 12/29/17
 * create_time: 9:33 AM
 **/

public class StandardBlockMiningTest {
        // fields used together with @Parameter must be public

        private BlockchainImpl blockchain;

        private String PATH = "/Users/harrisonhicks/Documents/github_nominis/ethereumj/ethereumj-core/src/test/resources/genesis/allocated-genesis.json";

        private StandaloneBlockchain sb;

        private ArrayList<Account> accounts;

        private ArrayList<Transaction> transactions;

        private Genesis genesis;

        private final int numAccounts = 5;

        private long[] initialBalances;

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
        for (int i = 0; i < accounts.size(); i++) {
            balance = new JSONObject();

            //store initial amounts in array for later reference
            amount = Math.abs(rand.nextLong());
            initialBalances[i] = amount;

            //convert amount into weis and encode as JSON object
            balance.put("balance", convert(amount, ETHER).toString());
            addresses.put(Hex.toHexString(accounts.get(i).getAddress()), balance);

        }

        genesisJSon.put("alloc", addresses);
        genesisJSon.put("nonce", "0x0000000000000000");
        genesisJSon.put("difficulty", "0x10");
        genesisJSon.put("mixhash", "0x0000000000000000000000000000000000000000000000000000000000000000");
        genesisJSon.put("coinbase", "0x0000000000000000000000000000000000000000");
        genesisJSon.put("timestamp", "0x00");
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


        /**
         * This section of the test utilizes genesis loader class in an attempt to read from the genesis file created previously
         */
    }

    @Test
    public void readFromGenesis(){
        Random rand = new Random();
        genesis = GenesisLoader.loadGenesis(
                getClass().getResourceAsStream("/genesis/allocated-genesis.json"));
        final StandaloneBlockchain bc = new StandaloneBlockchain();
        bc.withGenesis(genesis);

        //First check that initialBalances and accounts are size-compatible
        assertEquals(accounts.size(), initialBalances.length);


        //select random account 'j' and check that balance of 'j' has been recorded to blockchain.
        int j = rand.nextInt(accounts.size());
        final byte[] randomAccount = accounts.get(j).getAddress();

        System.out.println(Hex.toHexString(accounts.get(j).getAddress()));
        final BigInteger retrievedBalance = bc.getBlockchain().getRepository().getBalance(randomAccount);
        final long actualBalance = initialBalances[j];

        assertEquals(BigInteger.valueOf(actualBalance), retrievedBalance);
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

