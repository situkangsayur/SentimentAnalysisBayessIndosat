/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs.searchengine.services;

import com.cs.searchengine.entities.DocumentEntity;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author hendri
 */
public class DocumentContainer {

    private static ConcurrentHashMap<String, DocumentEntity> documents;

    public static void checkDocuments() {
        if (documents == null) {
            documents = new ConcurrentHashMap<String, DocumentEntity>();
        }
    }

    public static ConcurrentHashMap<String, DocumentEntity> getDocuments() {
        checkDocuments();

        return documents;
    }

    public static void setDocuments(ConcurrentHashMap<String, DocumentEntity> documents) {
        checkDocuments();

        DocumentContainer.documents = documents;
    }
}
