(ns ca.ch4.single)

(defn go-shopping-naive
  "Returns a list of items purchased"
  [shopping-list]
  (loop [[item & items] shopping-list
         cart []]
    (if item
      (recur items (conj cart item))
      cart)))

(defn shop-for-item [cart item]
  "Shop for an item, return updated cart"
  (if (store/grab item)
    (conj cart item)
    cart))

(defn go-shopping
  "Returns a list of items purchased"
  [shopping-list]
  (reduce shop-for-item [] shopping-list))


