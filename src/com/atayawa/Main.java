package com.atayawa;

import java.io.File;
import java.util.LinkedList;

public class Main
{
    private static final String DEFAULT_FILE_EXTENSION = "java";
    
    private static File sourceFile;
    private static String filenamePrefix;
    private static String filenameSuffix;
    private static String outputDirectory;  // TODO Find if "File" class can be used to store the directory's path
    private static LinkedList<String> filenames;
    
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
                case "outputDirectory":
                    setOutputDirectory(args[++i]);
                    break;
                case "wrongCommand":
                    printWrongCommandMessage(args[i]);
                    break;
            }
        }
    }
    
    private static void createFiles()   // TODO
    {
        for(var fileName : filenames)
        {

        }
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
            printPrefixHasDots(pf.indexOf(".") != pf.lastIndexOf("."));
            boolean keepDotsInSuffix = System.console().readLine().equalsIgnoreCase("y");
            if(!keepDotsInSuffix)
            {
                printDotsInPrefixRemoved(pf.indexOf(".") != pf.lastIndexOf("."));
                filenamePrefix = pf.replace(".", "");
            }
        }
    }
    
    private static void setSuffix(String sf)
    {
        filenameSuffix = sf;
        if(!sf.contains(".") || !sf.endsWith("."))  // TODO Need to fix this wrong logix of finding when there is no file extension
        {
            printSuffixNoFileExtension();
            boolean addDefaultExtension = System.console().readLine().equalsIgnoreCase("y");
            if(addDefaultExtension)
            {
                if(!sf.endsWith("."))
                    filenameSuffix += '.';
                filenameSuffix += DEFAULT_FILE_EXTENSION;
                printDefaultFileExtensionAdded();
            }
        }
    }
    
    private static void setFilenames(String fn)
    {
        String[] parsedInput = fn.split(",");
        for(String i : parsedInput)
        {
            if(i.contains("-"))
            {
                try
                {
                    int firstIndex = Integer.parseInt(i.substring(0, i.indexOf("-")));
                    int lastIndex = Integer.parseInt(i.substring(i.lastIndexOf("-") + 1));
                    for(int j = firstIndex; j <= lastIndex; j++)
                    {
                        filenames.add(Integer.toString(j));
                    }
                }
                catch (NumberFormatException e) // TODO
                {
                }
                catch (Exception e) // TODO
                {
                }
            }
            else
            {
                filenames.add(i);
            }
        }
    }

    private static void setOutputDirectory(String outDir)   // TODO
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
    
    private static void printHelpPrompt()   // TODO
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
    
    private static void printPrefixHasDots(boolean isMany)
    {
        if(isUnix())
        {
            System.out.printf("%sThe specified file prefix contains %s%n", Color.CYAN.code(), isMany ? "dots" : "a dot");
            System.out.printf("Are you sure you want to keep %s? [Y/n]%s ", isMany ? "them" : "it", Color.RESET.code());
        }
        else
        {
            System.out.printf("The specified file prefix contains %s%n", isMany ? "dots" : "a dot");
            System.out.printf("Are you sure you want to keep %s? [Y/n] %n", isMany ? "them" : "it");
        }

    }

    private static void printDotsInPrefixRemoved(boolean isMany)
    {
        if(isUnix())
            System.out.printf("%sThe %s been successfully removed from the prefix%s%n", Color.GREEN.code(), isMany ? "dots have" : "dot has", Color.RESET.code());
        else
            System.out.printf("The %s been successfully removed from the prefix%n", isMany ? "dots have" : "dot has");
    }
    
    private static void printSuffixNoFileExtension()
    {
        if(isUnix())
            System.out.printf("%sThe specified file suffix does not contain a file extension%n" +
                            "Do you want to add a default file extension(.java)? [Y/n]%s ", Color.CYAN.code(), Color.RESET.code());
        else
        {
            System.out.println("The specified file suffix does not contain a file extension");
            System.out.print("Do you want to add a default file extension? (.java) [Y/n] ");
        }
    }

    private static void printDefaultFileExtensionAdded()
    {
        if(isUnix())
            System.out.printf("%sDefault file extension (.java) successfully added to the files%s%n", Color.GREEN.code(), Color.RESET.code());
        else
            System.out.println("Default file extension (.java) successfully added to the files");
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
