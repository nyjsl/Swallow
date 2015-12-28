package org.nyjsl.swallow.test;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Message;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.nyjsl.swallow.R;
import org.nyjsl.swallow.Swallow;
import org.nyjsl.swallow.presenter.TestPresenter;
import org.nyjsl.swallow.ui.BaseActivity;
import org.nyjsl.swallow.utils.NetWorkUtil;
import org.nyjsl.swallow.views.TestMainView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Callback;


public class TestMainActivity extends BaseActivity implements TestMainView {


    @Bind(R.id.test_tv) TextView  test_tv;
    @Bind(R.id.tv_test) TextView  tv_test;
    @Bind(R.id.show)  Button show;
    @Bind(R.id.change) Button  change;
    @Bind(R.id.duang) Button  duang;

    @Bind(R.id.lv) PullToRefreshListView ptr;

    @Bind(R.id.avatar) ImageView avatar;

    private TestPresenter presenter;


    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_test_main;
    }


    @Override
    protected void setListeners() {}

    @Override
    protected void init() {

        presenter = new TestPresenter();
        bindPresenter(presenter);
        initPtrFrame();
        getRunningProcesses();

    }

    @Override
    public void onConnect(NetWorkUtil.NetType type) {
        showToast(type.name());
    }

    @Override
    public void onDisConnect() {
        showToast("断网咯");
    }

    @OnClick(R.id.change)
    void change(){
        Swallow.Configuration.MATERIAL_DIALOG = !Swallow.Configuration.MATERIAL_DIALOG;
    }
    @OnClick(R.id.duang)
    void duang(){
        replaceFragment(R.id.container, TestFragment.newInstance());
    }

    @Override
    public void getUser(GitHubService service, String user, Callback<User> callback) {
        presenter.requestUser(service, user, callback);
    }

    @Override
    public void bindUser(User user) {
        presenter.bindUserView(user, tv_test, avatar);
    }

    @Override
    public void getRunningProcesses() {
        List<ActivityManager.RunningServiceInfo> runningServices = getRunningServiceInfos();
        presenter.bind(ptr, runningServices);
    }

    private List<ActivityManager.RunningServiceInfo> getRunningServiceInfos() {
        ActivityManager systemService = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        return systemService.getRunningServices(2000);
    }

    @Override
    public void initPtrFrame() {

        ptr.setMode(PullToRefreshBase.Mode.BOTH);
        ptr.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                List<ActivityManager.RunningServiceInfo> runningServices = getRunningServiceInfos();
                presenter.refresh(ptr, runningServices);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉加载更多
                List<ActivityManager.RunningServiceInfo> runningServices = getRunningServiceInfos();
                presenter.loadMore(ptr,runningServices);
            }
        });
    }


}
