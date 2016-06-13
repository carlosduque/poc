package o.hibernate.many2many.jointable;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Category implements Serializable {

    private static final long serialVersionUID = -8158175844804049016L;

    private Long categoryId;
    private String categoryDescription;
    private Set<CategorizedItem> categorizedItems = new HashSet<CategorizedItem>(0);

    public Category() { }

    public Category(Long id, String description) {
        super();
        this.categoryId = id;
        this.categoryDescription = description;
    }

    @Override
    public String toString() {
        return String.format("Category [categoryId=%s, categoryDescription=%s]", categoryId, categoryDescription);
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long id) {
        this.categoryId = id;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String description) {
        this.categoryDescription = description;
    }

    public Set<CategorizedItem> getCategorizedItems() {
        return categorizedItems;
    }

    public void setCategorizedItems(Set<CategorizedItem> categorizedItems) {
        this.categorizedItems = categorizedItems;
    }

}
