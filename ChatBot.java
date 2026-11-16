package registrationloginapp;

import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Student
 */
public class ChatBot {

    //create a one dimensional array 
    //Sent Message array - contains all the messages sent
    private static Message[] messages = new Message[100];

    private static int messageCount = 0; 
    
    //Disregarded Messages array - contains all the messages that were disregarded
    private static Message[] disregardedMessages = new Message[100];

    private static int disregardedCount = 0;
    
    //Message ID array - contains all the message IDs
    private static String[] messageIds = new String[100];
    
    //Message Hash array - contains all the message hashes
    private static String[] messageHashes = new String[100];
    
    private static int idHashCount = 0;
    
    //Stored Messages array - read from the JSON file
    //same position in each array = same message
    private static String[] storedIds = new String[100];
    
    private static String[] storedHashes = new String[100];
    
    private static String[] storedRecipients = new String[100];
    
    private static String[] storedContents = new String[100];
    
    private static int storedCount = 0;
    
    //name of the file the stored messages are saved in
    private static final String FILE_NAME = "storedMessages.json";
    
    /**
     * @param args the command line arguments
     */
    public static void main (String[] args) {
        
        //creating  scanner to recieve user input
        Scanner scanner = new Scanner(System.in);
        
        //read the JSON file into the Stored Messages array
        loadStoredMessages();

        System.out.println("===== WELCOME TO QUICKCHAT =====");

        boolean loop = true;

        while (loop) {
            
            //Display menu to user
            System.out.println("MENU:");

            System.out.println("1) Send message");

            System.out.println("2) Show recently sent messages");

            System.out.println("3) Quit");
            
            System.out.println("4) Stored Messages");
            
            //ask user to choose from menu
            System.out.print("Enter your option: ");

            int option = scanner.nextInt();

            scanner.nextLine();
            
            //Display what user chooses from menu
            if (option == 1) {

                sendMessage(scanner);

            } else if (option == 2) {

                showMessages();

            } else if (option == 3) {

                loop = false;

                System.out.println("Goodbye!");

            } else if (option == 4) {

                storedMessagesMenu(scanner);

            } else {

                System.out.println("Invalid option. Please try again.");

            }

        }

    }
    
    //create a method to send messages
    public static void sendMessage(Scanner scanner) {
        
        //ask user how many messages do they want to send 
        System.out.print("How many messages do you want to send? ");

        int numMessages = scanner.nextInt();

        scanner.nextLine();
        
        // Loop for user to send multiple messages
        for (int count = 0; count < numMessages; count++) {

            if (messageCount >= messages.length) {

                System.out.println("Message storage is full.");

            } else {
                 
                //ask user to enter number using internationsl code
                System.out.print("Enter recipient's cell number:(Must start with +27) ");

                String recipient = scanner.nextLine();
                
                // Regular expression for a South African cellphone number
                String phoneRegex = "^\\+27[0-9]{9}$";
                
                if(recipient.matches(phoneRegex)){
                 
                }
                //must display a error message
                //if user does not use international code
                else{
                    System.out.println("Cell phone number is incorrectly formatted or does not contain international code.");
                    //skip this message and go to the next one
                    continue;
                }
                
                //ask user to enter messages not longer than 250
                System.out.print("Enter your message (maximum 250 characters): ");

                String content = scanner.nextLine();
                
                //Display if user does not enter any charaters
                if (content.length() == 0) {

                    System.out.println("Message cannot be empty.");
                    
                //Display if user enters max 250 characters
                } else if (content.length() > 250) {

                    System.out.println(

                        "Message must not exceed 250 characters."

                    );

                } else {
                    
                    //create a new message key with number and messages
                    Message message = new Message(recipient, content);

                    //ask user what to do with message
                    System.out.println("\nWhat would you like to do with this message?");
                    System.out.println("1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message to send later");
                    System.out.print("Enter choice: ");
                    int action = scanner.nextInt();
                    scanner.nextLine();

                    if(action == 1){
                        //store message in Sent Message array
                        messages[messageCount] = message;
                        messageCount++;
                        
                        //store the ID and hash in their arrays
                        addToIdAndHashArrays(message);
                        
                        //Display if message is sent
                        //Display message Hash
                        //Display recipient
                        //Display message content
                        System.out.println("Message successfully sent");

                        System.out.println("Message ID: " + message.getMessageId());

                        System.out.println("Message Hash: " + message.getMessageHash());

                        System.out.println("Recipient: " + message.getRecipient());

                        System.out.println("Message: " + message.getContent());
                    }
                    else if(action == 2){
                        //ask user to press 0 to delete
                        System.out.print("Press 0 to delete the message: ");
                        int del = scanner.nextInt();
                        scanner.nextLine();
                        if(del == 0){
                            //store message in Disregarded Messages array
                            disregardedMessages[disregardedCount] = message;
                            disregardedCount++;
                            
                            //store the ID and hash in their arrays
                            addToIdAndHashArrays(message);
                            
                            //must display Press 0 to delete the message
                            System.out.println("Press 0 to delete the message");
                            System.out.println("Message disregarded");
                        }
                    }
                    else if(action == 3){
                        //store message in JSON file and Stored Messages array
                        storeMessage(message);
                        //must display Message successfully stored
                        System.out.println("Message successfully stored");
                    }

                }

            }

        }
        //Display number of messages sent
        System.out.println("Total messages sent: " + messageCount);

    }

