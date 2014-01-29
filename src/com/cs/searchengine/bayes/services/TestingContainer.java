/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs.searchengine.bayes.services;

import com.cs.searchengine.bayes.entities.ResultEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author hendri
 */
public class TestingContainer {

    private static List<ResultEntity> testSetsPositif;
    private static List<ResultEntity> testSetsNegatif;
    private static List<ResultEntity> testSetsNonOpini;
    private static List<ResultEntity> testSetAll;

    public static void checkObjects() {
        if (testSetsPositif == null) {
            testSetsPositif = Collections.synchronizedList(new ArrayList<ResultEntity>());
        }
        if (testSetsNegatif == null) {
            testSetsNegatif = Collections.synchronizedList(new ArrayList<ResultEntity>());
        }
        if (testSetsNonOpini == null) {
            testSetsNonOpini = Collections.synchronizedList(new ArrayList<ResultEntity>());
        }
        if (testSetAll == null) {
            testSetAll = Collections.synchronizedList(new ArrayList<ResultEntity>());
        }
    }

    public static List<ResultEntity> getTestSetsPositif() {
        checkObjects();
        return testSetsPositif;
    }

    public static void setTestSetsPositif(List<ResultEntity> testSetsPositif) {
        checkObjects();
        TestingContainer.testSetsPositif = testSetsPositif;
    }

    public static List<ResultEntity> getTestSetsNegatif() {
        checkObjects();
        return testSetsNegatif;
    }

    public static void setTestSetsNegatif(List<ResultEntity> testSetsNegatif) {
        checkObjects();
        TestingContainer.testSetsNegatif = testSetsNegatif;
    }

    public static List<ResultEntity> getTestSetsNonOpini() {
        checkObjects();
        return testSetsNonOpini;
    }

    public static void setTestSetsNonOpini(List<ResultEntity> testSetsNonOpini) {
        checkObjects();
        TestingContainer.testSetsNonOpini = testSetsNonOpini;
    }

    public static List<ResultEntity> getTestSetAll() {
        return testSetAll;
    }

    public static void setTestSetAll(List<ResultEntity> testSetAll) {
        checkObjects();
        TestingContainer.testSetAll = testSetAll;
    }
}
