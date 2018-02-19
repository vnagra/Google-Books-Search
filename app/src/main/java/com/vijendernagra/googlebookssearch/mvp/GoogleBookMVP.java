package com.vijendernagra.googlebookssearch.mvp;

import com.vijendernagra.googlebookssearch.models.pojo.SearchResult;

import rx.Observable;

/**
 * Created by vijendernagra on 2/16/18.
 */

public interface GoogleBookMVP {

    interface Model {
        Observable<SearchResult> searchListOfBooks(String query);
    }

    interface View {
        void searhByQuery(String query);
        void showError();
        void showNoResults();
        void updateListView(SearchResult searchResult);
    }

    interface Presenter {
        void searchListOfBooks(String query);
    }

}
