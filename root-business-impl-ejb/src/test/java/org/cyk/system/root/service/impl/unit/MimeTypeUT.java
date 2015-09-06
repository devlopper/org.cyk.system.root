package org.cyk.system.root.service.impl.unit;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import javax.activation.MimetypesFileTypeMap;

import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class MimeTypeUT extends AbstractUnitTest {

    private static final long serialVersionUID = -6691092648665798471L;

    private String[] extensions = {"jpeg","jpg","bmp","gif","doc","doc","xls","ppt","pdf","txt","html","htm","xhtml"};
    
    @Override
    protected void _execute_() {
        super._execute_();
    }

    @Test
    public void jdk7Api() {
        for(String extension : extensions){
            String fileName = "p."+extension;
            try {
                
                String r1,r2,r3;
                Set<String> set = new HashSet<>();
                set.add(r1=Files.probeContentType(Paths.get(fileName)));
                set.add(r2=URLConnection.guessContentTypeFromName(fileName));
                set.add(r3=MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(fileName));
                if(set.size()!=1){
                    System.out.println("File : "+fileName);
                    System.out.println("Files.probeContentType                                      : " +r1);
                    System.out.println("URLConnection.guessContentTypeFromName                      : " +r2);
                    System.out.println("MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType : " +r3);
                }
                
            } catch (IOException e) {
    
            }
        }
    }

}
