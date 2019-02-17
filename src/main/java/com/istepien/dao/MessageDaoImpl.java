package com.istepien.dao;

import com.istepien.model.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageDaoImpl implements  MessageDao{
    private static final Logger logger = LoggerFactory.getLogger(MessageDaoImpl.class);


    @Autowired
    private SessionFactory sessionfactory;

    public MessageDaoImpl() {
    }

    @Override
    public List<Message> getAllMessages() {
        Session sessionObj = sessionfactory.openSession();
        sessionObj.beginTransaction();
        List<Message> messageList = sessionObj.createQuery("from Message").list();
        sessionObj.getTransaction().commit();
        for (Message message : messageList) {
            logger.info("Messages list:" + message);
        }
        return messageList;
    }

    @Override
    public void saveMessage(Message message) {
        Session sessionObj = sessionfactory.openSession();
        sessionObj.beginTransaction();
        sessionObj.saveOrUpdate(message);
        sessionObj.getTransaction().commit();
        logger.info("Message saved successfully, document details=" + message);

    }

    @Override
    public Message getMessage(Long id) {
        Session sessionObj = sessionfactory.openSession();
        sessionObj.beginTransaction();
        Message message = (Message) sessionObj.load(Message.class, new Long(id));
        sessionObj.getTransaction().commit();
        logger.info("Message loaded successfully, document details=" + message);
        return message;
    }

    @Override
    public void deleteMessage(Long id) {
        Session sessionObj = sessionfactory.openSession();
        sessionObj.beginTransaction();
        Message message = sessionObj.load(Message.class, new Long(id));
        sessionObj.delete(message);
        sessionObj.getTransaction().commit();
        logger.info("Message deleted successfully, document details=" + message);
        sessionObj.close();
    }


}
