package com.idea.producer.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileTool {
    private static final Logger logger = LogManager.getLogger(FileTool.class);

    public List read(String filePath) {
        List<String> list = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            list = stream.collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(e);
        }

        return list;
    }

}
