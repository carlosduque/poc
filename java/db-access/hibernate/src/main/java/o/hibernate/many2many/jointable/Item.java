package o.hibernate.many2many.jointable;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Item implements Serializable {

    private static final long serialVersionUID = -6864910547108497516L;

    private Long itemId;
    private String itemName;
    private Set<CategorizedItem> categorizedItems = new HashSet<CategorizedItem>(0);

    public Item() { }

    public Item(Long id, String name) {
        super();
        this.itemId = id;
        this.itemName = name;
    }

    @Override
    public String toString() {
        return String.format("Item [itemId=%s, itemName=%s]", itemId, itemName);
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long id) {
        this.itemId = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Set<CategorizedItem> getCategorizedItems() {
        return categorizedItems;
    }

    public void setCategorizedItems(Set<CategorizedItem> categorizedItems) {
        this.categorizedItems = categorizedItems;
    }

}
