package com.atayawa;

import java.io.File;

public class Main
{
    private static final String DEFAULT_FILE_EXTENSION = "java";
    
    private static File sourceFile;
    private static String filenamePrefix;
    private static String filenameSuffix;
    private static int[] filenames;
    
    public static void main(String[] args)
    {
        handleInput(args);
        createFiles();
    }
    
    private static void handleInput(String[] args)
    {
        for(int i = 0; i < args.length; i++)
        {
            switch (commandInterpreter(args[i]))
            {
                case "help":
                    printHelpPrompt();
                    break;
                case "sourceFile":
                    setSourceFile(args[++i]);
                    break;
                case "prefix":
                    setPrefix(args[++i]);
                    break;
                case "suffix":
                    setSuffix(args[++i]);
                    break;
                case "filenames":
                    setFilenames(args[++i]);
                    break;
                case "wrongCommand":
                    printWrongCommandMessage(args[i]);
                    break;
            }
        }
    }
    
    private static void createFiles()
    {
    
    }
    
    private static void setSourceFile(String filePath)
    {
        sourceFile = new File(filePath);
        
        if(!sourceFile.exists())
        {
            printSourceFileCannotBeFound();
            System.exit(1);
        }
        if(!sourceFile.canRead())
        {
            printSourceFileCannotBeRead();
            System.exit(2);
        }
        if(!sourceFile.isFile())
        {
            printSourceFileIsNotAFile();
            System.exit(3);
        }
    }
    
    private static void setPrefix(String pf)
    {
        filenamePrefix = pf;
        if(pf.contains("."))
        {
            printPrefixHasDot();
        }
    }
    
    private static void setSuffix(String sf)
    {
        filenameSuffix = sf;
        if(!sf.contains(".") || sf.indexOf('.') == sf.length())
        {
            printSuffixNoFileExtension();
            boolean addDefaultExtension = true;   // Should be based on user input, need to implement that
            if(addDefaultExtension)
            {
                if(!sf.contains("."))
                    filenameSuffix += '.';
                filenameSuffix += DEFAULT_FILE_EXTENSION;
            }
        }
    }
    
    private static void setFilenames(String fn)
    {
    
    }
    
    private static String commandInterpreter(String cmd)
    {
        switch (cmd)
        {
            case "-h":
            case "-help":
                return "help";
                
            case "-s":
            case "-source":
                return "sourceFile";
                
            case "-pf":
            case "-prefix":
                return "prefix";
                
            case "-sf":
            case "-suffix":
                return "suffix";
                
            case "-fn":
            case "-file-names":
                return "fileNames";
                
            default:
                return "wrongCommand";
                
        }
    }
    
    private static void printHelpPrompt()
    {
        if(isUnix())
        {
            System.out.printf("%sUsage:%s%n", Color.YELLOW.code(), Color.RESET.code());
        }
        else
        {
            System.out.println("Usage:");
        }
    }
    
    private static void printSourceFileCannotBeFound()
    {
        if(isUnix())
            System.out.printf("%sThe specified source file doesn't exist%s%n", Color.CYAN.code(), Color.RESET.code());
        else
            System.out.println("The specified source file doesn't exist");
    }
    
    private static void printSourceFileCannotBeRead()
    {
        if(isUnix())
            System.out.printf("%sThe specified source file cannot be read%s%n", Color.CYAN.code(), Color.RESET.code());
        else
            System.out.println("The specified source file cannot be read");
    }
    
    private static void printSourceFileIsNotAFile()
    {
        if(isUnix())
            System.out.printf("%sThe specified source file is not a file%s%n", Color.CYAN.code(), Color.RESET.code());
        else
            System.out.println("The specified source file is not a file");
    }
    
    private static void printPrefixHasDot()
    {
        if(isUnix())
            System.out.printf("");
        else
            System.out.println("");
    }
    
    private static void printSuffixNoFileExtension()
    {
        if(isUnix())
            System.out.printf("");
        else
            System.out.println("");
    }
    
    private static void printWrongCommandMessage(String cmd)
    {
        if(isUnix())
            System.out.printf("%sIncorrect argument: %s%nPlease check your input%s%n", Color.CYAN.code(), cmd, Color.RESET.code());
        else
            System.out.printf("Incorrect argument: %s%nPlease check your input", cmd);
    }
    
    private static boolean isUnix()
    {
        return File.separatorChar == '/';
    }
    
    private enum Color
    {
        RESET("\033[0m"),
        GREEN("\033[0;32m"),
        YELLOW("\033[0;33m"),
        CYAN("\033[0;36m");
        
        private final String colorCode;
        
        Color(final String s)
        {
            colorCode = s;
        }
        
        private String code()
        {
            return colorCode;
        }
        
    }
}
