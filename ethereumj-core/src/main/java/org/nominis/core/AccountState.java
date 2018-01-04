package org.nominis.core;

import org.ethereum.config.SystemProperties;
import org.ethereum.util.RLP;
import org.ethereum.util.RLPList;

import java.math.BigInteger;

public class AccountState extends org.ethereum.core.AccountState {

    /**
     * A scalar value representing importance level (the number of point) owned by the address.
    **/
    private final BigInteger importanceScore;


    /**-----------------------------------------
     * constructors
    -----------------------------------------**/

    public AccountState(SystemProperties config, BigInteger importanceScore) {
        super(config);
        this.importanceScore = importanceScore;
    }

    public AccountState(BigInteger nonce, BigInteger balance, BigInteger importanceScore) {
        super(nonce, balance);
        this.importanceScore = importanceScore;
    }

    public AccountState(BigInteger nonce, BigInteger balance, byte[] stateRoot, byte[] codeHash, BigInteger importanceScore) {
        super(nonce, balance, stateRoot, codeHash);
        this.importanceScore = importanceScore;
    }




    public AccountState(byte[] rlpData, BigInteger importanceScore) {
        super(rlpData);
        this.importanceScore = importanceScore;
    }

    public AccountState(byte[] rlpData){
        super(rlpData);
        RLPList items = (RLPList) RLP.decode2(rlpData);
        this.importanceScore = items.get(0).getRLPData() == null ? BigInteger.ZERO
                : new BigInteger(1, items.get(4).getRLPData());
    }


    /**-----------------------------------------
     * get importance score
    -----------------------------------------**/
    public BigInteger getImportanceScore() {
        return importanceScore;
    }

    /**-----------------------------------------
     * Increase importance score
     * This function is primitively designed for ADDING values to importance score within each of block.
     * The procedure should be applied for rewarding purpose.
    -----------------------------------------**/
    public AccountState withImportanceScoreIncrement(BigInteger value){
        return new AccountState(super.getNonce(), super.getBalance(), super.getStateRoot(), super.getCodeHash(), this.getImportanceScore().add(value));
    }




    @Override
    public byte[] getEncoded(){
        byte[] accountStateRlpEncode = null;
        byte[] nonce = RLP.encodeBigInteger(super.getNonce());
        byte[] balance = RLP.encodeBigInteger(super.getBalance());
        byte[] stateRoot = RLP.encodeElement(super.getStateRoot());
        byte[] codeHash = RLP.encodeElement(super.getCodeHash());
        byte[] importanceScore = RLP.encodeBigInteger(this.getImportanceScore());
        accountStateRlpEncode = RLP.encodeList(nonce,balance,stateRoot,codeHash,importanceScore);
        return accountStateRlpEncode;
    }


}
