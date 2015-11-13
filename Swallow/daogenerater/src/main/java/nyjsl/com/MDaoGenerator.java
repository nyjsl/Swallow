package nyjsl.com;

import java.io.File;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MDaoGenerator {

    /**
     * package name
     */
    private static final String PKG_NAME = "org.nyjsl.swallow.db";

    public static void main(String[] args) throws Exception {
        gen();
    }

    private static void gen() throws Exception {
        Schema schema = new Schema(1,PKG_NAME);

        companyEntity(schema);
        appliedRoomEntity(schema);
        burn(schema);
    }

//    private static void testEntity(Schema schema) {
//        Entity test = schema.addEntity("Test");
//        test.addIdProperty();
//        test.addStringProperty("test_pro1").notNull();
//        test.addDateProperty("date");
//        test.addStringProperty("test_pro2");
//    }
    private static void companyEntity(Schema schema) {
        Entity test = schema.addEntity("Company");
        test.addIdProperty();
        test.addStringProperty("buguid");
        test.addStringProperty("buname");
        test.addStringProperty("bufullname");
        test.addStringProperty("bucode");
        test.addStringProperty("inspectiontype");
        test.addStringProperty("username");
    }

    private static void appliedRoomEntity(Schema schema){
//        " CREATE TABLE IF NOT EXISTS " + SHENLING_ROOM_TABLE_NAME + "("
//                + "roomguid TEXT," + "huxing TEXT," + "roomstru TEXT,"
//                + "bldarea TEXT," + "tnarea TEXT," + "roominfo TEXT,"
//                + "dbdata TEXT," + "sbdata TEXT," + "qbdata TEXT,"
//                + "cstname TEXT," + "cstguid TEXT," + "gender TEXT,"
//                + "cardtype TEXT," + "cardid TEXT," + "mobiletel TEXT,"
//                + "hometel TEXT,"
//                + "isvirtualroom TEXT," // 是否是虚拟房间
//                + "hxpics TEXT," // 户型图ID，用逗号隔开
//                + "isNetLoad Text," // 0代表从后台获取的 1代表本地的
//                + "sltime Text," // 申领时间
//                + "slstate Text," // 申领状态
//                + "taskclfs Text," // 处理方式选项
//                + "closetype Text," // 关闭状态选项
//                + "rwendlevel Text," // 等级选项
//                + "xgyw Text," // 业务环节选项
//                + "feedback Text," // 客户反映选项
//                + "checkstate Text," + INSPECTION_TYPE + " text," + "username TEXT"
//                + ")";
        Entity test = schema.addEntity("AppliedRoom");
        test.addIdProperty();
        test.addStringProperty("roomguid");
        test.addStringProperty("huxing");
        test.addStringProperty("roomstru");
        test.addStringProperty("bldarea");
        test.addStringProperty("tnarea");
        test.addStringProperty("roominfo");

        test.addStringProperty("dbdata");
        test.addStringProperty("sbdata");
        test.addStringProperty("qbdata");
        test.addStringProperty("cstname");
        test.addStringProperty("cstguid");
        test.addStringProperty("gender");

        test.addStringProperty("cardtype");
        test.addStringProperty("cardid");
        test.addStringProperty("mobiletel");
        test.addStringProperty("hometel");
        test.addStringProperty("isvirtualroom");
        test.addStringProperty("hxpics");

        test.addStringProperty("isNetLoad");
        test.addStringProperty("sltime");
        test.addStringProperty("slstate");
        test.addStringProperty("taskclfs");
        test.addStringProperty("closetype");
        test.addStringProperty("rwendlevel");

        test.addStringProperty("xgyw");
        test.addStringProperty("feedback");
        test.addStringProperty("checkstate");
        test.addStringProperty("inspectiontype");
        test.addStringProperty("username");

    }

    /**
     * code generation
     * @param schema
     * @throws Exception
     */
    private static void burn(Schema schema) throws Exception {
        File f = new File("");
        new DaoGenerator().generateAll(schema,f.getCanonicalPath()+File.separator+"app\\src\\main\\java");
    }


}
