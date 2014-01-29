/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs.searchengine.bayes.entities;

/**
 *
 * @author hendri
 */
public class ResultEntity {

    private String phrase;
    private Integer real;
    private Integer result;

    public void check() {
        if (real == null) {
            real = new Integer(0);
        }
        if (result == null) {
            result = new Integer(0);
        }
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public int getReal() {
        check();
        return real;
    }

    public void setReal(int real) {
        check();
        this.real = real;
    }

    public int getResult() {
        check();
        return result;
    }

    public void setResult(int result) {
        check();
        this.result = result;
    }
}
