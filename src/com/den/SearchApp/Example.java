package com.den.SearchApp;

import android.content.Context;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 03.09.2014.
 */
public class Example {
    @Expose
    private String kind;
    @Expose
    private Url url;
    @Expose
    private Queries queries;
    @Expose
    private Context context;
    @Expose
    private SearchInformation searchInformation;
    @Expose
    private Spelling spelling;
    @Expose
    private List<Item> items = new ArrayList<Item>();

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public Queries getQueries() {
        return queries;
    }

    public void setQueries(Queries queries) {
        this.queries = queries;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public SearchInformation getSearchInformation() {
        return searchInformation;
    }

    public void setSearchInformation(SearchInformation searchInformation) {
        this.searchInformation = searchInformation;
    }

    public Spelling getSpelling() {
        return spelling;
    }

    public void setSpelling(Spelling spelling) {
        this.spelling = spelling;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    static class Url{
        @Expose
        private static String type;
        @Expose
        private static String template;

        public static String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public static String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }
    }

}
