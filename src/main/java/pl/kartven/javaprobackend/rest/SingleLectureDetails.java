package pl.kartven.javaprobackend.rest;

import lombok.Value;

@Value
public class SingleLectureDetails {
    Long id;
    String topic;
    String description;
}
