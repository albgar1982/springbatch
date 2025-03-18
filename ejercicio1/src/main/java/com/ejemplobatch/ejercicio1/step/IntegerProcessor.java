package com.ejemplobatch.ejercicio1.step;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class IntegerProcessor implements ItemProcessor<Integer, Integer> {

    @Override
    public Integer process(Integer item) {
        return item + 10;
    }
}