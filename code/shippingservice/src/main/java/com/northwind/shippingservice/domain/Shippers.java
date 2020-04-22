package com.northwind.shippingservice.domain;


import javax.persistence.*;

@Entity
@Table(name = "Shippers")

public class Shippers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SHipperID")
    private long id;
    @Column(name = "CompanyName")
    private String companyName;
    @Column(name = "Phone")
    private String phone;
    @Version
    private long version;
    public Shippers(){this.version=1;}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
