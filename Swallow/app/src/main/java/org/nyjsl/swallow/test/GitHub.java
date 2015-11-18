package org.nyjsl.swallow.test;

import org.nyjsl.swallow.http.retrofit.BaseAPI;


public class GitHub extends BaseAPI<GitHubService>{
	

	private static GitHub INSTANCE = null;

	static {
		baseURL = "https://api.github.com";
	}

	public static GitHub getInstance(){
		if(null == INSTANCE){
			synchronized (GitHub.class) {
				if(null == INSTANCE){
					INSTANCE = new GitHub();
				}
			}
		}
		return INSTANCE;
	}


}
