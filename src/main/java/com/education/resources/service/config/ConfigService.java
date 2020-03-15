package com.education.resources.service.config;

import com.education.resources.annotation.ConfigBean;
import com.education.resources.annotation.ConfigDes;
import com.education.resources.bean.config.Config;
import com.education.resources.bean.config.ConfigProperty;
import com.education.resources.bean.config.PackageConfiguration;
import com.education.resources.datasource.repository.config.ConfigRepository;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


//本地配置服务
@Service
@ConditionalOnBean(PackageConfiguration.class)
public class ConfigService implements ApplicationContextAware {

    private ConfigRepository mConfigMapper;

    private static Map<String, Object> store = new HashMap<>();

    private List<Config>  allConfigs = new ArrayList<>();
    private ApplicationContext mContext;

    public ConfigService(ConfigRepository configMapper, PackageConfiguration packageConfiguration) {
        this.mConfigMapper = configMapper;
        initConfig(packageConfiguration);
    }

    //初始化配置
    private void initConfig(PackageConfiguration packageConfiguration){
        Reflections reflections = new Reflections(packageConfiguration.getBasePackage());
        Set<Class<?>> configBeans = reflections.getTypesAnnotatedWith(ConfigBean.class);
        for (Class clazz:configBeans){
            ConfigBean configBean = (ConfigBean) clazz.getDeclaredAnnotation(ConfigBean.class);
            try {
                getConfig(clazz.newInstance());
                Config  config = new Config();
                config.setName(clazz.getName());
                config.setTitle(configBean.name());
                config.setValue(clazz.getName());
                allConfigs.add(config);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Config> getAllConfig(){
        for (Config config:allConfigs){
            Object object = store.get(config.getValue());
            config.setConfigs(convertToConfig(object));
        }
        return allConfigs;
    }

    private List<Config> convertToConfig(Object object){
        List<Config>  configs = new ArrayList<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field:fields){
            if(!field.isAnnotationPresent(ConfigDes.class)){
                continue;
            };
            ConfigDes configDes = field.getAnnotation(ConfigDes.class);
            Config config = new Config();
            config.setName(field.getName());
            config.setTitle(configDes.title());
            config.setMust(configDes.require());
            config.setType(configDes.type().toString());
            config.setDescription(configDes.description());
            config.setDefaultValue(configDes.defaultValue());
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if (value == null){
                    continue;
                }
                Method methods = value.getClass().getMethod("getValue");
                config.setValue((String) methods.invoke(value));
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
            configs.add(config);
        }

        return configs;
    }


    private <T> T getConfig(T object) {
        Class<? extends Object> clazz = object.getClass();
        if (store.get(clazz.getName()) != null) {
            return (T) store.get(clazz.getName());
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (!field.getType().equals(ConfigProperty.class)){
                continue;
            }
            try {
                Object configItem = field.get(object);
                if (configItem == null) {
                    field.set(object, createConfigItem(clazz.getSimpleName() + "." + field.getName(), field.getType(), field.getAnnotation(ConfigDes.class)));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        store.put(clazz.getName(), object);
        return object;
    }

    //get value from database
    private Object createConfigItem(String configName, Class clazz, ConfigDes annotation) throws IllegalAccessException, InstantiationException {
        Object object = clazz.getSuperclass().cast(clazz.newInstance());
        Class configClazz = object.getClass();
        try {
            Field defaultField = configClazz.getSuperclass().getDeclaredField("defaultValue");
            defaultField.setAccessible(true);
            defaultField.set(object,annotation.defaultValue());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        Config config = mConfigMapper.findByName(configName);
        if (config == null || StringUtils.isEmpty(config.getValue())) {
            return object;
        }
        try {
            Field valueField = configClazz.getSuperclass().getDeclaredField("value");
            valueField.setAccessible(true);
            valueField.set(object, config.getValue());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return object;
    }

    public <T> T getConfig(Class<T> clazz) {
        try {
            return getConfig(clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Config> saveConfig(String configName, Map<String, String> values) {
        Object object = store.get(configName);
        Class<?> clazz = object.getClass();
        for (Field field:clazz.getDeclaredFields()){
            if (!field.getType().equals(ConfigProperty.class)){
                continue;
            }
            String key = field.getName();
            String value = values.get(key);
            if (StringUtils.isEmpty(value)){
                continue;
            }
            field.setAccessible(true);
            try {
                Object configItem = field.get(object);
                if (configItem == null){
                    configItem = field.getType().newInstance();
                }
                Field configValueItem = configItem.getClass().getSuperclass().getDeclaredField("value");
                configValueItem.setAccessible(true);
                configValueItem.set(configItem,value);
                Config config = new Config();
                config.setValue(value);
                field.set(object,configItem);
                config.setName(clazz.getSimpleName()+"."+field.getName());
                saveToDataBase(config);
            } catch (IllegalAccessException | InstantiationException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        mContext.publishEvent(store.get(configName));
        return convertToConfig(object);
    }
    private void saveToDataBase(Config config){
        Config data = mConfigMapper.findByName(config.getName());
        if (data != null){
            config.setId(data.getId());
        }
        mConfigMapper.save(config);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.mContext = applicationContext;
    }
}
