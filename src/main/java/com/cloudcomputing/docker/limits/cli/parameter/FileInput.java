package com.cloudcomputing.docker.limits.cli.parameter;

import java.io.File;
import java.net.URI;

public class FileInput extends File {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -854809420653470352L;

    /**
     * Instantiates a new file input.
     *
     * @param parent
     *          the parent
     * @param child
     *          the child
     */
    public FileInput(File parent, String child) {
        super(parent, child);
    }

    /**
     * Instantiates a new file input.
     *
     * @param parent
     *          the parent
     * @param child
     *          the child
     */
    public FileInput(String parent, String child) {
        super(parent, child);
    }

    /**
     * Instantiates a new file input.
     *
     * @param pathname
     *          the pathname
     */
    public FileInput(String pathname) {
        super(pathname);
    }

    /**
     * Instantiates a new file input.
     *
     * @param uri
     *          the uri
     */
    public FileInput(URI uri) {
        super(uri);
    }

}
