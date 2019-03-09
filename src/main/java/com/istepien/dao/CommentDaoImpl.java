package com.istepien.dao;

import com.istepien.model.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDaoImpl implements CommentDao {

    private static final Logger logger = LoggerFactory.getLogger(CommentDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public CommentDaoImpl() {
    }

    @Override
    public List<Comment> getAllComments() {
        Session sessionObj = sessionFactory.openSession();
        sessionObj.beginTransaction();
        List<Comment> commentList = sessionObj.createQuery("from Comment").list();
        sessionObj.getTransaction().commit();
        for (Comment comment : commentList) {
            logger.info("Comment list:" + comment.getText());
        }

        return commentList;
    }

    @Override
        public void saveComment(Comment comment) {
        Session sessionObj = sessionFactory.openSession();
        sessionObj.beginTransaction();
        sessionObj.saveOrUpdate(comment);
        sessionObj.getTransaction().commit();
        logger.info("Comment saved successfully, detalis" + comment);
        sessionObj.close();

    }

    @Override
    public void deleteComment(Long id) {
        Session sessionObj = sessionFactory.openSession();
        sessionObj.beginTransaction();
        Comment comment = sessionObj.load(Comment.class, new Long (id));
        sessionObj.delete(comment);
        sessionObj.getTransaction().commit();
        logger.info("Comment deleted successfully, details"+comment);
        sessionObj.close();
    }

    @Override
    public Comment getComment(Long id) {
        Session sessionObj = sessionFactory.openSession();
        sessionObj.beginTransaction();
        Comment comment = (Comment)sessionObj.load(Comment.class, new Long(id));
        sessionObj.getTransaction().commit();
        logger.info("Comment loaded successfully, details "+comment);
        return comment;
    }
}
