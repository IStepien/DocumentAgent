package com.istepien.service;

import com.istepien.dao.CommentDao;
import com.istepien.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDao commentDao;


    @Override
    @Transactional
    public List<Comment> getAllComments() {
        return commentDao.getAllComments();
    }

    @Override
    @Transactional
    public void saveComment(Comment comment) {
        commentDao.saveComment(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        commentDao.deleteComment(id);
    }

    @Override
    @Transactional
    public Comment getComment(Long id) {
        return commentDao.getComment(id);
    }
}
