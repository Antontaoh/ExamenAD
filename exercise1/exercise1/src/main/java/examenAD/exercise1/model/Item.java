package examenAD.exercise1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;

@Data
@Document(collection = "items")
public class Item {
    @Id
    @JsonProperty("_id")
    private String id;

    @JsonProperty("id")
    private int itemId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category")
    private String category;

    @JsonProperty("image")
    private String image;

    @JsonProperty("rate")
    private double rate;

    @JsonProperty("count")
    private int count;

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public int getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public double getRate() {
        return rate;
    }

    public int getCount() {
        return count;
    }
}
