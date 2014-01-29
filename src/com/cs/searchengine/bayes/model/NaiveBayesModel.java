/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs.searchengine.bayes.model;

import com.cs.searchengine.bayes.entities.ClassEntity;
import com.cs.searchengine.entities.Statistic;
import com.cs.searchengine.entities.TokensEntity;
import com.cs.searchengine.services.TokenContainer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author hendri
 */
public class NaiveBayesModel {

    private List<ClassEntity> targetList;
    private List<Double> termList;
    private List<String> cases;

    public List<String> getCases() {
        return cases;
    }

    public void setCases(List<String> cases) {
        this.cases = cases;
    }

    public List<ClassEntity> getTargetList() {
        return targetList;
    }

    public void setTargetList(List<ClassEntity> targetList) {
        this.targetList = targetList;
    }

    public void setTermList() {
        Integer allData = Statistic.getNegatifPhrase() + Statistic.getPositivePhrase() + Statistic.getNonopiniPhrase();

        String sigma = "";
        String classTar = "";
        Double vJ = 0.0D;
        for (int k = 0; k < targetList.size(); k++) {
//            System.out.println("positif = " + Statistic.getPositive());
//            System.out.println("negatif = " + Statistic.getNegative());
//            System.out.println("nonOpini = " + Statistic.getNonopini());
            switch (k) {
                case 0:
                    vJ = new Double(((double) Statistic.getPositivePhrase()) / ((double) allData));
                    break;
                case 1:
                    vJ = new Double(((double) Statistic.getNegatifPhrase()) / ((double) allData));
                    break;
                case 2:
                    vJ = new Double(((double) Statistic.getNonopiniPhrase()) / ((double) allData));
                    break;
            }
            // targetList.get(k).setvClass(vJ);
            // Set<String> key = TokenContainer.getMainTokens().keySet();
            sigma = "";
            classTar = "";
            Double resultDiv = new Double(1.0D);
            Double resultMul = new Double(1.0D);
            for (String data : cases) {
                sigma += "(";
                if (TokenContainer.getMainTokens().containsKey(data)) {

                    switch (k) {
                        case 0:
                            if (TokenContainer.getMainTokens().get(data).getPositif() != 0) {
                                resultDiv = ((double) TokenContainer.getMainTokens().get(data).getPositif()) / ((double) Statistic.getPositive());
                                sigma = sigma + "*" + data + "*(" + TokenContainer.getMainTokens().get(data).getPositif() + "/" + Statistic.getPositive() + ")";
                            }
                            break;
                        case 1:
                            if (TokenContainer.getMainTokens().get(data).getNegatif() != 0) {
                                resultDiv = ((double) TokenContainer.getMainTokens().get(data).getNegatif()) / ((double) Statistic.getNegative());
                                sigma = sigma + "*" + data + "*(" + TokenContainer.getMainTokens().get(data).getNegatif() + "/" + Statistic.getNegative() + ")";
                            }
                            break;
                        case 2:
                            if (TokenContainer.getMainTokens().get(data).getNonOpini() != 0) {
                                resultDiv = ((double) TokenContainer.getMainTokens().get(data).getNonOpini()) / ((double) Statistic.getNonopini());
                                sigma = sigma + "*" + data + "*(" + TokenContainer.getMainTokens().get(data).getNonOpini() + "/" + Statistic.getNonopini() + ")";
                            }
                            break;
                    }

                    resultMul = resultMul * resultDiv;
                }
                sigma += ")";
            }
            switch (k) {
                case 0:
                    classTar += Statistic.getPositivePhrase() + "/" + allData + " * sigma";
                    break;
                case 1:
                    classTar += Statistic.getNegatifPhrase() + "/" + allData + " * sigma";
                    break;
                case 2:
                    classTar += Statistic.getNonopiniPhrase() + "/" + allData + " * sigma";
                    break;
            }
            resultMul = resultMul * vJ;
            System.out.println(targetList.get(k).getClassName() + " : " + classTar + sigma + " = " + resultMul);
            targetList.get(k).setvClass(resultMul);
        }
    }

    public ClassEntity resultBayes() {
        ClassEntity max = new ClassEntity();
        max.setClassName("nonopini");
        max.setvClass(0.0D);

        for (int i = 1; i < targetList.size(); i++) {
            if (targetList.get(i).getvClass() > max.getvClass()) {
                max = targetList.get(i);
            }
        }

        return max;
    }
}
