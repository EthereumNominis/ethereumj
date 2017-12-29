package org.nominis.export;

import org.ethereum.core.Account;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.ethereum.mithril.accountGenerator;
import org.spongycastle.util.encoders.Hex;

import static org.ethereum.util.blockchain.EtherUtil.Unit.ETHER;
import static org.ethereum.util.blockchain.EtherUtil.convert;

/**
 * class_name: preallocGenesisTest
 * package: org.nominis.export
 * describe: TODO
 * create_user: Harrison Hicks
 * create_date: 12/29/17
 * create_time: 9:33 AM
 **/

public class preallocGenesisTest {

    //WARNING YOU WILL WANT TO ALTER THIS STRING FOR YOUR LOCAL MACHINE !!!
    public static final String PATH = "/Users/harrisonhicks/Documents/github_nominis/ethereumj/ethereumj-core/src/test/java/org/nominis/export/resources/allocated-genesis.json";

    @Test
    public void toJsonFile() {
        Random rand = new Random();
        accountGenerator aGen = new accountGenerator(5);
        ArrayList<Account> Accounts = aGen.getGeneratedAccounts(12);
        JSONObject addresses = new JSONObject();
        JSONObject genesisJSon = new JSONObject();
        JSONObject balance;
        for(Account a : Accounts ) {
            balance = new JSONObject();
            balance.put("balance",convert(Math.abs(rand.nextLong()), ETHER).toString());
            addresses.put(Hex.toHexString(a.getAddress()),balance);

        }

        genesisJSon.put("alloc",addresses);
        genesisJSon.put("nonce","0x0000000000000000");
        genesisJSon.put("difficulty","0x10");
        genesisJSon.put("mixhash","0x0000000000000000000000000000000000000000000000000000000000000000");
        genesisJSon.put("coinbase","0x0000000000000000000000000000000000000000");
        genesisJSon.put("timestamp","0x00");
        genesisJSon.put("parentHash","0x0000000000000000000000000000000000000000000000000000000000000000");
        genesisJSon.put("extraData","0x11bbe8db4e347b4e8c937c1c8370e4b5ed33adb3db69cbdb7a38e1e50b1b82fa");
        genesisJSon.put("gasLimit","0x1000000000");

        // try-with-resources statement based on post comment below :)
        try (FileWriter file = new FileWriter(PATH,false)) {
            file.write(genesisJSon.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + genesisJSon);
        }
        catch(IOException e)
        {
            System.out.println("Java IO exception caught when trying to write to file");
        }
    }
}