    public static void showMessages() {
        
        //Display if theres no messages that has been sent
        if (messageCount == 0) {

            System.out.println("No messages have been sent yet.");

        } else {
            
            //Display if user wants to see messages that were sent
            System.out.println("===== RECENTLY SENT MESSAGES =====");

            for (int i = 0; i < messageCount; i++) {

                System.out.println("Message " + (i + 1));

                System.out.println("Message ID: "

                        + messages[i].getMessageId());

                System.out.println("Message Hash: "

                        + messages[i].getMessageHash());

                System.out.println("Recipient: "

                        + messages[i].getRecipient());

                System.out.println("Content: "

                        + messages[i].getContent());

            }

        }

    }

    //method to add the ID and hash of a message to their arrays
    public static void addToIdAndHashArrays(Message message){
        if (idHashCount < messageIds.length) {
            messageIds[idHashCount] = message.getMessageId();
            messageHashes[idHashCount] = message.getMessageHash();
            idHashCount++;
        }
    }

    //method to store message in JSON file AND in the Stored Messages array
    public static void storeMessage(Message message){
        
        //save to the JSON file
        storeMessageToJson(message);
        
        //store the ID and hash in their arrays
        addToIdAndHashArrays(message);
        
        //add to the Stored Messages array
        if (storedCount < storedIds.length) {
            storedIds[storedCount] = message.getMessageId();
            storedHashes[storedCount] = message.getMessageHash();
            storedRecipients[storedCount] = message.getRecipient();
            storedContents[storedCount] = message.getContent();
            storedCount++;
        }
    }

    //method to store message in JSON file
    public static void storeMessageToJson(Message message){
        try {
            //create FileWriter to write to JSON file - fixed to avoid quote errors
            java.io.FileWriter file = new java.io.FileWriter(FILE_NAME, true);
            file.write("MessageID: " + message.getMessageId() + "\n");
            file.write("Hash: " + message.getMessageHash() + "\n");
            file.write("To: " + message.getRecipient() + "\n");
            file.write("Text: " + message.getContent() + "\n");
            file.write("---\n");
            file.close();
            System.out.println("Saved to storedMessages.json");
        } catch (Exception e){
            System.out.println("Error storing JSON: " + e.getMessage());
        }
    }

