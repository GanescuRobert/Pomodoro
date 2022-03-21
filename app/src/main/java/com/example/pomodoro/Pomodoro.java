package com.example.pomodoro;

public class Pomodoro {


    private String name;
    private Integer focus;
    private Integer short_break;
    private Integer long_break;
    private Integer sets;
    private Integer sets_until_long_break;

    public Pomodoro() {
        this.name = "Default";
        this.focus = 50 * 60000;
        this.short_break = 10 * 60000;
        this.long_break = 30 * 60000;
        this.sets = 4;
        this.sets_until_long_break = 2;
    }

    public Pomodoro(String name, Integer focus, Integer short_break, Integer long_break, Integer sets, Integer sets_until_long_break) {
        this.name = name;
        this.focus = focus;
        this.short_break = short_break;
        this.long_break = long_break;
        this.sets = sets;
        this.sets_until_long_break = sets_until_long_break;
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


}
