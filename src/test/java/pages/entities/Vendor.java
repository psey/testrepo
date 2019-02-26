package pages.entities;

import java.util.Objects;

public class Vendor {
    private String brandCheckbox;
    private String brandName;

    public Vendor(String brandCheckboxIsChecked, String brandName) {
        this.brandCheckbox = brandCheckboxIsChecked;
        this.brandName = brandName;
    }

    public Vendor() {
    }

    public void setBrandCheckbox(String brandCheckboxIsChecked) {
        this.brandCheckbox = brandCheckboxIsChecked;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }

    public boolean getBrandCheckbox() {
        return Boolean.parseBoolean(brandCheckbox);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vendor)) return false;
        Vendor product = (Vendor) o;

        return Objects.equals(brandName, product.brandName) &&
                Objects.equals(brandCheckbox, product.brandCheckbox);
    }

}

