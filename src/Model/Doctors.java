/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Nicolas D'aleman
 */
public class Doctors {

    private int id_doctor;
    private String doctor;

    public Doctors(int id_doctor, String doctor) {
        this.id_doctor = id_doctor;
        this.doctor = doctor;
    }

    public Doctors() {
    }
    

    public int getId_doctor() {
        return id_doctor;
    }

    public void setId_doctor(int id_doctor) {
        this.id_doctor = id_doctor;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    @Override
    public String toString() {
        return getDoctor();
    }

}
