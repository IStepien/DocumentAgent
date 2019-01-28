package com.istepien.dao;

import com.istepien.model.Document;

import java.util.List;

public interface DocumentDao {

    public List<Document> getAllDocuments();

    public void saveDocument(Document document);

    public Document getDocument(Long id);

    public void deleteDocument(Long id);
}
