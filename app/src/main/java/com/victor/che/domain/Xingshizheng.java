package com.victor.che.domain;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/11.
 * 填写的行驶证
 */

public class Xingshizheng implements Serializable{
    private String carnumber;
    private String brandnum;

    private String chejianum;

    private String fadongjinum;

    private String firsttime;

    private String souyouren;

    private File filePath;

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }

    public String getBrandnum() {
        return brandnum;
    }

    public void setBrandnum(String brandnum) {
        this.brandnum = brandnum;
    }

    public String getChejianum() {
        return chejianum;
    }

    public void setChejianum(String chejianum) {
        this.chejianum = chejianum;
    }

    public String getFadongjinum() {
        return fadongjinum;
    }

    public void setFadongjinum(String fadongjinum) {
        this.fadongjinum = fadongjinum;
    }

    public String getFirsttime() {
        return firsttime;
    }

    public void setFirsttime(String firsttime) {
        this.firsttime = firsttime;
    }

    public String getSouyouren() {
        return souyouren;
    }

    public void setSouyouren(String souyouren) {
        this.souyouren = souyouren;
    }

    public File getFilePath() {
        return filePath;
    }

    public void setFilePath(File filePath) {
        this.filePath = filePath;
    }
}
