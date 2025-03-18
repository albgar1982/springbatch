package com.ejemplobatch.ejercicio1.step;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class StringReader {

    public ItemReader<String> reader() {
        List<String> names = Arrays.asList("Juan", "Ana", "Luis", "Maria");
        return new ListItemReader<>(names);
    }
}