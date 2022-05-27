/**
 * 消息属性
 * @author Yiur
 */
export class Message {

    public title: string;
    public target: string;
    public level: string;
    public content: string;
    public sendDate: any;
    public validDate: any;

    constructor(title?: string, target?: string, level?: string, content?: string, sendDate?: any, validDate?: any) {
        this.title = title;
        this.target = target;
        this.level = level;
        this.content = content;
        this.sendDate = sendDate;
        this.validDate = validDate;
    }

}