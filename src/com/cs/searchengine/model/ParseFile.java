/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs.searchengine.model;

import com.cs.searchengine.bayes.entities.ResultEntity;
import com.cs.searchengine.bayes.services.TestingContainer;
import com.cs.searchengine.entities.DocumentEntity;
import com.cs.searchengine.entities.HashMapGen;
import com.cs.searchengine.entities.Statistic;
import com.cs.searchengine.entities.TermEntity;
import com.cs.searchengine.entities.TokensEntity;
import com.cs.searchengine.services.RegexContainer;
import com.cs.searchengine.services.SpecialChar;
import com.cs.searchengine.services.TokenContainer;
import com.cs.searchengine.services.TupleContainer;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author hendri
 */
public class ParseFile implements Callable {

    private File file;
    private FileInputStream fstream;
    private DataInputStream in;
    private BufferedReader br;
    private String path;
    private String temp;
    private String strLine;
    private String tokens;
    private TokensEntity tokenEntity;
    private Integer job;
    private int lines;
    private String[] result;
    private int tuple;
    private ProcessFile walker;
    private HashMapGen tempHash;
    private List<TermEntity> tempTerms;
    private int position;
    private String before;

    /**
     * @author hendri
     * @param job
     */
    public void setJob(int job) {
        this.job = job;
    }

    /**
     * @author hendri
     * @param walker
     */
    public void setWalker(ProcessFile walker) {
        this.walker = walker;
    }

    /**
     * @author hendri
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @author hendri method will performed. method is a parser that will parse
     * all of word that find in every each tuple for each files that find from
     * file walker and the method will add the word and count the term to the
     * temporary hashmap return void
     * @throws FileNotFoundException
     */
    public void parseProgress() throws FileNotFoundException {
        try {
            position = 0;

            file = new File(this.path);
            fstream = new FileInputStream(file);
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            tempHash = new HashMapGen();
            tempTerms = new ArrayList<TermEntity>();

            int end = 0;
            boolean body = false;
            String symbols = " ";
            int near = 0;
            tuple = 6;
            temp = "all";

            while ((strLine = br.readLine()) != null) {

                ResultEntity re = new ResultEntity();
                re.setPhrase(strLine);

                if (path.indexOf("positif") != -1) {
                    re.setReal(0);
                    TestingContainer.getTestSetsPositif().add(re);
                } else if (path.indexOf("negatif") != -1) {
                    re.setReal(1);
                    TestingContainer.getTestSetsNegatif().add(re);
                } else {
                    re.setReal(2);
                    TestingContainer.getTestSetsNonOpini().add(re);
                }
            }
            body = false;
            in.close();
            br.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("string" + strLine + "|======================================================>> and lines :" + this.lines);
            e.printStackTrace();
        }
        in = null;
        br = null;
        file = null;
        result = null;

    }

    public void parserFromList(List<ResultEntity> datas, int type) {
        lines = 94;

        int end = 0;
        boolean body = false;
        String symbols = " ";
        int near = 0;
        tuple = 6;
        temp = "all";

        for (ResultEntity data : datas) {
            strLine = data.getPhrase();

            RegexContainer.setRegex(SpecialChar.SYMBOL_REGEX);
            RegexContainer.setPattern(Pattern.compile(RegexContainer.getRegex()));

            Matcher lastMatcher = RegexContainer.getPattern().matcher(strLine);

            strLine = lastMatcher.replaceAll("#");

            result = strLine.split("#");

//                if (strLine.length() == 0) {
//                    continue;
//                }

            for (int i = 0; i < result.length; i++) {

                tokens = result[i];
                tokens = tokens.trim();
                tokens = tokens.toLowerCase();

                if (tokens.length() > 0) {
                    tuple = TupleContainer.getTuples().get(temp);
                    addTokens(TokenContainer.getMainTokens(), type);

                }

            }
        }
    }

    /**
     * @author hendri method performance. To add a token to the temporary
     * hashmap and check the token condition that the token have input to the
     * hashmap before
     * @param tokenHash
     */
    public void addTokens(HashMapGen tokenHash, int type) {

        TokensEntity data;
        //UrlDocEntity urlEnt;
        Integer urlEnt;
        HashMap<String, Integer> hashDoc;
        DocumentEntity docEnt;

        int count = 0;
        int urlCount = 0;
        lines = 219;

        if (!tokenHash.containsKey(tokens)) {
            lines = 221;
            Statistic.setAllTokens(Statistic.getAllTokens() + 1);
            data = new TokensEntity();
            urlEnt = new Integer(1);
            hashDoc = new HashMap<String, Integer>();

            data.setCount(1);
            data.setWeight(0.0);
            data.setTokens(tokens);
            checkClass(data, type);

            hashDoc.put(job.toString(), urlEnt);

            data.setUrl(hashDoc);
            tokenHash.put(tokens, data);
        } else {
            lines = 231;
            Statistic.setAllTokens(Statistic.getAllTokens() + 1);
            data = tokenHash.get(tokens);
            count = data.getCount();
            data.setCount(count + 1);
            checkClass(data, type);

            if (data.getUrl().containsKey(job.toString())) {
                urlEnt = data.getUrl().get(job.toString());

                urlEnt = urlEnt + 1;

            } else {
                urlEnt = new Integer(1);
            }
            data.getUrl().put(job.toString(), urlEnt);
            lines = 244;
//            System.out.println("nonopini : "+data.getNonOpini());
            tokenHash.replace(tokens, data);
        }

        TermEntity term = new TermEntity();
        switch (type) {
            case 1:
                term.setClassTarget("positif");
                Statistic.setPositive(Statistic.getPositive() + 1);
                break;
            case 2:
                term.setClassTarget("negatif");
                Statistic.setNegative(Statistic.getNegative() + 1);
                break;
            case 3:
                term.setClassTarget("nonopini");
                Statistic.setNonopini(Statistic.getNonopini() + 1);
                break;
        }
        term.setTerms(tokens);
        tempTerms.add(term);
    }

    public void checkClass(TokensEntity entity, int type) {
        switch (type) {
            case 1:
                entity.setPositif(entity.getPositif() + 1);
                break;
            case 2:
                entity.setNegatif(entity.getNegatif() + 1);
                break;
            case 3:
                entity.setNonOpini(entity.getNonOpini() + 1);
                break;
        }

    }

    public List<String> parserAPhrase(String phraseCase) {

        position = 0;


        tempHash = new HashMapGen();
        List<String> pharseTerm = Collections.synchronizedList(new ArrayList<String>());

        int end = 0;
        boolean body = false;
        String symbols = " ";
        int near = 0;
        tuple = 6;
        temp = "all";
        ///==============
        strLine = phraseCase.trim();

        lines = 394;


        RegexContainer.setRegex(SpecialChar.SYMBOL_REGEX);
        RegexContainer.setPattern(Pattern.compile(RegexContainer.getRegex()));

        Matcher lastMatcher = RegexContainer.getPattern().matcher(strLine);

        strLine = lastMatcher.replaceAll("#");

        result = strLine.split("#");



        for (int i = 0; i < result.length; i++) {

            tokens = result[i];
            tokens = tokens.trim();
            tokens = tokens.toLowerCase();

            if (tokens.length() == 0) {
                continue;
            }

            tuple = TupleContainer.getTuples().get(temp);


            pharseTerm.add(tokens);

        }
        return pharseTerm;

    }

    @Override
    public Boolean call() throws Exception {
        parseProgress();
        walker.listener(job, tempHash, tempTerms);
        return true;
    }
}
