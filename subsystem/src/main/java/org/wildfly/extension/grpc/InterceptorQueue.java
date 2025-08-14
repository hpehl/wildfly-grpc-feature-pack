/*
 *  Copyright The WildFly Authors
 *  SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.extension.grpc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import javax.annotation.concurrent.NotThreadSafe;

import io.grpc.ServerInterceptor;

/**
 * Specialized {@link PriorityQueue} that sorts {@link ServerInterceptor}s based on the value of a {@link @Priority}
 * annotation. A {@link ServerInterceptor} with no {@link @Priority} annotation has the lowest priority.
 */
@NotThreadSafe
public class InterceptorQueue extends PriorityQueue<InterceptorQueue.Element> {

    private static final long serialVersionUID = 1L;

    public static class Element {
        public int priority;
        public Class<? extends ServerInterceptor> clazz;

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public Class<? extends ServerInterceptor> getClazz() {
            return clazz;
        }

        public void setClazz(Class<? extends ServerInterceptor> clazz) {
            this.clazz = clazz;
        }
    }

    public InterceptorQueue() {
        this(new InterceptorComparator());
    }

    public InterceptorQueue(InterceptorComparator comp) {
        super(comp);
    }

    public void add(Class<? extends ServerInterceptor> clazz) {
        Element element = new Element();
        element.setPriority(getPriority(clazz));
        element.setClazz(clazz);
        add(element);
    }

    /**
     * Returns the contents in reverse priority order. That is suitable for wrapping them around a method so that the
     * highest priority {@link ServerInterceptor} executes first. {@link GrpcServerService#installInterceptors(String)
     * GrpcServerService.installInterceptors()}
     */
    public List<Class<? extends ServerInterceptor>> toList() {
        List<Class<? extends ServerInterceptor>> list = new ArrayList<Class<? extends ServerInterceptor>>();
        while (peek() != null) {
            list.add(poll().getClazz());
        }
        return list;
    }

    private Integer getPriority(Class<? extends ServerInterceptor> clazz) {
        try {
            for (Annotation an : clazz.getDeclaredAnnotations()) {
                if ("jakarta.annotation.Priority".equals(an.annotationType().getName())) {
                    Method value = an.annotationType().getMethod("value");
                    value.setAccessible(true);
                    Integer i = (Integer) value.invoke(an);
                    return i;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Integer.MAX_VALUE - 1;
    }

    public static class InterceptorComparator implements Comparator<Element> {

        @Override
        public int compare(Element arg0, Element arg1) {
            if (arg0.priority > arg1.priority) {
                return -1;
            }
            if (arg0.priority == arg1.priority) {
                return 0;
            }
            return 1;
        }
    }
}
