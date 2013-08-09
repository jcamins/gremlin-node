package com.entrendipity.gremlin.javascript;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.Tokens;
import com.tinkerpop.gremlin.groovy.GremlinGroovyPipeline;

import com.entrendipity.gremlin.javascript.CapThreaded;

/**
 * @author Frank Panetta (frank.panetta@entrendipity.com.au)
 */
public class GremlinJSPipeline<S, E> extends GremlinGroovyPipeline<S, E> {

    private static final String FLOAT_SUFFIX = "f";

    public GremlinJSPipeline() {
        super();
    }

    public GremlinJSPipeline(final Object starts) {
        super(starts);
    }

    public GremlinJSPipeline<S, ? extends Element> has(final String key, final String value) {
        if (value.endsWith(FLOAT_SUFFIX) && !Float.isNaN(Float.parseFloat(value))){
            return (GremlinJSPipeline<S, ? extends Element>)super.has(key, Float.parseFloat(value));
        }
        return (GremlinJSPipeline<S, ? extends Element>)super.has(key, value);
    }

    public GremlinJSPipeline<S, ? extends Element> has(final String key, final Tokens.T comparison, final String value) {
        if (value.endsWith(FLOAT_SUFFIX) && !Float.isNaN(Float.parseFloat(value))){
            return (GremlinJSPipeline<S, ? extends Element>)super.has(key, comparison, Float.parseFloat(value));
        }
        return (GremlinJSPipeline<S, ? extends Element>)super.has(key, comparison, value);
    }

    public GremlinGroovyPipeline<S, ? extends Element> hasNot(final String key, final String value) {
        if (value.endsWith(FLOAT_SUFFIX) && !Float.isNaN(Float.parseFloat(value))){
            return (GremlinJSPipeline<S, ? extends Element>)super.hasNot(key, Float.parseFloat(value));
        }
        return (GremlinJSPipeline<S, ? extends Element>)super.hasNot(key, value);
    }

    public GremlinJSPipeline<S, ? extends Element> interval(final String key, final String startValue, final String endValue) {
        Comparable tmpStartValue = startValue;
        Comparable tmpEndValue = endValue;
        if (startValue.endsWith(FLOAT_SUFFIX) && !Float.isNaN(Float.parseFloat(startValue))){
            tmpStartValue = Float.parseFloat(startValue);
        }
        if (endValue.endsWith(FLOAT_SUFFIX) && !Float.isNaN(Float.parseFloat(endValue))){
            tmpEndValue = Float.parseFloat(endValue);
        }
        return (GremlinJSPipeline<S, ? extends Element>)super.interval(key, tmpStartValue, tmpEndValue);
    }

    public static Class<Vertex> getVertexTypeClass(){
        return Vertex.class;
    }

    public static Class<Edge> getEdgeTypeClass(){
        return Edge.class;
    }

    public GremlinJSPipeline<S, ? extends Element> capThreaded() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Callable<GremlinGroovyPipeline> worker = new CapThreaded(this);
        Future<GremlinGroovyPipeline> submit = executor.submit(worker);
        try {
            return (GremlinJSPipeline<S, ? extends Element>)submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}

