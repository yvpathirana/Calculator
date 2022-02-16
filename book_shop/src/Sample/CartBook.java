package Sample;

public class CartBook {
    private int itemNo;
    private String bokTitle;
    private int bokQuantity;
    private Double bokPrice;
    private Double TotalPrice;

    public CartBook(int itemNo, String bokTitle, int bokQuantity, Double bokPrice, Double totalPrice) {
        this.itemNo = itemNo;
        this.bokTitle = bokTitle;
        this.bokQuantity = bokQuantity;
        this.bokPrice = bokPrice;
        TotalPrice = totalPrice;
    }

    public int getItemNo() {
        return itemNo;
    }

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }

    public String getBokTitle() {
        return bokTitle;
    }

    public void setBokTitle(String bokTitle) {
        this.bokTitle = bokTitle;
    }

    public int getBokQuantity() {
        return bokQuantity;
    }

    public void setBokQuantity(int bokQuantity) {
        this.bokQuantity = bokQuantity;
    }

    public Double getBokPrice() {
        return bokPrice;
    }

    public void setBokPrice(Double bokPrice) {
        this.bokPrice = bokPrice;
    }

    public Double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        TotalPrice = totalPrice;
    }
}