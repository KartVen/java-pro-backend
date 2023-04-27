package pl.kartven.javaprobackend.rest;

import lombok.Value;
import pl.kartven.javaprobackend.lecture.Lecture;

@Value
public class SingleSlideDetails {
    Long id;
    byte[] content;
}
