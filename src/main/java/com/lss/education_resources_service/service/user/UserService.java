package com.lss.education_resources_service.service.user;

import com.lss.education_resources_service.bean.entity.user.User;
import com.lss.education_resources_service.exception.APIError;
import com.lss.education_resources_service.framework.redis.RedisCache;
import com.lss.education_resources_service.repository.UserRepository;
import com.lss.education_resources_service.service.BaseService;
import com.lss.education_resources_service.util.Digests;
import com.lss.education_resources_service.util.Encodes;
import com.lss.education_resources_service.util.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
public class UserService extends BaseService {




    public static final int HASH_INTERATIONS = 1024;

    public static final int SALT_SIZE = 8;



    @Autowired
    RedisCache redisCache;

    @Autowired
    UserRepository userRepository;




    public User getUser(String token) {
        Object cacheObject = redisCache.getCacheObject(token);
        return (User) cacheObject;
    }




    public HashMap<String, Object> saveDetail(String phone, String avatar, Integer gender, String userName, Timestamp birthday) {
        User user = new User();
        user.setPhone(phone);
        user.setAvatar(avatar);
        user.setGender(gender);
        user.setUsername(userName);
        user.setBirthday(birthday);
        userRepository.save(user);
        HashMap map = new HashMap();
        String token = EncryptUtil.getMd5((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + String.valueOf((int) (Math.random() * 900) + 100) + "-" + user.getUsername());
        redisCache.setCacheObject(token, user, 1800, TimeUnit.SECONDS);
        map.put("user", user);
        map.put("token", token);
        return map;
    }

//    public HashMap<String, Object> openLogin(String openId) {
//        User userDto = userMapper.queryByOpenId(openId);
//        //未绑定
//        if (userDto == null) {
//            User userDto1 = new User();
//            userDto1.setOpenId(openId);
//            HashMap map = new HashMap();
//            map.put("userDto", userDto1);
//            map.put("token", null);
//            return map;
//        } else {
//            //已经绑定
//            HashMap map = new HashMap();
//            String token = EncryptUtil.getMd5((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + String.valueOf((int) (Math.random() * 900) + 100) + "-" + userDto.getUserName());
//            redisCache.setCacheObject(token, userDto, 1800, TimeUnit.SECONDS);
//            map.put("userDto", userDto);
//            map.put("token", token);
//            return map;
//        }
//
//
//    }



    public User getUserInfo() {
        User user = userRepository.findById(super.getCurrentUser().getId()).orElse(null);
        return user;
    }

    public void updatePassword(String password, String surePassword, String phone, String phoneCoad) {
        if (!password.equals(surePassword)) {
            APIError.e(400, "两次密码不相等");
        }
        Object cacheObject = redisCache.getCacheObject(phone);
        if (cacheObject == null) {
            APIError.e(400, "验证码过期");
        }
        if (!phoneCoad.equals(cacheObject)) {
            APIError.e(400, "验证码错误");
        }
        redisCache.deleteObject(phone);

        User userDto = userRepository.findById(super.getCurrentUser().getId()).orElse(null);
        userDto.setPassword(entryptPassword(password));
        userRepository.save(userDto);

    }


    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static String entryptPassword(String plainPassword) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
    }

    /**
     * 验证密码
     *
     * @param plainPassword 明文密码
     * @param password      密文密码
     * @return 验证成功返回true
     */
    public boolean validatePassword(String plainPassword, String password) {
        byte[] salt = Encodes.decodeHex(password.substring(0, 16));
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
    }

    public static void main(String[] args) {
        String s = entryptPassword("1");
        System.out.println(s);
    }

    public HashMap<String, Object> login(String username, String password) {
        User user = userRepository.queryByUserName(username);
        if (user == null) {
            APIError.e(400, "用户不存在");
        }

        if (StringUtils.isEmpty(user.getPassword())) {
            APIError.e(400, "还未设置密码");
        }

        if (!(validatePassword(password, user.getPassword()))) {
            APIError.e(400, "密码错误");
        }

        HashMap map = new HashMap();
        String token = EncryptUtil.getMd5((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + String.valueOf((int) (Math.random() * 900) + 100) + "-" + user.getUsername());
        redisCache.setCacheObject(token, user, 180000, TimeUnit.SECONDS);
        map.put("user", user);
        map.put("token", token);
        return map;

    }




    public void savePassword(String password, String surePassword) {
        if (!password.equals(surePassword)){
            APIError.e("两次密码不一致");
        }
        User user = super.getCurrentUser();
        if (user!=null){
            user.setPassword(entryptPassword(password));
            userRepository.save(user);
        }
    }

}
