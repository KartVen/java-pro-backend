package pl.kartven.javaprobackend.restapi;

import lombok.Value;

@Value
public class SingleRangeDetails {
    Long id;
    String topic;
    String description;
}
