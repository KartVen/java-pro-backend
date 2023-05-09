package pl.kartven.javaprobackend.http;

import lombok.Value;

@Value
public class SingleLectureDetails {
    Long id;
    String topic;
    String description;
}
