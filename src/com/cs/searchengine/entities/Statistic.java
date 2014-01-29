/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs.searchengine.entities;

/**
 *
 * @author hendri Class to save all of information of files and directory count
 */
public class Statistic {

    private static Integer files;
    private static Integer directory;
    public static final Integer COUNT = 999000;
    public static final String TEAM = "BayesTeam";
    private static Integer allTokens;
    private static Integer kTop;
    //jumlah untuk setiap instances keseluruhan untuk negatif, positif dan non opini
    private static Integer positive;
    private static Integer negative;
    private static Integer nonOpini;
    private static Integer positivePhrase;
    private static Integer negatifPhrase;
    private static Integer nonopiniPhrase;

    public static void setCount() {
        if ((files == null) || (directory == null)) {
            files = new Integer(0);
            directory = new Integer(0);
        }
        if (allTokens == null) {
            allTokens = new Integer(0);
        }
        if (directory == null) {
            kTop = new Integer(40);
        }
        if (positive == null) {
            positive = new Integer(0);
        }
        if (negative == null) {
            negative = new Integer(0);
        }

        if (nonOpini == null) {
            nonOpini = new Integer(0);
        }

        if (nonopiniPhrase == null) {
            nonopiniPhrase = new Integer(0);
        }
        if (positivePhrase == null) {
            positivePhrase = new Integer(0);
        }
        if (negatifPhrase == null) {
            negatifPhrase = new Integer(0);
        }

    }

    public static Integer getNonOpini() {
        setCount();
        return nonOpini;
    }

    public static void setNonOpini(Integer nonOpini) {
        setCount();
        Statistic.nonOpini = nonOpini;
    }

    public static Integer getPositivePhrase() {
        setCount();
        return positivePhrase;
    }

    public static void setPositivePhrase(Integer positivePhrase) {
        setCount();
        Statistic.positivePhrase = positivePhrase;
    }

    public static Integer getNegatifPhrase() {
        setCount();
        return negatifPhrase;
    }

    public static void setNegatifPhrase(Integer negatifPhrase) {
        setCount();
        Statistic.negatifPhrase = negatifPhrase;
    }

    public static Integer getNonopiniPhrase() {
        setCount();
        return nonopiniPhrase;
    }

    public static void setNonopiniPhrase(Integer nonopiniPhrase) {
        setCount();
        Statistic.nonopiniPhrase = nonopiniPhrase;
    }

    public static Integer getPositive() {
        setCount();
        return positive;
    }

    public static void setPositive(Integer positive) {
        setCount();
        Statistic.positive = positive;
    }

    public static Integer getNegative() {
        setCount();
        return negative;
    }

    public static void setNegative(Integer negative) {
        setCount();
        Statistic.negative = negative;
    }

    public static Integer getNonopini() {
        setCount();
        return nonOpini;
    }

    public static void setNonopini(Integer nonopini) {
        setCount();
        Statistic.nonOpini = nonopini;
    }

    public static Integer getkTop() {
        setCount();
        return kTop;
    }

    public static void setkTop(Integer kTop) {
        setCount();
        Statistic.kTop = kTop;
    }

    public static Integer getAllTokens() {
        setCount();
        return allTokens;
    }

    public static void setAllTokens(Integer allTokens) {
        setCount();
        Statistic.allTokens = allTokens;
    }

    public static int getFiles() {
        setCount();
        return files;
    }

    public static void setFiles() {
        setCount();
        Statistic.files++;
    }

    public static int getDirectory() {
        setCount();
        return directory;
    }

    public static void setDirectory() {
        setCount();
        Statistic.directory++;
    }

    public static Integer getCountsDir() {
        setCount();
        return Statistic.directory;
    }

    public static Integer getCountsFiles() {
        setCount();
        return Statistic.files;
    }
}
