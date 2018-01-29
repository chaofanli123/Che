package com.victor.che.bean;

import java.util.List;

/**
 * Created by jcc on 2018/1/29.
 */

public class Files {

    private List<FilesBean> files;

    public List<FilesBean> getFiles() {
        return files;
    }

    public void setFiles(List<FilesBean> files) {
        this.files = files;
    }

    public static class FilesBean {
        /**
         * filePath : /aims/userfiles/e0ef8af9cae6416f8bb359714a1b4244\2018\01\.nomedia1517214429300.jpg
         */

        private String filePath;

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }
    }
}
