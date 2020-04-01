// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: notifications.proto

package notifications.grpc;

public final class Notifications {
  private Notifications() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_subscribeRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_subscribeRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_subscribeResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_subscribeResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_unsubscribeRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_unsubscribeRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_unsubscribeResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_unsubscribeResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_getNotificationsRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_getNotificationsRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_getNotificationsResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_getNotificationsResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_getSubscriptionsRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_getSubscriptionsRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_getSubscriptionsResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_getSubscriptionsResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_seenNotificationRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_seenNotificationRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_seenNotificationResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_seenNotificationResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_addNotificationRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_addNotificationRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_addNotificationResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_addNotificationResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023notifications.proto\"5\n\020subscribeReques" +
      "t\022\017\n\007user_id\030\001 \001(\t\022\020\n\010forum_id\030\002 \001(\t\"%\n\021" +
      "subscribeResponse\022\020\n\010response\030\001 \001(\t\"7\n\022u" +
      "nsubscribeRequest\022\017\n\007user_id\030\001 \001(\t\022\020\n\010fo" +
      "rum_id\030\002 \001(\t\"\'\n\023unsubscribeResponse\022\020\n\010r" +
      "esponse\030\001 \001(\t\"*\n\027getNotificationsRequest" +
      "\022\017\n\007user_id\030\001 \001(\t\"\215\001\n\030getNotificationsRe" +
      "sponse\022\017\n\007user_id\030\001 \001(\t\022\020\n\010forum_id\030\002 \001(" +
      "\t\022\017\n\007post_id\030\003 \001(\t\022\021\n\ttimestamp\030\004 \001(\t\022\017\n" +
      "\007message\030\005 \001(\t\022\031\n\021notificationCount\030\006 \001(",
      "\t\"*\n\027getSubscriptionsRequest\022\017\n\007user_id\030" +
      "\001 \001(\t\"X\n\030getSubscriptionsResponse\022\017\n\007use" +
      "r_id\030\001 \001(\t\022\020\n\010forum_id\030\002 \001(\t\022\031\n\021subscrip" +
      "tionCount\030\003 \001(\t\"`\n\027seenNotificationReque" +
      "st\022\017\n\007user_id\030\001 \001(\t\022\020\n\010forum_id\030\002 \001(\t\022\017\n" +
      "\007post_id\030\003 \001(\t\022\021\n\ttimestamp\030\004 \001(\t\",\n\030see" +
      "nNotificationResponse\022\020\n\010response\030\001 \001(\t\"" +
      ";\n\026addNotificationRequest\022\020\n\010forum_id\030\001 " +
      "\001(\t\022\017\n\007post_id\030\002 \001(\t\"+\n\027addNotificationR" +
      "esponse\022\020\n\010response\030\001 \001(\t2\251\003\n\023notificati",
      "onService\0222\n\tsubscribe\022\021.subscribeReques" +
      "t\032\022.subscribeResponse\0228\n\013unsubscribe\022\023.u" +
      "nsubscribeRequest\032\024.unsubscribeResponse\022" +
      "I\n\020getNotifications\022\030.getNotificationsRe" +
      "quest\032\031.getNotificationsResponse0\001\022I\n\020ge" +
      "tSubscriptions\022\030.getSubscriptionsRequest" +
      "\032\031.getSubscriptionsResponse0\001\022G\n\020seenNot" +
      "ification\022\030.seenNotificationRequest\032\031.se" +
      "enNotificationResponse\022E\n\020addNotificatio" +
      "ns\022\027.addNotificationRequest\032\030.addNotific",
      "ationResponseB\026\n\022notifications.grpcP\001b\006p" +
      "roto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_subscribeRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_subscribeRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_subscribeRequest_descriptor,
        new java.lang.String[] { "UserId", "ForumId", });
    internal_static_subscribeResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_subscribeResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_subscribeResponse_descriptor,
        new java.lang.String[] { "Response", });
    internal_static_unsubscribeRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_unsubscribeRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_unsubscribeRequest_descriptor,
        new java.lang.String[] { "UserId", "ForumId", });
    internal_static_unsubscribeResponse_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_unsubscribeResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_unsubscribeResponse_descriptor,
        new java.lang.String[] { "Response", });
    internal_static_getNotificationsRequest_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_getNotificationsRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_getNotificationsRequest_descriptor,
        new java.lang.String[] { "UserId", });
    internal_static_getNotificationsResponse_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_getNotificationsResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_getNotificationsResponse_descriptor,
        new java.lang.String[] { "UserId", "ForumId", "PostId", "Timestamp", "Message", "NotificationCount", });
    internal_static_getSubscriptionsRequest_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_getSubscriptionsRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_getSubscriptionsRequest_descriptor,
        new java.lang.String[] { "UserId", });
    internal_static_getSubscriptionsResponse_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_getSubscriptionsResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_getSubscriptionsResponse_descriptor,
        new java.lang.String[] { "UserId", "ForumId", "SubscriptionCount", });
    internal_static_seenNotificationRequest_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_seenNotificationRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_seenNotificationRequest_descriptor,
        new java.lang.String[] { "UserId", "ForumId", "PostId", "Timestamp", });
    internal_static_seenNotificationResponse_descriptor =
      getDescriptor().getMessageTypes().get(9);
    internal_static_seenNotificationResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_seenNotificationResponse_descriptor,
        new java.lang.String[] { "Response", });
    internal_static_addNotificationRequest_descriptor =
      getDescriptor().getMessageTypes().get(10);
    internal_static_addNotificationRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_addNotificationRequest_descriptor,
        new java.lang.String[] { "ForumId", "PostId", });
    internal_static_addNotificationResponse_descriptor =
      getDescriptor().getMessageTypes().get(11);
    internal_static_addNotificationResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_addNotificationResponse_descriptor,
        new java.lang.String[] { "Response", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
