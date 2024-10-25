package com.example.apptools.utils.soul.bean.avatar;

import java.util.List;

public class AvatarData {
	private List<AvatarsItem> maleAvatars;
	private List<AvatarsItem> femaleAvatars;

	public void setMaleAvatars(List<AvatarsItem> maleAvatars){
		this.maleAvatars = maleAvatars;
	}

	public List<AvatarsItem> getMaleAvatars(){
		return maleAvatars;
	}

	public void setFemaleAvatars(List<AvatarsItem> femaleAvatars){
		this.femaleAvatars = femaleAvatars;
	}

	public List<AvatarsItem> getFemaleAvatars(){
		return femaleAvatars;
	}
}