package com.vijendernagra.googlebookssearch.models.http;

import com.vijendernagra.googlebookssearch.models.pojo.SearchResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by vijendernagra on 2/16/18.
 */

public interface GoogleBooksAPI {

    public static final String URL_BASE = "https://www.googleapis.com/books/v1/volumes/";

    @GET("./")
    Observable<SearchResult> searchBook(@Query("q") String q);

}
