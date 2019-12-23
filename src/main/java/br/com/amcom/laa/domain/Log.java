package br.com.amcom.laa.domain;

import javax.json.bind.annotation.JsonbDateFormat;
import java.util.Date;


public class Log {

    private String url;
    @JsonbDateFormat(JsonbDateFormat.TIME_IN_MILLIS)
    private Date dateTime;
    private String uuid;
    private Integer regionCode;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(Integer regionCode) {
        this.regionCode = regionCode;
    }
}
