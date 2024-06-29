package org.yearup.data;

import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

public interface ShoppingCartDao {
    ShoppingCart getByUserId(int userId);

    // add additional method signatures here
    void addProductToCart(int userId, int productId);

    void updateProductInCart(int userId, int productId, int quantity);

    void clearCart(int userId);
}
