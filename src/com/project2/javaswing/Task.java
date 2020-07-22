/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project2.javaswing;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author dinhviethoang
 */
public class Task implements Serializable {

    private int id;
    private Date date;
    private String note;
    private int priovity;

    public Task() {
    }

    public Task(int id, Date date, String note, int priovity) {
        this.id = id;
        this.date = date;
        this.note = note;
        this.priovity = priovity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPriovity() {
        return priovity;
    }

    public void setPriovity(int priovity) {
        this.priovity = priovity;
    }

    public int getId() {
        return id;
    }

}
