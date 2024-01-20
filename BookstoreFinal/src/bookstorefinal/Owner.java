/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstorefinal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Owner {

    private static String customersFileName = "customers.txt";
    ObservableList<Customer> customers = FXCollections.observableArrayList();



    public void modifyCustomers(String oldString, String newString) {
        File fileToBeModified = new File("customers.txt");

        String oldContent = "";

        BufferedReader reader = null;

        FileWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(fileToBeModified));

            //Reading all the lines of input text file into oldContent
            String line = reader.readLine();

            while (line != null) {
                oldContent = oldContent + line + System.lineSeparator();

                line = reader.readLine();
            }

            //Replacing oldString with newString in the oldContent
            String newContent = oldContent.replaceAll(oldString, newString);

            //Rewriting the input text file with newContent
            writer = new FileWriter(fileToBeModified);

            writer.write(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        } 
            try {
                //Closing the resources

                reader.close();

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        
    }

    public Owner(String list) {
        customersFileName = list;
    }

    public void write(String file, Customer newCustomer) {
        customers.add(newCustomer);
        try {
            FileWriter fw = new FileWriter(customersFileName, true);
            fw.write(newCustomer.getUsername() + ", " + newCustomer.getPassword() + ", " + newCustomer.getPoints() + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public ObservableList<Customer> getUser() {
        String username, password;
        int points;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("customers.txt"));

            String line = reader.readLine();
            while (line != null) {
                String info[] = line.split(", ");
                username = info[0];
                password = info[1];
                points = Integer.parseInt(info[2]);

                customers.add(new Customer(username, password, points));
                line = reader.readLine();
            }
            reader.close();

        } catch (Exception e) {
            System.out.println("Invalid");
        }

        return customers;
    }

    public void delete(String file, Customer customer) {
        File inFile = new File(file);
        File temp = new File("temp.txt");
        String line;

        BufferedReader br = null;
        FileWriter fw = null;
        FileReader fr = null;
        customers.remove(customer);
        try {
            br = new BufferedReader(new FileReader(inFile));
            fw = new FileWriter(temp, true);
            while ((line = br.readLine()) != null) {
                if (line.equals(customer.getUsername() + ", " + customer.getPassword() + ", " + customer.getPoints())) {

                } else {
                    fw.write(line + "\n");

                }
            }
            fw.close();
            br.close();

            inFile.delete();
            temp.renameTo(inFile);
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}
