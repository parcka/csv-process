package com.parcka.csvprocess.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.stream.Stream;

@Slf4j
@Component
public class CSVReader {

    private static final String COMMA_DELIMITER = ",";

    public void processFile() throws FileNotFoundException {
        log.info("Reading file....");

        //Data que se va a buscar
        String dataToFind = "data/data-to-find.txt";
        Path pathDataToFind = Paths.get(getFileUriFromResources(dataToFind));

        //Data donde buscar
        String dataWhereFind = "data/copy-import.csv";
        Path pathDataWhereFind = Paths.get(getFileUriFromResources(dataWhereFind));

        //Archivo resultado
        String result = "data/result.csv";
        Path pathResult = Paths.get(getFileUriFromResources(result));

        try (Stream<String> stream = Files.lines(pathDataWhereFind)) {

            stream.forEach(

                    (element) -> {
                        HashMap<String, String> newMap = new HashMap<>();
                        String id = element.split(COMMA_DELIMITER)[0];
                        newMap.put(id, element);

                        try (Stream<String> innerStream = Files.lines(pathDataToFind)) {

                            innerStream.forEach(

                                    (line) -> {

                                        String newLine = "";
                                        String[] lineElement = element.split(COMMA_DELIMITER);

                                        if (lineElement[0].equals(line)) {
                                            log.info("ES IGUAL {} == {}", lineElement[0], element.split(COMMA_DELIMITER)[0]);

                                            newLine = element + "x";
                                            newMap.put(line, newLine);
                                        }

                                    }
                            );


                        } catch (Exception e) {
                            log.error("Error {}", e);
                        }

                        newMap.put(id,

                                newMap.get(id)
                                        + "\n");



                        try {
                            Files.write(pathResult, newMap.get(id).getBytes(), StandardOpenOption.APPEND);
                        } catch (IOException e) {
                            log.error("Error {}", e);
                        }

                        log.info(newMap.toString());

                    });

        } catch (IOException e) {
            log.error("ERROR {}", e);
        }

    }

    private URI getFileUriFromResources(String relativeResourcesPath) {

        URI uri = null;
        URL url = null;


        url = getClass().getClassLoader().getResource(relativeResourcesPath);

        try {


            uri = url.toURI().normalize();


        } catch (Exception e) {
            log.error("ERROR {}", e);
        }

        return uri;
    }

    private void createIfnoExist(String relativeResourcesPath) {
        try {
            Files.createFile(Paths.get(relativeResourcesPath));
        } catch (IOException e) {
            log.error("ERROR {}", e);
        }
    }
}