    //method to read the JSON file into the Stored Messages array
    public static void loadStoredMessages(){
        
        java.io.File f = new java.io.File(FILE_NAME);
        
        //nothing to read if the file does not exist yet
        if (!f.exists()) {
            return;
        }
        
        try {
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(f));
            String line;
            String id = "";
            String hash = "";
            String to = "";
            String text = "";
            
            while ((line = reader.readLine()) != null) {
                
                if (line.startsWith("MessageID: ")) {
                    id = line.substring(11);
                } else if (line.startsWith("Hash: ")) {
                    hash = line.substring(6);
                } else if (line.startsWith("To: ")) {
                    to = line.substring(4);
                } else if (line.startsWith("Text: ")) {
                    text = line.substring(6);
                } else if (line.equals("---")) {
                    //end of one message so add it to the arrays
                    if (storedCount < storedIds.length) {
                        storedIds[storedCount] = id;
                        storedHashes[storedCount] = hash;
                        storedRecipients[storedCount] = to;
                        storedContents[storedCount] = text;
                        storedCount++;
                    }
                }
            }
            reader.close();
            
        } catch (Exception e){
            System.out.println("Error reading stored messages: " + e.getMessage());
        }
        
    }

    //method to rewrite the JSON file after a message is deleted
    public static void rewriteStoredFile(){
        try {
            java.io.FileWriter file = new java.io.FileWriter(FILE_NAME, false);
            for (int i = 0; i < storedCount; i++) {
                file.write("MessageID: " + storedIds[i] + "\n");
                file.write("Hash: " + storedHashes[i] + "\n");
                file.write("To: " + storedRecipients[i] + "\n");
                file.write("Text: " + storedContents[i] + "\n");
                file.write("---\n");
            }
            file.close();
        } catch (Exception e){
            System.out.println("Error updating file: " + e.getMessage());
        }
    }

    //method to add a message using its flag (Sent, Disregard or Stored)
    public static void addMessage(String recipient, String content, String flag){
        
        Message message = new Message(recipient, content);
        
        if (flag.equals("Sent")) {
            messages[messageCount] = message;
            messageCount++;
            addToIdAndHashArrays(message);
        } else if (flag.equals("Disregard")) {
            disregardedMessages[disregardedCount] = message;
            disregardedCount++;
            addToIdAndHashArrays(message);
        } else if (flag.equals("Stored")) {
            storeMessage(message);
        }
        
    }

    //method to populate the arrays with the test data
    public static void loadTestData(){
        
        //Test Data Message 1
        addMessage("+27834557896", "Did you get the cake?", "Sent");
        
        //Test Data Message 2
        addMessage("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        
        //Test Data Message 3
        addMessage("+27834484567", "Yohoooo, I am at your gate.", "Disregard");
        
        //Test Data Message 4
        addMessage("0838884567", "It is dinner time !", "Sent");
        
        //Test Data Message 5
        addMessage("+27838884567", "Ok, I am leaving without you.", "Stored");
        
        System.out.println("Test data loaded.");
        
    }

    //methods used by the unit tests to read the arrays
    public static Message[] getSentMessages(){
        return messages;
    }

    public static int getSentCount(){
        return messageCount;
    }

    public static Message[] getDisregardedMessages(){
        return disregardedMessages;
    }

    public static int getDisregardedCount(){
        return disregardedCount;
    }

    public static String[] getStoredIds(){
        return storedIds;
    }

    public static String[] getStoredHashes(){
        return storedHashes;
    }

    public static int getStoredCount(){
        return storedCount;
    }

    //method used by the unit tests to clear the arrays so each test starts fresh
    public static void resetData(){
        messageCount = 0;
        disregardedCount = 0;
        idHashCount = 0;
        storedCount = 0;
        new java.io.File(FILE_NAME).delete();
        Message.resetMessageCount();
    }

    //Menu option 4 - Stored Messages
    public static void storedMessagesMenu(Scanner scanner) {
        
        boolean back = false;
        
        while (!back) {
            
            System.out.println("\n===== STORED MESSAGES =====");
            System.out.println("a) Display the sender and recipient of all stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for a message ID");
            System.out.println("d) Search for all messages stored for a recipient");
            System.out.println("e) Delete a message using the message hash");
            System.out.println("f) Display a report of all stored messages");
            System.out.println("g) Back to main menu");
            System.out.print("Enter your choice: ");
            
            String choice = scanner.nextLine();
            
            if (choice.equals("a")) {
                
                displaySenderAndRecipient();
                
            } else if (choice.equals("b")) {
                
                System.out.println(getLongestMessage());
                
            } else if (choice.equals("c")) {
                
                System.out.print("Enter message ID: ");
                String id = scanner.nextLine();
                System.out.println(searchByMessageId(id));
                
            } else if (choice.equals("d")) {
                
                System.out.print("Enter recipient: ");
                String recipient = scanner.nextLine();
                System.out.println(searchByRecipient(recipient));
                
            } else if (choice.equals("e")) {
                
                System.out.print("Enter message hash: ");
                String hash = scanner.nextLine();
                System.out.println(deleteByHash(hash));
                
            } else if (choice.equals("f")) {
                
                System.out.println(getReport());
                
            } else if (choice.equals("g")) {
                
                back = true;
                
            } else {
                
                System.out.println("Invalid option. Please try again.");
                
            }
            
        }
        
    }

    //a) display the sender and recipient of all stored messages
    public static void displaySenderAndRecipient() {
        
        if (storedCount == 0) {
            System.out.println("No stored messages.");
            return;
        }
        
        for (int i = 0; i < storedCount; i++) {
            System.out.println("Sender: You");
            System.out.println("Recipient: " + storedRecipients[i]);
            System.out.println("---");
        }
        
    }

    //b) find the longest stored message
    public static String getLongestMessage() {
        
        String longest = "";
        
        for (int i = 0; i < storedCount; i++) {
            if (storedContents[i].length() > longest.length()) {
                longest = storedContents[i];
            }
        }
        
        return longest;
        
    }

    //c) search for a message ID and return the recipient and message
    public static String searchByMessageId(String id) {
        
        //search the sent messages
        for (int i = 0; i < messageCount; i++) {
            if (messages[i].getMessageId().equals(id)) {
                return "Recipient: " + messages[i].getRecipient()
                        + ", Message: " + messages[i].getContent();
            }
        }
        
        //search the disregarded messages
        for (int i = 0; i < disregardedCount; i++) {
            if (disregardedMessages[i].getMessageId().equals(id)) {
                return "Recipient: " + disregardedMessages[i].getRecipient()
                        + ", Message: " + disregardedMessages[i].getContent();
            }
        }
        
        //search the stored messages
        for (int i = 0; i < storedCount; i++) {
            if (storedIds[i].equals(id)) {
                return "Recipient: " + storedRecipients[i]
                        + ", Message: " + storedContents[i];
            }
        }
        
        return "Message ID not found.";
        
    }

    //d) search for all the messages sent or stored for a recipient
    public static String searchByRecipient(String recipient) {
        
        String result = "";
        
        //search the sent messages
        for (int i = 0; i < messageCount; i++) {
            if (messages[i].getRecipient().equals(recipient)) {
                result = result + messages[i].getContent() + "\n";
            }
        }
        
        //search the stored messages
        for (int i = 0; i < storedCount; i++) {
            if (storedRecipients[i].equals(recipient)) {
                result = result + storedContents[i] + "\n";
            }
        }
        
        if (result.equals("")) {
            return "No messages found for that recipient.";
        }
        
        return result;
        
    }

    //e) delete a stored message using the message hash
    public static String deleteByHash(String hash) {
        
        for (int i = 0; i < storedCount; i++) {
            if (storedHashes[i].equals(hash)) {
                
                String deleted = storedContents[i];
                
                //move the rest of the messages up one place
                for (int j = i; j < storedCount - 1; j++) {
                    storedIds[j] = storedIds[j + 1];
                    storedHashes[j] = storedHashes[j + 1];
                    storedRecipients[j] = storedRecipients[j + 1];
                    storedContents[j] = storedContents[j + 1];
                }
                
                storedCount--;
                
                //update the JSON file
                rewriteStoredFile();
                
                return "Message \"" + deleted + "\" successfully deleted.";
            }
        }
        
        return "Message hash not found.";
        
    }

    //f) report that lists the full details of all the stored messages
    public static String getReport() {
        
        if (storedCount == 0) {
            return "No stored messages.";
        }
        
        String report = "===== STORED MESSAGES REPORT =====\n";
        
        for (int i = 0; i < storedCount; i++) {
            report = report + "Message " + (i + 1) + "\n"
                    + "Message ID: " + storedIds[i] + "\n"
                    + "Message Hash: " + storedHashes[i] + "\n"
                    + "Recipient: " + storedRecipients[i] + "\n"
                    + "Message: " + storedContents[i] + "\n\n";
        }
        
        return report;
        
    }

}