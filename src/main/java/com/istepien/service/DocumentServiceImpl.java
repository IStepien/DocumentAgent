package com.istepien.service;

import com.istepien.dao.DocumentDao;
import com.istepien.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    DocumentDao documentDao;

    @Override
    @Transactional
    public List<Document> getAllDocuments() {
        return documentDao.getAllDocuments();
    }

    @Override
    @Transactional
    public void saveDocument(Document document) {
        documentDao.saveDocument(document);
    }


    @Override
    @Transactional
    public Document getDocument(Long id) {

        return documentDao.getDocument(id);
    }

    @Override
    @Transactional
    public void deleteDocument(Long id) {
        documentDao.deleteDocument(id);
    }


}
