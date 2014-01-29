/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs.searchengine.bayes.entities;

/**
 *
 * @author hendri
 */
public class MatriksEntity {

    private Integer positif;
    private Integer negatif;
    private Integer nonopini;

    public MatriksEntity() {
        if (positif == null) {
            positif = new Integer(0);
        }
        if (negatif == null) {
            negatif = new Integer(0);
        }
        if (nonopini == null) {
            nonopini = new Integer(0);
        }
    }

    public Integer getPositif() {
        return positif;
    }

    public void setPositif(Integer positif) {
        this.positif = positif;
    }

    public Integer getNegatif() {
        return negatif;
    }

    public void setNegatif(Integer negatif) {
        this.negatif = negatif;
    }

    public Integer getNonopini() {
        return nonopini;
    }

    public void setNonopini(Integer nonopini) {
        this.nonopini = nonopini;
    }
}
