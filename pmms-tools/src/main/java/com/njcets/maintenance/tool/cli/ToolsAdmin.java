package com.njcets.maintenance.tool.cli;

import com.njcets.maintenance.tool.handler.TableFilesHandler;
import org.apache.commons.cli.*;

/**
 * @author gexinl
 */

public class ToolsAdmin {

    public static void main(String[] args) {
        Options options = new Options();

        Option help = Option.builder( "help" )
                .longOpt( "help" )
                .desc( "print help message" )
                .build();

        Option importTables = Option.builder( "importTables" )
                .longOpt( "import-tables" )
                .desc( "import standard tables into Tables XML file" )
                .hasArg()
                .numberOfArgs( 1 ).build();

        Option updateTables = Option.builder( "updateTables" )
                .longOpt( "import-tables" )
                .desc( "update standard tables into Tables XML file" )
                .hasArg()
                .numberOfArgs( 1 ).build();

        options.addOption( importTables );
        options.addOption( updateTables );
        options.addOption( help );

        // parse the command line arguments
        CommandLineParser parser = new DefaultParser();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse( options, args );
            if( line.hasOption( "help" ) ) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp( "ToolsAdmin", options );
            }

            if( line.hasOption( "importTables" ) ) {
                String tableFilesDir = line.getOptionValue( "importTables" );
                TableFilesHandler tableFilesHandler = new TableFilesHandler();
                tableFilesHandler.readTableFilesToXML(tableFilesDir);
            }

            if( line.hasOption( "updateTables" ) ) {
                String tableFilesDir = line.getOptionValue( "updateTables" );
            }
        } catch( ParseException exp ) {
            // oops, something went wrong
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "ToolsAdmin", options );
            System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
        }
    }

}
