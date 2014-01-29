/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs.searchengine.services;

import com.cs.searchengine.bayes.entities.ResultEntity;
import com.cs.searchengine.entities.Statistic;
import com.cs.searchengine.entities.UrlDocEntity;
import com.cs.searchengine.entities.HashMapGen;
import com.cs.searchengine.entities.TermEntity;
import com.cs.searchengine.entities.TokensEntity;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hendri
 */
public class TokenContainer {

    private static HashMapGen mainTokens;//statistic unique terms
    private static List<TermEntity> mainTerms;//instances or dataset or data training
    private static List<ResultEntity> mainTrainingPhrase;
    private static String tokens;

    /**
     *
     */
    public static void checkAvailHash() {

        if (mainTokens == null) {
            mainTokens = new HashMapGen();
            mainTerms = Collections.synchronizedList(new ArrayList<TermEntity>());
        }
        if (mainTrainingPhrase == null) {
            mainTrainingPhrase = new ArrayList<ResultEntity>();
        }

    }

    public static List<ResultEntity> getMainTrainingPhrase() {
        checkAvailHash();
        return mainTrainingPhrase;
    }

    public static void setMainTrainingPhrase(List<ResultEntity> mainTrainingPhrase) {
        checkAvailHash();
        TokenContainer.mainTrainingPhrase = mainTrainingPhrase;
    }

    public static List<TermEntity> getMainTerms() {
        checkAvailHash();
        return mainTerms;
    }

    public static void setMainTerms(List<TermEntity> mainTerms) {
        checkAvailHash();
        TokenContainer.mainTerms = mainTerms;
    }

    public static String getTokens() {
        return tokens;
    }

    public static void setTokens(String tokens) {
        TokenContainer.tokens = tokens;
    }

    /**
     *
     * @return
     */
    public static HashMapGen getMainTokens() {
        checkAvailHash();
        return mainTokens;
    }

    /**
     *
     * @param bodyTokens
     */
    public static void setMainTokens(HashMapGen mainTokens) {
        checkAvailHash();
        TokenContainer.mainTokens = mainTokens;
    }

    /**
     * @author hendri Method Performance. This method will perform to marge all
     * of temporary token that have passed by the parameter and marge it to the
     * main tokens.
     * @param container
     * @param tokenHash
     */
    public static void margeTokens(HashMapGen container, HashMapGen tokenHash) {
        Set<String> result = tokenHash.keySet();

        for (String data : result) {
            tokens = data;
            addTokens(container, tokenHash.get(data));
        }

    }

    public static void margeTokens(List<TermEntity> mainList, List<TermEntity> appenList) {

        for (TermEntity data : appenList) {
            mainList.add(data);
        }

    }

    public static void margeDataSet(List<ResultEntity> srcList, List<ResultEntity> dstList) {

        for (ResultEntity data : dstList) {
            srcList.add(data);
        }

    }

    /**
     * @author hendri This method perform to add a token to the hashmap
     * @param hashObject
     * @param entity
     */
    public static void addTokens(HashMapGen hashObject, TokensEntity entity) {

        TokensEntity data = null;
        Integer docEnt;
        Integer docHash;
        HashMap<String, Integer> tempHash;
        HashMap<String, Integer> tempToken;

        Double weight;
        int tF = 0;
        int dF = 0;
        int nMess = Statistic.getFiles();
        double tempR;
        double tempL;

        Set<String> key;

        int urlCount = 0;

        if (!hashObject.containsKey(tokens)) {
            data = new TokensEntity();
            data.setCount(entity.getCount());
            data.setWeight(entity.getWeight());
            data.setUrl(entity.getUrl());
            data.setDocFreq(1);
            data.setPositif(entity.getPositif());
            data.setNegatif(entity.getNegatif());
            data.setNonOpini(entity.getNonOpini());
            data.setTokens(entity.getTokens());
            // key = entity.getUrl().keySet();
        } else {

            data = hashObject.get(tokens);
            data.setCount(data.getCount() + entity.getCount());
            tempHash = data.getUrl();
            tempToken = entity.getUrl();
            data.setPositif(data.getPositif() + entity.getPositif());
            data.setNegatif(data.getNegatif() + entity.getNegatif());
            data.setNonOpini(data.getNonOpini() + entity.getNonOpini());

            if (tempToken.size() != 0) {
                data.setDocFreq(data.getDocFreq() + tempToken.size());
            }
            data.setUrl(tempHash);
        }
        dF = data.getDocFreq();
        tF = data.getCount();
        tempL = (double) tF / (double) nMess;
        tempR = Math.log((double) nMess / (double) dF);
        weight = tempL / tempR;
        data.setWeight(weight);
        data.setUrl(null);

        if (!hashObject.containsKey(tokens)) {
            hashObject.put(tokens, data);
        } else {
            hashObject.replace(tokens, data);
        }

    }

    /**
     * @author ken : to sorting the main hashmap and change to be Map that have
     * been sorted by weight
     * @param unsortMap
     * @return
     */
    public static Map<String, TokensEntity> sortByComparator(HashMapGen unsortMap) {

        // Set<String> key = unsortMap.keySet();

        List<TokensEntity> list = new LinkedList<TokensEntity>(unsortMap.values());
        System.out.println(" size hash before to be list :" + unsortMap.size());
        System.out.println(" size before sort : " + list.size());

        return selectionSort(list);
    }

