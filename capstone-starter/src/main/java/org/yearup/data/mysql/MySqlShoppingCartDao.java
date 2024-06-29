package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    private static final Logger logger = LoggerFactory.getLogger(MySqlShoppingCartDao.class);

    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        String sql = "SELECT sc.product_id, sc.quantity, p.name, p.price, p.category_id, p.description, p.color, p.stock, p.featured, p.image_url " +
                "FROM shopping_cart sc " +
                "JOIN products p ON sc.product_id = p.product_id " +
                "WHERE sc.user_id = ?";

        ShoppingCart cart = new ShoppingCart();
        Map<Integer, ShoppingCartItem> items = new HashMap<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                int quantity = resultSet.getInt("quantity");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
                int categoryId = resultSet.getInt("category_id");
                String description = resultSet.getString("description");
                String color = resultSet.getString("color");
                int stock = resultSet.getInt("stock");
                boolean isFeatured = resultSet.getBoolean("featured");
                String imageUrl = resultSet.getString("image_url");

                Product product = new Product(productId, name, price, categoryId, description, color, stock, isFeatured, imageUrl);
                ShoppingCartItem item = new ShoppingCartItem(product, quantity);
                items.put(productId, item);
            }
        } catch (SQLException e) {
            logger.error("SQL error during retrieving shopping cart by user ID", e);
            throw new RuntimeException(e);
        }

        cart.setItems(items);
        return cart;
    }

    @Override
    public void addProductToCart(int userId, int productId) {
        // Implementation code to add product to cart
    }

    @Override
    public void updateProductInCart(int userId, int productId, int quantity) {
        // Implementation code to update product in cart
    }

    @Override
    public void clearCart(int userId) {
        // Implementation code to clear cart
    }
}
