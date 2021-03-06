// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: notifications.proto

package notifications.grpc;

/**
 * Protobuf type {@code getSubscriptionsResponse}
 */
public  final class getSubscriptionsResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:getSubscriptionsResponse)
    getSubscriptionsResponseOrBuilder {
  // Use getSubscriptionsResponse.newBuilder() to construct.
  private getSubscriptionsResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private getSubscriptionsResponse() {
    userId_ = "";
    forumId_ = "";
    subscriptionCount_ = "";
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private getSubscriptionsResponse(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            userId_ = s;
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            forumId_ = s;
            break;
          }
          case 26: {
            java.lang.String s = input.readStringRequireUtf8();

            subscriptionCount_ = s;
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return notifications.grpc.Notifications.internal_static_getSubscriptionsResponse_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return notifications.grpc.Notifications.internal_static_getSubscriptionsResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            notifications.grpc.getSubscriptionsResponse.class, notifications.grpc.getSubscriptionsResponse.Builder.class);
  }

  public static final int USER_ID_FIELD_NUMBER = 1;
  private volatile java.lang.Object userId_;
  /**
   * <code>string user_id = 1;</code>
   */
  public java.lang.String getUserId() {
    java.lang.Object ref = userId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      userId_ = s;
      return s;
    }
  }
  /**
   * <code>string user_id = 1;</code>
   */
  public com.google.protobuf.ByteString
      getUserIdBytes() {
    java.lang.Object ref = userId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      userId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int FORUM_ID_FIELD_NUMBER = 2;
  private volatile java.lang.Object forumId_;
  /**
   * <code>string forum_id = 2;</code>
   */
  public java.lang.String getForumId() {
    java.lang.Object ref = forumId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      forumId_ = s;
      return s;
    }
  }
  /**
   * <code>string forum_id = 2;</code>
   */
  public com.google.protobuf.ByteString
      getForumIdBytes() {
    java.lang.Object ref = forumId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      forumId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int SUBSCRIPTIONCOUNT_FIELD_NUMBER = 3;
  private volatile java.lang.Object subscriptionCount_;
  /**
   * <code>string subscriptionCount = 3;</code>
   */
  public java.lang.String getSubscriptionCount() {
    java.lang.Object ref = subscriptionCount_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      subscriptionCount_ = s;
      return s;
    }
  }
  /**
   * <code>string subscriptionCount = 3;</code>
   */
  public com.google.protobuf.ByteString
      getSubscriptionCountBytes() {
    java.lang.Object ref = subscriptionCount_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      subscriptionCount_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getUserIdBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, userId_);
    }
    if (!getForumIdBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, forumId_);
    }
    if (!getSubscriptionCountBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, subscriptionCount_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getUserIdBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, userId_);
    }
    if (!getForumIdBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, forumId_);
    }
    if (!getSubscriptionCountBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, subscriptionCount_);
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof notifications.grpc.getSubscriptionsResponse)) {
      return super.equals(obj);
    }
    notifications.grpc.getSubscriptionsResponse other = (notifications.grpc.getSubscriptionsResponse) obj;

    boolean result = true;
    result = result && getUserId()
        .equals(other.getUserId());
    result = result && getForumId()
        .equals(other.getForumId());
    result = result && getSubscriptionCount()
        .equals(other.getSubscriptionCount());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + USER_ID_FIELD_NUMBER;
    hash = (53 * hash) + getUserId().hashCode();
    hash = (37 * hash) + FORUM_ID_FIELD_NUMBER;
    hash = (53 * hash) + getForumId().hashCode();
    hash = (37 * hash) + SUBSCRIPTIONCOUNT_FIELD_NUMBER;
    hash = (53 * hash) + getSubscriptionCount().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static notifications.grpc.getSubscriptionsResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static notifications.grpc.getSubscriptionsResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static notifications.grpc.getSubscriptionsResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static notifications.grpc.getSubscriptionsResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static notifications.grpc.getSubscriptionsResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static notifications.grpc.getSubscriptionsResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static notifications.grpc.getSubscriptionsResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static notifications.grpc.getSubscriptionsResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static notifications.grpc.getSubscriptionsResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static notifications.grpc.getSubscriptionsResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static notifications.grpc.getSubscriptionsResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static notifications.grpc.getSubscriptionsResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(notifications.grpc.getSubscriptionsResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code getSubscriptionsResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:getSubscriptionsResponse)
      notifications.grpc.getSubscriptionsResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return notifications.grpc.Notifications.internal_static_getSubscriptionsResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return notifications.grpc.Notifications.internal_static_getSubscriptionsResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              notifications.grpc.getSubscriptionsResponse.class, notifications.grpc.getSubscriptionsResponse.Builder.class);
    }

    // Construct using notifications.grpc.getSubscriptionsResponse.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      userId_ = "";

      forumId_ = "";

      subscriptionCount_ = "";

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return notifications.grpc.Notifications.internal_static_getSubscriptionsResponse_descriptor;
    }

    public notifications.grpc.getSubscriptionsResponse getDefaultInstanceForType() {
      return notifications.grpc.getSubscriptionsResponse.getDefaultInstance();
    }

    public notifications.grpc.getSubscriptionsResponse build() {
      notifications.grpc.getSubscriptionsResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public notifications.grpc.getSubscriptionsResponse buildPartial() {
      notifications.grpc.getSubscriptionsResponse result = new notifications.grpc.getSubscriptionsResponse(this);
      result.userId_ = userId_;
      result.forumId_ = forumId_;
      result.subscriptionCount_ = subscriptionCount_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof notifications.grpc.getSubscriptionsResponse) {
        return mergeFrom((notifications.grpc.getSubscriptionsResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(notifications.grpc.getSubscriptionsResponse other) {
      if (other == notifications.grpc.getSubscriptionsResponse.getDefaultInstance()) return this;
      if (!other.getUserId().isEmpty()) {
        userId_ = other.userId_;
        onChanged();
      }
      if (!other.getForumId().isEmpty()) {
        forumId_ = other.forumId_;
        onChanged();
      }
      if (!other.getSubscriptionCount().isEmpty()) {
        subscriptionCount_ = other.subscriptionCount_;
        onChanged();
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      notifications.grpc.getSubscriptionsResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (notifications.grpc.getSubscriptionsResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object userId_ = "";
    /**
     * <code>string user_id = 1;</code>
     */
    public java.lang.String getUserId() {
      java.lang.Object ref = userId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        userId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string user_id = 1;</code>
     */
    public com.google.protobuf.ByteString
        getUserIdBytes() {
      java.lang.Object ref = userId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        userId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string user_id = 1;</code>
     */
    public Builder setUserId(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      userId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string user_id = 1;</code>
     */
    public Builder clearUserId() {
      
      userId_ = getDefaultInstance().getUserId();
      onChanged();
      return this;
    }
    /**
     * <code>string user_id = 1;</code>
     */
    public Builder setUserIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      userId_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object forumId_ = "";
    /**
     * <code>string forum_id = 2;</code>
     */
    public java.lang.String getForumId() {
      java.lang.Object ref = forumId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        forumId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string forum_id = 2;</code>
     */
    public com.google.protobuf.ByteString
        getForumIdBytes() {
      java.lang.Object ref = forumId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        forumId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string forum_id = 2;</code>
     */
    public Builder setForumId(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      forumId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string forum_id = 2;</code>
     */
    public Builder clearForumId() {
      
      forumId_ = getDefaultInstance().getForumId();
      onChanged();
      return this;
    }
    /**
     * <code>string forum_id = 2;</code>
     */
    public Builder setForumIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      forumId_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object subscriptionCount_ = "";
    /**
     * <code>string subscriptionCount = 3;</code>
     */
    public java.lang.String getSubscriptionCount() {
      java.lang.Object ref = subscriptionCount_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        subscriptionCount_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string subscriptionCount = 3;</code>
     */
    public com.google.protobuf.ByteString
        getSubscriptionCountBytes() {
      java.lang.Object ref = subscriptionCount_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        subscriptionCount_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string subscriptionCount = 3;</code>
     */
    public Builder setSubscriptionCount(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      subscriptionCount_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string subscriptionCount = 3;</code>
     */
    public Builder clearSubscriptionCount() {
      
      subscriptionCount_ = getDefaultInstance().getSubscriptionCount();
      onChanged();
      return this;
    }
    /**
     * <code>string subscriptionCount = 3;</code>
     */
    public Builder setSubscriptionCountBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      subscriptionCount_ = value;
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:getSubscriptionsResponse)
  }

  // @@protoc_insertion_point(class_scope:getSubscriptionsResponse)
  private static final notifications.grpc.getSubscriptionsResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new notifications.grpc.getSubscriptionsResponse();
  }

  public static notifications.grpc.getSubscriptionsResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<getSubscriptionsResponse>
      PARSER = new com.google.protobuf.AbstractParser<getSubscriptionsResponse>() {
    public getSubscriptionsResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new getSubscriptionsResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<getSubscriptionsResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<getSubscriptionsResponse> getParserForType() {
    return PARSER;
  }

  public notifications.grpc.getSubscriptionsResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

