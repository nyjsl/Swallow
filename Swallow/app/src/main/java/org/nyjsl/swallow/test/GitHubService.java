package org.nyjsl.swallow.test;

import com.squareup.okhttp.RequestBody;

import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

public interface GitHubService {
	
	// https://api.github.com/users/octocat
	@GET("/users/{user}")
	Call<User> user(@Path("user")String user);
	// https://api.github.com/users/octocat/repos
	@GET("/users/{user}/repos")
	Call<List<Repo>> listRepos(@Path("user")String user);
	
	@GET("/group/{id}/users")
	Call<User> groupList(@Path("id") int groupid,@Query("sort") String sort);
	
	@GET("/group/{id}/users")
	Call<User> groupList(@Path("id") int groupid,@QueryMap Map<String,String> options);
	
	@POST("/users/new")
	Call<User> createUser(@Body User user);	
	
	@FormUrlEncoded
	@POST("/user/edit")
	Call<User> updateUser(@Field("first_name") String first, @Field("last_name") String last);
	
	@Multipart
	@PUT("/user/photo")
	Call<User> updateUser(@Part("photo") RequestBody photo, @Part("description") RequestBody description);

	@Headers({
	    "Accept: application/vnd.github.v3.full+json",
	    "User-Agent: Retrofit-Sample-App"
	})
	@GET("/users/{username}")
	Call<User> getUser(@Path("username") String username);
	
	@GET("/user")
	Call<User> getUser1(@Header("Authorization") String authorization);
	
}
