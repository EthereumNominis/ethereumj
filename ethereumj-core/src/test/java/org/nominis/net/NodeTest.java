package org.nominis.net;




import org.ethereum.crypto.ECKey;
import org.ethereum.net.rlpx.Node;
import org.junit.Test;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;

import static org.ethereum.crypto.HashUtil.sha3;
import static org.junit.Assert.assertEquals;


public class NodeTest {



    /**-----------------------------------------------------------
     * generally speaking, the Ethereum generate an account first,
     * by utilizing the private key, public key and account address info,
     * Node.java is able to build new node.
     * NOTE: A complete account would also need more info, like port, ip.
     * NOTE: Check @ethereumj-core\src\main\java\org\ethereum\net\rlpx\Node.java$ for more info
     -----------------------------------------------------------**/





    @Test
    public void generateNewRandomNode(){
        /**
         * generate random address/account
         * generate node info based on the account
        **/
        ECKey Account1 = new ECKey();
        System.out.println(  "Account 1: " + Hex.toHexString(Account1.getAddress())   );
        ECKey generatedNodeKey = ECKey.fromPrivate(sha3(Account1.getAddress()));
        String generatedNodeId = Hex.toHexString(generatedNodeKey.getNodeId());
        System.out.println("NodeID:\t" + generatedNodeId);
        Node node = new Node("enode://" + generatedNodeId + "@" + Hex.toHexString(Account1.getAddress()));
        node.setDiscoveryNode(true);
    }

    @Test
    public void generateCertainNode(){
        /**
         * generate a certain account
         * generate node info based on the account
        **/
        //seed
        BigInteger seedNumber = BigInteger.valueOf(1111111111);
        ECKey Account1 = ECKey.fromPrivate(seedNumber);
        System.out.println(  "Account 1: " + Hex.toHexString(Account1.getAddress())   );
        assertEquals("784c95c102441f6df296aca7920056ed7a424d1f",Hex.toHexString(Account1.getAddress()));
        ECKey generatedNodeKey = ECKey.fromPrivate(sha3(Account1.getAddress()));
        String generatedNodeId = Hex.toHexString(generatedNodeKey.getNodeId());
        System.out.println("NodeID:\t" + generatedNodeId);
        assertEquals("c4370be6ccd6704d6284978f6701a5018914e795bc6c4b4c3277f749a25be8b919f9bc155e292f7cbb8ff3ce54847c6a3bb222d3a241d2cba312ee8ba525638f", generatedNodeId);




    }


}
