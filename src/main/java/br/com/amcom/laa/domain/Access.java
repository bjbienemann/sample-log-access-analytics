package br.com.amcom.laa.domain;

public class Access {

    private String url;
    private Long count;

    public Access(String url, Long count) {
        this.url = url;
        this.count = count;
    }

    public String getUrl() {
        return url;
    }

    public Long getCount() {
        return count;
    }

}
