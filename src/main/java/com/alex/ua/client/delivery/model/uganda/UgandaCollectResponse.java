package com.alex.ua.client.delivery.model.uganda;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UgandaCollectResponse {
    private int k;
    private int bs;
    private int lv;
    private int ctc;
    private List<UgrqItem> ugrq;

    // Getters and Setters

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getBs() {
        return bs;
    }

    public void setBs(int bs) {
        this.bs = bs;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public int getCtc() {
        return ctc;
    }

    public void setCtc(int ctc) {
        this.ctc = ctc;
    }

    public List<UgrqItem> getUgrq() {
        return ugrq;
    }

    public void setUgrq(List<UgrqItem> ugrq) {
        this.ugrq = ugrq;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UgrqItem {

        private Result result;
        private Map<String, Integer> requires;
        private Long uge;

        // Getters and Setters

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }

        public Map<String, Integer> getRequires() {
            return requires;
        }

        public void setRequires(Map<String, Integer> requires) {
            this.requires = requires;
        }

        public Long getUge() {
            return uge;
        }

        public void setUge(Long uge) {
            this.uge = uge;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Result {

        private int k;
        private int bs;

        // Getters and Setters

        public int getK() {
            return k;
        }

        public void setK(int k) {
            this.k = k;
        }

        public int getBs() {
            return bs;
        }

        public void setBs(int bs) {
            this.bs = bs;
        }
    }
}
