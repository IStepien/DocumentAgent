package com.istepien.service;

import com.istepien.model.Message;

import java.util.List;

public interface MessageService {
    public List<Message> getAllMessages();

    public void saveMessage(Message message);

    public Message getMessage(Long id);

    public void deleteMessage(Long id);
}
