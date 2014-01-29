/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs.searchengine.entities;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author hendri Entity class to save all of information about count of every
 * tuple that find in a file
 */
public class TokensEntity {

    private int count;
    private Double weight;
    private int docFreq;
    private String tokens;
    private Integer positif;
    private Integer negatif;
    private Integer nonOpini;
    private HashMap<String, Integer> url;
    //private int docFreq;

    // private String tokens;
    public void checkUrl() {
        if (url == null) {
            url = new HashMap<String, Integer>();
        }

        if (positif == null) {
            positif = new Integer(0);
        }
        if (negatif == null) {
            negatif = new Integer(0);
        }

        if (nonOpini == null) {
            nonOpini = new Integer(0);
        }
    }

    public Integer getPositif() {
        checkUrl();
        return positif;
    }

    public void setPositif(Integer positif) {
        checkUrl();
        this.positif = positif;
    }

    public Integer getNegatif() {
        checkUrl();
        return negatif;
    }

    public void setNegatif(Integer negatif) {
        checkUrl();
        this.negatif = negatif;
    }

    public Integer getNonOpini() {
        checkUrl();
        return nonOpini;
    }

    public void setNonOpini(Integer nonOpini) {
        checkUrl();
        this.nonOpini = nonOpini;
    }

    public String getTokens() {
        return tokens;
    }

    public void setTokens(String tokens) {
        this.tokens = tokens;
    }

    public int getDocFreq() {
        return docFreq;
    }

    public void setDocFreq(int docFreq) {
        this.docFreq = docFreq;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public HashMap<String, Integer> getUrl() {
        checkUrl();
        return url;
    }

    public void setUrl(HashMap<String, Integer> url) {
        checkUrl();
        this.url = url;
    }
}
