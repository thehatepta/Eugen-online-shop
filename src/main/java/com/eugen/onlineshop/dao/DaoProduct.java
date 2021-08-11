package com.eugen.onlineshop.dao;

import com.eugen.onlineshop.entity.Product;
import com.eugen.onlineshop.jdbc.JdbcConnector;
import com.eugen.onlineshop.jdbc.JdbcDataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DaoProduct implements DaoInterface{
    private final String getAllQuery = "SELECT ID, NAME, PRICE  FROM PRODUCTS";
    private final String getProductQuery = "SELECT id, name, price FROM PRODUCTS WHERE ID = ?";
    private final String deleteQuery = "DELETE FROM PRODUCTS WHERE ID = ?";
    private final String updateQuery = "UPDATE PRODUCTS SET NAME = ?, PRICE = ? WHERE ID = ?";
    private final String addQuery = "insert into products(id, name, price) values (?, ?, ?)";





    private final JdbcConnector jdbcConnector;

    public DaoProduct(JdbcConnector jdbcConnector) {
        this.jdbcConnector = jdbcConnector;
    }

    @Override
    public List<Product> findAllProducts() {
        ArrayList<Product> products = new ArrayList<>();

        try (Connection connection = jdbcConnector.getConnection();
             PreparedStatement getAllStatement = connection.prepareStatement(getAllQuery)) {
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
             PreparedStatement getStatement = connection.prepareStatement(getProductQuery)) {
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

    public void delete(int id){
        try (Connection connection = jdbcConnector.getConnection();
             PreparedStatement getStatement = connection.prepareStatement(deleteQuery)) {
            getStatement.setInt(1, id);
            getStatement.execute();
        }catch (SQLException e) {
            throw new JdbcDataException("Unable to delete product with id {" + id + "}", e);
        }
    }

    public void update(Product product){
        try (Connection connection = jdbcConnector.getConnection();
             PreparedStatement getStatement = connection.prepareStatement(updateQuery)) {
            getStatement.setString(1, product.getName());
            getStatement.setDouble(2, product.getPrice());
            getStatement.setInt(3, product.getId());
            getStatement.execute();
        }catch (SQLException e) {
            throw new JdbcDataException("Unable to delete update product with id {" + product.getId() + "}", e);
        }

    }

    private Product getResultProducts(ResultSet resultSet) throws SQLException {
        return new Product(resultSet.getInt("ID"),
                resultSet.getString("NAME"),
                resultSet.getDouble("PRICE"));
    }
}
