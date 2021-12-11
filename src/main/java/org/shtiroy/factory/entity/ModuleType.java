package org.shtiroy.factory.entity;

import javax.persistence.*;

@Entity
@Table(schema = "working_data", name = "t_module_type")
public class ModuleType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idModuleType;

    @ManyToOne(targetEntity = Module.class)
    @JoinColumn(name = "id_module")
    private Module idModule;

    @ManyToOne(targetEntity = Photo.class)
    @JoinColumn(name = "id_photo")
    private Photo idPhoto;

    @Column(name = "module_type_name")
    private String moduleTypeName;

    @Column(name = "depth")
    private Integer depth;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    public ModuleType() {
    }

    public ModuleType(Integer idModuleType, Module idModule, String moduleTypeName, Integer depth, Integer width, Integer height) {
        this.idModuleType = idModuleType;
        this.idModule = idModule;
        this.moduleTypeName = moduleTypeName;
        this.depth = depth;
        this.width = width;
        this.height = height;
    }

    public ModuleType(Integer idModuleType, Module idModule, Photo idPhoto, String moduleTypeName, Integer depth, Integer width, Integer height) {
        this.idModuleType = idModuleType;
        this.idModule = idModule;
        this.idPhoto = idPhoto;
        this.moduleTypeName = moduleTypeName;
        this.depth = depth;
        this.width = width;
        this.height = height;
    }

    public Integer getIdModuleType() {
        return idModuleType;
    }

    public void setIdModuleType(Integer idModuleType) {
        this.idModuleType = idModuleType;
    }

    public Module getIdModule() {
        return idModule;
    }

    public void setIdModule(Module idModule) {
        this.idModule = idModule;
    }

    public Photo getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(Photo idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getModuleTypeName() {
        return moduleTypeName;
    }

    public void setModuleTypeName(String moduleTypeName) {
        this.moduleTypeName = moduleTypeName;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "ModuleType[" + moduleTypeName + "]";
    }
}
