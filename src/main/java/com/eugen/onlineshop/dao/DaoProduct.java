package com.eugen.onlineshop.dao;

import com.eugen.onlineshop.entity.Product;
import com.eugen.onlineshop.jdbc.JdbcConnector;
import com.eugen.onlineshop.jdbc.JdbcDataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoProduct implements DaoProductInterface {

    private final JdbcConnector jdbcConnector;

    public DaoProduct(JdbcConnector jdbcConnector) {
        this.jdbcConnector = jdbcConnector;
    }

    @Override
    public List<Product> findAllProducts() {
        ArrayList<Product> products = new ArrayList<>();

        try (Connection connection = jdbcConnector.getConnection();
             PreparedStatement getAllStatement = connection.prepareStatement("SELECT ID, NAME, PRICE  FROM PRODUCTS")) {
            getAllStatement.execute();
            try (ResultSet resultSet = getAllStatement.getResultSet()) {
                while (resultSet.next()) {
                    Product product = getResultProducts(resultSet);
                    products.add(product);
                }
                return products;
            }
        } catch (SQLException e) {
            throw new JdbcDataException("Unable to get products with id", e);
        }
    }

    public Product get(int id){
        try (Connection connection = jdbcConnector.getConnection();
             PreparedStatement getStatement = connection.prepareStatement("SELECT id, name, price FROM PRODUCTS WHERE ID = ?")) {
            getStatement.setInt(1, id);
            getStatement.execute();
            try (ResultSet resultSet = getStatement.getResultSet()) {
                if (!resultSet.next()) {
                    return null;
                }
                return getResultProducts(resultSet);
            }
        } catch (SQLException e) {
            throw new JdbcDataException("Unable to get product with id {" + id + "}", e);
        }
    }

    public void add(Product product){
        try (Connection connection = jdbcConnector.getConnection();
             PreparedStatement addStatement = connection.prepareStatement("insert into products(id, name, price) values (?, ?, ?)")) {
            addStatement.setString(1, String.valueOf(getNewProductId() +1));
            addStatement.setString(2, product.getName());
            addStatement.setDouble(3, product.getPrice());
            addStatement.executeUpdate();
        } catch (SQLException e) {
            throw new JdbcDataException("Unable to generate new product", e);
        }
    }

    public void delete(int id){
        try (Connection connection = jdbcConnector.getConnection();
             PreparedStatement getStatement = connection.prepareStatement("DELETE FROM PRODUCTS WHERE ID = ?")) {
            getStatement.setInt(1, id);
            getStatement.execute();
        }catch (SQLException e) {
            throw new JdbcDataException("Unable to delete product with id {" + id + "}", e);
        }
    }

    public void update(Product product){
        try (Connection connection = jdbcConnector.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement("UPDATE PRODUCTS SET NAME = ?, PRICE = ? WHERE ID = ?")) {
            updateStatement.setString(1, product.getName());
            updateStatement.setDouble(2, product.getPrice());
            updateStatement.setInt(3, product.getId());
            updateStatement.executeUpdate();
        }catch (SQLException e) {
            throw new JdbcDataException("Unable to delete update product with id {" + product.getId() + "}", e);
        }

    }

    private Product getResultProducts(ResultSet resultSet) throws SQLException {
        return new Product(resultSet.getInt("ID"),
                resultSet.getString("NAME"),
                resultSet.getDouble("PRICE"));
    }

    private int getNewProductId() {
        int newId;
        try (Connection connection = jdbcConnector.getConnection();
             PreparedStatement newIdStatement = connection.prepareStatement("select max(id) from PRODUCTS")) {
            newIdStatement.execute();
            try (ResultSet resultSet = newIdStatement.getResultSet()) {
                if (resultSet.next()) {
                    newId = resultSet.getInt(1);
                } else {
                    newId = 1;
                }
                return newId;
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
}
