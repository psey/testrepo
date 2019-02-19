package pages.entities;

import java.util.Objects;

public class Vendor {
    private String brandCheckboxIsChecked;
    private String brandName;

    public Vendor(String brandCheckboxIsChecked, String brandName) {
        this.brandCheckboxIsChecked = brandCheckboxIsChecked;
        this.brandName = brandName;
    }


    public void setBrandCheckboxIsChecked(String brandCheckboxIsChecked) {
        this.brandCheckboxIsChecked = brandCheckboxIsChecked;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }

    public boolean isBrandCheckboxIsChecked() {
        return Boolean.parseBoolean(brandCheckboxIsChecked);
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vendor)) return false;
        Vendor product = (Vendor) o;


        return Objects.equals(brandName, product.brandName) &&
                Objects.equals(brandCheckboxIsChecked, product.brandCheckboxIsChecked);
    }


}

