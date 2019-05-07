package kr.ac.sunmoon.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Song implements IsSerializable
{
	private String s_id;
	private String s_title;
	private String s_lyrics;
	
	private ArrayList<Artist> artists;
	
	public String getS_id() {
		return s_id;
	}
	public void setS_id(String s_id) {
		this.s_id = s_id;
	}
	public String getS_title() {
		return s_title;
	}
	public void setS_title(String s_title) {
		this.s_title = s_title;
	}
	public String getS_lyrics() {
		return s_lyrics;
	}
	public void setS_lyrics(String s_lyrics) {
		this.s_lyrics = s_lyrics;
	}
	
	public void addArtist(Artist artist)
	{
		if(artists == null)
			artists = new ArrayList<Artist>();
		this.artists.add(artist);
	}
	
	public ArrayList<Artist> getArtists()
	{
		return artists;
	}
	
}
