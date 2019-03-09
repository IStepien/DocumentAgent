package com.istepien.service;

import com.istepien.model.Comment;

import java.util.List;

public interface CommentService {
    public List<Comment> getAllComments();
    public void saveComment(Comment comment);
    public void deleteComment(Long id);
    public Comment getComment (Long id);
}
