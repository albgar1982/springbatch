package com.ejemplobatch.ejercicio1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job processStringsJob;

    @Autowired
    private Job processIntegersJob;

    private final ConfigurableApplicationContext context;

    public Application(ConfigurableApplicationContext context) {
        this.context = context;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Ejecutar los jobs de Spring Batch
        jobLauncher.run(processStringsJob, new JobParameters());
        jobLauncher.run(processIntegersJob, new JobParameters());

        /*
        Spring Batch necesita una base de datos para registrar ciertos datos de la ejecución. Para ello incluimos la H2.

        Si no hacemos nada más, la app correrá y finalizará rápidamente, sin que nos dé tiempo a consultar esa BBDD.
        Para poder acceder a la consola de H2 desde un navegador, necesitamos incluir spring-boot-starter-web, que permite recibir solicitudes HTTP.
        SIN ESTA DEPENDENCIA NO PODREMOS ACCEDER A http://localhost:9090/h2-console

        EL problema es que ese servidor se queda arrancado para siempre a no ser que lo paremos de alguna forma:
         */

        // Verificar si estamos en modo de prueba
        boolean isTestProfileActive = Arrays.stream(context.getEnvironment().getActiveProfiles())
                .anyMatch(profile -> profile.equalsIgnoreCase("test"));

        if (!isTestProfileActive) { //Si no estamos ejecutando el test
            // Crear un CountDownLatch para esperar antes de cerrar la aplicación
            CountDownLatch latch = new CountDownLatch(1);

            // Usar un hilo separado para ejecutar la tarea de cierre después de un tiempo determinado
            new Thread(() -> {
            	final byte SEGUNDOS = 30;
                System.out.println("La aplicación se cerrará en " + SEGUNDOS + " segundos.");
         
                // Contar hacia atrás
                for (int i = SEGUNDOS; i > 0; i -= 10) {
                    try {
                        System.out.println("Cerrando en " + i + " segundos...");
                        Thread.sleep(10_000); // Esperar 10 segundos
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                // Cerrar la aplicación después de la cuenta atrás
                System.out.println("Aplicación cerrada.");
                context.close();
                latch.countDown();  // Disparar el cierre del hilo principal
            }).start();

            // Esperar hasta que el hilo de cierre complete
            latch.await();
        }
    }
}
