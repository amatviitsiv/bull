package com.alex.ua.client.delivery.model.burundi;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BurundiCollectResponse {

    private int k;
    private int bs;
    private int lv;
    private int ctc;
    private List<BrrqItem> brrq;

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

    public List<BrrqItem> getBrrq() {
        return brrq;
    }

    public void setBrrq(List<BrrqItem> brrq) {
        this.brrq = brrq;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BrrqItem {

        private Result result;
        private Map<String, Integer> requires;
        private Long bre;

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

        public Long getBre() {
            return bre;
        }

        public void setBre(Long bre) {
            this.bre = bre;
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
