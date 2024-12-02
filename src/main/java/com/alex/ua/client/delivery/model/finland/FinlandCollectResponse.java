package com.alex.ua.client.delivery.model.finland;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FinlandCollectResponse {

    private int k;
    private int bs;
    private int lv;
    private int ctc;
    private List<FirqItem> firq;

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

    public List<FirqItem> getFirq() {
        return firq;
    }

    public void setFirq(List<FirqItem> firq) {
        this.firq = firq;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FirqItem {

        private FinlandCollectResponse.Result result;
        private Map<String, Integer> requires;
        private Long fie;

        // Getters and Setters

        public FinlandCollectResponse.Result getResult() {
            return result;
        }

        public void setResult(FinlandCollectResponse.Result result) {
            this.result = result;
        }

        public Map<String, Integer> getRequires() {
            return requires;
        }

        public void setRequires(Map<String, Integer> requires) {
            this.requires = requires;
        }

        public Long getFie() {
            return fie;
        }

        public void setFie(Long fie) {
            this.fie = fie;
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
