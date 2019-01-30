package com.istepien.dao;

import com.istepien.model.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DocumentDaoImpl implements DocumentDao{
    private static final Logger logger = LoggerFactory.getLogger(DocumentDaoImpl.class);
    private SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public List<Document> getAllDocuments() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Document> documentList = session.createQuery("from Document").list();
        for(Document document : documentList){
            logger.info("Document list:"+document);
        }
        return documentList;
    }

    @Override
    public void saveDocument(Document document) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(document);
        logger.info("Document saved successfully, document details="+document);
    }

    @Override
    public Document getDocument(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Document document = (Document) session.load(Document.class, new Long(id));
        logger.info("Document loaded successfully, document details="+document);
        return document;
    }

    @Override
    public void deleteDocument(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Document doc = (Document) session.load(Document.class, new Long(id));
        if(doc != null){
            session.delete(doc);
        }
        logger.info("Document deleted successfully, document details="+doc);
    }

    @Override
    public void updateDocument(Document document) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(document);
        logger.info("Document updated successfully, document details="+document);
    }
}
