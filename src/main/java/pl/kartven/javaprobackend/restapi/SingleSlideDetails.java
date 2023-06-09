package pl.kartven.javaprobackend.restapi;

import lombok.Value;

@Value
public class SingleSlideDetails {
    Long id;
    byte[] content;
}
