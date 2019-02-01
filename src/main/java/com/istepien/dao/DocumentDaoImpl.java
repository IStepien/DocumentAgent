package com.istepien.dao;

import com.istepien.model.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class DocumentDaoImpl implements DocumentDao{
    private static final Logger logger = LoggerFactory.getLogger(DocumentDaoImpl.class);


    @Autowired
   private SessionFactory session;

    @Override
    public List<Document> getAllDocuments() {

        List<Document> documentList = session.openSession().createQuery("from Document").list();
        for(Document document : documentList){
            logger.info("Document list:"+document);
        }
        return documentList;
    }

    @Override
    public void saveDocument(Document document) {

        session.openSession().save(document);
        logger.info("Document saved successfully, document details="+document);
    }

    @Override
    public Document getDocument(Long id) {
        Document document= (Document) session.openSession().load(Document.class, new Long(id));
        logger.info("Document loaded successfully, document details="+document);
        return document;
    }

    @Override
    public void deleteDocument(Long id) {

        Document doc = (Document) session.openSession().load(Document.class, new Long(id));
        if(doc != null){
            session.openSession().delete(doc);
        }
        logger.info("Document deleted successfully, document details="+doc);
    }

    @Override
    public void updateDocument(Document document) {
        session.openSession().update(document);
        logger.info("Document updated successfully, document details="+document);
    }
}
