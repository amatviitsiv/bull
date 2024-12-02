package com.alex.ua.client.delivery.model.serbiya;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SerbiaCollectResponse {

    private int k;
    private int bs;
    private int lv;
    private int ctc;
    private List<SbrqItem> sbrq;

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

    public List<SbrqItem> getSbrq() {
        return sbrq;
    }

    public void setSbrq(List<SbrqItem> sbrq) {
        this.sbrq = sbrq;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SbrqItem {

        private SerbiaCollectResponse.Result result;
        private Map<String, Integer> requires;
        private Long sbe;

        // Getters and Setters

        public SerbiaCollectResponse.Result getResult() {
            return result;
        }

        public void setResult(SerbiaCollectResponse.Result result) {
            this.result = result;
        }

        public Map<String, Integer> getRequires() {
            return requires;
        }

        public void setRequires(Map<String, Integer> requires) {
            this.requires = requires;
        }

        public Long getSbe() {
            return sbe;
        }

        public void setSbe(Long sbe) {
            this.sbe = sbe;
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
