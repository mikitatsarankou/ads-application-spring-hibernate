package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.enums.AdStatusDto;

public class PriorityAnnouncementDto {

    private Integer id;

    private int priority;

    private int days;

    private AdStatusDto status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public AdStatusDto getStatus() {
        return status;
    }

    public void setStatus(AdStatusDto status) {
        this.status = status;
    }
}
