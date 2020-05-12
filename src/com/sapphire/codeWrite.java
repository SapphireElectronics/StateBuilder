package com.sapphire;

import java.io.BufferedWriter;
import java.io.IOException;

public class codeWrite {
    public BufferedWriter bw = null;
    public int indent = 0;

    public codeWrite( BufferedWriter bw ) {
        this.bw = bw;
    }

    public void incIndent() {
        indent++;
    }

    public void decIndent() {
        if( indent > 0 )
            indent--;
    }

    public void setIndent( int indent ) {
        this.indent = indent;
    }

    public void write( String code ) throws IOException {
        for (int i = 0; i < indent; i++)
            bw.write("\t");
        bw.write(code + "\n");
    }
}
