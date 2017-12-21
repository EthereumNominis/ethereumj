package org.ethereum.mithril;


        import com.typesafe.config.ConfigException;
        import org.apache.commons.codec.binary.Hex;
        import org.ethereum.config.SystemProperties;
        import org.ethereum.core.Transaction;
        import org.ethereum.core.Account;
        import org.ethereum.core.Block;
        import org.ethereum.crypto.ECKey;
        import org.ethereum.facade.Ethereum;
        import org.ethereum.util.ByteUtil;
        import org.ethereum.util.blockchain.StandaloneBlockchain;
        import org.junit.AfterClass;
        import org.junit.BeforeClass;
        import org.junit.Test;

        import java.math.BigInteger;
        import java.nio.charset.StandardCharsets;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Random;

        import static org.ethereum.TestUtils.randomBytes;
        import static org.ethereum.util.ByteUtil.toHexString;


public class txGenerator {

    public final List<Account> accounts;
    public Random rand;


    public txGenerator(ArrayList<Account> accs) {

        this.accounts = accs;
        this.rand = new Random();
    }

    private Transaction randomTransaction() {
        if(accounts !=null){
            Transaction transaction = Transaction.createDefault(toHexString(randomAccount().getAddress()), new BigInteger(randomBytes(2)), new BigInteger(randomBytes(1)), null);
            transaction.sign(randomAccount().getEcKey());
            return transaction;
        }
        else {
            System.out.println("accounts null");
            return null;
        }

    }

    private Transaction randomValidTx()throws NullPointerException{
        Account a = randomAccount();
        BigInteger amount;
        do {
            amount = new BigInteger(a.getBalance().bitLength(), this.rand);
        } while (amount.compareTo(a.getBalance()) >= 0);
        if(accounts != null){
            Transaction transaction = Transaction.createDefault(toHexString(randomAccount().getAddress()), amount, new BigInteger(randomBytes(1)), null);
            return transaction;
        }
        else{
            System.out.println("Accounts null");
            return null;
        }
    }

//    private byte[] createTx(byte[] senderPrivateKey, byte[] receiveAddress, byte[] data) throws InterruptedException {
//        byte[] fromAddress = ECKey.fromPrivate(senderPrivateKey).getAddress();
//
//        BigInteger nonce = senderNode.getRepository().getNonce(fromAddress);
//        Integer chainId = senderNode.getChainIdForNextBlock();
//
//        Transaction tx = new Transaction(
//                ByteUtil.bigIntegerToBytes(nonce),
//                ByteUtil.longToBytesNoLeadZeroes(senderNode.getGasPrice()),
//                ByteUtil.longToBytesNoLeadZeroes(200000),
//                receiveAddress,
//                ByteUtil.bigIntegerToBytes(BigInteger.valueOf(1)),  // 1 gwei
//                data,
//                chainId);
//
//        tx.sign(ECKey.fromPrivate(senderPrivateKey));
//        System.out.println("Raw transaction data: 0x{}" + Hex.encodeHexString(tx.getEncodedRaw()));
//
//        new Transaction(tx.getEncodedRaw()).verify(); // <-- this throws an exception
//
//        // senderNode.submitTransaction(tx);
//        // System.out.println("<=== Sending transaction: " + tx);
//
//        return tx.getHash();
//    }

    private Account randomAccount() throws NullPointerException {
        if(accounts != null)
            return accounts.get(rand.nextInt(accounts.size()));
        else {
            System.out.println("Null accounts");
            return null;
        }


    public Transaction[] generateRandomTxArray(int numTx) {
        int counter = 0;
        Transaction[] txs = new Transaction[numTx];

        while(counter < numTx) {
                txs[counter] = randomTransaction();
                counter++;
            }
        return txs;

    }
    public Transaction[] generateValidTxArray(int numTx){
        int counter = 0;
        Transaction[] txs = new Transaction[numTx];

        while(counter < numTx) {
            txs[counter] = randomValidTx();
            counter++;
        }

        return txs;

    }



    }



//    @Test
//    public List<Transaction> generateValidTx(){
//        return;
//    }



