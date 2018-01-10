package com.victor.che.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.victor.che.domain.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SharedPreferences的工具类
 *
 * @author Administrator
 */
public class SharedPreferencesUtil {
    Context context;
    String name;
    private static SharedPreferences sPrefs ;

    public SharedPreferencesUtil(Context context, String name) {
        this.context = context;
        this.name = name;
        sPrefs= context.getSharedPreferences(
                name, Context.MODE_APPEND );
    }

    /**
     * 根据key和预期的value类型获取value的值
     *
     * @param key
     * @param clazz
     * @return
     */
    public boolean getBoolean(String key, boolean defValue) {
        if (context == null) {
            throw new RuntimeException("请先调用带有context，name参数的构造！");
        }
        SharedPreferences sp = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 根据key和预期的value类型获取value的值
     *
     * @param key
     * @param clazz
     * @return
     */
    public boolean getBoolean(String key) {
        if (context == null) {
            throw new RuntimeException("请先调用带有context，name参数的构造！");
        }
        SharedPreferences sp = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public void setBoolean(String key, boolean value) {
        if (context == null) {
            throw new RuntimeException("请先调用带有context，name参数的构造！");
        }
        SharedPreferences sp = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 根据key和预期的value类型获取value的值
     *
     * @param key
     * @param clazz
     * @return
     */
    public <T> T getValue(String key, Class<T> clazz) {
        if (context == null) {
            throw new RuntimeException("请先调用带有context，name参数的构造！");
        }
        SharedPreferences sp = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
        return getValue(key, clazz, sp);
    }

    /**
     * 存储值在sp中
     *
     * @param key
     * @param value
     */
    public void putValue(String key, Object value) {
        if (context == null) {
            throw new RuntimeException("请先调用带有context，name参数的构造！");
        }
        if (value == null) {
            throw new RuntimeException("value值为空");
        }
        SharedPreferences sp = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        }
        editor.commit();
    }

    /**
     * 针对复杂类型存储<对象>
     *
     * @param key
     * @param object
     */
    public void setObject(String key, Object object) {
        SharedPreferences sp = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {

            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, objectVal);
            editor.commit();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(String key, Class<T> clazz) {
        SharedPreferences sp = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
        if (sp.contains(key)) {
            String objectVal = sp.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 对于外部不可见的过渡方法
     *
     * @param key
     * @param clazz
     * @param sp
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T> T getValue(String key, Class<T> clazz, SharedPreferences sp) {
        T t;
        try {

            t = clazz.newInstance();

            if (t instanceof Integer) {
                return (T) Integer.valueOf(sp.getInt(key, 0));
            } else if (t instanceof String) {
                return (T) sp.getString(key, "");
            } else if (t instanceof Boolean) {
                return (T) Boolean.valueOf(sp.getBoolean(key, false));
            } else if (t instanceof Long) {
                return (T) Long.valueOf(sp.getLong(key, 0L));
            } else if (t instanceof Float) {
                return (T) Float.valueOf(sp.getFloat(key, 0L));
            }
        } catch (InstantiationException e) {//类型输入错误或者复杂类型无法解析
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存搜索历史
     *
     * @param key  SP中key的名称
     * @param text 文本
     */
    public void saveHistory(String key, String text) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        String history = getValue("history", String.class);
        if (!history.contains(text + ",")) {
            StringBuilder builder = new StringBuilder(history);
            builder.insert(0, text + ",");

            putValue("history", builder.toString());
        }
    }

    /**
     * 获取搜索历史
     *
     * @param key SP中key的名称
     * @return
     */
    public List<String> getHistory(String key) {
        List<String> list = new ArrayList<String>();
        if (TextUtils.isEmpty(key)) {
            return list;
        }
        String history = getValue("history", String.class);
        if (!TextUtils.isEmpty(history)) {
            String[] split = history.split(",");
            return Arrays.asList(split);
        }
        return list;
    }

    /**
     * 清空搜索历史
     *
     * @param key SP中key的名称
     */
    public void clearHistory(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        putValue(key, "");
    }

    public void setUser(User user) {
        setObject("USER", user == null ? new User() : user);
    }

    public User getUser() {
        User user = getObject("USER", User.class);
        if (user == null) {
            user = new User();
        }
        return user;
    }


    public static String getString(String key,String defaultValue)
    {
        if(sPrefs ==null)
            return defaultValue;
        return sPrefs.getString(key, defaultValue);
    }

    public static void setString(String key,String value) {
        if(sPrefs ==null)
            return ;
        sPrefs.edit().putString(key, value).commit();
    }
}
