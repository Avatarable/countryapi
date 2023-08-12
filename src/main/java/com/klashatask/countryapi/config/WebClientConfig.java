package com.klashatask.countryapi.config;

import com.klashatask.countryapi.enums.ResponseCode;
import com.klashatask.countryapi.exceptions.CustomException;
import com.klashatask.countryapi.remote.ApiClient;
import com.klashatask.countryapi.remote.request.ErrorModel;
import io.netty.handler.logging.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;
import reactor.util.retry.Retry;

import javax.naming.ServiceUnavailableException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Slf4j
@Configuration
public class WebClientConfig {
    @Value("${external.base_url}")
    String baseUrl;

    @Value("${connection.attempts}")
    long maxAttempts;

    @Value("${connection.retry_duration}")
    long retry_duration;

    @Value("${connection.block_timeout}")
    long block_timeout;

    @Bean
    public ApiClient createClient(){
        HttpClient httpClient = HttpClient.create()
                .wiretap("reactor.netty.http.client.HttpClient", LogLevel.INFO, AdvancedByteBufFormat.TEXTUAL, StandardCharsets.UTF_8);

        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))

                .defaultStatusHandler(HttpStatusCode::isError, response ->{
                    if(response.statusCode().is4xxClientError()){
                        log.info("400 internal server error");
                        if(response.statusCode().equals(HttpStatus.NOT_FOUND)){
                            throw new CustomException(ResponseCode.NO_RECORD_FOUND, ResponseCode.NO_RECORD_FOUND.getMessage());
                        }
                        return Mono.error(new HttpClientErrorException(response.statusCode(), response.toString()));
                    }
                    return response.bodyToMono(ErrorModel.class)
                            .flatMap(error -> Mono.error(
                                    new CustomException(ResponseCode.UNABLE_TO_PROCESS_REQUEST, ResponseCode.UNABLE_TO_PROCESS_REQUEST.getMessage())));
                })
                .filter((request, next) -> next.exchange(request)
                        .retryWhen(Retry.fixedDelay(maxAttempts, Duration.ofSeconds(retry_duration))
                                .filter(exception -> exception instanceof UnknownHostException || exception instanceof ServiceUnavailableException)
                        ))
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).blockTimeout(Duration.ofSeconds(block_timeout)).build();
        return factory.createClient(ApiClient.class);
    }

}
