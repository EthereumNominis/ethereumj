package org.nominis.core;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.ethereum.core.BlockHeader;
import org.ethereum.core.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.nominis.export.toJsonFormat;
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



    public Block(byte[] rawData) {
        super(rawData);
    }

    public Block(BlockHeader header, List<Transaction> transactionsList, List<BlockHeader> uncleList) {
        super(header, transactionsList, uncleList);
    }

    public Block(byte[] parentHash, byte[] unclesHash, byte[] coinbase, byte[] logsBloom, byte[] difficulty, long number, byte[] gasLimit, long gasUsed, long timestamp, byte[] extraData, byte[] mixHash, byte[] nonce, byte[] receiptsRoot, byte[] transactionsRoot, byte[] stateRoot, List<Transaction> transactionsList, List<BlockHeader> uncleList) {
        super(parentHash, unclesHash, coinbase, logsBloom, difficulty, number, gasLimit, gasUsed, timestamp, extraData, mixHash, nonce, receiptsRoot, transactionsRoot, stateRoot, transactionsList, uncleList);
    }

    public Block(byte[] parentHash, byte[] unclesHash, byte[] coinbase, byte[] logsBloom, byte[] difficulty, long number, byte[] gasLimit, long gasUsed, long timestamp, byte[] extraData, byte[] mixHash, byte[] nonce, List<Transaction> transactionsList, List<BlockHeader> uncleList) {
        super(parentHash, unclesHash, coinbase, logsBloom, difficulty, number, gasLimit, gasUsed, timestamp, extraData, mixHash, nonce, transactionsList, uncleList);
    }

    /**-----------------------------------------
     * export to Json file
    -----------------------------------------**/
    public void toJsonFile(){
        //FIXME: need to add size limit here
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








        blockMap.put("blockHeader",blockHeaderJSON);

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
     * get citation chain
    -----------------------------------------**/


}
