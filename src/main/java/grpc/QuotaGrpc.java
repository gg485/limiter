package grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.35.0)",
    comments = "Source: api.proto")
public final class QuotaGrpc {

  private QuotaGrpc() {}

  public static final String SERVICE_NAME = "Quota";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<Api.GetCapacityReq,
      Api.GetCapacityResp> getGetCapacityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCapacity",
      requestType = Api.GetCapacityReq.class,
      responseType = Api.GetCapacityResp.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Api.GetCapacityReq,
      Api.GetCapacityResp> getGetCapacityMethod() {
    io.grpc.MethodDescriptor<Api.GetCapacityReq, Api.GetCapacityResp> getGetCapacityMethod;
    if ((getGetCapacityMethod = QuotaGrpc.getGetCapacityMethod) == null) {
      synchronized (QuotaGrpc.class) {
        if ((getGetCapacityMethod = QuotaGrpc.getGetCapacityMethod) == null) {
          QuotaGrpc.getGetCapacityMethod = getGetCapacityMethod =
              io.grpc.MethodDescriptor.<Api.GetCapacityReq, Api.GetCapacityResp>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCapacity"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Api.GetCapacityReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Api.GetCapacityResp.getDefaultInstance()))
              .setSchemaDescriptor(new QuotaMethodDescriptorSupplier("GetCapacity"))
              .build();
        }
      }
    }
    return getGetCapacityMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Api.ReleaseCapacityReq,
      Api.ReleaseCapacityResp> getReleaseCapacityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReleaseCapacity",
      requestType = Api.ReleaseCapacityReq.class,
      responseType = Api.ReleaseCapacityResp.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Api.ReleaseCapacityReq,
      Api.ReleaseCapacityResp> getReleaseCapacityMethod() {
    io.grpc.MethodDescriptor<Api.ReleaseCapacityReq, Api.ReleaseCapacityResp> getReleaseCapacityMethod;
    if ((getReleaseCapacityMethod = QuotaGrpc.getReleaseCapacityMethod) == null) {
      synchronized (QuotaGrpc.class) {
        if ((getReleaseCapacityMethod = QuotaGrpc.getReleaseCapacityMethod) == null) {
          QuotaGrpc.getReleaseCapacityMethod = getReleaseCapacityMethod =
              io.grpc.MethodDescriptor.<Api.ReleaseCapacityReq, Api.ReleaseCapacityResp>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReleaseCapacity"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Api.ReleaseCapacityReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Api.ReleaseCapacityResp.getDefaultInstance()))
              .setSchemaDescriptor(new QuotaMethodDescriptorSupplier("ReleaseCapacity"))
              .build();
        }
      }
    }
    return getReleaseCapacityMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static QuotaStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QuotaStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QuotaStub>() {
        @Override
        public QuotaStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QuotaStub(channel, callOptions);
        }
      };
    return QuotaStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static QuotaBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QuotaBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QuotaBlockingStub>() {
        @Override
        public QuotaBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QuotaBlockingStub(channel, callOptions);
        }
      };
    return QuotaBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static QuotaFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QuotaFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QuotaFutureStub>() {
        @Override
        public QuotaFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QuotaFutureStub(channel, callOptions);
        }
      };
    return QuotaFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class QuotaImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 客户端获取额度
     * </pre>
     */
    public void getCapacity(Api.GetCapacityReq request,
                            io.grpc.stub.StreamObserver<Api.GetCapacityResp> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCapacityMethod(), responseObserver);
    }

    /**
     * <pre>
     * 客户端释放额度
     * </pre>
     */
    public void releaseCapacity(Api.ReleaseCapacityReq request,
                                io.grpc.stub.StreamObserver<Api.ReleaseCapacityResp> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReleaseCapacityMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetCapacityMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Api.GetCapacityReq,
                Api.GetCapacityResp>(
                  this, METHODID_GET_CAPACITY)))
          .addMethod(
            getReleaseCapacityMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Api.ReleaseCapacityReq,
                Api.ReleaseCapacityResp>(
                  this, METHODID_RELEASE_CAPACITY)))
          .build();
    }
  }

  /**
   */
  public static final class QuotaStub extends io.grpc.stub.AbstractAsyncStub<QuotaStub> {
    private QuotaStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected QuotaStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QuotaStub(channel, callOptions);
    }

    /**
     * <pre>
     * 客户端获取额度
     * </pre>
     */
    public void getCapacity(Api.GetCapacityReq request,
                            io.grpc.stub.StreamObserver<Api.GetCapacityResp> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCapacityMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 客户端释放额度
     * </pre>
     */
    public void releaseCapacity(Api.ReleaseCapacityReq request,
                                io.grpc.stub.StreamObserver<Api.ReleaseCapacityResp> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReleaseCapacityMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class QuotaBlockingStub extends io.grpc.stub.AbstractBlockingStub<QuotaBlockingStub> {
    private QuotaBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected QuotaBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QuotaBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 客户端获取额度
     * </pre>
     */
    public Api.GetCapacityResp getCapacity(Api.GetCapacityReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCapacityMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 客户端释放额度
     * </pre>
     */
    public Api.ReleaseCapacityResp releaseCapacity(Api.ReleaseCapacityReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReleaseCapacityMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class QuotaFutureStub extends io.grpc.stub.AbstractFutureStub<QuotaFutureStub> {
    private QuotaFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected QuotaFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QuotaFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 客户端获取额度
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<Api.GetCapacityResp> getCapacity(
        Api.GetCapacityReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCapacityMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 客户端释放额度
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<Api.ReleaseCapacityResp> releaseCapacity(
        Api.ReleaseCapacityReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReleaseCapacityMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_CAPACITY = 0;
  private static final int METHODID_RELEASE_CAPACITY = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final QuotaImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(QuotaImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_CAPACITY:
          serviceImpl.getCapacity((Api.GetCapacityReq) request,
              (io.grpc.stub.StreamObserver<Api.GetCapacityResp>) responseObserver);
          break;
        case METHODID_RELEASE_CAPACITY:
          serviceImpl.releaseCapacity((Api.ReleaseCapacityReq) request,
              (io.grpc.stub.StreamObserver<Api.ReleaseCapacityResp>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class QuotaBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    QuotaBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Api.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Quota");
    }
  }

  private static final class QuotaFileDescriptorSupplier
      extends QuotaBaseDescriptorSupplier {
    QuotaFileDescriptorSupplier() {}
  }

  private static final class QuotaMethodDescriptorSupplier
      extends QuotaBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    QuotaMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (QuotaGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new QuotaFileDescriptorSupplier())
              .addMethod(getGetCapacityMethod())
              .addMethod(getReleaseCapacityMethod())
              .build();
        }
      }
    }
    return result;
  }
}
