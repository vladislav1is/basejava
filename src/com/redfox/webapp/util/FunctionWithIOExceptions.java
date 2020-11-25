package com.redfox.webapp.util;

import java.io.IOException;

public interface FunctionWithIOExceptions<A, T extends IOException> {
    void apply(A a) throws T;
}
