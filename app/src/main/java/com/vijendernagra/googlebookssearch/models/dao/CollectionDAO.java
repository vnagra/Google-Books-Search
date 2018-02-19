package com.vijendernagra.googlebookssearch.models.dao;

import android.util.Log;

import com.vijendernagra.googlebookssearch.models.pojo.Book;
import com.vijendernagra.googlebookssearch.models.realm.po.RealmBook;
import com.vijendernagra.googlebookssearch.models.realm.util.RealmUtil;
import com.vijendernagra.googlebookssearch.mvp.CollectionMVP;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by vijendernagra on 2/16/18.
 */

public class CollectionDAO implements CollectionMVP.Model {

    private Realm realm;
    private boolean stateRealm;

    @Override
    public int saveBook(Book book) {
        this.openRealm();
        int result = BOOK_EXISTS_NOT_EXISTS;

        RealmBook realmBook = RealmUtil.convertParcelableBookToPOBook(book);

        if (this.getBook(realmBook) == null) {

            try {
                this.realm.executeTransaction(
                        realm -> realm.copyToRealmOrUpdate(realmBook)
                );

                result = PERSIST_OK;
            } catch (Exception e) {
                Log.e("ERROR", "CollectionDAO - saveBook");
                e.printStackTrace();
                result = PERSIST_PROBLEM;
            }

        }

        return result;
    }

    @Override
    public Observable<RealmResults<RealmBook>> getCollection() {
        this.openRealm();

        return this.realm
                .where(RealmBook.class)
                .findAll()
                .asObservable();
    }

    @Override
    public RealmBook getBook(RealmBook realmBook) {
        this.openRealm();
        RealmResults<RealmBook> books =
                this.realm
                        .where(RealmBook.class)
                        .equalTo(RealmBook.ID, realmBook.getId())
                        .findAll();

        if (!books.isEmpty()) {
            return books.first();
        }

        return null;
    }

    @Override
    public int removeBook(RealmBook realmBook) {
        this.openRealm();
        int result = PERSIST_PROBLEM;

        try {
            this.realm.executeTransaction(realm -> {
                realm.where(RealmBook.class)
                        .equalTo(RealmBook.ID, realmBook.getId())
                        .findAll()
                        .deleteAllFromRealm();
            });

            result = PERSIST_OK;
        } catch (Exception e) {
            Log.e("ERROR", "CollectionDAO - removeBook");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void openRealm() {
        this.stateRealm = true;
        if (this.realm == null) {
            this.realm = Realm.getDefaultInstance();
        }
    }

    @Override
    public void closeRealm() {
        if (this.stateRealm) {
            this.realm.close();
            this.realm = null;
        }
    }

}

