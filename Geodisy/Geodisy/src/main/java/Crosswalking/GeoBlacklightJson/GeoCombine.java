package Crosswalking.GeoBlacklightJson;

import BaseFiles.GeoLogger;
import Crosswalking.XML.XMLTools.JGit;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static BaseFiles.GeodisyStrings.*;

public class GeoCombine {
    GeoLogger logger;
    public GeoCombine() {
        logger = new GeoLogger(this.getClass());
    }

    public void index(){
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("/bin/bash", "-c", MOVE_METADATA);

        Process p = null;
        try {
            System.out.println("Moving metadata");
            p = processBuilder.start();
            p.waitFor();
            p.destroy();

            processBuilder.command("/bin/bash", "-c", DELETE_DUPLICATE_META_FOLDER);
            p = processBuilder.start();
            p.waitFor();
            p.destroy();
            /*processBuilder.command("/bin/bash", "-c", MOVE_DATA);
            System.out.println("Moving data");
            p = processBuilder.start();
            p.waitFor();
            p.destroy();
            processBuilder.command("/bin/bash", "-c", DELETE_DUPLICATE_DATA_FOLDER);
            p = processBuilder.start();
            p.waitFor();
            p.destroy();*/
            System.out.println("Clearing Solr");
            processBuilder.command("/bin/bash", "-c", CLEAR_SOLR);
            p = processBuilder.start();
            p.waitFor();
            p.destroy();
            System.out.println("Calling Geocombine");
            processBuilder.command(GEOCOMBINE);
            processBuilder.directory(new File("/home/geoblack/GeoCombine"));
            p = processBuilder.start();
            p.waitFor();
            p.destroy();
        } catch (IOException | InterruptedException e) {
            logger.error("Something went wrong calling GeoCombine to index files");
        }
    }

    public void updateOpenGeoMetadata(String fileName, JGit jgit){
        String filePath = jgit.getOpenGeoLocalFilePath(fileName);
        //TODO uncomment once I've got JGit working
        //jgit.addXMLFileToIndex(filePath + fileName + ".json");
    }
}
