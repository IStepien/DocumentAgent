package com.istepien.service;

import com.istepien.model.Document;

import java.util.List;

public interface DocumentService {
    public List<Document> getAllDocuments();

    public void saveDocument(Document document);

    public Document getDocument(Long id);

    public void deleteDocument(Long id);
    public void updateDocument(Document document);
}
