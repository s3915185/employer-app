package org.example.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.EmployerServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GrpcClient {
    @Bean
    public ManagedChannel channel() {
        return ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
    }

    @Bean
    public EmployerServiceGrpc.EmployerServiceBlockingStub getEmployerServiceBlockingStub() {
        return EmployerServiceGrpc.newBlockingStub(channel());
    }
}
