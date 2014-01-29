/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs.searchengine.entities;

import java.util.HashMap;

/**
 *
 * @author hendri
 */
public class TermEntity {

    private String classTarget;
    private String terms;

    public String getClassTarget() {
        return classTarget;
    }

    public void setClassTarget(String classTarget) {
        this.classTarget = classTarget;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }
}
