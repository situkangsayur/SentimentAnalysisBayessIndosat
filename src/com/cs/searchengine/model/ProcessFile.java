/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs.searchengine.model;

import com.cs.searchengine.MachineLearning;
import com.cs.searchengine.bayes.entities.ClassEntity;
import com.cs.searchengine.bayes.entities.ResultEntity;
import com.cs.searchengine.bayes.model.NaiveBayesModel;
import com.cs.searchengine.bayes.services.TestingContainer;
import com.cs.searchengine.bayes.services.TrainingContainer;
import com.cs.searchengine.entities.Statistic;
import com.cs.searchengine.entities.HashMapGen;
import com.cs.searchengine.entities.TermEntity;
import com.cs.searchengine.entities.TokensEntity;
import com.cs.searchengine.services.DocumentContainer;
import com.cs.searchengine.services.TokenContainer;
import com.cs.searchengine.services.TupleContainer;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hendri this class for walking files activity, to explore all of
 * directory that have send from main class and do parsing activity to add all
 * of token to the hashmap. This class will do all of activity in multithread.
 * So first thread will do searching files to explore all of possible directory
 * and all of files and another threads will process the parserProgress for each
 * files that have got from the first thread
 */
public class ProcessFile extends SimpleFileVisitor<Path> {

    private ParseFile parser;
    private Integer jumlahFiles;
    private StringBuilder outPut;
    private long start;
    private long end;
    private long midle;
    private int availableProc = Runtime.getRuntime().availableProcessors();
    private ExecutorService serviceEx = Executors.newFixedThreadPool(availableProc);
    private Runtime runtime = Runtime.getRuntime();
    private String test;

