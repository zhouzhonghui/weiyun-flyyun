package cn.fly.yun.base;

public class BasePageRes extends BaseRes{
    private static final long serialVersionUID = 7229966082491831281L;
    private PageBean pageBean ;

    public PageBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }
}
