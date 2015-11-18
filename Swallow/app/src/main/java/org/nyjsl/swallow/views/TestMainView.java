package org.nyjsl.swallow.views;

import org.nyjsl.swallow.test.GitHubService;
import org.nyjsl.swallow.test.User;

import retrofit.Callback;

/**
 * Created by weix01 on 2015-11-18.
 */
public interface TestMainView  extends  BaseView {

    void getUser(GitHubService service,String user, Callback<User> callback);

    void bindUser(User user);

}
