package kr.ac.sunmoon.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TextBox;

import kr.ac.sunmoon.shared.Artist;
import kr.ac.sunmoon.shared.Song;

public interface SearchServiceAsync {

	void getSongs(String u_id, String year, String month, AsyncCallback<Song[]> callback);
	
	void getSongs_year(String u_id, String year, String month, AsyncCallback<Song[]> callback);

	void getSongs2(String u_id, String selected_txt, AsyncCallback<Song[]> Callback);

	void getSongs3(String u_id, String selected_txt, AsyncCallback<Song[]> asyncCallback);

	void getSongs_all(String u_id, String selected_year, String selected_month, AsyncCallback<Song[]> asyncCallback);

	void getArtists(String u_id, String selecetd_txt, AsyncCallback<Song[]> asyncCallback);
}
