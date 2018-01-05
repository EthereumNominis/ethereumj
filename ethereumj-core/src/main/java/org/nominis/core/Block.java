package org.nominis.core;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.ethereum.core.BlockHeader;
import org.ethereum.core.Transaction;
import org.ethereum.util.ByteUtil;
import org.ethereum.util.RLP;
import org.ethereum.util.RLPList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.nominis.export.toJsonFormat;
import org.spongycastle.util.BigIntegers;
import org.spongycastle.util.encoders.Hex;

import java.io.FileWriter;
import java.util.List;

/**
* class_name: Block
* package: org.nominis.core
* describe: TODO
* create_user: Yaojin Sun
* create_date: 1/3/18
* create_time: 2:40 PM
**/

/**-----------------------------------------
 * The block class under nominis package would be
 * the final block in the project.
 * nominis block is derived from org.ethereum.core.Block
 * more/overriding functions
 *  1.  export to Json file
 *  2.  get citation chain
 * more elements
 *  (1). citation chain
-----------------------------------------**/



public class Block extends org.ethereum.core.Block implements toJsonFormat {


    /**-----------------------------------------
     * @param citationChain
     *      An arbitrary byte array containing data relevant to
     *      random walk answers.
     *      If no random walk, the number of steps = zero
     *          and the citationChain = null
    -----------------------------------------**/
    private byte[] citationChain = null;




    /**-----------------------------------------
     * constructors
    -----------------------------------------**/


    public Block(byte[] rawData) {
        super(rawData);
    }



    public Block(BlockHeader header, List<Transaction> transactionsList, List<BlockHeader> uncleList) {
        super(header, transactionsList, uncleList);
    }

    public Block(BlockHeader header, List<Transaction> transactionsList, List<BlockHeader> uncleList, byte[] citationChain) {
        super(header, transactionsList, uncleList);
        this.citationChain = citationChain;
    }





    public Block(byte[] parentHash, byte[] unclesHash, byte[] coinbase, byte[] logsBloom, byte[] difficulty, long number, byte[] gasLimit, long gasUsed, long timestamp, byte[] extraData, byte[] mixHash, byte[] nonce, byte[] receiptsRoot, byte[] transactionsRoot, byte[] stateRoot, List<Transaction> transactionsList, List<BlockHeader> uncleList) {
        super(parentHash, unclesHash, coinbase, logsBloom, difficulty, number, gasLimit, gasUsed, timestamp, extraData, mixHash, nonce, receiptsRoot, transactionsRoot, stateRoot, transactionsList, uncleList);
    }
    public Block(byte[] parentHash, byte[] unclesHash, byte[] coinbase, byte[] logsBloom, byte[] difficulty, long number, byte[] gasLimit, long gasUsed, long timestamp, byte[] extraData, byte[] mixHash, byte[] nonce, byte[] receiptsRoot, byte[] transactionsRoot, byte[] stateRoot, List<Transaction> transactionsList, List<BlockHeader> uncleList, byte[] citationChain) {
        super(parentHash, unclesHash, coinbase, logsBloom, difficulty, number, gasLimit, gasUsed, timestamp, extraData, mixHash, nonce, receiptsRoot, transactionsRoot, stateRoot, transactionsList, uncleList);
        this.citationChain = citationChain;
    }







    public Block(byte[] parentHash, byte[] unclesHash, byte[] coinbase, byte[] logsBloom, byte[] difficulty, long number, byte[] gasLimit, long gasUsed, long timestamp, byte[] extraData, byte[] mixHash, byte[] nonce, List<Transaction> transactionsList, List<BlockHeader> uncleList) {
        super(parentHash, unclesHash, coinbase, logsBloom, difficulty, number, gasLimit, gasUsed, timestamp, extraData, mixHash, nonce, transactionsList, uncleList);
    }
    public Block(byte[] parentHash, byte[] unclesHash, byte[] coinbase, byte[] logsBloom, byte[] difficulty, long number, byte[] gasLimit, long gasUsed, long timestamp, byte[] extraData, byte[] mixHash, byte[] nonce, List<Transaction> transactionsList, List<BlockHeader> uncleList, byte[] citationChain) {
        super(parentHash, unclesHash, coinbase, logsBloom, difficulty, number, gasLimit, gasUsed, timestamp, extraData, mixHash, nonce, transactionsList, uncleList);
        this.citationChain = citationChain;
    }







