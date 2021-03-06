package scrawler;

/**
 * @author:binblink
 * @Description
 * @Date: Create on  2018/11/9 15:52
 * @Modified By:
 * @Version:1.0.0
 **/
public class Address {

    /**
     * id
     */
    private String id;
    /**
     * 父id
     */
    private String pid;
    /**
     * 地区代码
     */
    private String areacode = "DEFAULT";
    /**
     * 名称
     */
    private String name;


    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getAreacode() {
        return areacode;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPid() { return pid; }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                ", areacode='" + areacode + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
