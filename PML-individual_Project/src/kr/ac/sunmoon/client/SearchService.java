package kr.ac.sunmoon.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import kr.ac.sunmoon.shared.Song;

@RemoteServiceRelativePath("search")
public interface SearchService  extends RemoteService
{
	public Song[] getSongs(String u_id, String year, String month);
	
	Song[] getSongs_year(String u_id, String year, String month);
	
	Song[] getArtists(String u_id, String selecetd_txt);

	Song[] getSongs2(String u_id, String selected_txt);

	Song[] getSongs3(String u_id, String selected_txt);

	Song[] getSongs_all(String u_id, String selected_year, String selected_month);
}
