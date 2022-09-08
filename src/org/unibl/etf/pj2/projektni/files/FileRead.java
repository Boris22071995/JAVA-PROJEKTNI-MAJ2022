package org.unibl.etf.pj2.projektni.files;

public class FileRead {
    String nameOfFile;
    String filePath;

    public FileRead(String nameOfFile, String filePath) {
        this.filePath = filePath;
        this.nameOfFile = nameOfFile;
    }

    public String getNameOfFile() {
        return nameOfFile;
    }

    public void setNameOfFile(String nameOfFile) {
        this.nameOfFile = nameOfFile;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
