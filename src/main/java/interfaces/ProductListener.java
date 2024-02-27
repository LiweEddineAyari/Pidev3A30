package interfaces;

import entity.Product;

public interface ProductListener {
    void onViewDetails(Product product);
    void onPressShopAdd(int counter);
}