package android.com.category.model;

import java.util.List;

/*
 * Created by thh on 2019/8/9.
 */
public class CategoryThirdItemBean {

    private String id;
    private String parent_id;
    private String name;
    private String sname;
    private String logo;
    private List<CategoryThirdItemBean> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<CategoryThirdItemBean> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryThirdItemBean> children) {
        this.children = children;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
