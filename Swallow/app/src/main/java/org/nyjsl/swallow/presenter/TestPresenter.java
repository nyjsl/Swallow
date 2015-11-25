package org.nyjsl.swallow.presenter;

import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.nyjsl.swallow.test.GitHub;
import org.nyjsl.swallow.test.GitHubService;
import org.nyjsl.swallow.test.User;
import org.nyjsl.swallow.views.TestMainView;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by weix01 on 2015-11-17.
 */
public class TestPresenter extends Presenter<TestMainView> {



    @Override
    public void start() {
        mView.showD();
        GitHubService service = GitHub.getInstance().getServiceImpl(GitHubService.class);
        final TestMainView mView = this.mView;
        mView.getUser(service, "nyjsl", new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                mView.disD();
                if (null != response && response.isSuccess()) {
                    mView.bindUser(response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mView.disD();
            }
        });
    }

    public void requestUser(GitHubService service, String user, Callback<User> callback) {
        Call<User> nyjsl = service.getUser(user);
        nyjsl.enqueue(callback);
    }

    public void bindUserView(User user,TextView tv,ImageView img) {
        tv.setText(user.getLogin());
        Picasso.with(mView.getContext()).load(user.getAvatar_url()).into(img);
    }

    @Override
    public void stop() {

    }

}
