package com.ejemplobatch.ejercicio1.step;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class StringProcessor implements ItemProcessor<String, String> {

    @Override
    public String process(String item) {
        return item.toUpperCase();
    }
}