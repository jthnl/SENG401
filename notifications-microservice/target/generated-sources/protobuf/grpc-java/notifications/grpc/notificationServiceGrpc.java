package notifications.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: notifications.proto")
public final class notificationServiceGrpc {

  private notificationServiceGrpc() {}

  public static final String SERVICE_NAME = "notificationService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<notifications.grpc.subscribeRequest,
      notifications.grpc.subscribeResponse> METHOD_SUBSCRIBE =
      io.grpc.MethodDescriptor.<notifications.grpc.subscribeRequest, notifications.grpc.subscribeResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "notificationService", "subscribe"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              notifications.grpc.subscribeRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              notifications.grpc.subscribeResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<notifications.grpc.unsubscribeRequest,
      notifications.grpc.unsubscribeResponse> METHOD_UNSUBSCRIBE =
      io.grpc.MethodDescriptor.<notifications.grpc.unsubscribeRequest, notifications.grpc.unsubscribeResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "notificationService", "unsubscribe"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              notifications.grpc.unsubscribeRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              notifications.grpc.unsubscribeResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<notifications.grpc.getNotificationsRequest,
      notifications.grpc.getNotificationsResponse> METHOD_GET_NOTIFICATIONS =
      io.grpc.MethodDescriptor.<notifications.grpc.getNotificationsRequest, notifications.grpc.getNotificationsResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "notificationService", "getNotifications"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              notifications.grpc.getNotificationsRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              notifications.grpc.getNotificationsResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<notifications.grpc.getSubscriptionsRequest,
      notifications.grpc.getSubscriptionsResponse> METHOD_GET_SUBSCRIPTIONS =
      io.grpc.MethodDescriptor.<notifications.grpc.getSubscriptionsRequest, notifications.grpc.getSubscriptionsResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "notificationService", "getSubscriptions"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              notifications.grpc.getSubscriptionsRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              notifications.grpc.getSubscriptionsResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<notifications.grpc.seenNotificationRequest,
      notifications.grpc.seenNotificationResponse> METHOD_SEEN_NOTIFICATION =
      io.grpc.MethodDescriptor.<notifications.grpc.seenNotificationRequest, notifications.grpc.seenNotificationResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "notificationService", "seenNotification"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              notifications.grpc.seenNotificationRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              notifications.grpc.seenNotificationResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<notifications.grpc.addNotificationRequest,
      notifications.grpc.addNotificationResponse> METHOD_ADD_NOTIFICATIONS =
      io.grpc.MethodDescriptor.<notifications.grpc.addNotificationRequest, notifications.grpc.addNotificationResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "notificationService", "addNotifications"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              notifications.grpc.addNotificationRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              notifications.grpc.addNotificationResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static notificationServiceStub newStub(io.grpc.Channel channel) {
    return new notificationServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static notificationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new notificationServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static notificationServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new notificationServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class notificationServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *they call subscribe, pass in subscribe request, and get back subscribe response
     * </pre>
     */
    public void subscribe(notifications.grpc.subscribeRequest request,
        io.grpc.stub.StreamObserver<notifications.grpc.subscribeResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SUBSCRIBE, responseObserver);
    }

