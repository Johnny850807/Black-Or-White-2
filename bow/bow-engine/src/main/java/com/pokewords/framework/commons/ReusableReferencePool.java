package com.pokewords.framework.commons;

import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Thread-safe ObjectPool for only creating reusable reference.
 * @author johnny850807
 */
public class ReusableReferencePool<T> {
    private Set<T> instances = Collections.newSetFromMap(new IdentityHashMap<>());
    private Supplier<T> referenceSupplier;

    public ReusableReferencePool(int initialCapacity, Supplier<T> referenceSupplier) {
        this.referenceSupplier = referenceSupplier;
        for (int i = 0; i < initialCapacity; i++) {
            instances.add(referenceSupplier.get());
        }
    }

    public synchronized T get() {
        if (instances.isEmpty())
            return referenceSupplier.get();

        T firstInstance = instances.iterator().next();
        instances.remove(firstInstance);
        return firstInstance;
    }

    public synchronized void put(T instance) {
        instances.add(instance);
    }

    public synchronized void put(Collection<T> instanceCollection) {
        instances.addAll(instanceCollection);
    }
}
