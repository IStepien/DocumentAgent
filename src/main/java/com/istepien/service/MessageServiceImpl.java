package com.istepien.service;

import com.istepien.dao.MessageDao;
import com.istepien.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
@Service
public class MessageServiceImpl implements MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    MessageDao messageDao;

    @Transactional
    @Override
    public List<Message> getAllMessages() {
        return messageDao.getAllMessages();
    }

    @Transactional
    @Override
    public void saveMessage(Message message) {
        messageDao.saveMessage(message);
    }

    @Transactional
    @Override
    public Message getMessage(Long id) {
        return messageDao.getMessage(id);
    }

    @Transactional
    @Override
    public void deleteMessage(Long id) {
        messageDao.deleteMessage(id);
    }
}
