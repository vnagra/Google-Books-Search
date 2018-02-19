package com.vijendernagra.googlebookssearch.presenter;

import android.util.Log;

import com.vijendernagra.googlebookssearch.models.http.GoogleBooksService;
import com.vijendernagra.googlebookssearch.models.pojo.SearchResult;
import com.vijendernagra.googlebookssearch.mvp.GoogleBookMVP;

import org.greenrobot.eventbus.EventBus;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vijendernagra on 2/16/18.
 */

public class GoogleBooksPresenter implements GoogleBookMVP.Presenter {

    private GoogleBookMVP.View view;

    private GoogleBookMVP.Model model;

    public GoogleBooksPresenter(GoogleBookMVP.View view) {
        this.view = view;
        this.model = new GoogleBooksService();
    }

    @Override
    public void searchListOfBooks(String query) {
        Observable<SearchResult> result = model.searchListOfBooks(query);
        result
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        searchResult -> EventBus.getDefault().postSticky(searchResult),
                        throwable -> {
                            view.showError();
                            throwable.printStackTrace();
                        },
                        () -> Log.d("LOG", "searchListOfBooks complete!")
                );
    }
}

