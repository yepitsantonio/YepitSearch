package com.myPackage;

public class SearchResult {
    private String source;
    private String title;
    private String link;
    private String displayedLink;
    private String snippet;

    public SearchResult(String source, String title, String link, String displayedLink, String snippet) {
        this.source = source;
        this.title = title;
        this.link = link;
        this.displayedLink = displayedLink;
        this.snippet = snippet;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "source='" + source + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", displayedLink='" + displayedLink + '\'' +
                ", snippet='" + snippet + '\'' +
                '}';
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDisplayedLink() {
        return displayedLink;
    }

    public void setDisplayedLink(String displayedLink) {
        this.displayedLink = displayedLink;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
}
