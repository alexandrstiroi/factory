package org.shtiroy.factory.entity;

import javax.persistence.*;

@Entity
@Table(schema = "working_data", name = "t_composition")
public class Composition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = OrderProduct.class)
    @JoinColumn(name = "id_order_product")
    private OrderProduct idOrderProduct;

    @ManyToOne(targetEntity = Module.class)
    @JoinColumn(name = "id_module")
    private Module idModule;

    @ManyToOne(targetEntity = Accessory.class)
    @JoinColumn(name = "id_accessory")
    private Accessory idAccessory;

    @ManyToOne(targetEntity = Consumable.class)
    @JoinColumn(name = "id_consumable")
    private Consumable idConsumable;

    @ManyToOne(targetEntity = ModuleType.class)
    @JoinColumn(name = "id_module_type")
    private ModuleType idModuleType;

    public Composition() {
    }

    public Composition(OrderProduct idOrderProduct, Module idModule, Accessory idAccessory, Consumable idConsumable, ModuleType idModuleType) {
        this.idOrderProduct = idOrderProduct;
        this.idModule = idModule;
        this.idAccessory = idAccessory;
        this.idConsumable = idConsumable;
        this.idModuleType = idModuleType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderProduct getIdOrderProduct() {
        return idOrderProduct;
    }

    public void setIdOrderProduct(OrderProduct idOrderProduct) {
        this.idOrderProduct = idOrderProduct;
    }

    public Module getIdModule() {
        return idModule;
    }

    public void setIdModule(Module idModule) {
        this.idModule = idModule;
    }

    public Accessory getIdAccessory() {
        return idAccessory;
    }

    public void setIdAccessory(Accessory idAccessory) {
        this.idAccessory = idAccessory;
    }

    public Consumable getIdConsumable() {
        return idConsumable;
    }

    public void setIdConsumable(Consumable idConsumable) {
        this.idConsumable = idConsumable;
    }

    public ModuleType getIdModuleType() {
        return idModuleType;
    }

    public void setIdModuleType(ModuleType idModuleType) {
        this.idModuleType = idModuleType;
    }
}
