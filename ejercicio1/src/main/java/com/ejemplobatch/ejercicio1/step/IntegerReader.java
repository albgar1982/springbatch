package com.ejemplobatch.ejercicio1.step;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class IntegerReader {

    public ItemReader<Integer> reader() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        return new ListItemReader<>(numbers);
    }
}