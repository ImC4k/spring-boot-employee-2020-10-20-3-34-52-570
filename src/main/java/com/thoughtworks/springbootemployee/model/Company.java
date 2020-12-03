package com.thoughtworks.springbootemployee.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class Company {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String name;
    private String address;

    public Company(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Company) {
            return this.id.equals(((Company) obj).getId()) &&
                    this.name.equals(((Company) obj).getName()) &&
                    this.address.equals(((Company) obj).getAddress());
        }
        return super.equals(obj);
    }
}
