package org.nyjsl.swallow.presenter;

import android.app.ActivityManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;

import org.nyjsl.swallow.test.GitHub;
import org.nyjsl.swallow.test.GitHubService;
import org.nyjsl.swallow.test.User;
import org.nyjsl.swallow.views.TestMainView;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by weix01 on 2015-11-17.
 */
public class TestPresenter extends Presenter<TestMainView> {


    private ArrayAdapter<String> adapter;

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

    public void bind(PullToRefreshListView lv,List<ActivityManager.RunningServiceInfo> serviceInfos){
        if(null != serviceInfos){

            adapter = new ArrayAdapter<String>(mView.getContext(),android.R.layout.simple_expandable_list_item_1);
            for(ActivityManager.RunningServiceInfo s: serviceInfos){
                adapter.add("process :" + s.process + " started:" + s.started + "class:" + s.service.getClassName());
            }
            lv.getRefreshableView().setAdapter(adapter);
        }
    }

    public void refresh(final PullToRefreshListView lv,List<ActivityManager.RunningServiceInfo> serviceInfos){

        bind(lv, serviceInfos);
        lv.postDelayed(new Runnable() {
            @Override
            public void run() {
                lv.onRefreshComplete();
            }
        },1000);
    }

    public void loadMore(final PullToRefreshListView lv,List<ActivityManager.RunningServiceInfo> serviceInfos){
        if(null == adapter){
            refresh(lv, serviceInfos);
        }else{
            for(ActivityManager.RunningServiceInfo s: serviceInfos){
                adapter.add("process :" + s.process + " started:" + s.started + "class:" + s.service.getClassName());
            }
            adapter.notifyDataSetChanged();
        }
        lv.postDelayed(new Runnable() {
            @Override
            public void run() {
                lv.onRefreshComplete();
            }
        }, 1000);

    }



    @Override
    public void stop() {

    }

}
