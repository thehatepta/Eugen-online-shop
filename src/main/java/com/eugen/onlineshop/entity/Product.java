package com.eugen.onlineshop.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Getter
@Setter
public class Product  {

    int id;
    String name;
    double price;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Double.compare(product.price, price) == 0 && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }


    public Product( String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Product( int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