    /**
     */
    public void unsubscribe(notifications.grpc.unsubscribeRequest request,
        io.grpc.stub.StreamObserver<notifications.grpc.unsubscribeResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UNSUBSCRIBE, responseObserver);
    }

    /**
     */
    public void getNotifications(notifications.grpc.getNotificationsRequest request,
        io.grpc.stub.StreamObserver<notifications.grpc.getNotificationsResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_NOTIFICATIONS, responseObserver);
    }

    /**
     */
    public void getSubscriptions(notifications.grpc.getSubscriptionsRequest request,
        io.grpc.stub.StreamObserver<notifications.grpc.getSubscriptionsResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SUBSCRIPTIONS, responseObserver);
    }

    /**
     */
    public void seenNotification(notifications.grpc.seenNotificationRequest request,
        io.grpc.stub.StreamObserver<notifications.grpc.seenNotificationResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SEEN_NOTIFICATION, responseObserver);
    }

    /**
     */
    public void addNotifications(notifications.grpc.addNotificationRequest request,
        io.grpc.stub.StreamObserver<notifications.grpc.addNotificationResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ADD_NOTIFICATIONS, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_SUBSCRIBE,
            asyncUnaryCall(
              new MethodHandlers<
                notifications.grpc.subscribeRequest,
                notifications.grpc.subscribeResponse>(
                  this, METHODID_SUBSCRIBE)))
          .addMethod(
            METHOD_UNSUBSCRIBE,
            asyncUnaryCall(
              new MethodHandlers<
                notifications.grpc.unsubscribeRequest,
                notifications.grpc.unsubscribeResponse>(
                  this, METHODID_UNSUBSCRIBE)))
          .addMethod(
            METHOD_GET_NOTIFICATIONS,
            asyncServerStreamingCall(
              new MethodHandlers<
                notifications.grpc.getNotificationsRequest,
                notifications.grpc.getNotificationsResponse>(
                  this, METHODID_GET_NOTIFICATIONS)))
          .addMethod(
            METHOD_GET_SUBSCRIPTIONS,
            asyncServerStreamingCall(
              new MethodHandlers<
                notifications.grpc.getSubscriptionsRequest,
                notifications.grpc.getSubscriptionsResponse>(
                  this, METHODID_GET_SUBSCRIPTIONS)))
          .addMethod(
            METHOD_SEEN_NOTIFICATION,
            asyncUnaryCall(
              new MethodHandlers<
                notifications.grpc.seenNotificationRequest,
                notifications.grpc.seenNotificationResponse>(
                  this, METHODID_SEEN_NOTIFICATION)))
          .addMethod(
            METHOD_ADD_NOTIFICATIONS,
            asyncUnaryCall(
              new MethodHandlers<
                notifications.grpc.addNotificationRequest,
                notifications.grpc.addNotificationResponse>(
                  this, METHODID_ADD_NOTIFICATIONS)))
          .build();
    }
  }

  /**
   */
  public static final class notificationServiceStub extends io.grpc.stub.AbstractStub<notificationServiceStub> {
    private notificationServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private notificationServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected notificationServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new notificationServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *they call subscribe, pass in subscribe request, and get back subscribe response
     * </pre>
     */
    public void subscribe(notifications.grpc.subscribeRequest request,
        io.grpc.stub.StreamObserver<notifications.grpc.subscribeResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SUBSCRIBE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void unsubscribe(notifications.grpc.unsubscribeRequest request,
        io.grpc.stub.StreamObserver<notifications.grpc.unsubscribeResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UNSUBSCRIBE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getNotifications(notifications.grpc.getNotificationsRequest request,
        io.grpc.stub.StreamObserver<notifications.grpc.getNotificationsResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_GET_NOTIFICATIONS, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getSubscriptions(notifications.grpc.getSubscriptionsRequest request,
        io.grpc.stub.StreamObserver<notifications.grpc.getSubscriptionsResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_GET_SUBSCRIPTIONS, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void seenNotification(notifications.grpc.seenNotificationRequest request,
        io.grpc.stub.StreamObserver<notifications.grpc.seenNotificationResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SEEN_NOTIFICATION, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addNotifications(notifications.grpc.addNotificationRequest request,
        io.grpc.stub.StreamObserver<notifications.grpc.addNotificationResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ADD_NOTIFICATIONS, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class notificationServiceBlockingStub extends io.grpc.stub.AbstractStub<notificationServiceBlockingStub> {
    private notificationServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private notificationServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected notificationServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new notificationServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *they call subscribe, pass in subscribe request, and get back subscribe response
     * </pre>
     */
    public notifications.grpc.subscribeResponse subscribe(notifications.grpc.subscribeRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SUBSCRIBE, getCallOptions(), request);
    }

    /**
     */
    public notifications.grpc.unsubscribeResponse unsubscribe(notifications.grpc.unsubscribeRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UNSUBSCRIBE, getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<notifications.grpc.getNotificationsResponse> getNotifications(
        notifications.grpc.getNotificationsRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_GET_NOTIFICATIONS, getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<notifications.grpc.getSubscriptionsResponse> getSubscriptions(
        notifications.grpc.getSubscriptionsRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_GET_SUBSCRIPTIONS, getCallOptions(), request);
    }

    /**
     */
    public notifications.grpc.seenNotificationResponse seenNotification(notifications.grpc.seenNotificationRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SEEN_NOTIFICATION, getCallOptions(), request);
    }

    /**
     */
    public notifications.grpc.addNotificationResponse addNotifications(notifications.grpc.addNotificationRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ADD_NOTIFICATIONS, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class notificationServiceFutureStub extends io.grpc.stub.AbstractStub<notificationServiceFutureStub> {
    private notificationServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private notificationServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected notificationServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new notificationServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *they call subscribe, pass in subscribe request, and get back subscribe response
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<notifications.grpc.subscribeResponse> subscribe(
        notifications.grpc.subscribeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SUBSCRIBE, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<notifications.grpc.unsubscribeResponse> unsubscribe(
        notifications.grpc.unsubscribeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UNSUBSCRIBE, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<notifications.grpc.seenNotificationResponse> seenNotification(
        notifications.grpc.seenNotificationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SEEN_NOTIFICATION, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<notifications.grpc.addNotificationResponse> addNotifications(
        notifications.grpc.addNotificationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ADD_NOTIFICATIONS, getCallOptions()), request);
    }
  }

  private static final int METHODID_SUBSCRIBE = 0;
  private static final int METHODID_UNSUBSCRIBE = 1;
  private static final int METHODID_GET_NOTIFICATIONS = 2;
  private static final int METHODID_GET_SUBSCRIPTIONS = 3;
  private static final int METHODID_SEEN_NOTIFICATION = 4;
  private static final int METHODID_ADD_NOTIFICATIONS = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final notificationServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(notificationServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SUBSCRIBE:
          serviceImpl.subscribe((notifications.grpc.subscribeRequest) request,
              (io.grpc.stub.StreamObserver<notifications.grpc.subscribeResponse>) responseObserver);
          break;
        case METHODID_UNSUBSCRIBE:
          serviceImpl.unsubscribe((notifications.grpc.unsubscribeRequest) request,
              (io.grpc.stub.StreamObserver<notifications.grpc.unsubscribeResponse>) responseObserver);
          break;
        case METHODID_GET_NOTIFICATIONS:
          serviceImpl.getNotifications((notifications.grpc.getNotificationsRequest) request,
              (io.grpc.stub.StreamObserver<notifications.grpc.getNotificationsResponse>) responseObserver);
          break;
        case METHODID_GET_SUBSCRIPTIONS:
          serviceImpl.getSubscriptions((notifications.grpc.getSubscriptionsRequest) request,
              (io.grpc.stub.StreamObserver<notifications.grpc.getSubscriptionsResponse>) responseObserver);
          break;
        case METHODID_SEEN_NOTIFICATION:
          serviceImpl.seenNotification((notifications.grpc.seenNotificationRequest) request,
              (io.grpc.stub.StreamObserver<notifications.grpc.seenNotificationResponse>) responseObserver);
          break;
        case METHODID_ADD_NOTIFICATIONS:
          serviceImpl.addNotifications((notifications.grpc.addNotificationRequest) request,
              (io.grpc.stub.StreamObserver<notifications.grpc.addNotificationResponse>) responseObserver);
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

  private static final class notificationServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return notifications.grpc.Notifications.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (notificationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new notificationServiceDescriptorSupplier())
              .addMethod(METHOD_SUBSCRIBE)
              .addMethod(METHOD_UNSUBSCRIBE)
              .addMethod(METHOD_GET_NOTIFICATIONS)
              .addMethod(METHOD_GET_SUBSCRIPTIONS)
              .addMethod(METHOD_SEEN_NOTIFICATION)
              .addMethod(METHOD_ADD_NOTIFICATIONS)
              .build();
        }
      }
    }
    return result;
  }
}
