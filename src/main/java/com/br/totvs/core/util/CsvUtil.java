package com.br.totvs.core.util;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class CsvUtil<T> {

    public List<T> read(File file, Class<T> clazz) throws FileNotFoundException {
        return new CsvToBeanBuilder(new FileReader(file))
                .withType(clazz)
                .build()
                .parse();
    }
}
