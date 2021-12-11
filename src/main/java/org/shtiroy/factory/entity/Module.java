package org.shtiroy.factory.entity;

import javax.persistence.*;

@Entity
@Table(schema = "working_data", name = "t_module")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idModule;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "id_product")
    private Product idProduct;

    @ManyToOne(targetEntity = Photo.class)
    @JoinColumn(name = "id_photo")
    private Photo idPhoto;

    @Column(name = "module_name")
    private String moduleName;

    @Column(name = "module_unique")
    private Boolean moduleUnique;

    @Column(name = "module_is_addition")
    private Integer  moduleIsAddition;

    public Module() {
    }

    public Module(Product idProduct, String moduleName, Boolean moduleUnique, Integer moduleIsAddition) {
        this.idProduct = idProduct;
        this.moduleName = moduleName;
        this.moduleUnique = moduleUnique;
        this.moduleIsAddition = moduleIsAddition;
    }

    public Module(Integer idModule, Product idProduct, Photo idPhoto, String moduleName, Boolean moduleUnique, Integer moduleIsAddition) {
        this.idModule = idModule;
        this.idProduct = idProduct;
        this.idPhoto = idPhoto;
        this.moduleName = moduleName;
        this.moduleUnique = moduleUnique;
        this.moduleIsAddition = moduleIsAddition;
    }

    public Integer getIdModule() {
        return idModule;
    }

    public void setIdModule(Integer idModule) {
        this.idModule = idModule;
    }

    public Product getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Product idProduct) {
        this.idProduct = idProduct;
    }

    public Photo getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(Photo idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Boolean getModuleUnique() {
        return moduleUnique;
    }

    public void setModuleUnique(Boolean moduleUnique) {
        this.moduleUnique = moduleUnique;
    }

    public Integer getModuleIsAddition() {
        return moduleIsAddition;
    }

    public void setModuleIsAddition(Integer moduleIsAddition) {
        this.moduleIsAddition = moduleIsAddition;
    }

    @Override
    public String toString() {
        return "Module[" + moduleName + "]";
    }
}
