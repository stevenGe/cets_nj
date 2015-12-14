package com.njcets.tools.core.cli;

import org.apache.commons.cli.*;

/**
 * CLI entrance for nj-cets PMMS project.
 * @author gexinl
 * @version 1.0-SNAPSHOT
 */

public class PMMSAdmin {

    public static void main( String[] args ) {
        Options options = new Options();

        Option importTemplate = Option.builder( "importTemplate" )
                .longOpt( "import-template" )
                .desc( "import source template to db" )
                .hasArg()
                .numberOfArgs( 1 ).build();

        Option listTemplates = Option.builder( "listTemplates" )
                .longOpt( "list-templates" )
                .desc( "list current existed templates" ).build();

        Option deleteTemplate = Option.builder( "deleteTemplate" )
                .longOpt("delete-template")
                .desc("delete a specified template")
                .hasArg()
                .numberOfArgs( 1 ).build();

        Option importData = Option.builder( "importData" )
                .longOpt( "import-data" )
                .desc( "import data files by existed template" )
                .hasArgs()
                .numberOfArgs( 2 ).build();

        Option exportData = Option.builder( "exportData" )
                .longOpt( "export-data" )
                .desc( "export data to specified xls file" )
                .hasArgs()
                .numberOfArgs( 2 ).build();

        Option help = Option.builder( "help" )
                .longOpt( "help" )
                .desc( "print help message" )
                .build();

        options.addOption( importTemplate );
        options.addOption( listTemplates );
        options.addOption( deleteTemplate );
        options.addOption( importData );
        options.addOption( exportData );
        options.addOption( help );

        // parse the command line arguments
        CommandLineParser parser = new DefaultParser();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse( options, args );
            if( line.hasOption( "help" ) ) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp( "PMMSAdmin", options );
            }
            if( line.hasOption( "import-template" ) ) {
                String templateDir = line.getOptionValue( "import-template" );
                System.out.println( "Import template from directory Successfully" );
            }
            if( line.hasOption( "list-templates" ) ) {
                System.out.println( "list templates successfully" );
            }
            if ( line.hasOption( "delete-template" ))  {
                String templateDir = line.getOptionValue( "delete-template" );
                System.out.println( "Delete template Successfully" );
            }
            if( line.hasOption( "import-data" ) ) {
                String[] values = line.getOptionValues( "import-data" );
                System.out.println( "Import data from directory Successfully" );
            }
            if( line.hasOption( "export-data" ) ) {
                String[] values = line.getOptionValues( "export-data" );

                System.out.println( "Export data Successfully" );
            }
        }
        catch( ParseException exp ) {
            // oops, something went wrong
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "PMMSAdmin", options );
            System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
        }
    }
}
