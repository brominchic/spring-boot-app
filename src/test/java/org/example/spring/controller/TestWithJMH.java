package org.example.spring.controller;

import jakarta.transaction.Transactional;
import org.example.Application;
import org.example.spring.model.entity.CurrencyEntity;
import org.example.spring.model.entity.OperationEntity;
import org.example.spring.repositories.CurrencyRepository;
import org.example.spring.repositories.OperationRepository;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@State(Scope.Benchmark)
public class TestWithJMH {
    static CurrencyRepository currencyRepository;
    static OperationRepository operationRepository;
    @LocalServerPort
    private final Integer port = 9999;

    @Transactional
    @Test
    public void
    launchBenchmark() throws Exception {
        Options jmhRunnerOptions = new OptionsBuilder()
                .include("\\." + this.getClass().getSimpleName() + "\\.")
                .warmupIterations(3)
                .measurementIterations(3)
                .forks(0)
                .threads(1)
                .shouldDoGC(true)
                .shouldFailOnError(true)
                .resultFormat(ResultFormatType.JSON)
                .shouldFailOnError(true)
                .jvmArgs("-server")
                .build();
        new Runner(jmhRunnerOptions).run();
        List<OperationEntity> result = new ArrayList<>();
        operationRepository.findAll().forEach(result::add);
        long time = 60;
        System.out.println("time = " + time + ", amount = " + result.size());
    }

    @Benchmark
    public void benchmark(BenchmarkState state, Blackhole bh) {
        String uploadUrl = "http://localhost:" + port + "/example-application/unsecured/operation/create";
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 0; i < 1; i++) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String json = "{" +
                    "\"sum\":1," +
                    "\"currency\":\"rub\"" +
                    "}";
            HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
            ResponseEntity<String> uploadResponse = restTemplate.postForEntity(uploadUrl, requestEntity, String.class);
        }

    }

    @Transactional
    @State(Scope.Thread)
    public static class BenchmarkState {
        List<Integer> list;

        @Setup(Level.Trial)
        public void
        initialize() {
            String[] args = {};
            ApplicationContext context = SpringApplication.run(Application.class, args);
            currencyRepository = (CurrencyRepository) context.getBean("currencyRepository");
            operationRepository = (OperationRepository) context.getBean("operationRepository");
            currencyRepository.save(CurrencyEntity.
                    builder().
                    id(1L).
                    name("rub").
                    code("1l").
                    build());
            currencyRepository.save(CurrencyEntity.
                    builder().
                    id(2L).
                    name("euro").
                    code("2l").
                    build());
        }
    }
}