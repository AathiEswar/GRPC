package org.fslabs.Collections;

import com.fslabs.collections.WrapperGoogle;
import com.google.protobuf.Int32Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WrapperType {

    private static final Logger logger = LoggerFactory.getLogger(WrapperType.class);

    public static void main(String[] args) {
        WrapperGoogle wrapper = WrapperGoogle.newBuilder()
                .setNum(Int32Value.of(10))
                .build();
    }
}
