package com.thoughtworks.springbootemployee.model;

public class Company {
    private String id;
    private String name;
    private String address;

    public Company() {
    }

    public Company(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setId(String id) {
        this.id = id;
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
