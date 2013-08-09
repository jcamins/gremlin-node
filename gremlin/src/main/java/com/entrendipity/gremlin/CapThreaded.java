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

public class CapThreaded implements Callable<GremlinGroovyPipeline> {

    protected GremlinGroovyPipeline pipeline;

    public CapThreaded(GremlinGroovyPipeline pipe) {
        super();
        this.pipeline = pipe;
    }

    public GremlinGroovyPipeline call() throws Exception {
        return (GremlinGroovyPipeline)this.pipeline.cap();
    }
}


