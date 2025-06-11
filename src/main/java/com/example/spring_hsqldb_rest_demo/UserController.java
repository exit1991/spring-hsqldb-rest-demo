package com.example.spring_hsqldb_rest_demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserController {
    
    // UserService を依存性注入（DI）。
    // ここではメンバ変数に対して @Autowired を付与して行っているが、
    // メンバ変数に対する @Autowired は、現在では非推奨なので、
    // 実践では、「コンストラクタインジェクション」で行うようにする。
    @Autowired
    private UserService userService;
    
    /**
     * ユーザーデータ取得エンドポイント
     * @param userId 取得対象のユーザーID
     * @return 指定したユーザーのデータ
     */
    @GetMapping("/api/v1/users/{userId}")
    public UserResponse fetchUser(@PathVariable("userId") String userId) {
        UserResponse user = userService.fetchUser(userId);
        return user;
    }
    
    /**
     * ユーザーデータ登録エンドポイント
     * @param userRequest 登録するユーザーの情報
     * @return 処理結果
     */
    @PostMapping("/api/v1/users")
    public ProcessResultResponse addUser(@RequestBody UserRequest userRequest) {
        userService.addUser(userRequest);
        return new ProcessResultResponse(true, "ユーザー登録しました。 userId: " + userRequest.getUserId());
    }
    
    /**
     * ユーザーデータ更新エンドポイント
     * @param userRequest 更新するユーザーの情報
     * @return 処理結果
     */
    @PutMapping("/api/v1/users")
    public ProcessResultResponse updateUser(@RequestBody UserRequest userRequest) {
        userService.updateUser(userRequest);
        return new ProcessResultResponse(true, "ユーザーを更新しました。 userId: " + userRequest.getUserId());
    }
    
    /**
     * ユーザーデータ削除エンドポイント
     * @param userId 削除対象のユーザーID
     * @return 処理結果
     */
    @DeleteMapping("/api/v1/users/{userId}")
    public ProcessResultResponse deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return new ProcessResultResponse(true, "ユーザーを削除しました。 userId: " + userId);
    }
    
    /**
     * 全ユーザーデータ取得エンドポイント
     * @param activeOnly アクティブユーザーに限定するかを指定するクエリパラメータ
     * @return 全ユーザーのデータ
     */
    @GetMapping("/api/v1/users")
    public UserResponseList fetchAllUser(@RequestParam("activeOnly") Optional<Boolean> activeOnlyOpt) {
        // activeOnly のクエリパラメータがあり、 かつ true の場合はアクティブユーザーに限定して返却
        if (activeOnlyOpt.isPresent() && activeOnlyOpt.get().booleanValue()) {
            return userService.fetchAllActiveUser();
        }
        // すべてのユーザー情報を取得して返却
        return userService.fetchAllUser();
    }
}
