package com.alex.ua.client.delivery.model.eddy;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EddyResponse {
    private String st;
    private String et;
    private Map<String, Integer> requires;
    private Result result;

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getEt() {
        return et;
    }

    public void setEt(String et) {
        this.et = et;
    }

    public Map<String, Integer> getRequires() {
        return requires;
    }

    public void setRequires(Map<String, Integer> requires) {
        this.requires = requires;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Result {

        private int c;
        private int k;

        public int getC() {
            return c;
        }

        public void setC(int c) {
            this.c = c;
        }

        public int getK() {
            return k;
        }

        public void setK(int k) {
            this.k = k;
        }
    }
}
