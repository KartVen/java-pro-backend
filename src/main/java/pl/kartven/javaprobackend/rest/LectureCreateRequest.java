package pl.kartven.javaprobackend.rest;

import java.util.List;

public class LectureCreateRequest {
    String topic;
    String description;
    List<SlideCreate> slides;

    public static class SlideCreate {
        byte[] imgByte;
    }
}
