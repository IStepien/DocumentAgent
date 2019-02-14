package com.istepien.dao;

import com.istepien.model.Document;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public class DocumentDaoImpl implements DocumentDao {
    private static final Logger logger = LoggerFactory.getLogger(DocumentDaoImpl.class);


    @Autowired
    private SessionFactory sessionfactory;

    public DocumentDaoImpl() {
    }

    @Override
    public List<Document> getAllDocuments() {
        Session sessionObj = sessionfactory.openSession();
        sessionObj.beginTransaction();
        List<Document> documentList = sessionObj.createQuery("from Document").list();
        sessionObj.getTransaction().commit();
        for (Document document : documentList) {
            logger.info("Document list:" + document);
        }
        return documentList;
    }

    @Override
    public void saveDocument(Document document) {
        Session sessionObj = sessionfactory.openSession();
        sessionObj.beginTransaction();
        sessionObj.saveOrUpdate(document);
        sessionObj.getTransaction().commit();
        logger.info("Document saved successfully, document details=" + document);

    }

    @Override
    public Document getDocument(Long id) {
        Session sessionObj = sessionfactory.openSession();
        sessionObj.beginTransaction();
        Document document = (Document) sessionObj.load(Document.class, new Long(id));
        sessionObj.getTransaction().commit();
        logger.info("Document loaded successfully, document details=" + document);
        return document;
    }

    @Override
    public void deleteDocument(Long id) {
        Session sessionObj = sessionfactory.openSession();
        sessionObj.beginTransaction();
        Document doc = sessionObj.load(Document.class, new Long(id));
        sessionObj.delete(doc);
        sessionObj.getTransaction().commit();
        logger.info("Document deleted successfully, document details=" + doc);
        sessionObj.close();
    }


}
