package o.hibernate.many2many.jointable;

import java.sql.Date;

public class CategorizedItem {

    private Long categorizedItemId;
    private Date categorizedItemDateModified = new Date(System.currentTimeMillis());
    private Category category;
    private Item item;

    public CategorizedItem() { }

    public CategorizedItem(Category category, Item item) {
        super();
        this.category = category;
        this.item = item;
    }

    @Override
    public String toString() {
        return String.format("CategorizedItem [categorizedItemId=%s, dateModified=%s, item=%s, category=%s]",
                categorizedItemId, categorizedItemDateModified, item.getItemName(), category.getCategoryDescription());
    }

    public Long getCategorizedItemId() {
        return categorizedItemId;
    }
    public void setCategorizedItemId(Long id) {
        this.categorizedItemId = id;
    }
    public Date getCategorizedItemDateModified() {
        return categorizedItemDateModified;
    }

    public void setCategorizedItemDateModified(Date dateModified) {
        this.categorizedItemDateModified = dateModified;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
