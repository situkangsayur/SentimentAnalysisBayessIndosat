package com.cs.searchengine;

import com.cs.searchengine.bayes.entities.MatriksEntity;
import com.cs.searchengine.bayes.entities.ResultEntity;
import com.cs.searchengine.bayes.services.TestingContainer;
import com.cs.searchengine.entities.Statistic;
import com.cs.searchengine.entities.HashMapGen;
import com.cs.searchengine.model.ProcessFile;
import com.cs.searchengine.services.TokenContainer;
import com.cs.searchengine.services.TupleContainer;
import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hendri Main class in this program The main class will call
 * Files.walkFileTree
 */
public final class MachineLearning {

    public static HashMapGen test;
    public static boolean allDoct;
    public static String outPut;
    public static String testing;
    public static final int jumlahFiles = 1000;
    public static final long start = System.currentTimeMillis();
    public static long end;

    public static void main(String[] args) throws IOException {
        //String ROOT = "/home/hendri/enron_mail_20110402/";
        outPut = "";
        if (args.length == 0) {
            System.exit(0);
        }
        if (args[1] != null) {
            testing = args[1];
        } else {
            testing = "indosat";
        }

        String ROOT = args[0];


        TupleContainer.checkAvailTuples();
        TokenContainer.checkAvailHash();

        FileVisitor<Path> fileProcessor = new ProcessFile(start, testing);
        //fileProcessor.
        Files.walkFileTree(Paths.get(ROOT), fileProcessor);

        fileProcessor.toString();
        System.out.println(" jumlah direktory : " + Statistic.getDirectory());
        System.out.println(" jumlah files : " + Statistic.getFiles());

        System.out.println("=========================================================");
        System.out.println("result for the matriks");
        List<MatriksEntity> matriks = new ArrayList<MatriksEntity>(3);
        matriks.add(new MatriksEntity());
        matriks.add(new MatriksEntity());
        matriks.add(new MatriksEntity());
        int t = 0;
        for (ResultEntity rs : TestingContainer.getTestSetAll()) {
            switch (rs.getReal()) {
                case 0:
                    checkCollision(rs.getReal(), rs.getResult(), matriks, rs);
                    break;
                case 1:
                    checkCollision(rs.getReal(), rs.getResult(), matriks, rs);
                    break;
                case 2:
                    checkCollision(rs.getReal(), rs.getResult(), matriks, rs);
                    break;
            }
            t++;
        }
        System.out.println(" i = " + t);

        System.out.println("");
        System.out.println("jumlah data : " + TestingContainer.getTestSetAll().size());
        String val = "";
        System.out.println("======================================================");
        System.out.println("         positif  negatif  nonopini");
        for (int i = 0; i < matriks.size(); i++) {

            switch (i) {
                case 0:
                    val = "positif";
                    break;
                case 1:
                    val = "negatif";
                    break;
                case 2:
                    val = "nonopini";
                    break;
            }
            System.out.println(val + "      " + matriks.get(i).getPositif() + "       "
                    + matriks.get(i).getNegatif() + "        " + matriks.get(i).getNonopini());

            System.out.println("======================================================");
        }
    }

    public static void checkCollision(int source, int result, List<MatriksEntity> list, ResultEntity data) {
//        if (source == result) {
            switch (data.getResult()) {
                case 0:
                    list.get(source).setPositif(list.get(source).getPositif() + 1);
                    break;
                case 1:
                    list.get(source).setNegatif(list.get(source).getNegatif() + 1);
                    break;
                case 2:
                    list.get(source).setNonopini(list.get(source).getNonopini() + 1);
                    break;
            }

//        } else {
//        }
    }
}
