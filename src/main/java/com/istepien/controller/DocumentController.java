//package com.istepien.controller;
//
//import com.istepien.model.Document;
//import com.istepien.service.DocumentService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/documents")
//public class DocumentController {
//
//    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);
//
//    @Autowired
//    private DocumentService documentService;
//
//    @GetMapping("/formForAddDocument")
//    public String showFormForAdd(Model theModel) {
//
//        Document document = new Document();
//
//        theModel.addAttribute("document", document);
//
//        return "add-document";
//    }
//
//    @PostMapping("/saveDocument")
//    public String saveDocument(@ModelAttribute("document") Document document) {
//        documentService.saveDocument(document);
//
//        return "home-page";
//    }
//
//    @GetMapping("/list")
//    public String listDocuments(Model model) {
//
//        List<Document> documentList = documentService.getAllDocuments();
//
//        model.addAttribute(documentList);
//
//        return "document-list";
//    }
//
//    @GetMapping("/deleteDocument")
//    public String deleteDocument(@RequestParam(name = "docId") Long docId) {
//
//        documentService.deleteDocument(docId);
//
//        return "redirect:list";
//    }
//
//    @GetMapping("/formForUpdateDocument")
//    public String showFormForUpdateDocument(@RequestParam(name = "docId") Long docId, Model model) {
//
//        Document doc = documentService.getDocument(docId);
//
//        model.addAttribute("document", doc);
//
//        return "update-document";
//    }
//
//
//}
