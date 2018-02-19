package com.vijendernagra.googlebookssearch.presenter;

import com.vijendernagra.googlebookssearch.models.dao.CollectionDAO;
import com.vijendernagra.googlebookssearch.models.pojo.Book;
import com.vijendernagra.googlebookssearch.mvp.CollectionMVP;

/**
 * Created by vijendernagra on 2/16/18.
 */

public class AddCollectionPresenter implements CollectionMVP.AddPresenter {

    private CollectionMVP.Model model;

    public AddCollectionPresenter() {
        this.model = new CollectionDAO();
    }

    @Override
    public int saveBook(Book book) {
        return this.model.saveBook(book);
    }

    @Override
    public void closeRealm() {
        this.model.closeRealm();
    }

}

