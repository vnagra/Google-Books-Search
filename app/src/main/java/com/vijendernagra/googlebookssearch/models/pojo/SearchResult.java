package com.vijendernagra.googlebookssearch.models.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vijendernagra on 2/16/18.
 */

public class SearchResult {

    @SerializedName("items")
    private List<Book> listBooks;

    private String className;

    public List<Book> getListBooks() {
        return listBooks;
    }

    public void setListBooks(List<Book> listBooks) {
        this.listBooks = listBooks;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
