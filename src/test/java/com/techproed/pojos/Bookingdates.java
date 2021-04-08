package com.techproed.pojos;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "checkin",
        "checkout"
})

/**
 * No args constructor for use in serialization
 *
 */
public class Bookingdates {

    @JsonProperty("checkin")
    private String checkin;
    @JsonProperty("checkout")
    private String checkout;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public Bookingdates()
    {

    }

    /**
     *
     * @param checkin
     * @param checkout
     */
    public Bookingdates(String checkin, String checkout) {
        //super();//Object Class
        this.checkin = checkin;
        this.checkout = checkout;
    }

    @JsonProperty("checkin")
    public String getCheckin() {
        return checkin;
    }

    @JsonProperty("checkin")
    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    @JsonProperty("checkout")
    public String getCheckout() {
        return checkout;
    }

    @JsonProperty("checkout")
    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("checkin", checkin).
                append("checkout", checkout).
                //append("additionalProperties", additionalProperties).
                toString();
    }

    /*
      <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-annotations</artifactId>
            <version>2.11.2</version>
        </dependency>

        I added this dependecy for the pojo class
     */

}