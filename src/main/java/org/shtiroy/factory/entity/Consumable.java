package org.shtiroy.factory.entity;

import javax.persistence.*;

@Entity
@Table(schema = "working_data",name = "t_consumable")
public class Consumable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idConsumable;

    @Column(name = "id_provider")
    private Integer idProvider;

    @Column(name = "consumable_name")
    private String consumableName;

    @Column(name = "consumable_color")
    private String consumableColor;

    @Column(name = "consumable_cat")
    private String consumableCat;

    @Column(name = "consumable_type")
    private Integer consumableType;

    public Consumable() {
    }

    public Consumable(Integer idProvider, String consumableName, String consumableColor, String consumableCat, Integer consumableType) {
        this.idProvider = idProvider;
        this.consumableName = consumableName;
        this.consumableColor = consumableColor;
        this.consumableCat = consumableCat;
        this.consumableType = consumableType;
    }

    public Integer getIdConsumable() {
        return idConsumable;
    }

    public void setIdConsumable(Integer idConsumable) {
        this.idConsumable = idConsumable;
    }

    public Integer getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    public String getConsumableName() {
        return consumableName;
    }

    public void setConsumableName(String consumableName) {
        this.consumableName = consumableName;
    }

    public String getConsumableColor() {
        return consumableColor;
    }

    public void setConsumableColor(String consumableColor) {
        this.consumableColor = consumableColor;
    }

    public String getConsumableCat() {
        return consumableCat;
    }

    public void setConsumableCat(String consumableCat) {
        this.consumableCat = consumableCat;
    }

    public Integer getConsumableType() {
        return consumableType;
    }

    public void setConsumableType(Integer consumableType) {
        this.consumableType = consumableType;
    }

    @Override
    public String toString() {
        return consumableName + " " + consumableColor + " кат. " + consumableCat;
    }
}
