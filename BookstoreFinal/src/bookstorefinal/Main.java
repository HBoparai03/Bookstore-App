package bookstorefinal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    private Text titleLabel;
    private Label usernameLabel, passwordLabel;
    private TextField usernameTextField;
    private PasswordField passwordTextField;
    private Button loginButton, cancelButton;
    
    
    Customer currentCustomer;
    TextField addTitle, addPrice, addUsername, addPassword, addPoints;
    TableView<Books> bookTable;
    TableView<Customer> customerTable;
    ObservableList<Books> books = FXCollections.observableArrayList();
    ObservableList<Customer> selectCustomers = FXCollections.observableArrayList();
    ObservableList<Books> selectBooks = FXCollections.observableArrayList();
    ObservableList<Customer> customers = FXCollections.observableArrayList();
    

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bookstore Login");

        // create components
        titleLabel = new Text("Welcome to the BookStore App");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        usernameLabel = new Label("Username:");
        passwordLabel = new Label("Password:");

        usernameTextField = new TextField();
        passwordTextField = new PasswordField();

        loginButton = new Button("Login");
        cancelButton = new Button("Cancel");

        // add components to grid pane
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(titleLabel, 0, 0, 2, 1);
        grid.add(usernameLabel, 0, 1);
        grid.add(usernameTextField, 1, 1);
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordTextField, 1, 2);
        grid.add(loginButton, 0, 3);
        grid.add(cancelButton, 1, 3);

        // set constraints for title label
        GridPane.setHalignment(titleLabel, HPos.CENTER);
        GridPane.setColumnSpan(titleLabel, 2);

        // add action handlers to buttons
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
public void handle(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();

                // check username and password
                if (username.equals("admin") && password.equals("admin")) {
                    ownerScreen(primaryStage);
                } else if (Customer.verify(username, password)) {
                    //System.out.println("LOGIN VERIFY WORKED");

                    customerScreen(primaryStage, username, password);
                } else {
                     //login failed
                    showAlert("Invalid username or password.");
                }
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.close();
            }
        });
        grid.setStyle("-fx-background-color: #C4A484;");
        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void ownerScreen(Stage primaryStage) {

        Button books = new Button("Books");
        books.setMinSize(100, 30);
        Button customers = new Button("Customers");
        customers.setMinSize(100, 30);
        Button logout = new Button("Logout");
        logout.setMinSize(100, 30);

        GridPane managerGrid = new GridPane();

        managerGrid.setAlignment(Pos.CENTER);
        managerGrid.setHgap(10);
        managerGrid.setVgap(10);
        managerGrid.setPadding(new Insets(25, 25, 25, 25));

        managerGrid.add(books, 0, 1);
        managerGrid.add(customers, 0, 2);
        managerGrid.add(logout, 0, 3);

        books.setOnAction((ActionEvent e) -> {
            manageBooks(primaryStage);
        });

        customers.setOnAction((ActionEvent e) -> {
            manageCustomers(primaryStage);
        });

        logout.setOnAction((ActionEvent e) -> {
            start(primaryStage);
        });
        managerGrid.setStyle("-fx-background-color: #C4A484;");
        Scene scene = new Scene(managerGrid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void customerScreen(Stage primaryStage, String username, String password) {
        BookStore book = new BookStore("books.txt");
        Owner customer = new Owner("customers.txt");
        customers = customer.getUser();
 

        for (int i = 0; i < customers.size(); i++) {

            if (customers.get(i).getUsername().equals(username)) {
                currentCustomer = customers.get(i);
            }
        }

        Text welcome = new Text("Welcome " + username + ". You have " + currentCustomer.getPoints() + " point(s)." + " Your status is " + currentCustomer.getStatus() + ".");

        TableView<Books> bookTable;

        //Title Column
        TableColumn<Books, String> nameColumn = new TableColumn<>("Book Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));

        //Price Column
        TableColumn<Books, String> priceColumn = new TableColumn<>("Book Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));

        //Select Column
        TableColumn<Books, Boolean> selectColumn = new TableColumn<>("Select");
        selectColumn.setMinWidth(100);
        selectColumn.setCellValueFactory(new PropertyValueFactory<>("select"));

        Button buy = new Button("Buy");
        buy.setMinSize(100, 30);
        Button redNBuy = new Button("Redeem & Buy");
        redNBuy.setMinSize(100, 30);
        Button logout = new Button("Logout");
        logout.setMinSize(100, 30);

        GridPane customerGrid = new GridPane();

        customerGrid.setAlignment(Pos.BOTTOM_CENTER);
        customerGrid.setHgap(10);
        customerGrid.setVgap(10);
        customerGrid.setPadding(new Insets(25, 25, 25, 25));

        bookTable = new TableView<>();
        bookTable.setItems(book.read());
        bookTable.getColumns().addAll(nameColumn, priceColumn, selectColumn);

        buy.setOnAction((ActionEvent e) -> {

            ObservableList<Books> Books = bookTable.getItems();
            selectBooks = currentCustomer.selectedBooks(Books);
            int currentPoints = currentCustomer.getPoints();

            customerCostScreen(primaryStage, currentCustomer.payMoney(selectBooks), currentCustomer.getPoints(), currentCustomer.getStatus());
            customer.modifyCustomers(currentCustomer.getUsername() + ", " + currentCustomer.getPassword() + ", " + currentPoints, currentCustomer.getUsername() + ", " + currentCustomer.getPassword() + ", " + currentCustomer.getPoints());
            int i;
            for (i = 0; i < selectBooks.size(); i++) {
                book.delete("books.txt", selectBooks.get(i));

            }
        });

        redNBuy.setOnAction((ActionEvent e) -> {
            ObservableList<Books> Books = bookTable.getItems();

            selectBooks = currentCustomer.selectedBooks(Books);
            int currentPoints = currentCustomer.getPoints();
            customerCostScreen(primaryStage, currentCustomer.payPoints(selectBooks), currentCustomer.getPoints(), currentCustomer.getStatus());
            customer.modifyCustomers(currentCustomer.getUsername() + ", " + currentCustomer.getPassword() + ", " + currentPoints, currentCustomer.getUsername() + ", " + currentCustomer.getPassword() + ", " + currentCustomer.getPoints());

            int i;
            for (i = 0; i < selectBooks.size(); i++) {
                book.delete("books.txt", selectBooks.get(i));

            }
        });

        logout.setOnAction((ActionEvent e) -> {
            start(primaryStage);
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(welcome, bookTable, buy, redNBuy, logout);
        vBox.setPadding(new Insets(35, 35, 35, 35));
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-background-color: #C4A484;");
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void customerCostScreen(Stage primaryStage, double TC, int points, String status) {

        Text transactionCost = new Text("Transaction cost: $ " + String.format("%.2f", TC));
        transactionCost.setFont(Font.font("Calibiri", FontWeight.NORMAL, 15));
        Text pointStatus = new Text("Points: " + points + ", Status: " + status);
        pointStatus.setFont(Font.font("Calibiri", FontWeight.NORMAL, 15));

        Button logout = new Button("Logout");

        GridPane customerGrid = new GridPane();

        customerGrid.setAlignment(Pos.BOTTOM_CENTER);
        customerGrid.setHgap(10);
        customerGrid.setVgap(10);
        customerGrid.setPadding(new Insets(25, 25, 25, 25));

        logout.setOnAction((ActionEvent e) -> {
            start(primaryStage);
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(transactionCost, pointStatus, logout);
        vBox.setPadding(new Insets(35, 35, 35, 35));
        vBox.setSpacing(15);
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-background-color: #C4A484;");
        Scene scene1 = new Scene(vBox, 300, 200);
        primaryStage.setScene(scene1);
        primaryStage.show();

    }

    public void manageBooks(Stage primaryStage) {
        BookStore book = new BookStore("books.txt");
        //Title input
        addTitle = new TextField();
        addTitle.setPromptText("Title");
        addTitle.setMinWidth(100);

        //Price Input
        addPrice = new TextField();
        addPrice.setPromptText("Price");
        addPrice.setMinWidth(80);

        //Title Column
        TableColumn<Books, String> nameColumn = new TableColumn<>("Book Title");
        nameColumn.setMinWidth(400);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));

        //Price Column
        TableColumn<Books, String> priceColumn = new TableColumn<>("Book Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));

        bookTable = new TableView<>();
        bookTable.setItems(book.read());
        bookTable.getColumns().addAll(nameColumn, priceColumn);

        Button delete = new Button("Delete");
        Button back = new Button("Back");
        Button add = new Button("Add");

        back.setOnAction((ActionEvent e) -> {
            ownerScreen(primaryStage);
        });

        add.setOnAction((ActionEvent e) -> {
            book.write("books.txt", new Books(addTitle.getText(), Double.parseDouble(addPrice.getText())));
            addTitle.clear();
            addPrice.clear();
        });

        delete.setOnAction((ActionEvent e) -> {
            ObservableList<Books> Books = bookTable.getItems();
            selectBooks = bookTable.getSelectionModel().getSelectedItems();
            book.delete("books.txt", selectBooks.get(0));

        });

        HBox hBox = new HBox();

        hBox.setSpacing(10);
        hBox.getChildren().addAll(addTitle, addPrice, add);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(bookTable, hBox, delete, back);
        vBox.setPadding(new Insets(35, 35, 35, 35));
        vBox.setSpacing(10);
        vBox.setStyle("-fx-background-color: #C4A484;");
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void manageCustomers(Stage primaryStage) {
         Owner customer = new Owner("customers.txt");

        //Username input
        TextField addUsername = new TextField();
        addUsername.setPromptText("Username");
        addUsername.setMinWidth(100);

        //Password Input
        TextField addPassword = new TextField();
        addPassword.setPromptText("Password");
        addPassword.setMinWidth(80);

        //Username Column
        TableColumn<Customer, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setMinWidth(200);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        //Password Column
        TableColumn<Customer, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setMinWidth(200);
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        //Points Column
        TableColumn<Customer, String> pointsColumn = new TableColumn<>("Points");
        pointsColumn.setMinWidth(100);
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        customerTable = new TableView<>();
        customerTable.setItems(customer.getUser());
        customerTable.getColumns().addAll(usernameColumn, passwordColumn, pointsColumn);

        Button delete = new Button("Delete");
        Button back = new Button("Back");
        Button add = new Button("Add");

        back.setOnAction((ActionEvent e) -> {
            ownerScreen(primaryStage);
        });

        add.setOnAction((ActionEvent e) -> {

            String usernameinput = addUsername.getText();

            try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {
                String line = br.readLine();
                boolean usernameExists = false;
                while (line != null) {
                    String[] info2 = line.split(",");

                    if (info2[0].equals(usernameinput)) {
                        // user already exists
                        usernameExists = true;
                        break;
                    }
                    line = br.readLine();
                }
                br.close();

                if (usernameExists) {
                    System.out.println("User with that name already exists.");
                    showAlert("User with that name already exists.");
                } else {
                    customer.write("customers.txt", new Customer(addUsername.getText(), addPassword.getText(), 0));
                    addUsername.clear();
                    addPassword.clear();
                    addPoints.clear();
                }

            } catch (IOException o) {
                o.printStackTrace();
            }
        });


        delete.setOnAction((ActionEvent e) -> {
            customers = customerTable.getItems();
            selectCustomers = customerTable.getSelectionModel().getSelectedItems();
            customer.delete("customers.txt", selectCustomers.get(0));
        });

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(addUsername, addPassword, add);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(customerTable, hBox, delete, back);
        vBox.setPadding(new Insets(35, 35, 35, 35));
        vBox.setSpacing(10);
        vBox.setStyle("-fx-background-color: #C4A484;");
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bookstore Login");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}