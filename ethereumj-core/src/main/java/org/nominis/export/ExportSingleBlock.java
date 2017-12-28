package org.nominis.export;


import org.ethereum.crypto.ECKey;
import org.ethereum.util.blockchain.StandaloneBlockchain;
import org.spongycastle.util.encoders.Hex;

import static org.ethereum.util.blockchain.EtherUtil.Unit.ETHER;
import static org.ethereum.util.blockchain.EtherUtil.convert;

/**
* class_name: ExportSingleBlock
* package: org.nominis.export
* describe: TODO
* create_user: Yaojin Sun
* create_date: 12/21/17
* create_time: 3:22 PM
**/





public class ExportSingleBlock {

    public static void toJsonFile(){
        System.out.println("--> *.Json");
        StandaloneBlockchain sb = new StandaloneBlockchain();
        ECKey alice = sb.getSender();
        ECKey bob = new ECKey();
        System.out.println(Hex.toHexString(alice.getAddress()));

        sb.withAccountBalance(bob.getAddress(), convert(1234, ETHER));

    }
}
