package com.ontology2.centipede.shell;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;
import com.ontology2.centipede.shell.CommandLineApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static com.google.common.base.Charsets.*;

abstract public class ResourceAwareObject {
    @Autowired
    ResourceLoader loader;

    // Wouldn't it be nice if we wrote some tests?

    protected ByteSource stream(final String resourceId) {
        return new ByteSource() {
            @Override
            public InputStream openStream() throws IOException {
                return loader.getResource(resourceId).getInputStream();
            }
        };
    }

    protected CharSource reader(final String resourceId) {
        return new CharSource() {
            @Override
            public Reader openStream() throws IOException {
                return new InputStreamReader(loader.getResource(resourceId).getInputStream(), UTF_8);
            }
        };
    }

    protected String string(final String resourceId) throws IOException {
        return reader(resourceId).read();
    }
}
