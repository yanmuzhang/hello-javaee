package io.sufeng.jmybatis.page;

/**
 * @description:
 * @author: sufeng
 * @create: 2019-12-05 09:59
 */
public class PageParames {
    private int pageIndex;
    private int pageSize;

    private PageParames(int pageIndex, int pageSize) {
        if (pageIndex < 1) {
            this.pageIndex = 1;
        } else {
            this.pageIndex = pageIndex;
        }

        this.pageSize = pageSize;
    }

    public static PageParames create(int pageIndex, int pageSize) {
        return new PageParames(pageIndex, pageSize);
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public int getPageSize() {
        return this.pageSize;
    }
}
