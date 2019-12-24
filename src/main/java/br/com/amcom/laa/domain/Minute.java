package br.com.amcom.laa.domain;

public class Minute {

    private String dateTime;
    private Long count;

    public Minute(String dateTime, Long count) {
        this.dateTime = dateTime;
        this.count = count;
    }

    public String getDateTime() {
        return dateTime;
    }

    public Long getCount() {
        return count;
    }
}
