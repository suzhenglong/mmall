package com.mmall.util;

import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.04.20 16:51
 */
@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //序列化参数
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);
        // 取消默认转换timestamps形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        //忽略空Bean转Json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        //所有的日期格式都为统一的样式,即:yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

        //反序列化
        //忽略 在json字符串中存在,但在java对象中不存在对应属性的情况,防止错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> String obj2String(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return (obj instanceof String) ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse object to String error:", e);
            return null;
        }
    }

    public static <T> String obj2StringPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return (obj instanceof String) ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse object to String error:", e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (IOException e) {
            log.warn("Parse String to object error:", e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? (T) str : objectMapper.readValue(str, typeReference));
        } catch (IOException e) {
            log.warn("Parse String to object error:", e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return objectMapper.readValue(str, javaType);
        } catch (IOException e) {
            log.warn("Parse String to object error:", e);
            return null;
        }
    }


    public static void main(String[] args) {
        User user = new User();
        user.setId(123);
        user.setPassword("123");
        user.setUsername("geely");
        user.setEmail("geely@happymmall.com");
        user.setPhone("13800138000");
        user.setQuestion("105204");
        user.setAnswer("105204");
        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        String str = JsonUtil.obj2String(user);
        System.out.println("JsonUtil.obj2String(user)-->" + str);
        str = JsonUtil.obj2StringPretty(user);
        System.out.println("JsonUtil.obj2StringPretty(user)-->" + str);

        User user1 = JsonUtil.string2Obj(str, User.class);
        System.out.println("JsonUtil.string2Obj(str, User.class)--->" + user1);


        List<User> userList = Lists.newArrayList();
        userList.add(user);
        userList.add(user1);

        String stringPretty = JsonUtil.obj2StringPretty(userList);
        System.out.println("JsonUtil.obj2StringPretty(userList)--->" + stringPretty);

        List<User> users = (List<User>) JsonUtil.string2Obj(stringPretty, new TypeReference<List<User>>() {
        });
        System.out.println("JsonUtil.string2Obj(stringPretty, new TypeReference<List<User>>()-->" + users);
        System.out.println(users.get(0));
        System.out.println(users.get(1));
        System.out.println(users.get(1).getId());
        System.out.println(users.get(1).getUsername());

        users = JsonUtil.string2Obj(stringPretty, List.class, User.class);
        System.out.println("JsonUtil.string2Obj(stringPretty, List.class,User.class)-->" + users);

    }
}
