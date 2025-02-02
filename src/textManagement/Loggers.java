package textManagement;

import fightord1e.main.FightOrD1e;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Loggers {
     //this function is responsible for showing information to user in terminal or/and saving info to logs.txt
    public final static void logMessage(String message, boolean log, boolean terminal) {
        if (log) {
            try {
                PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("logs.txt", true)), true);
                writer.println(message);
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(FightOrD1e.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (terminal) {
            System.out.println(message);
        }
    }
    
    public final static void readFile(String file, boolean log, boolean terminal) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            String[] dictionary = line.split(";");
            for (String word : dictionary) {
                logMessage(word, log, terminal);
            }
            //line = reader.readLine();
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(FightOrD1e.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public final static void clearScreen() {
        //there should be console cleaing function
        //simmilar to windows cmd 'system("cls");'
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
    }
    public static int choiceValidator(Scanner scanner)
    {
        int choice;
        while(true)
        {
            try {
//              Loggers.logMessage("Choice: ", false, true);
                choice = scanner.nextInt();        
                break;
            } catch (java.util.InputMismatchException e) {
                Loggers.logMessage("Wrong input type. Try again.", false, true);
                scanner.next();
            }
        }
        return choice;
    }
}
