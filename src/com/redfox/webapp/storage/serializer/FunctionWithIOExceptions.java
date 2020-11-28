package com.redfox.webapp.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface FunctionWithIOExceptions<C, T extends IOException> {
    void apply(C item) throws T;
}
