package com.example.pomodoro;

import java.io.Serializable;

public class Pomodoro implements Serializable {


    private String name;
    private Integer focus;
    private Integer short_break;
    private Integer long_break;
    private Integer sets;
    private Integer sets_until_long_break;
    private Integer color;

    public Pomodoro() {
        this.name = "Studying";
        this.focus = 50 * 60000;
        this.short_break = 10 * 60000;
        this.long_break = 30 * 60000;
        this.sets = 4;
        this.sets_until_long_break = 2;
        this.sets_until_long_break = 2;
        this.color = 12345;
    }

    public Pomodoro(String name, Integer focus, Integer short_break, Integer long_break, Integer sets, Integer sets_until_long_break, Integer color) {
        this.name = name;
        this.focus = focus;
        this.short_break = short_break;
        this.long_break = long_break;
        this.sets = sets;
        this.sets_until_long_break = sets_until_long_break;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Pomodoro{" +
                "name='" + name + '\'' +
                ", focus=" + focus +
                ", short_break=" + short_break +
                ", long_break=" + long_break +
                ", sets=" + sets +
                ", sets_until_long_break=" + sets_until_long_break +
                ", color=" + color +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFocus() {
        return focus;
    }

    public void setFocus(Integer focus) {
        this.focus = focus;
    }

    public Integer getShort_break() {
        return short_break;
    }

    public void setShort_break(Integer short_break) {
        this.short_break = short_break;
    }

    public Integer getLong_break() {
        return long_break;
    }

    public void setLong_break(Integer long_break) {
        this.long_break = long_break;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public Integer getSets_until_long_break() {
        return sets_until_long_break;
    }

    public void setSets_until_long_break(Integer sets_until_long_break) {
        this.sets_until_long_break = sets_until_long_break;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }
}
