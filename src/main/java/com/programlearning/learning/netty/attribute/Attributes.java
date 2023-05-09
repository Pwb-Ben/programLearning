package com.programlearning.learning.netty.attribute;

import com.programlearning.learning.netty.session.Session;
import io.netty.util.AttributeKey;

public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
