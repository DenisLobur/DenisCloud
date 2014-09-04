package com.den.SearchApp;

import com.google.gson.annotations.Expose;

/**
 * Created by Denis on 03.09.2014.
 */
public class Spelling {
    @Expose
    private String correctedQuery;
    @Expose
    private String htmlCorrectedQuery;

    public String getCorrectedQuery() {
        return correctedQuery;
    }

    public void setCorrectedQuery(String correctedQuery) {
        this.correctedQuery = correctedQuery;
    }

    public String getHtmlCorrectedQuery() {
        return htmlCorrectedQuery;
    }

    public void setHtmlCorrectedQuery(String htmlCorrectedQuery) {
        this.htmlCorrectedQuery = htmlCorrectedQuery;
    }

}
