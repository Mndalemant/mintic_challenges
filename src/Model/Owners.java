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
public class Owners {

    private int id_owner;
    private String owner;

    public Owners() {
    }
    
    public Owners(int id_owner, String owner) {
        this.id_owner = id_owner;
        this.owner = owner;
    }

    public int getId_owner() {
        return id_owner;
    }

    public void setId_owner(int id_owner) {
        this.id_owner = id_owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return getOwner();
    }
}
