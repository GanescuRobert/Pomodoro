package com.example.pomodoro;

public class Pomodoro {

    private Integer focus;
    private Integer short_break;
    private Integer long_break;
    private Integer sets;
    private Integer sets_until_long_break;

    public Pomodoro() {
       this.focus=50 * 60000;
       this.short_break=10 * 60000;
       this.long_break=30 * 60000;
       this.sets=4;
       this.sets_until_long_break=2;
    }

    @Override
    public String toString() {
        return "Pomodoro{" +
                "focus=" + focus +
                ", short_break=" + short_break +
                ", long_break=" + long_break +
                ", sets=" + sets +
                ", sets_until_long_break=" + sets_until_long_break +
                '}';
    }

    public Pomodoro(Integer focus, Integer short_break, Integer long_break, Integer sets, Integer sets_until_long_break) {
        this.focus = focus * 60000;
        this.short_break = short_break * 60000;
        this.long_break = long_break * 60000 ;
        this.sets = sets;
        this.sets_until_long_break = sets_until_long_break;
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
