package com.den.SearchApp;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 03.09.2014.
 */
public class Queries {
    @Expose
    private List<NextPage> nextPage = new ArrayList<NextPage>();
    @Expose
    private List<Request> request = new ArrayList<Request>();

    public List<NextPage> getNextPage() {
        return nextPage;
    }

    public void setNextPage(List<NextPage> nextPage) {
        this.nextPage = nextPage;
    }

    public List<Request> getRequest() {
        return request;
    }

    public void setRequest(List<Request> request) {
        this.request = request;
    }

}
