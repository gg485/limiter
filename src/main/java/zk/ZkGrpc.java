package zk;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.35.0)",
    comments = "Source: zk.proto")
public final class ZkGrpc {

  private ZkGrpc() {}

  public static final String SERVICE_NAME = "Zk";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ZkOuterClass.GetMasterAddrReq,
      ZkOuterClass.GetMasterAddrResp> getGetMasterAddrMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMasterAddr",
      requestType = ZkOuterClass.GetMasterAddrReq.class,
      responseType = ZkOuterClass.GetMasterAddrResp.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ZkOuterClass.GetMasterAddrReq,
      ZkOuterClass.GetMasterAddrResp> getGetMasterAddrMethod() {
    io.grpc.MethodDescriptor<ZkOuterClass.GetMasterAddrReq, ZkOuterClass.GetMasterAddrResp> getGetMasterAddrMethod;
    if ((getGetMasterAddrMethod = ZkGrpc.getGetMasterAddrMethod) == null) {
      synchronized (ZkGrpc.class) {
        if ((getGetMasterAddrMethod = ZkGrpc.getGetMasterAddrMethod) == null) {
          ZkGrpc.getGetMasterAddrMethod = getGetMasterAddrMethod =
              io.grpc.MethodDescriptor.<ZkOuterClass.GetMasterAddrReq, ZkOuterClass.GetMasterAddrResp>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMasterAddr"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ZkOuterClass.GetMasterAddrReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ZkOuterClass.GetMasterAddrResp.getDefaultInstance()))
              .setSchemaDescriptor(new ZkMethodDescriptorSupplier("GetMasterAddr"))
              .build();
        }
      }
    }
    return getGetMasterAddrMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ZkStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ZkStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ZkStub>() {
        @Override
        public ZkStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ZkStub(channel, callOptions);
        }
      };
    return ZkStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ZkBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ZkBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ZkBlockingStub>() {
        @Override
        public ZkBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ZkBlockingStub(channel, callOptions);
        }
      };
    return ZkBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ZkFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ZkFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ZkFutureStub>() {
        @Override
        public ZkFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ZkFutureStub(channel, callOptions);
        }
      };
    return ZkFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ZkImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 客户端获取资源主节点地址
     * </pre>
     */
    public void getMasterAddr(ZkOuterClass.GetMasterAddrReq request,
                              io.grpc.stub.StreamObserver<ZkOuterClass.GetMasterAddrResp> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMasterAddrMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetMasterAddrMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ZkOuterClass.GetMasterAddrReq,
                ZkOuterClass.GetMasterAddrResp>(
                  this, METHODID_GET_MASTER_ADDR)))
          .build();
    }
  }

  /**
   */
  public static final class ZkStub extends io.grpc.stub.AbstractAsyncStub<ZkStub> {
    private ZkStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected ZkStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ZkStub(channel, callOptions);
    }

    /**
     * <pre>
     * 客户端获取资源主节点地址
     * </pre>
     */
    public void getMasterAddr(ZkOuterClass.GetMasterAddrReq request,
                              io.grpc.stub.StreamObserver<ZkOuterClass.GetMasterAddrResp> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMasterAddrMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ZkBlockingStub extends io.grpc.stub.AbstractBlockingStub<ZkBlockingStub> {
    private ZkBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected ZkBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ZkBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 客户端获取资源主节点地址
     * </pre>
     */
    public ZkOuterClass.GetMasterAddrResp getMasterAddr(ZkOuterClass.GetMasterAddrReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMasterAddrMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ZkFutureStub extends io.grpc.stub.AbstractFutureStub<ZkFutureStub> {
    private ZkFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected ZkFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ZkFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 客户端获取资源主节点地址
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ZkOuterClass.GetMasterAddrResp> getMasterAddr(
        ZkOuterClass.GetMasterAddrReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMasterAddrMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_MASTER_ADDR = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ZkImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ZkImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_MASTER_ADDR:
          serviceImpl.getMasterAddr((ZkOuterClass.GetMasterAddrReq) request,
              (io.grpc.stub.StreamObserver<ZkOuterClass.GetMasterAddrResp>) responseObserver);
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

  private static abstract class ZkBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ZkBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ZkOuterClass.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Zk");
    }
  }

  private static final class ZkFileDescriptorSupplier
      extends ZkBaseDescriptorSupplier {
    ZkFileDescriptorSupplier() {}
  }

  private static final class ZkMethodDescriptorSupplier
      extends ZkBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ZkMethodDescriptorSupplier(String methodName) {
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
      synchronized (ZkGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ZkFileDescriptorSupplier())
              .addMethod(getGetMasterAddrMethod())
              .build();
        }
      }
    }
    return result;
  }
}
