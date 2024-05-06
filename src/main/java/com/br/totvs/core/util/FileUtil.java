package com.br.totvs.core.util;

import com.br.totvs.conta.exceptions.CsvInvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileUtil {

    public static final String TEMP_FILE = "tempfile";
    public static String CSV_FORMAT = ".csv";
    public static String DOT_REGEX = "\\.";
    public static String DOT = ".";

    public static File toFile(MultipartFile multipartFile) throws IOException {
        Path path = Files.createTempFile(TEMP_FILE, getFileExtension(multipartFile));

        Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        File file = path.toFile();
        file.deleteOnExit();

        return file;
    }

    public static void validFormat(File file, String extension) {
        if (!file.getName().contains(extension)) {
            throw new CsvInvalidFormatException();
        }
    }

    public static String getFileExtension(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();

        if (originalFilename != null && !originalFilename.isEmpty()) {
            String[] split = originalFilename.split(DOT_REGEX);

            if (split.length > 0) {
                return DOT.concat(split[split.length - 1]);
            }
        }

        return null;
    }
}