    /**
     *
     * @param start
     */
    public ProcessFile(long start, String testCase) {
        this.start = start;
        this.jumlahFiles = Statistic.COUNT;
        this.outPut = new StringBuilder();
        this.test = testCase;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    /*
     * @author hendri
     * method to explore all of possible "files"
     */
    @Override
    public FileVisitResult visitFile(
            Path aFile, BasicFileAttributes aAttrs) throws IOException {

        if (Statistic.getCountsFiles() > jumlahFiles) {
            return FileVisitResult.TERMINATE;
        } else {
//            if (aFile.toString().indexOf("all_documents") == -1) {
            Statistic.setFiles();
            System.out.println("" + aFile);
            parser = new ParseFile();
            parser.setPath(aFile.toString());
            parser.setJob(Statistic.getCountsFiles());
            parser.setWalker(this);



            if (Statistic.getCountsFiles() % 1000 == 0) {
                outPut.append("task stack : " + Statistic.getCountsFiles());
                System.out.println(outPut);
                outPut.delete(0, outPut.length());
            }
            try {
                parser.call();
                //serviceEx.submit(parser);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return FileVisitResult.CONTINUE;
//            } else {
//                //    System.out.println("\nskip" + aFile.toString() + "\n");
//                return FileVisitResult.SKIP_SIBLINGS;
//            }
        }
    }

    @Override
    public String toString() {
        finishing();
        return super.toString();
    }


    /*
     * @author hendri
     * method to explore all of possible "directories"
     */
    @Override
    public FileVisitResult preVisitDirectory(
            Path aDir, BasicFileAttributes aAttrs) throws IOException {
        Statistic.setDirectory();
        return FileVisitResult.CONTINUE;

    }

    /**
     * @author hendri Method performance. This method will calling from class
     * ParseFile when the progress of parsing file activity is done, and the
     * listener will accept the tikenHash parameter that contains temporary hash
     * all of token that find in a file, and then the this method will marge all
     * of tokenHash tokens to the Main Token in TokenContainer. And the listener
     * will show all of tokens that contains in TokenContainers
     * @param done
     * @param tokenHash
     * @throws InterruptedException
     */
    public void listener(int done, HashMapGen tokenHash, List<TermEntity> listTerms) throws InterruptedException {


        // System.out.println("up");
//        TokenContainer.margeTokens(TokenContainer.getMainTokens(), tokenHash);
//        TokenContainer.margeTokens(TokenContainer.getMainTerms(), listTerms);


        if (done % 1000 == 0) {
            System.out.println("task finished : " + done + " from: " + Statistic.getCountsFiles() + " files, time : " + ((System.currentTimeMillis() - start) / 1000) + " sec");
        }
        // System.out.println("down");
        /**
         * Terminate job while all job has finished
         */
    }

    public void testSpliter() {
        int sizePositif = TestingContainer.getTestSetsPositif().size() / 3;
        int sizeNegatif = TestingContainer.getTestSetsNegatif().size() / 3;
        int sizeNonOpini = TestingContainer.getTestSetsNonOpini().size() / 3;
        Collections.shuffle(TestingContainer.getTestSetsPositif());
        Collections.shuffle(TestingContainer.getTestSetsNegatif());
        Collections.shuffle(TestingContainer.getTestSetsNonOpini());

        //trainingset
        for (int i = 0; i < sizePositif; i++) {
            TestingContainer.getTestSetAll().add(TestingContainer.getTestSetsPositif().get(i));
        }
        for (int i = 0; i < sizeNegatif; i++) {
            TestingContainer.getTestSetAll().add(TestingContainer.getTestSetsNegatif().get(i));
        }
        for (int i = 0; i < sizeNonOpini; i++) {
            TestingContainer.getTestSetAll().add(TestingContainer.getTestSetsNonOpini().get(i));
        }
        //adataset
        for (int i = sizePositif; i < TestingContainer.getTestSetsPositif().size(); i++) {
            TrainingContainer.getTrainSetsPositif().add(TestingContainer.getTestSetsPositif().get(i));
        }

        for (int i = sizeNegatif; i < TestingContainer.getTestSetsNegatif().size(); i++) {
            TrainingContainer.getTrainSetsNegatif().add(TestingContainer.getTestSetsNegatif().get(i));
        }

        for (int i = sizeNonOpini; i < TestingContainer.getTestSetsNonOpini().size(); i++) {
            TrainingContainer.getTrainSetsNonOpini().add(TestingContainer.getTestSetsNonOpini().get(i));
        }

        Collections.shuffle(TestingContainer.getTestSetAll());
        TokenContainer.margeDataSet(TokenContainer.getMainTrainingPhrase(), TrainingContainer.getTrainSetsPositif());
        TokenContainer.margeDataSet(TokenContainer.getMainTrainingPhrase(), TrainingContainer.getTrainSetsNegatif());
        TokenContainer.margeDataSet(TokenContainer.getMainTrainingPhrase(), TrainingContainer.getTrainSetsNonOpini());
        Collections.shuffle(TokenContainer.getMainTrainingPhrase());
        Statistic.setNonopiniPhrase(TrainingContainer.getTrainSetsNonOpini().size());
        Statistic.setNegatifPhrase(TrainingContainer.getTrainSetsNegatif().size());
        Statistic.setPositivePhrase(TrainingContainer.getTrainSetsPositif().size());
    }

    public void finishing() {

        System.out.println("");


        end = System.currentTimeMillis();
        end = end - start;
        System.out.println("waktu =========> " + end);


        testSpliter();
        System.out.println("size Testing positif " + TestingContainer.getTestSetsPositif().size());
        System.out.println("size Testing negatif  " + TestingContainer.getTestSetsNegatif().size());
        System.out.println("size Testing nonopini " + TestingContainer.getTestSetsNonOpini().size());
        System.out.println("===========================================================================");
        parser.parserFromList(TrainingContainer.getTrainSetsPositif(), 1);
        parser.parserFromList(TrainingContainer.getTrainSetsNegatif(), 2);
        parser.parserFromList(TrainingContainer.getTrainSetsNonOpini(), 3);
        System.out.println("size training positif " + TrainingContainer.getTrainSetsPositif().size());
        System.out.println("size training negatif  " + TrainingContainer.getTrainSetsNegatif().size());
        System.out.println("size training nonopini " + TrainingContainer.getTrainSetsNonOpini().size());


        System.out.println("\n\n");



        LinkedHashMap<String, TokensEntity> list;
        Set<String> key;




        String tuple = "all";
        // list = (LinkedHashMap<String, TokensEntity>) TokenContainer.sortByComparator(TokenContainer.getMainTokens());


        int k = 1;
        key = TokenContainer.getMainTokens().keySet();
        System.out.println(" Load information tokens for " + tuple + " " + TokenContainer.getMainTokens().size() + " tokens");

        TokenContainer.printInformation(TokenContainer.getMainTokens());
        System.out.println("=================================================================================================================");
        System.out.println("Done.....\n\n");
        end = System.currentTimeMillis();
        end = end - start;

        System.out.println("\n\n");

        System.out.println(" jumlah record : " + TokenContainer.getMainTerms().size());
        System.out.println("All Data Instances Recording.... ");
        TokenContainer.printInstances(TokenContainer.getMainTerms(), TokenContainer.getMainTokens());

        end = System.currentTimeMillis();
        end = end - start;

        System.out.println("waktu instances recording finished =========> " + end);
        System.out.println("");
        System.out.println("load to file training and dataset .....");
        TokenContainer.printInstances(TrainingContainer.getTrainSetsPositif(), "Data TrainingSet Positif", 1);
        TokenContainer.printInstances(TrainingContainer.getTrainSetsNegatif(), "Data TrainingSet Negatif", 1);
        TokenContainer.printInstances(TrainingContainer.getTrainSetsNonOpini(), "Data TrainingSet NonOpini", 1);
        TokenContainer.printInstances(TestingContainer.getTestSetAll(), "Data Testing All", 0);

        TokenContainer.printWeka(TestingContainer.getTestSetAll(), "Testing DataSet Weka");
        TokenContainer.printWeka(TokenContainer.getMainTrainingPhrase(), "Training DataSet Weka ");
        System.out.println("");


        for (ResultEntity data : TestingContainer.getTestSetAll()) {
            System.out.println("=========================================================\n"
                    + data.getPhrase());
            System.out.println("=========================================================");
            data = bayesBuildRun(data);
            System.out.println("---------------------------------------");
            System.out.println("");
        }

        //for all field
//            shutDownService();

    }

    public void shutDownService() throws InterruptedException {
        serviceEx.shutdown();
        serviceEx.awaitTermination((long) 100, TimeUnit.MILLISECONDS);
    }

    public ResultEntity bayesBuildRun(ResultEntity testerStr) {
        ParseFile parser = new ParseFile();
        NaiveBayesModel bayes = new NaiveBayesModel();

        //case..
//        String phraseCase = test;
        //
        List<String> result = parser.parserAPhrase(testerStr.getPhrase());
        List<ClassEntity> classEnt = new ArrayList<ClassEntity>();
        ClassEntity positif = new ClassEntity();
        positif.setClassName("positif");
        ClassEntity negatif = new ClassEntity();
        negatif.setClassName("negatif");
        ClassEntity nonopini = new ClassEntity();
        nonopini.setClassName("nonopini");

        classEnt.add(positif);
        classEnt.add(negatif);
        classEnt.add(nonopini);
        bayes.setCases(result);
        bayes.setTargetList(classEnt);
        bayes.setTermList();

        ClassEntity bayesResult = bayes.resultBayes();
        System.out.println("");
        System.out.println("positif  : " + bayes.getTargetList().get(0).getvClass());
        System.out.println("negatif  : " + bayes.getTargetList().get(1).getvClass());
        System.out.println("nonopini : " + bayes.getTargetList().get(2).getvClass());
        System.out.println("hasil naive bayess : " + bayesResult.getClassName());
        if (bayesResult.getClassName().equals("positif")) {
            testerStr.setResult(0);
        } else if (bayesResult.getClassName().equals("negatif")) {
            testerStr.setResult(1);
        } else {
            testerStr.setResult(2);
        }
        return testerStr;
    }
}
