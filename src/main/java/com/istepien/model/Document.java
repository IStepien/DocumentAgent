package com.istepien.model;

import com.sun.deploy.security.ValidationState;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long docId;
    @Column(name = "title")
    private String docTitle;
    @Column(name = "description")
    private String docDescription;
    @Column(name = "comment")
    private String docComment;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_added")
    private LocalDate docDateAdded;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "last_modified")
    private LocalDate docLastModified;
    @Column(name = "file")
    @Lob
    private byte[] file;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Document() {
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getDocDescription() {
        return docDescription;
    }

    public void setDocDescription(String docDescription) {
        this.docDescription = docDescription;
    }

    public String getDocComment() {
        return docComment;
    }

    public void setDocComment(String docComment) {
        this.docComment = docComment;
    }

    public LocalDate getDocDateAdded() {
        return docDateAdded;
    }

    public void setDocDateAdded(LocalDate docDateAdded) {
        this.docDateAdded = docDateAdded;
    }

    public LocalDate getDocLastModified() {
        return docLastModified;
    }

    public void setDocLastModified(LocalDate docLastModified) {
        this.docLastModified = docLastModified;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

        public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Document{" +
                "docId=" + docId +
                ", docTitle='" + docTitle + '\'' +
                ", docDescription='" + docDescription + '\'' +
                ", docComment='" + docComment + '\'' +
                ", docDateAdded=" + docDateAdded +
                ", docLastModified=" + docLastModified +
                ", file='" + file + '\'' +
                ", user=" + user +
                '}';
    }
}
