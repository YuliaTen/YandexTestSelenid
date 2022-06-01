package apiUtils.modelMessage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    private String fid;
    @JsonProperty("firstline")
    private String firstLine;
    private String subject;
    private List<Field> field;
    private int idx;

    @Override
    public String toString() {
        return "Message{" +
                "fid='" + fid + '\'' +
                ", firstLine='" + firstLine + '\'' +
                ", subject='" + subject + '\'' +
                ", field=" + field +
                ", idx=" + idx +
                '}';
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFirstLine() {
        return firstLine;
    }

    public void setFirstLine(String firstLine) {
        this.firstLine = firstLine;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public List<Field> getField() {
        return field;
    }

    public void setField(List<Field> field) {
        this.field = field;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }
}
