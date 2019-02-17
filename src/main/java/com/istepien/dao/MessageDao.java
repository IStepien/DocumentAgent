package com.istepien.dao;

import com.istepien.model.Message;

import java.util.List;

public interface MessageDao {
    public List<Message> getAllMessages();

    public void saveMessage(Message message);

    public Message getMessage(Long id);

    public void deleteMessage(Long id);
}
