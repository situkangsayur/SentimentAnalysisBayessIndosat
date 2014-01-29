/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs.searchengine.bayes.services;

import com.cs.searchengine.bayes.entities.ResultEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author hendri
 */
public class TrainingContainer {

    private static List<ResultEntity> trainSetsPositif;
    private static List<ResultEntity> trainSetsNegatif;
    private static List<ResultEntity> trainSetsNonOpini;
//    private static List<String> trainSetAll;

    public static void checkObjects() {
        if (trainSetsPositif == null) {
            trainSetsPositif = Collections.synchronizedList(new ArrayList<ResultEntity>());
        }
        if (trainSetsNegatif == null) {
            trainSetsNegatif = Collections.synchronizedList(new ArrayList<ResultEntity>());
        }
        if (trainSetsNonOpini == null) {
            trainSetsNonOpini = Collections.synchronizedList(new ArrayList<ResultEntity>());
        }
//        if (trainSetAll == null) {
//            trainSetAll = Collections.synchronizedList(new ArrayList<String>());
//        }
    }

    public static List<ResultEntity> getTrainSetsPositif() {
        checkObjects();
        return trainSetsPositif;
    }

    public static void setTrainSetsPositif(List<ResultEntity> testSetsPositif) {
        checkObjects();
        TrainingContainer.trainSetsPositif = testSetsPositif;
    }

    public static List<ResultEntity> getTrainSetsNegatif() {
        checkObjects();
        return trainSetsNegatif;
    }

    public static void setTrainSetsNegatif(List<ResultEntity> testSetsNegatif) {
        checkObjects();
        TrainingContainer.trainSetsNegatif = testSetsNegatif;
    }

    public static List<ResultEntity> getTrainSetsNonOpini() {
        checkObjects();
        return trainSetsNonOpini;
    }

    public static void setTrainSetsNonOpini(List<ResultEntity> testSetsNonOpini) {
        checkObjects();
        TrainingContainer.trainSetsNonOpini = testSetsNonOpini;
    }
}
