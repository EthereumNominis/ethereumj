package org.nominis.core;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.math.BigInteger;

public class AccountStateTest {



    @Test
    public void getEncodedTest() {
        BigInteger nonce = BigInteger.ZERO;
        BigInteger balance = BigInteger.valueOf(2).pow(200);
        BigInteger importanceScore = BigInteger.valueOf(1000000);
        AccountState acct = new AccountState(nonce,balance,importanceScore);
        System.out.println(  Hex.encodeHex(acct.getEncoded())  );
    }
}