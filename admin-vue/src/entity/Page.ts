/**
 * 分页
 * @author Yiur
 */
export class Page {

    public pageIndex: number;
    public pageCount: number;
    public pageTotal: number;
    public pageLike: string;

    constructor(pageIndex: number, pageCount: number, pageTotal: number, pageLike?: string) {
        this.pageIndex = pageIndex;
        this.pageCount = pageCount;
        this.pageTotal = pageTotal;
        this.pageLike = pageLike;
    }

}