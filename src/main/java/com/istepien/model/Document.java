package com.istepien.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long docId;
    @Column(name = "title")
    private String docTitle;
    @Column(name = "description")
    private String docDescription;
    @Column(name = "comment")
    private String docComment;
    @Column(name = "date_added")
    private Date docDateAdded;
    @Column(name = "last_modified")
    private Date docLastModified;
    @Column(name = "link")
    private String link;

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

    public Date getDocDateAdded() {
        return docDateAdded;
    }

    public void setDocDateAdded(Date docDateAdded) {
        this.docDateAdded = docDateAdded;
    }

    public Date getDocLastModified() {
        return docLastModified;
    }

    public void setDocLastModified(Date docLastModified) {
        this.docLastModified = docLastModified;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
                ", link='" + link + '\'' +
                '}';
    }
}
