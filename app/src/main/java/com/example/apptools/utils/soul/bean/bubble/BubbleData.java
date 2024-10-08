package com.example.apptools.utils.soul.bean.bubble;

import java.util.List;

public class BubbleData {
	private int skinCnt;
	private boolean pageEnd;
	private boolean haveBubbling;
	private List<BubblingListItem> bubblingList;

	public void setSkinCnt(int skinCnt){
		this.skinCnt = skinCnt;
	}

	public int getSkinCnt(){
		return skinCnt;
	}

	public void setPageEnd(boolean pageEnd){
		this.pageEnd = pageEnd;
	}

	public boolean isPageEnd(){
		return pageEnd;
	}

	public void setHaveBubbling(boolean haveBubbling){
		this.haveBubbling = haveBubbling;
	}

	public boolean isHaveBubbling(){
		return haveBubbling;
	}

	public void setBubblingList(List<BubblingListItem> bubblingList){
		this.bubblingList = bubblingList;
	}

	public List<BubblingListItem> getBubblingList(){
		return bubblingList;
	}
}