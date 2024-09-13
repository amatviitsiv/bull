package com.alex.ua.client.delivery.model.moldova;

import com.alex.ua.client.delivery.model.laos.LaosCollectResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoldovaCollectResponse {

    private int k;
    private int bs;
    private int lv;
    private int ctc;
    private List<MoldovaCollectResponse.MdrqItem> mdrq;

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

    public List<MoldovaCollectResponse.MdrqItem> getMdrq() {
        return mdrq;
    }

    public void setMdrq(List<MoldovaCollectResponse.MdrqItem> mdrq) {
        this.mdrq = mdrq;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MdrqItem {

        private MoldovaCollectResponse.Result result;
        private Map<String, Integer> requires;
        private Long mde;

        // Getters and Setters

        public MoldovaCollectResponse.Result getResult() {
            return result;
        }

        public void setResult(MoldovaCollectResponse.Result result) {
            this.result = result;
        }

        public Map<String, Integer> getRequires() {
            return requires;
        }

        public void setRequires(Map<String, Integer> requires) {
            this.requires = requires;
        }

        public Long getMde() {
            return mde;
        }

        public void setMde(Long mde) {
            this.mde = mde;
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
