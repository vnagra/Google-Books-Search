package com.vijendernagra.googlebookssearch.models.realm.util;

import com.vijendernagra.googlebookssearch.models.pojo.Book;
import com.vijendernagra.googlebookssearch.models.pojo.ImageLinks;
import com.vijendernagra.googlebookssearch.models.pojo.VolumeInfo;
import com.vijendernagra.googlebookssearch.models.realm.po.RealmBook;
import com.vijendernagra.googlebookssearch.models.realm.po.RealmString;

import io.realm.RealmList;

/**
 * Created by vijendernagra on 2/16/18.
 */

public final class RealmUtil {

    private RealmUtil() {
    }

    public static RealmBook convertParcelableBookToPOBook(Book book) {
        RealmBook realmBook = new RealmBook();
        realmBook.setId(book.getId());

        VolumeInfo vInfo = book.getVolumeInfo();
        realmBook.setTitle(vInfo.getTitle());
        realmBook.setSubtitle(vInfo.getSubtitle());

        realmBook.setAuthors(new RealmList<>());
        for (String author : vInfo.getAuthors()) {
            realmBook.getAuthors().add(new RealmString(author));
        }

        realmBook.setPublisher(vInfo.getPublisher());
        realmBook.setDescription(vInfo.getDescription());
        realmBook.setPublishedDate(vInfo.getPublishedDate());
        realmBook.setPageCount(vInfo.getPageCount());

        ImageLinks imageLinks = vInfo.getImageLinks();
        realmBook.setSmallThumbnail(imageLinks.getSmallThumbnail());
        realmBook.setThumbnail(imageLinks.getThumbnail());

        return realmBook;
    }

    public static String realmListToString(RealmList<RealmString> realmList) {
        if (realmList == null)
            return null;

        int iMax = realmList.size() - 1;
        if (iMax == -1)
            return null;

        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0; ; i++) {
            builder.append(String.valueOf(realmList.get(i).getValue()));
            if (i == iMax)
                return builder.append(']').toString();
            builder.append(", ");
        }
    }

    public static Book convertPOBookToParcelableBook(RealmBook realmBook) {
        Book book = new Book();
        book.setId(realmBook.getId());

        VolumeInfo vInfo = new VolumeInfo();
        book.setVolumeInfo(vInfo);
        vInfo.setTitle(realmBook.getTitle());
        vInfo.setSubtitle(realmBook.getSubtitle());

        String[] authors = new String[realmBook.getAuthors().size()];
        for (int i = 0; i < realmBook.getAuthors().size(); i++) {
            authors[i] = realmBook.getAuthors().get(i).getValue();
        }
        vInfo.setAuthors(authors);

        vInfo.setPublisher(realmBook.getPublisher());
        vInfo.setDescription(realmBook.getDescription());
        vInfo.setPublishedDate(realmBook.getPublishedDate());
        vInfo.setPageCount(realmBook.getPageCount());

        ImageLinks imageLinks = new ImageLinks();
        vInfo.setImageLinks(imageLinks);
        imageLinks.setSmallThumbnail(realmBook.getSmallThumbnail());
        imageLinks.setThumbnail(realmBook.getThumbnail());

        return book;
    }
}
