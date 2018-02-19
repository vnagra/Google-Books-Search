package com.vijendernagra.googlebookssearch.models.realm.po;

import io.realm.RealmObject;

/**
 * Created by vijendernagra on 2/16/18.
 */

public class RealmString extends RealmObject {

    private String value;

    public RealmString() {
    }

    public RealmString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
