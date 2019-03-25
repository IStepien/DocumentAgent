package com.istepien.model;

import com.sun.istack.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

@Entity
@Table(name = "Document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long docId;
    @Column(name = "title")
    @NotNull
    private String docTitle;
    @Column(name = "description")
    private String docDescription;

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

    @OneToMany(mappedBy = "document", cascade = CascadeType.REMOVE)
    private Set<Message> messageSet;

    @OneToMany(mappedBy = "document", cascade = CascadeType.REMOVE)
    private Set<Comment> commentSet;


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

    public Set<Message> getMessageSet() {
        return messageSet;
    }

    public void setMessageSet(Set<Message> messageSet) {
        this.messageSet = messageSet;
    }

    public Set<Comment> getCommentSet() {
        return commentSet;
    }

    public void setCommentSet(Set<Comment> commentSet) {
        this.commentSet = commentSet;
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
                ", docDateAdded=" + docDateAdded +
                ", docLastModified=" + docLastModified +
                ", file=" + Arrays.toString(file) +
                ", user=" + user +
                '}';
    }
}
