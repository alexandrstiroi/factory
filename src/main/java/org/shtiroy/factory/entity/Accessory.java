package org.shtiroy.factory.entity;

import javax.persistence.*;

@Entity
@Table(schema = "working_data", name = "t_accessory")
public class Accessory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAccessory;

    @ManyToOne(targetEntity = Photo.class)
    @JoinColumn(name = "id_photo")
    private Photo idPhoto;

    @Column(name = "accessory_name")
    private String accessoryName;

    public Accessory() {
    }

    public Accessory(Integer idAccessory, Photo idPhoto, String accessoryName) {
        this.idAccessory = idAccessory;
        this.idPhoto = idPhoto;
        this.accessoryName = accessoryName;
    }

    public Integer getIdAccessory() {
        return idAccessory;
    }

    public void setIdAccessory(Integer idAccessory) {
        this.idAccessory = idAccessory;
    }

    public Photo getIdPhoto() {
        return idPhoto;
    }

    public void setIdProduct(Photo idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getAccessoryName() {
        return accessoryName;
    }

    public void setAccessoryName(String accessoryName) {
        this.accessoryName = accessoryName;
    }

    @Override
    public String toString() {
        return accessoryName;
    }
}
