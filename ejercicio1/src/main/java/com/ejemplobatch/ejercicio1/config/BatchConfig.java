package com.ejemplobatch.ejercicio1.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.ejemplobatch.ejercicio1.step.IntegerProcessor;
import com.ejemplobatch.ejercicio1.step.IntegerReader;
import com.ejemplobatch.ejercicio1.step.IntegerWriter;
import com.ejemplobatch.ejercicio1.step.StringProcessor;
import com.ejemplobatch.ejercicio1.step.StringReader;
import com.ejemplobatch.ejercicio1.step.StringWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private StringReader stringReader;

    @Autowired
    private StringProcessor stringProcessor;

    @Autowired
    private StringWriter stringWriter;

    @Autowired
    private IntegerReader integerReader;

    @Autowired
    private IntegerProcessor integerProcessor;

    @Autowired
    private IntegerWriter integerWriter;

    // Job para procesar Strings
    @Bean
    public Job processStringsJob() {
        return new JobBuilder("processStringsJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    // Job para procesar Integers
    @Bean
    public Job processIntegersJob() {
        return new JobBuilder("processIntegersJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step2())
                .build();
    }

    // Step para procesar Strings
    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<String, String>chunk(10, transactionManager)
                .reader(stringReader.reader())
                .processor(stringProcessor)
                .writer(stringWriter)
                .build();
    }

    // Step para procesar Integers
    @Bean
    public Step step2() {
        return new StepBuilder("step2", jobRepository)
                .<Integer, Integer>chunk(10, transactionManager)
                .reader(integerReader.reader())
                .processor(integerProcessor)
                .writer(integerWriter)
                .build();
    }
}