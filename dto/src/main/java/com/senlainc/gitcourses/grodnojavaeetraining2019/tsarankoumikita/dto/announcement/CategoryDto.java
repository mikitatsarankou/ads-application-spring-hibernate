package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement;

public class CategoryDto {

    private Integer id;

    private String name;

    public CategoryDto(int id) {
        this.id = id;
    }

    public CategoryDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
