package com.vijendernagra.googlebookssearch.presenter;

import android.util.Log;

import com.vijendernagra.googlebookssearch.models.dao.CollectionDAO;
import com.vijendernagra.googlebookssearch.models.realm.po.RealmBook;
import com.vijendernagra.googlebookssearch.mvp.CollectionMVP;

import org.greenrobot.eventbus.EventBus;

import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by vijendernagra on 2/16/18.
 */
public class MyCollectionPresenter implements CollectionMVP.GridPresenter {

    private CollectionMVP.Model model;

    private CollectionMVP.GridView view;

    public MyCollectionPresenter(CollectionMVP.GridView view) {
        this.model = new CollectionDAO();
        this.view = view;
    }

    @Override
    public void getMyCollection() {
        Observable<RealmResults<RealmBook>> results = this.model.getCollection();
        results
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        realmResults -> EventBus.getDefault().postSticky(realmResults),
                        throwable -> {
                            view.showError();
                            throwable.printStackTrace();
                        },
                        () -> Log.d("LOG", "getMyCollection complete!")
                );
    }

    @Override
    public int removeBook(RealmBook realmBook) {
        return this.model.removeBook(realmBook);
    }

    @Override
    public void closeRealm() {
        this.model.closeRealm();
    }

}

