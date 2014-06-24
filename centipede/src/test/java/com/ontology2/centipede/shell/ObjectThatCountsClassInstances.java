package com.ontology2.centipede.shell;

import java.util.concurrent.atomic.AtomicInteger;

public class ObjectThatCountsClassInstances {
    private static final AtomicInteger instanceCount=new AtomicInteger(0);
    private static final AtomicInteger totalCreated=new AtomicInteger(0);

    public ObjectThatCountsClassInstances() {
        instanceCount.addAndGet(1);
        totalCreated.addAndGet(1);
    }

    public void destroy() {
        instanceCount.addAndGet(-1);
    }

    public static int get() {
        return instanceCount.get();
    }

    public static int getCreated() {
        return totalCreated.get();
    }

    public static void reset() {
        instanceCount.set(0);
    }
}
