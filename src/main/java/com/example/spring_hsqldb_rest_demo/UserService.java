package com.example.spring_hsqldb_rest_demo;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {
    
    // UserRepository を依存性注入（DI）。
    // ここではメンバ変数に対して @Autowired を付与して行っているが、
    // メンバ変数に対する @Autowired は、現在では非推奨なので、
    // 実践では、「コンストラクタインジェクション」で行うようにする。
    @Autowired
    private UserRepository userRepository;
    
    /**
     * 指定ユーザーデータ取得
     * @param userId 取得対象のユーザーID
     * @return 指定したユーザーのデータ
     */
    public UserResponse fetchUser(String userId) {
        // DBからレコードの取得
        // （SQLにすると：SELECT * FROM user WHERE user_id = 引数のuserId）
        Optional<User> record = userRepository.findById(userId);
        
        // レコードがなかった場合は例外
        if (record.isEmpty()) {
            throw new NoSuchElementException("指定したレコードがありません。 userId: " + userId);
        }
        User user = record.get();
        
        // Response用の型に変換して返却
        UserResponse userResponse = new UserResponse(user.getUserId(), user.getUserName(), user.getUserKana(), user.getDeleteFlg());
        return userResponse;
    }
    
    
    public UserResponseList fetchAllUser() {
        // DBからレコードの取得
        // （SQLにすると：SELECT * FROM user）
        List<User> records = userRepository.findAll();
        
        // Response用の型に変換して返却
        List<UserResponse> userResponses = records.stream()
            .map(user -> new UserResponse(user.getUserId(), user.getUserName(), user.getUserKana(), user.getDeleteFlg()))
            .toList();
        UserResponseList userResponseList = new UserResponseList(userResponses);
        return userResponseList;
    }
    
    public UserResponseList fetchAllActiveUser() {
        // DBからレコードの取得
        // （SQLにすると：SELECT * FROM user WHERE delete_flg = FALSE）
        List<User> records = userRepository.findAllByDeleteFlg(false);
        
        // Response用の型に変換して返却
        List<UserResponse> userResponses = records.stream()
            .map(user -> new UserResponse(user.getUserId(), user.getUserName(), user.getUserKana(), user.getDeleteFlg()))
            .toList();
        UserResponseList userResponseList = new UserResponseList(userResponses);
        return userResponseList;
    }
    
    
    @Transactional
    public void addUser(UserRequest userRequest) {
        // UserRequest → User の型の変換
        User user = new User(userRequest.getUserId(), userRequest.getUserName(), userRequest.getUserKana(), userRequest.getIsDelete());
        // DBに保存
        //  （SQLにすると：INSERT INTO user VALUES 〜）
        userRepository.save(user);
    }
    
    @Transactional
    public void updateUser(UserRequest userRequest) {
        // UserRequest → User の型の変換
        User user = new User(userRequest.getUserId(), userRequest.getUserName(), userRequest.getUserKana(), userRequest.getIsDelete());
        // DBに保存（主キーに紐づいて更新される）
        //  （SQLにすると：UPDATE user SET 〜 WHERE user_id = 引数のuserId）
        userRepository.save(user);
    }
    
    @Transactional
    public void deleteUser(String userId) {
        // DBのレコードの削除
        // （SQLにすると：DELETE FROM user WHERE user_id = 引数のuserId）
        userRepository.deleteById(userId);
        return;
    }
}
