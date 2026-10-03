package com.kallucompound.dto;

public class CustomerNameDTO {

    private Long id;
    private String name;

    public CustomerNameDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
