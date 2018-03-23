package com.lxvoice.domain.greendao;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

/**
 * Created by lz on 2018/3/13.
 */
public class DaoMaker {

    public static void main(String[] args) {
        //生成数据库的实体类,还有版本号
        Schema schema = new Schema(1, "com.lxvoice.domain.greendao.entity");
        addBook(schema);
        addNote(schema);
        //指定dao
        schema.setDefaultJavaPackageDao("com.lxvoice.domain.greendao.main");
        try {
            //指定路径
            new DaoGenerator().generateAll(schema, "D:\\lz\\workspace\\LXNote-master\\app\\src\\main\\java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建数据库的表
     *
     * @param schema
     */
    public static void addNote(Schema schema) {
        //创建数据库的表
        Entity entity = schema.addEntity("Note");
        //主键 是int类型
        entity.addIdProperty();
        //所属数目id
        entity.addLongProperty("book_id");
        //标题
        entity.addStringProperty("title");
        //内容
        entity.addStringProperty("context");
        //创建时间
        entity.addDateProperty("ctime");
        //更新时间
        entity.addDateProperty("utime");
    }

    /**
     * 创建数据库的表
     *
     * @param schema
     */
    public static void addBook(Schema schema) {
        //创建数据库的表
        Entity entity = schema.addEntity("Book");
        //主键 是int类型
        entity.addIdProperty();
        //作者
        entity.addStringProperty("author");
        //类型
        entity.addStringProperty("type");
        //字数
        entity.addLongProperty("text_number");
        //标题
        entity.addStringProperty("name");
        //描述
        entity.addStringProperty("depict");
        //创建时间
        entity.addDateProperty("ctime");

        //创建时间
        entity.addDateProperty("utime");
    }


}
