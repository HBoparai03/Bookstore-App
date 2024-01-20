package bookstorefinal;

/**
 *

 */
import javafx.scene.control.CheckBox;

public class Books {
    private String Title;
    private double Price;
    private CheckBox select;

    public Books(String bookName, double bookPrice) {
        this.Title = bookName;
        this.Price = bookPrice;

        select = new CheckBox();
    }
    public double getBookPrice() {
        return Price;
    }

    public void setBookPrice(double bookPrice) {
        this.Price = bookPrice;
    }
    public String getBookName() {
        return Title;
    }

    public void setBookName(String bookName) {
        this.Title = bookName;
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }
}
