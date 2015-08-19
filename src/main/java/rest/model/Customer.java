package rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 18.08.2015.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefault_card_id() {
        return default_card_id;
    }

    public void setDefault_card_id(String default_card_id) {
        this.default_card_id = default_card_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String id;
    private String email;
    private String description;
    private String default_card_id;
    private String name;

}
