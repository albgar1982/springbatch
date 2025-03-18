package com.ejemplobatch.ejercicio1.step;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class IntegerWriter implements ItemWriter<Integer> {

    @Override
    public void write(Chunk<? extends Integer> chunk) throws Exception {
        // Crear el directorio si no existe
        File directory = new File("output");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Configurar el writer
        FlatFileItemWriter<Integer> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("output/integers_output.txt"));
        writer.setLineAggregator(new PassThroughLineAggregator<>());
        writer.open(new ExecutionContext());

        // Escribir el Chunk
        writer.write(chunk);

        writer.close(); // Cerrar el writer
    }
}