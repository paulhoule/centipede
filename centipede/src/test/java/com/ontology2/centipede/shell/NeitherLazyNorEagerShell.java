package com.ontology2.centipede.shell;

public class NeitherLazyNorEagerShell extends InfovoreShell {
    @Override
    protected Boolean isLazyByDefault() {
        return null;
    }

    static void main(String[] arguments) {
        new NeitherLazyNorEagerShell().run(arguments);
    }
}