    /**
     * @author ken : to sort the List of values from the hashmap or main hashmap
     * and it will be sorting by Collection class and using sort method from
     * collection interface
     * @param unsortList
     * @return
     */
    public static Map<String, TokensEntity> selectionSort(List<TokensEntity> unsortList) {
        List<TokensEntity> sortList = unsortList;

        //Collections.sort(unsortList, );
        Collections.sort(sortList, new Comparator<TokensEntity>() {
            public int compare(TokensEntity o1, TokensEntity o2) {
                TokensEntity val1 = o1;
                TokensEntity val2 = o2;
                return (Double.compare(val2.getWeight(), val1.getWeight()));
            }
        });
        LinkedHashMap<String, TokensEntity> sortedMap = new LinkedHashMap();
        System.out.println(" size sorted list :" + sortList.size());
        for (TokensEntity data : sortList) {
            sortedMap.put(data.getTokens(), data);
        }

        return sortedMap;
    }

    /**
     * @author adhe: to write the results of sorted hashmap to the file, and
     * manage the string of results for each values in hashmap.
     * @param field
     * @param sortedMap
     */
    public static void printInformation(HashMapGen sortedMap) {
        DecimalFormat df = new DecimalFormat("####.#####");

        String result = "Freq : Ada berapa file yg mengandung kata tersebut \r\n"
                + "weight : perhitungan bobot tersendiri *bekas Search Engines hahaha.. *\r\n"
                + "id, term, frequensi, freq documents, wight, jumlah positif, jumlah negatif, "
                + "jumlah non opini \r\n\r\n";


        int i = 0;
        Set<String> key = sortedMap.keySet();

        for (String token : key) {
//            System.out.println(""+sortedMap.get(token).getNonOpini());
            result += (i + 1) + " "
                    + sortedMap.get(token).getTokens() + " "
                    + sortedMap.get(token).getCount() + " " + sortedMap.get(token).getDocFreq()
                    + " " + sortedMap.get(token).getPositif() + " " + sortedMap.get(token).getNegatif()
                    + " " + sortedMap.get(token).getNonOpini() + "\r\n";
            i++;
        }
        printToFile(Statistic.TEAM + " " + ".txt", result);
    }

    public static void printInstances(List<TermEntity> listInstance, HashMapGen uniqTerm) {

        List<TermEntity> list = listInstance;

        String result = "%Jumlah Instances positive = " + Statistic.getPositive() + " \r\n";
        result += "%Jumlah Instances negative = " + Statistic.getNegative() + " \r\n";
        result += "%Jumlah Instances non opini = " + Statistic.getNonopini() + " \r\n";
        result += "@relation Primary-Twitt \r\n";

        String term = "@attribute term { ";
        String target = "@attribute target { positif, negatif, nonopini}\r\n";

        String instances = "@data ";

        Set<String> key = uniqTerm.keySet();

        int i = 0;
        for (String data : key) {

            term += uniqTerm.get(data).getTokens();

            if (i < key.size()) {
                term += ",";
            }

        }

        i = 0;
        for (TermEntity data : list) {

            instances += "\'" + data.getTerms() + "\'" + "," + data.getClassTarget() + "\r\n";

        }
        term += "}\r\n";
        result += term + target + instances;

        printToFile("Data Instances.arff", result);
        System.out.println("jumlah record : " + listInstance.size());
    }

    public static void printInstances(List<ResultEntity> listInstance, String name, int mode) {

        List<ResultEntity> list = listInstance;

        String result = "";
        String instances = "";
        String target = null;
        int i = 0;
        for (ResultEntity data : list) {
            switch (data.getReal()) {
                case 0:
                    target = "positif";
                    break;
                case 1:
                    target = "negatif";
                    break;
                case 2:
                    target = "nonopini";
                    break;
            }
            if (mode == 0) {
                instances += data.getPhrase() + "  " + target + "\r\n";
            } else {
                instances += data.getPhrase() + "\r\n";
            }
        }

        result += instances;

        printToFile(name + ".txt", result);
        System.out.println("jumlah record : " + listInstance.size());
    }

    public static void printWeka(List<ResultEntity> listInstance, String name) {

        List<ResultEntity> list = listInstance;

        String result = "@relation sentimen_twit \r\n"
                + "@attribute term string \r\n"
                + "@attribute target {positif, negatif, nonopini} \r\n"
                + "@data\r\n";
        String instances = "";
        String target = null;
        int i = 0;
        for (ResultEntity data : list) {
            switch (data.getReal()) {
                case 0:
                    target = "positif";
                    break;
                case 1:
                    target = "negatif";
                    break;
                case 2:
                    target = "nonopini";
                    break;
            }

            instances += "\"" + data.getPhrase() + "\"," + target + "\r\n";

        }

        result += instances;

        printToFile(name + ".arff", result);
        System.out.println("finish print file in weka format...");
    }

    /**
     * @author adhe : main method to write the string to the file.txt
     * @param fileName
     * @param text
     */
    public static void printToFile(String fileName, String text) {
        FileWriter fstream = null;
        BufferedWriter out = null;
        try {

            fstream = new FileWriter(fileName);
            out = new BufferedWriter(fstream);
            out.write(text);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // fstream = null;
        }

    }
}
