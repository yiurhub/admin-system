/**
 * 用户属性
 * @author Yiur
 */
export class User {

    /**
     * 用户key
     */
    public static readonly key: string = "user";

    public online: boolean;
    public uid: Number;
    public username: string;
    public password: string;
    public name: string;
    public address: string;
    public lastLoginAddress: string;
    public face: string;
    public desc: string;
    public lastLoginDate: Date;
    public registerDate: Date;
    public perms: string;

    constructor(uid?: Number, username?: string, password?: string, name?: string, address?: string, lastLoginAddress?: string, face?: string, desc?: string, lastLoginDate?: Date, registerDate?: Date, perms?: string) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.lastLoginAddress = lastLoginAddress;
        this.face = face;
        this.desc = desc;
        this.lastLoginDate = lastLoginDate;
        this.registerDate = registerDate;
        this.perms = perms;
    }

}