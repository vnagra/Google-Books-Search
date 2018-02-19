package com.vijendernagra.googlebookssearch.models.http;

import com.vijendernagra.googlebookssearch.models.pojo.SearchResult;
import com.vijendernagra.googlebookssearch.mvp.GoogleBookMVP;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by vijendernagra on 2/16/18.
 */

public class GoogleBooksService implements GoogleBookMVP.Model {

    public Observable<SearchResult> searchListOfBooks(String query) {

        RxJavaCallAdapterFactory rxAdapter =
                RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GoogleBooksAPI.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build();

        GoogleBooksAPI googleBooksService = retrofit.create(GoogleBooksAPI.class);
        Observable<SearchResult> searchResultObservable = googleBooksService.searchBook(query);

        return searchResultObservable;
    }

}
