package bookstorefinal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class BookStore {
    private static String bookFile = "books.txt";
    ObservableList<Books> books = FXCollections.observableArrayList();

    public BookStore(String list) {
        bookFile = list;
    }

    public ObservableList<Books> read() {
        try {
            BufferedReader Reader = new BufferedReader(new java.io.FileReader("books.txt"));

            String line = Reader.readLine();
            while (line != null) {
                String bookData[] = line.split(", ");
                books.add(new Books(bookData[0], Double.parseDouble(bookData[1])));

                line = Reader.readLine();
            }
            Reader.close();

        } catch (Exception e) {
            System.out.println("Error");
        }
        return books;
    }

    public void write(String file, Books newBook) {
        books.add(newBook);
        try {
            FileWriter Writer = new FileWriter(bookFile, true);
            Writer.write(newBook.getBookName() + ", " + String.valueOf(newBook.getBookPrice()) + "\n");
            Writer.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public void delete(String file, Books book) {
        File inFile;
        inFile = new File(file);
        File x = new File("temp.txt");
        String line;

        BufferedReader buffread = null;
        FileWriter Writer = null;
        books.remove(book);
        try {
            buffread = new BufferedReader(new FileReader(inFile));
            Writer = new FileWriter(x, true);
            while ((line = buffread.readLine()) != null) {
                if (line.equals(book.getBookName() + ", " + String.valueOf(book.getBookPrice()))) {
                } else {
                    Writer.write(line + "\n");
                }
            }
            Writer.close();
            buffread.close();
            inFile.delete();
            x.renameTo(inFile);
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}
