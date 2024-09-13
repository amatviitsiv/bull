package com.alex.ua.client.delivery.model.laos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LaosCollectResponse {
    private int k;
    private int bs;
    private int lv;
    private int ctc;
    private List<LarqItem> larq;

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

    public List<LarqItem> getLarq() {
        return larq;
    }

    public void setLarq(List<LarqItem> larq) {
        this.larq = larq;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class LarqItem {

        private Result result;
        private Map<String, Integer> requires;
        private Long lae;

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

        public Long getLae() {
            return lae;
        }

        public void setLae(Long lae) {
            this.lae = lae;
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
