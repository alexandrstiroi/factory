package org.shtiroy.factory.entity;

import javax.persistence.*;

@Entity
@Table(schema = "working_data", name = "t_client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_address")
    private String clientAddress;

    @Column(name = "client_phone")
    private String clientPhone;

    public Client() {
    }

    public Client(Integer id, String clientName, String clientAddress, String clientPhone) {
        this.id = id;
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.clientPhone = clientPhone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    @Override
    public String toString() {
        return "Client["  + clientName + " " + clientAddress + " " + clientPhone + "]";
    }
}
