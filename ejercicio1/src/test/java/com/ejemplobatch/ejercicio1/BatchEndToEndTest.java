package com.ejemplobatch.ejercicio1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBatchTest
@SpringBootTest
@ActiveProfiles("test")
public class BatchEndToEndTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private Job processStringsJob; // Inyecta el Job para procesar Strings

    @Autowired
    private Job processIntegersJob; // Inyecta el Job para procesar Integers

    @BeforeEach
    public void setUp() {
        // Verifica que los Jobs no sean nulos
        assertNotNull(processStringsJob, "El Job processStringsJob no debe ser nulo");
        assertNotNull(processIntegersJob, "El Job processIntegersJob no debe ser nulo");

        // Configura el Job en JobLauncherTestUtils
        jobLauncherTestUtils.setJob(processStringsJob);
    }

    @Test
    public void testProcessStringsJob() throws Exception {
        // Ejecuta el job completo
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // Verifica que el job haya terminado correctamente
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        // Aquí puedes agregar más verificaciones, como leer el archivo de salida
        // y verificar que los nombres estén en mayúsculas.
    }

    @Test
    public void testProcessIntegersJob() throws Exception {
        // Cambia el Job a processIntegersJob para esta prueba
        jobLauncherTestUtils.setJob(processIntegersJob);

        // Ejecuta el job completo
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(new JobParametersBuilder()
                .addString("jobName", "processIntegersJob")
                .toJobParameters());

        // Verifica que el job haya terminado correctamente
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        // Aquí puedes agregar más verificaciones, como leer el archivo de salida
        // y verificar que los números se hayan incrementado en 10.
    }


}