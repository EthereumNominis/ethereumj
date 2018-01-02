package org.nominis.export;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class ExportSingleBlockTest {

    //WARNING: THIS PATH MAY NOT BE CORRECT FOR YOUR LOCAL MACHINE
    public static final String PATH = "org/nominis/export/resources/singleBlockTest-genesis.json";

    @Test
    public void toJsonTest(){
        ExportSingleBlock.toJsonFile();
    }

    @Test
    public void toJsonFile() {

    }

}