package org.unibl.etf.pj2.projektni.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileVisit implements FileVisitor<Path> {
   private final PathMatcher match;
   List<FileRead> fileReadList;
   private static final String folder = "rezultati";

   public FileVisit(String pattern) {
       match = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
       fileReadList = new ArrayList<>();
   }
   public int getNumberOfFiles() {
       return new File(System.getProperty("user.dir") + File.separator + folder).list().length;
   }
   void find(Path file) {
       Path name  = file.getFileName();
       if(name != null && match.matches(name)) {
           fileReadList.add(new FileRead(String.valueOf(file.getFileName()), file.toFile().getAbsolutePath()));
       }
   }
   public List<FileRead> getFileReadList() {
       return this.fileReadList;
   }
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        find(dir);
        return FileVisitResult.CONTINUE;
    }
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        find(file);
        return FileVisitResult.CONTINUE;
    }
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
       System.err.println(exc);
       return FileVisitResult.CONTINUE;
    }
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
       find(dir);
       return FileVisitResult.CONTINUE;
    }
}
