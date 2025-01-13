package org.example.spring.clients;


import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "SecuredClient", url = "http://localhost:9999/example-application", configuration = AuthorizedClient.FeignConfig.class)
public interface AuthorizedClient {
    @GetMapping(value = "/secured/test")
    String securedTest();

    @GetMapping(value = "/unsecured/test")
    String unsecuredTest();

    class FeignConfig {
        @Bean
        public RequestInterceptor basicAuthRequestInterceptor() {
            return new BasicAuthRequestInterceptor("user", "password");
        }
    }
}
