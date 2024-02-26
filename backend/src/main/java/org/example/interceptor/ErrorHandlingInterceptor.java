package org.example.interceptor;

import io.grpc.*;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.logging.Logger;

@GrpcGlobalServerInterceptor
public class ErrorHandlingInterceptor implements ServerInterceptor {
    private static final Logger logger = Logger.getLogger(ErrorHandlingInterceptor.class.getName());
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        ServerCall.Listener<ReqT> listener = serverCallHandler.startCall(serverCall, metadata);
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(listener) {
            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (Exception e) {
                    handleException(serverCall, e);
                }
            }
            @Override
            public void onReady() {
                try {
                    super.onReady();
                } catch (Exception e) {
                    handleException(serverCall, e);
                }
            }
            private void handleException(ServerCall<ReqT, RespT> serverCall, Exception e) {
                if (e instanceof StatusRuntimeException) {
                    logger.warning("Exception in gRPC call : " +  e.getMessage());
                    serverCall.close(((StatusRuntimeException) e).getStatus(), new Metadata());
                } else {
                    StatusRuntimeException statusRuntimeException = new StatusRuntimeException(Status.ABORTED);
                    serverCall.close(statusRuntimeException.getStatus(), new Metadata());
                }
            }
        };
    }
}
