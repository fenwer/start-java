package rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

/**
 * 18.08.2015.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

    @Getter (AccessLevel.PUBLIC) private String id;
    private String email;
    private String description;
    private String default_card_id;
    private String name;

}