    /**-----------------------------------------
     * export to Json file
    -----------------------------------------**/
    public void toJsonFile(){
        //FIXME: need to add size limit here!!!
        JSONObject blockMap = new JSONObject();
        JSONArray blockJsonArray = new JSONArray();

        JSONObject blockHeaderJSON = new JSONObject();
        JSONArray blockheaderJsonArray = new JSONArray();

        /**
         * difficulty
        **/
        if (getCoinbase() != null){
            blockHeaderJSON.put("difficulty", "0x" + Hex.toHexString(getDifficulty()));
        }else {
            blockHeaderJSON.put("difficulty", null);
        }

        /**
         * coinbase
        **/
        if (getCoinbase() != null){
            blockHeaderJSON.put("coinbase", "0x" + Hex.toHexString(getCoinbase()));
        }else {
            blockHeaderJSON.put("coinbase", null);
        }

        /**
         * extradata
        **/
        if (getExtraData() != null){
            blockHeaderJSON.put("extraData", "0x" + Hex.toHexString(getExtraData()) );
        }else {
            blockHeaderJSON.put("extraData", null );
        }



        /**
         * gasLimit
        **/
        if (getGasLimit() != null){
            blockHeaderJSON.put("gasLimit", "0x" + Hex.toHexString(getGasLimit()) );
        }else {
            blockHeaderJSON.put("gasLimit", null );
        }


        /**
         * gasUsed
        **/
        if (getGasUsed() != 0){
            blockHeaderJSON.put("gasUsed", getGasUsed() );
        }else {
            blockHeaderJSON.put("gasUsed", "0" );
        }


        /**
         * hash
        **/
        if (getHash() != null){
            blockHeaderJSON.put("hash", "0x" + Hex.toHexString(getHash()) );
        }else {
            blockHeaderJSON.put("hash", null );
        }


        /**
         * mixHash
        **/
        if (getMixHash() != null){
            blockHeaderJSON.put("mixhash", "0x" + Hex.toHexString(getMixHash()) );
        }else {
            blockHeaderJSON.put("mixhash", null );
        }


        /**
         * nonce
        **/
        if (getNonce() != null){
            blockHeaderJSON.put("nounce", "0x" + Hex.toHexString(getNonce()) );
        }else {
            blockHeaderJSON.put("nounce", null );
        }



        /**
         * number
        **/
        if (getNumber() != 0){
            blockHeaderJSON.put("number", getNumber() );
        }else {
            blockHeaderJSON.put("number", null );
        }



        /**
         * parentHash
        **/
        if (getParentHash() != null){
            blockHeaderJSON.put("parentHash", "0x" + Hex.toHexString(getParentHash()) );
        }else {
            blockHeaderJSON.put("parentHash", null );
        }



        /**
         * receiptTrie
        **/
        if (getReceiptsRoot() != null){
            blockHeaderJSON.put("receiptTrie", "0x" + Hex.toHexString(getReceiptsRoot()) );
        }else {
            blockHeaderJSON.put("receiptTrie", null );
        }


        /**
         * stateRoot
        **/
        if (getStateRoot() != null){
            blockHeaderJSON.put("stateRoot", "0x" + Hex.toHexString(getStateRoot()) );
        }else {
            blockHeaderJSON.put("stateRoot", null );
        }



        /**
         * timestamp
        **/
        if (getTimestamp() != 0){
            blockHeaderJSON.put("timestamp", getTimestamp() );
        }else {
            blockHeaderJSON.put("timestamp", null );
        }


        /**
         * transactionsTrie
        **/
        if (getTxTrieRoot() != null){
            blockHeaderJSON.put("transactionsTrie", "0x" + Hex.toHexString(getTxTrieRoot()) );
        }else {
            blockHeaderJSON.put("transactionsTrie", null );
        }

        /**
         * uncleHash
        **/
        if (getUnclesHash() != null){
            blockHeaderJSON.put("uncleHash", "0x" + Hex.toHexString(getUnclesHash()) );
        }else {
            blockHeaderJSON.put("uncleHash", null );
        }





        /**
         * -------- ----------- ----- tx list ----- ---- ----
        **/
        JSONObject TxList = new JSONObject();
        JSONArray TxListArray = new JSONArray();


        if (!getTransactionsList().isEmpty()){
            JSONObject TxItem;
            for (Transaction tx : getTransactionsList()){
                TxItem = new JSONObject();
                // data
                if (tx.getData() != null){
                    TxItem.put("data", "0x" + Hex.toHexString(tx.getData()));
                }else{
                    TxItem.put("data","0");
                }
                // gasLimit
                if (tx.getGasLimit() != null){
                    TxItem.put("gasLimit", "0x" + Hex.toHexString(tx.getGasLimit()));
                }else{
                    TxItem.put("gasLimit",null);
                }
                //gasPrice
                if (tx.getGasPrice() != null){
                    TxItem.put("gasPrice", "0x" + Hex.toHexString(tx.getGasPrice()));
                }else{
                    TxItem.put("gasPrice",null);
                }
                //nonce
                if (tx.getNonce() != null){
                    TxItem.put("gasNonce", "0x" + Hex.toHexString(tx.getNonce()));
                }else{
                    TxItem.put("gasNonce",null);
                }
                //signature-r
                TxItem.put("r", "0x" + ByteUtil.toHexString(BigIntegers.asUnsignedByteArray(tx.getSignature().r)));
                //signature-s
                TxItem.put("s", "0x" + ByteUtil.toHexString(BigIntegers.asUnsignedByteArray(tx.getSignature().s)));
                //signature-v
                TxItem.put("v",  tx.getSignature().v);
                //receiver
                TxItem.put("to", "0x" + Hex.toHexString(tx.getReceiveAddress()));

                TxListArray.add(TxItem);
            }

        }


        blockMap.put("blockHeader",blockHeaderJSON);
        blockMap.put("transactions",TxListArray);

        try{
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(blockMap.toJSONString());
            String prettyJson = gson.toJson(je);
            String fileName = "Nominis_" + getNumber() + ".json";
            FileWriter JsonFile = new FileWriter(fileName);
            JsonFile.write(prettyJson);
            JsonFile.flush();
            JsonFile.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    };





    /**-----------------------------------------
     * init with parseRLP();
    -----------------------------------------**/
    private void initParseRLP(){
        super.getTimestamp();
    }


    /**-----------------------------------------
     * get citation chain
    -----------------------------------------**/
    public void getCitationChain(){
        initParseRLP();
        byte[] EncodeList = super.getEncoded();
        RLPList params = RLP.decode2(EncodeList);
        RLPList block = (RLPList) params.get(0);
        if (block.size() == 3){
            System.out.println(block.size());
        }else{
            //FIXME: not finish the return part here. We need to do block generator first,
            // FIXME: then generate list of blocks with RLP data.
            // FIXME: then testing this function.
            System.out.println(block.size());
        }
    }









    /**-----------------------------------------
     * get block size
    -----------------------------------------**/
    public long getBlockSize(){
        initParseRLP();  /**This step is necessary, because we need to run parseRLP();**/
        byte[] body = super.getEncoded();
        return body.length;
    }







}
