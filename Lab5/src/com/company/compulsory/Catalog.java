package com.company.compulsory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Catalog implements Serializable {
    private String name;
    private String path;
    private List<Document> documents = new ArrayList<>();

    public Catalog(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public void addDocument(Document document){documents.add(document);}

    public Document findByID(String ID){
        return documents.stream()
                .filter(d -> d.getId().equals(ID))
                .findFirst().orElse(null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    @Override
    public String toString() {
        return "Catalog:\n" +
                "name: '" + name + '\'' +
                "\npath='" + path + '\'' +
                "\ndocuments:\n" + documents;
    }

    public String toHTML(){
        StringBuilder sb = new StringBuilder();

        sb.append("<h1>").append(name).append(" </h1>\n");
        sb.append("\t<p>path: ").append(path).append(" </p>\n");

        sb.append("<h2>Documents:</h2>\n");

        for(var document: documents){
            sb.append(document.toHTML());
        }

        return sb.toString();
    }
}
