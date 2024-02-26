package org.example;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.14.0)",
    comments = "Source: employer.proto")
public final class EmployerServiceGrpc {

  private EmployerServiceGrpc() {}

  public static final String SERVICE_NAME = "org.example.EmployerService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.example.EmployerRequest,
      org.example.Count> getGetEmployersSalariesCountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getEmployersSalariesCount",
      requestType = org.example.EmployerRequest.class,
      responseType = org.example.Count.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.EmployerRequest,
      org.example.Count> getGetEmployersSalariesCountMethod() {
    io.grpc.MethodDescriptor<org.example.EmployerRequest, org.example.Count> getGetEmployersSalariesCountMethod;
    if ((getGetEmployersSalariesCountMethod = EmployerServiceGrpc.getGetEmployersSalariesCountMethod) == null) {
      synchronized (EmployerServiceGrpc.class) {
        if ((getGetEmployersSalariesCountMethod = EmployerServiceGrpc.getGetEmployersSalariesCountMethod) == null) {
          EmployerServiceGrpc.getGetEmployersSalariesCountMethod = getGetEmployersSalariesCountMethod = 
              io.grpc.MethodDescriptor.<org.example.EmployerRequest, org.example.Count>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "org.example.EmployerService", "getEmployersSalariesCount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.EmployerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.Count.getDefaultInstance()))
                  .setSchemaDescriptor(new EmployerServiceMethodDescriptorSupplier("getEmployersSalariesCount"))
                  .build();
          }
        }
     }
     return getGetEmployersSalariesCountMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.EmployerRequest,
      org.example.Employer> getGetEmployerByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getEmployerById",
      requestType = org.example.EmployerRequest.class,
      responseType = org.example.Employer.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.EmployerRequest,
      org.example.Employer> getGetEmployerByIdMethod() {
    io.grpc.MethodDescriptor<org.example.EmployerRequest, org.example.Employer> getGetEmployerByIdMethod;
    if ((getGetEmployerByIdMethod = EmployerServiceGrpc.getGetEmployerByIdMethod) == null) {
      synchronized (EmployerServiceGrpc.class) {
        if ((getGetEmployerByIdMethod = EmployerServiceGrpc.getGetEmployerByIdMethod) == null) {
          EmployerServiceGrpc.getGetEmployerByIdMethod = getGetEmployerByIdMethod = 
              io.grpc.MethodDescriptor.<org.example.EmployerRequest, org.example.Employer>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "org.example.EmployerService", "getEmployerById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.EmployerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.Employer.getDefaultInstance()))
                  .setSchemaDescriptor(new EmployerServiceMethodDescriptorSupplier("getEmployerById"))
                  .build();
          }
        }
     }
     return getGetEmployerByIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.EmployerRequest,
      org.example.Salary> getGetSalaryByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getSalaryById",
      requestType = org.example.EmployerRequest.class,
      responseType = org.example.Salary.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<org.example.EmployerRequest,
      org.example.Salary> getGetSalaryByIdMethod() {
    io.grpc.MethodDescriptor<org.example.EmployerRequest, org.example.Salary> getGetSalaryByIdMethod;
    if ((getGetSalaryByIdMethod = EmployerServiceGrpc.getGetSalaryByIdMethod) == null) {
      synchronized (EmployerServiceGrpc.class) {
        if ((getGetSalaryByIdMethod = EmployerServiceGrpc.getGetSalaryByIdMethod) == null) {
          EmployerServiceGrpc.getGetSalaryByIdMethod = getGetSalaryByIdMethod = 
              io.grpc.MethodDescriptor.<org.example.EmployerRequest, org.example.Salary>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "org.example.EmployerService", "getSalaryById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.EmployerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.Salary.getDefaultInstance()))
                  .setSchemaDescriptor(new EmployerServiceMethodDescriptorSupplier("getSalaryById"))
                  .build();
          }
        }
     }
     return getGetSalaryByIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.EmployersPagination,
      org.example.EmployerNoSalaries> getGetEmployersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getEmployers",
      requestType = org.example.EmployersPagination.class,
      responseType = org.example.EmployerNoSalaries.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<org.example.EmployersPagination,
      org.example.EmployerNoSalaries> getGetEmployersMethod() {
    io.grpc.MethodDescriptor<org.example.EmployersPagination, org.example.EmployerNoSalaries> getGetEmployersMethod;
    if ((getGetEmployersMethod = EmployerServiceGrpc.getGetEmployersMethod) == null) {
      synchronized (EmployerServiceGrpc.class) {
        if ((getGetEmployersMethod = EmployerServiceGrpc.getGetEmployersMethod) == null) {
          EmployerServiceGrpc.getGetEmployersMethod = getGetEmployersMethod = 
              io.grpc.MethodDescriptor.<org.example.EmployersPagination, org.example.EmployerNoSalaries>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "org.example.EmployerService", "getEmployers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.EmployersPagination.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.EmployerNoSalaries.getDefaultInstance()))
                  .setSchemaDescriptor(new EmployerServiceMethodDescriptorSupplier("getEmployers"))
                  .build();
          }
        }
     }
     return getGetEmployersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.EmployerSearchCriteria,
      org.example.Count> getGetEmployersByCriteriaCountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getEmployersByCriteriaCount",
      requestType = org.example.EmployerSearchCriteria.class,
      responseType = org.example.Count.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.EmployerSearchCriteria,
      org.example.Count> getGetEmployersByCriteriaCountMethod() {
    io.grpc.MethodDescriptor<org.example.EmployerSearchCriteria, org.example.Count> getGetEmployersByCriteriaCountMethod;
    if ((getGetEmployersByCriteriaCountMethod = EmployerServiceGrpc.getGetEmployersByCriteriaCountMethod) == null) {
      synchronized (EmployerServiceGrpc.class) {
        if ((getGetEmployersByCriteriaCountMethod = EmployerServiceGrpc.getGetEmployersByCriteriaCountMethod) == null) {
          EmployerServiceGrpc.getGetEmployersByCriteriaCountMethod = getGetEmployersByCriteriaCountMethod = 
              io.grpc.MethodDescriptor.<org.example.EmployerSearchCriteria, org.example.Count>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "org.example.EmployerService", "getEmployersByCriteriaCount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.EmployerSearchCriteria.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.Count.getDefaultInstance()))
                  .setSchemaDescriptor(new EmployerServiceMethodDescriptorSupplier("getEmployersByCriteriaCount"))
                  .build();
          }
        }
     }
     return getGetEmployersByCriteriaCountMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.EmployerSearchCriteria,
      org.example.EmployerNoSalaries> getGetEmployersByCriteriaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getEmployersByCriteria",
      requestType = org.example.EmployerSearchCriteria.class,
      responseType = org.example.EmployerNoSalaries.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<org.example.EmployerSearchCriteria,
      org.example.EmployerNoSalaries> getGetEmployersByCriteriaMethod() {
    io.grpc.MethodDescriptor<org.example.EmployerSearchCriteria, org.example.EmployerNoSalaries> getGetEmployersByCriteriaMethod;
    if ((getGetEmployersByCriteriaMethod = EmployerServiceGrpc.getGetEmployersByCriteriaMethod) == null) {
      synchronized (EmployerServiceGrpc.class) {
        if ((getGetEmployersByCriteriaMethod = EmployerServiceGrpc.getGetEmployersByCriteriaMethod) == null) {
          EmployerServiceGrpc.getGetEmployersByCriteriaMethod = getGetEmployersByCriteriaMethod = 
              io.grpc.MethodDescriptor.<org.example.EmployerSearchCriteria, org.example.EmployerNoSalaries>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "org.example.EmployerService", "getEmployersByCriteria"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.EmployerSearchCriteria.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.EmployerNoSalaries.getDefaultInstance()))
                  .setSchemaDescriptor(new EmployerServiceMethodDescriptorSupplier("getEmployersByCriteria"))
                  .build();
          }
        }
     }
     return getGetEmployersByCriteriaMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.LastEmployerNumber,
      org.example.LastEmployerNumber> getGetLastEmployerNumberMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getLastEmployerNumber",
      requestType = org.example.LastEmployerNumber.class,
      responseType = org.example.LastEmployerNumber.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.LastEmployerNumber,
      org.example.LastEmployerNumber> getGetLastEmployerNumberMethod() {
    io.grpc.MethodDescriptor<org.example.LastEmployerNumber, org.example.LastEmployerNumber> getGetLastEmployerNumberMethod;
    if ((getGetLastEmployerNumberMethod = EmployerServiceGrpc.getGetLastEmployerNumberMethod) == null) {
      synchronized (EmployerServiceGrpc.class) {
        if ((getGetLastEmployerNumberMethod = EmployerServiceGrpc.getGetLastEmployerNumberMethod) == null) {
          EmployerServiceGrpc.getGetLastEmployerNumberMethod = getGetLastEmployerNumberMethod = 
              io.grpc.MethodDescriptor.<org.example.LastEmployerNumber, org.example.LastEmployerNumber>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "org.example.EmployerService", "getLastEmployerNumber"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.LastEmployerNumber.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.LastEmployerNumber.getDefaultInstance()))
                  .setSchemaDescriptor(new EmployerServiceMethodDescriptorSupplier("getLastEmployerNumber"))
                  .build();
          }
        }
     }
     return getGetLastEmployerNumberMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.EmployerRequest,
      org.example.DeleteEmployerResponse> getDeleteEmployerByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "deleteEmployerById",
      requestType = org.example.EmployerRequest.class,
      responseType = org.example.DeleteEmployerResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.EmployerRequest,
      org.example.DeleteEmployerResponse> getDeleteEmployerByIdMethod() {
    io.grpc.MethodDescriptor<org.example.EmployerRequest, org.example.DeleteEmployerResponse> getDeleteEmployerByIdMethod;
    if ((getDeleteEmployerByIdMethod = EmployerServiceGrpc.getDeleteEmployerByIdMethod) == null) {
      synchronized (EmployerServiceGrpc.class) {
        if ((getDeleteEmployerByIdMethod = EmployerServiceGrpc.getDeleteEmployerByIdMethod) == null) {
          EmployerServiceGrpc.getDeleteEmployerByIdMethod = getDeleteEmployerByIdMethod = 
              io.grpc.MethodDescriptor.<org.example.EmployerRequest, org.example.DeleteEmployerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "org.example.EmployerService", "deleteEmployerById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.EmployerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.DeleteEmployerResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new EmployerServiceMethodDescriptorSupplier("deleteEmployerById"))
                  .build();
          }
        }
     }
     return getDeleteEmployerByIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.Employer,
      org.example.Employer> getAddEmployerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "addEmployer",
      requestType = org.example.Employer.class,
      responseType = org.example.Employer.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.Employer,
      org.example.Employer> getAddEmployerMethod() {
    io.grpc.MethodDescriptor<org.example.Employer, org.example.Employer> getAddEmployerMethod;
    if ((getAddEmployerMethod = EmployerServiceGrpc.getAddEmployerMethod) == null) {
      synchronized (EmployerServiceGrpc.class) {
        if ((getAddEmployerMethod = EmployerServiceGrpc.getAddEmployerMethod) == null) {
          EmployerServiceGrpc.getAddEmployerMethod = getAddEmployerMethod = 
              io.grpc.MethodDescriptor.<org.example.Employer, org.example.Employer>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "org.example.EmployerService", "addEmployer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.Employer.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.Employer.getDefaultInstance()))
                  .setSchemaDescriptor(new EmployerServiceMethodDescriptorSupplier("addEmployer"))
                  .build();
          }
        }
     }
     return getAddEmployerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.Employer,
      org.example.Employer> getUpdateEmployerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "updateEmployer",
      requestType = org.example.Employer.class,
      responseType = org.example.Employer.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.Employer,
      org.example.Employer> getUpdateEmployerMethod() {
    io.grpc.MethodDescriptor<org.example.Employer, org.example.Employer> getUpdateEmployerMethod;
    if ((getUpdateEmployerMethod = EmployerServiceGrpc.getUpdateEmployerMethod) == null) {
      synchronized (EmployerServiceGrpc.class) {
        if ((getUpdateEmployerMethod = EmployerServiceGrpc.getUpdateEmployerMethod) == null) {
          EmployerServiceGrpc.getUpdateEmployerMethod = getUpdateEmployerMethod = 
              io.grpc.MethodDescriptor.<org.example.Employer, org.example.Employer>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "org.example.EmployerService", "updateEmployer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.Employer.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.Employer.getDefaultInstance()))
                  .setSchemaDescriptor(new EmployerServiceMethodDescriptorSupplier("updateEmployer"))
                  .build();
          }
        }
     }
     return getUpdateEmployerMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EmployerServiceStub newStub(io.grpc.Channel channel) {
    return new EmployerServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EmployerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new EmployerServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EmployerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new EmployerServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class EmployerServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getEmployersSalariesCount(org.example.EmployerRequest request,
        io.grpc.stub.StreamObserver<org.example.Count> responseObserver) {
      asyncUnimplementedUnaryCall(getGetEmployersSalariesCountMethod(), responseObserver);
    }

    /**
     */
    public void getEmployerById(org.example.EmployerRequest request,
        io.grpc.stub.StreamObserver<org.example.Employer> responseObserver) {
      asyncUnimplementedUnaryCall(getGetEmployerByIdMethod(), responseObserver);
    }

    /**
     */
    public void getSalaryById(org.example.EmployerRequest request,
        io.grpc.stub.StreamObserver<org.example.Salary> responseObserver) {
      asyncUnimplementedUnaryCall(getGetSalaryByIdMethod(), responseObserver);
    }

    /**
     */
    public void getEmployers(org.example.EmployersPagination request,
        io.grpc.stub.StreamObserver<org.example.EmployerNoSalaries> responseObserver) {
      asyncUnimplementedUnaryCall(getGetEmployersMethod(), responseObserver);
    }

    /**
     */
    public void getEmployersByCriteriaCount(org.example.EmployerSearchCriteria request,
        io.grpc.stub.StreamObserver<org.example.Count> responseObserver) {
      asyncUnimplementedUnaryCall(getGetEmployersByCriteriaCountMethod(), responseObserver);
    }

    /**
     */
    public void getEmployersByCriteria(org.example.EmployerSearchCriteria request,
        io.grpc.stub.StreamObserver<org.example.EmployerNoSalaries> responseObserver) {
      asyncUnimplementedUnaryCall(getGetEmployersByCriteriaMethod(), responseObserver);
    }

    /**
     */
    public void getLastEmployerNumber(org.example.LastEmployerNumber request,
        io.grpc.stub.StreamObserver<org.example.LastEmployerNumber> responseObserver) {
      asyncUnimplementedUnaryCall(getGetLastEmployerNumberMethod(), responseObserver);
    }

    /**
     */
    public void deleteEmployerById(org.example.EmployerRequest request,
        io.grpc.stub.StreamObserver<org.example.DeleteEmployerResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteEmployerByIdMethod(), responseObserver);
    }

    /**
     */
    public void addEmployer(org.example.Employer request,
        io.grpc.stub.StreamObserver<org.example.Employer> responseObserver) {
      asyncUnimplementedUnaryCall(getAddEmployerMethod(), responseObserver);
    }

    /**
     */
    public void updateEmployer(org.example.Employer request,
        io.grpc.stub.StreamObserver<org.example.Employer> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateEmployerMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetEmployersSalariesCountMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.example.EmployerRequest,
                org.example.Count>(
                  this, METHODID_GET_EMPLOYERS_SALARIES_COUNT)))
          .addMethod(
            getGetEmployerByIdMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.example.EmployerRequest,
                org.example.Employer>(
                  this, METHODID_GET_EMPLOYER_BY_ID)))
          .addMethod(
            getGetSalaryByIdMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                org.example.EmployerRequest,
                org.example.Salary>(
                  this, METHODID_GET_SALARY_BY_ID)))
          .addMethod(
            getGetEmployersMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                org.example.EmployersPagination,
                org.example.EmployerNoSalaries>(
                  this, METHODID_GET_EMPLOYERS)))
          .addMethod(
            getGetEmployersByCriteriaCountMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.example.EmployerSearchCriteria,
                org.example.Count>(
                  this, METHODID_GET_EMPLOYERS_BY_CRITERIA_COUNT)))
          .addMethod(
            getGetEmployersByCriteriaMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                org.example.EmployerSearchCriteria,
                org.example.EmployerNoSalaries>(
                  this, METHODID_GET_EMPLOYERS_BY_CRITERIA)))
          .addMethod(
            getGetLastEmployerNumberMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.example.LastEmployerNumber,
                org.example.LastEmployerNumber>(
                  this, METHODID_GET_LAST_EMPLOYER_NUMBER)))
          .addMethod(
            getDeleteEmployerByIdMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.example.EmployerRequest,
                org.example.DeleteEmployerResponse>(
                  this, METHODID_DELETE_EMPLOYER_BY_ID)))
          .addMethod(
            getAddEmployerMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.example.Employer,
                org.example.Employer>(
                  this, METHODID_ADD_EMPLOYER)))
          .addMethod(
            getUpdateEmployerMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.example.Employer,
                org.example.Employer>(
                  this, METHODID_UPDATE_EMPLOYER)))
          .build();
    }
  }

  /**
   */
  public static final class EmployerServiceStub extends io.grpc.stub.AbstractStub<EmployerServiceStub> {
    private EmployerServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EmployerServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EmployerServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EmployerServiceStub(channel, callOptions);
    }

    /**
     */
    public void getEmployersSalariesCount(org.example.EmployerRequest request,
        io.grpc.stub.StreamObserver<org.example.Count> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetEmployersSalariesCountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getEmployerById(org.example.EmployerRequest request,
        io.grpc.stub.StreamObserver<org.example.Employer> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetEmployerByIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getSalaryById(org.example.EmployerRequest request,
        io.grpc.stub.StreamObserver<org.example.Salary> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetSalaryByIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getEmployers(org.example.EmployersPagination request,
        io.grpc.stub.StreamObserver<org.example.EmployerNoSalaries> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetEmployersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getEmployersByCriteriaCount(org.example.EmployerSearchCriteria request,
        io.grpc.stub.StreamObserver<org.example.Count> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetEmployersByCriteriaCountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getEmployersByCriteria(org.example.EmployerSearchCriteria request,
        io.grpc.stub.StreamObserver<org.example.EmployerNoSalaries> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetEmployersByCriteriaMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getLastEmployerNumber(org.example.LastEmployerNumber request,
        io.grpc.stub.StreamObserver<org.example.LastEmployerNumber> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetLastEmployerNumberMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteEmployerById(org.example.EmployerRequest request,
        io.grpc.stub.StreamObserver<org.example.DeleteEmployerResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteEmployerByIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addEmployer(org.example.Employer request,
        io.grpc.stub.StreamObserver<org.example.Employer> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAddEmployerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateEmployer(org.example.Employer request,
        io.grpc.stub.StreamObserver<org.example.Employer> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateEmployerMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class EmployerServiceBlockingStub extends io.grpc.stub.AbstractStub<EmployerServiceBlockingStub> {
    private EmployerServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EmployerServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EmployerServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EmployerServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public org.example.Count getEmployersSalariesCount(org.example.EmployerRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetEmployersSalariesCountMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.example.Employer getEmployerById(org.example.EmployerRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetEmployerByIdMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<org.example.Salary> getSalaryById(
        org.example.EmployerRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getGetSalaryByIdMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<org.example.EmployerNoSalaries> getEmployers(
        org.example.EmployersPagination request) {
      return blockingServerStreamingCall(
          getChannel(), getGetEmployersMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.example.Count getEmployersByCriteriaCount(org.example.EmployerSearchCriteria request) {
      return blockingUnaryCall(
          getChannel(), getGetEmployersByCriteriaCountMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<org.example.EmployerNoSalaries> getEmployersByCriteria(
        org.example.EmployerSearchCriteria request) {
      return blockingServerStreamingCall(
          getChannel(), getGetEmployersByCriteriaMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.example.LastEmployerNumber getLastEmployerNumber(org.example.LastEmployerNumber request) {
      return blockingUnaryCall(
          getChannel(), getGetLastEmployerNumberMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.example.DeleteEmployerResponse deleteEmployerById(org.example.EmployerRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteEmployerByIdMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.example.Employer addEmployer(org.example.Employer request) {
      return blockingUnaryCall(
          getChannel(), getAddEmployerMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.example.Employer updateEmployer(org.example.Employer request) {
      return blockingUnaryCall(
          getChannel(), getUpdateEmployerMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class EmployerServiceFutureStub extends io.grpc.stub.AbstractStub<EmployerServiceFutureStub> {
    private EmployerServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EmployerServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EmployerServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EmployerServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.Count> getEmployersSalariesCount(
        org.example.EmployerRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetEmployersSalariesCountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.Employer> getEmployerById(
        org.example.EmployerRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetEmployerByIdMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.Count> getEmployersByCriteriaCount(
        org.example.EmployerSearchCriteria request) {
      return futureUnaryCall(
          getChannel().newCall(getGetEmployersByCriteriaCountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.LastEmployerNumber> getLastEmployerNumber(
        org.example.LastEmployerNumber request) {
      return futureUnaryCall(
          getChannel().newCall(getGetLastEmployerNumberMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.DeleteEmployerResponse> deleteEmployerById(
        org.example.EmployerRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteEmployerByIdMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.Employer> addEmployer(
        org.example.Employer request) {
      return futureUnaryCall(
          getChannel().newCall(getAddEmployerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.Employer> updateEmployer(
        org.example.Employer request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateEmployerMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_EMPLOYERS_SALARIES_COUNT = 0;
  private static final int METHODID_GET_EMPLOYER_BY_ID = 1;
  private static final int METHODID_GET_SALARY_BY_ID = 2;
  private static final int METHODID_GET_EMPLOYERS = 3;
  private static final int METHODID_GET_EMPLOYERS_BY_CRITERIA_COUNT = 4;
  private static final int METHODID_GET_EMPLOYERS_BY_CRITERIA = 5;
  private static final int METHODID_GET_LAST_EMPLOYER_NUMBER = 6;
  private static final int METHODID_DELETE_EMPLOYER_BY_ID = 7;
  private static final int METHODID_ADD_EMPLOYER = 8;
  private static final int METHODID_UPDATE_EMPLOYER = 9;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final EmployerServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(EmployerServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_EMPLOYERS_SALARIES_COUNT:
          serviceImpl.getEmployersSalariesCount((org.example.EmployerRequest) request,
              (io.grpc.stub.StreamObserver<org.example.Count>) responseObserver);
          break;
        case METHODID_GET_EMPLOYER_BY_ID:
          serviceImpl.getEmployerById((org.example.EmployerRequest) request,
              (io.grpc.stub.StreamObserver<org.example.Employer>) responseObserver);
          break;
        case METHODID_GET_SALARY_BY_ID:
          serviceImpl.getSalaryById((org.example.EmployerRequest) request,
              (io.grpc.stub.StreamObserver<org.example.Salary>) responseObserver);
          break;
        case METHODID_GET_EMPLOYERS:
          serviceImpl.getEmployers((org.example.EmployersPagination) request,
              (io.grpc.stub.StreamObserver<org.example.EmployerNoSalaries>) responseObserver);
          break;
        case METHODID_GET_EMPLOYERS_BY_CRITERIA_COUNT:
          serviceImpl.getEmployersByCriteriaCount((org.example.EmployerSearchCriteria) request,
              (io.grpc.stub.StreamObserver<org.example.Count>) responseObserver);
          break;
        case METHODID_GET_EMPLOYERS_BY_CRITERIA:
          serviceImpl.getEmployersByCriteria((org.example.EmployerSearchCriteria) request,
              (io.grpc.stub.StreamObserver<org.example.EmployerNoSalaries>) responseObserver);
          break;
        case METHODID_GET_LAST_EMPLOYER_NUMBER:
          serviceImpl.getLastEmployerNumber((org.example.LastEmployerNumber) request,
              (io.grpc.stub.StreamObserver<org.example.LastEmployerNumber>) responseObserver);
          break;
        case METHODID_DELETE_EMPLOYER_BY_ID:
          serviceImpl.deleteEmployerById((org.example.EmployerRequest) request,
              (io.grpc.stub.StreamObserver<org.example.DeleteEmployerResponse>) responseObserver);
          break;
        case METHODID_ADD_EMPLOYER:
          serviceImpl.addEmployer((org.example.Employer) request,
              (io.grpc.stub.StreamObserver<org.example.Employer>) responseObserver);
          break;
        case METHODID_UPDATE_EMPLOYER:
          serviceImpl.updateEmployer((org.example.Employer) request,
              (io.grpc.stub.StreamObserver<org.example.Employer>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class EmployerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EmployerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.example.EmployerOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("EmployerService");
    }
  }

  private static final class EmployerServiceFileDescriptorSupplier
      extends EmployerServiceBaseDescriptorSupplier {
    EmployerServiceFileDescriptorSupplier() {}
  }

  private static final class EmployerServiceMethodDescriptorSupplier
      extends EmployerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    EmployerServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (EmployerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EmployerServiceFileDescriptorSupplier())
              .addMethod(getGetEmployersSalariesCountMethod())
              .addMethod(getGetEmployerByIdMethod())
              .addMethod(getGetSalaryByIdMethod())
              .addMethod(getGetEmployersMethod())
              .addMethod(getGetEmployersByCriteriaCountMethod())
              .addMethod(getGetEmployersByCriteriaMethod())
              .addMethod(getGetLastEmployerNumberMethod())
              .addMethod(getDeleteEmployerByIdMethod())
              .addMethod(getAddEmployerMethod())
              .addMethod(getUpdateEmployerMethod())
              .build();
        }
      }
    }
    return result;
  }
}
