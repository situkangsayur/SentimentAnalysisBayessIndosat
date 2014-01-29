/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs.searchengine.entities;

/**
 *
 * @author hendri
 */
public class UrlDocEntity {

    private Integer termFreq;
    private Integer termPosition;

    public Integer getTermFreq() {
        return termFreq;
    }

    public void setTermFreq(Integer termFreq) {
        this.termFreq = termFreq;
    }

    public Integer getTermPosition() {
        return termPosition;
    }

    public void setTermPosition(Integer termPosition) {
        this.termPosition = termPosition;
    }
}
