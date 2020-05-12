package com.sapphire;

import com.oracle.tools.packager.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List states = new ArrayList<String>();
        BufferedReader br = null;

        try {
            br =  new BufferedReader( new FileReader( "states.txt" ) );
            String line;

            while( ( line  = br.readLine() ) != null )
                states.add( line );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }

        String name = new String();
        String first = new String();
        String last = new String();


        if( states.size() > 0 ) {
            name = states.get( 0 ).toString();
            states.remove(states.get( 0 ));
        }

        if( states.size() > 0 ) {
            first = states.get( 0 ).toString();
            states.remove(states.get( 0 ));
        }

        if( states.size() > 0 ) {
            last = states.get( states.size()-1 ).toString();
            states.remove(states.get( states.size()-1 ));
        }



        BufferedWriter bw = null;
        bw = new BufferedWriter( new FileWriter( "vhdl_code.txt ") );

        codeWrite vhdl = new codeWrite( bw );

        writeVHDL(  bw, 0, "type " + name + "_state_type is (");

        writeVHDL( bw, 1, first + "," );
        for( Object state : states )
            writeVHDL( bw, 1, state + "," );
        writeVHDL( bw, 1, last );

        vhdl.write( ");" );
        writeVHDL( bw, 0, "" );

        writeVHDL( bw, 1, name + ": process( clk, rst )" );
        writeVHDL( bw, 1, "begin" );
        writeVHDL( bw, 2, "-- set initial state on power up");
        writeVHDL( bw, 2, "if rst = '0' then" );
        writeVHDL( bw, 3, "state <= " + first + ";" );

        writeVHDL( bw, 0, "" );
        writeVHDL( bw, 2, "elsif rising_edge( clk ) then" );
        writeVHDL( bw, 2, "-- the state transitions are here");
        writeVHDL( bw, 3, "case state is" );

        writeVHDL( bw, 4, "when " + first + " => " );
        writeVHDL( bw, 0, "" );
        for( Object state : states ) {
            writeVHDL(bw, 4, "when " + state + " => " );
            writeVHDL( bw, 0, "" );
        }

        writeVHDL( bw, 4, "when " + last + " =>" );
        writeVHDL( bw, 5, "state <= " + last + ";" );
        writeVHDL( bw, 0, "" );
        writeVHDL( bw, 4, "when others => ");
        writeVHDL( bw, 5, "state <= " + last + ";");
        writeVHDL( bw, 0, "" );
        writeVHDL( bw, 3, "end case;" );

        writeVHDL( bw, 0, "" );
        writeVHDL( bw, 2, "elsif falling_edge( clk ) then" );
        writeVHDL( bw, 2, "-- the state outputs are here");
        writeVHDL( bw, 3, "case state is" );

        writeVHDL( bw, 4, "when " + first + " => " );
        writeVHDL( bw, 0, "" );
        for( Object state : states ) {
            writeVHDL(bw, 4, "when " + state + " => " );
            writeVHDL( bw, 0, "" );
        }

        writeVHDL( bw, 4, "when " + last + " =>" );
        writeVHDL( bw, 5, "state <= " + last + ";" );
        writeVHDL( bw, 0, "" );
        writeVHDL( bw, 4, "when others => ");
        writeVHDL( bw, 5, "state <= " + last + ";");
        writeVHDL( bw, 0, "" );
        writeVHDL( bw, 3, "end case;" );
        writeVHDL( bw, 2, "end if;" );
        writeVHDL( bw, 1, "end process;" );
        writeVHDL( bw, 0, "" );

        bw.close();

    }

    public static void writeVHDL( BufferedWriter bw, int indent, String code ) throws IOException {
        for (int i = 0; i < indent; i++)
            bw.write( "\t" );

        bw.write( code + "\n" );
    }
}
