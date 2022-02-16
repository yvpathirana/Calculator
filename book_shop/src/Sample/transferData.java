package Sample;

public class transferData {
    private String bTitle;
    private int bQuantity;
    private String bPrice;

    public transferData(String bTitle, int bQuantity, String bPrice) {
        this.bTitle = bTitle;
        this.bQuantity = bQuantity;
        this.bPrice = bPrice;
    }

    public String getbTitle() {
        return bTitle;
    }

    public void setbTitle(String bTitle) {
        this.bTitle = bTitle;
    }

    public int getbQuantity() {
        return bQuantity;
    }

    public void setbQuantity(int bQuantity) {
        this.bQuantity = bQuantity;
    }

    public String getbPrice() {
        return bPrice;
    }

    public void setbPrice(String bPrice) {
        this.bPrice = bPrice;
    }
}
