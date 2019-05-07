package kr.ac.sunmoon.server;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import kr.ac.sunmoon.client.PML;
import kr.ac.sunmoon.client.SearchService;
import kr.ac.sunmoon.shared.Artist;
import kr.ac.sunmoon.shared.Song;
import java.sql.*;
import java.util.ArrayList;

public class SearchServiceImpl extends RemoteServiceServlet implements SearchService
{
	 ListBox month;
	 ListBox year;
	 
	 @Override
		public Song[] getSongs(String u_id, String year, String month) {
			// TODO Auto-generated method stub
			try{
				String url = "jdbc:mysql://localhost:3306/db?useSSL=false";
				String user = "root";
				String password = "";
				
				Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				
				if(month.length() == 1)
					month = "0" + month;
				String time = year + "-" + month;
				
				String sql = "select s_id from rel_song_user where rel_song_user.u_id = '" + u_id + 
							 "' and s_u_date >='" + time +"-" +"01"+ "'and s_u_date <= '"+ time + "-" + "31" + "';";
				
				ArrayList<Song> arraySongs = new ArrayList<Song>();
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next())
				{
					String s_id = rs.getString(1);
					Song song = new Song();
					song.setS_id(s_id);
					arraySongs.add(song);
				}
				
				if(arraySongs.size() == 0)
					return new Song[0];
				
				sql = "select s_title from song where";
				for(int i=0; i<arraySongs.size(); i++)
				{
					String s_id = arraySongs.get(i).getS_id();
					sql += " s_id='" + s_id + "'";
					if(i<arraySongs.size()-1)
						sql += " or";
				}
				
				rs = stmt.executeQuery(sql);
				int index = 0;
				while(rs.next())
				{
					String s_title = rs.getString(1);
					arraySongs.get(index).setS_title(s_title);
					index++;
				}
				
				Song[] songs = new Song[arraySongs.size()];
				for(int i=0; i<songs.length; i++)
					songs[i] = arraySongs.get(i);
				
				for(int i=0; i<songs.length; i++)
				{
					sql = "select a_id from rel_song_artist where s_id='" + songs[i].getS_id() + "';";
					rs = stmt.executeQuery(sql);
					ArrayList<String> a_ids = new ArrayList<String>();
					while(rs.next())
					{
						String a_id = rs.getString(1);
						a_ids.add(a_id);
					}
					
					for(int j=0; j<a_ids.size(); j++)
					{
						sql = "select a_name from artist where a_id='" + a_ids.get(j) + "';";
						rs = stmt.executeQuery(sql);
						while(rs.next())
						{
							String a_name = rs.getString(1);
							Artist artist = new Artist();
							artist.setA_id(a_ids.get(j));
							artist.setA_name(a_name);
							songs[i].addArtist(artist);
						}
					}
				}
				
				rs.close();
				stmt.close();
				con.close();
				
				return songs;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}
	 
	@Override
	public Song[] getSongs_year(String u_id, String year, String month) {
		
		
		try{
			String url = "jdbc:mysql://localhost:3306/db?useSSL=false";
			String user = "root";
			String password = "";
			
			Connection con = DriverManager.getConnection(url, user, password);
			Statement stmt = con.createStatement();
			
			if(month.length() == 1)
				month = "0" + month;
			String time = year + "-" + month;
			

			String sql = "select s_id from rel_song_user where rel_song_user.u_id = '" + u_id + 
					 "' and s_u_date >='" + year +"-01-" +"01"+ "'and s_u_date <= '"+ year + "-12-" + "31" + "';";
			
			ArrayList<Song> arraySongs = new ArrayList<Song>();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				String s_id = rs.getString(1);
				Song song = new Song();
				song.setS_id(s_id);
				arraySongs.add(song);
			}
			
			if(arraySongs.size() == 0)
				return new Song[0];
			
			sql = "select s_title from song where";
			for(int i=0; i<arraySongs.size(); i++)
			{
				String s_id = arraySongs.get(i).getS_id();
				sql += " s_id='" + s_id + "'";
				if(i<arraySongs.size()-1)
					sql += " or";
			}
			
			rs = stmt.executeQuery(sql);
			int index = 0;
			while(rs.next())
			{
				String s_title = rs.getString(1);
				arraySongs.get(index).setS_title(s_title);
				index++;
			}
			
			Song[] songs = new Song[arraySongs.size()];
			for(int i=0; i<songs.length; i++)
				songs[i] = arraySongs.get(i);
			
			for(int i=0; i<songs.length; i++)
			{
				sql = "select a_id from rel_song_artist where s_id='" + songs[i].getS_id() + "';";
				rs = stmt.executeQuery(sql);
				ArrayList<String> a_ids = new ArrayList<String>();
				while(rs.next())
				{
					String a_id = rs.getString(1);
					a_ids.add(a_id);
				}
				
				for(int j=0; j<a_ids.size(); j++)
				{
					sql = "select a_name from artist where a_id='" + a_ids.get(j) + "';";
					rs = stmt.executeQuery(sql);
					while(rs.next())
					{
						String a_name = rs.getString(1);
						Artist artist = new Artist();
						artist.setA_id(a_ids.get(j));
						artist.setA_name(a_name);
						songs[i].addArtist(artist);
					}
				}
			}
			
			rs.close();
			stmt.close();
			con.close();
			
			return songs;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
		
		
	}

	@Override
	public Song[] getSongs_all(String u_id, String year, String month) {
		// TODO Auto-generated method stub
		try{
			String url = "jdbc:mysql://localhost:3306/db?useSSL=false";
			String user = "root";
			String password = "";
			
			Connection con = DriverManager.getConnection(url, user, password);
			Statement stmt = con.createStatement();
			
			String sql = "select s_id from rel_song_user where rel_song_user.u_id = '" + u_id + 
						 "' and s_u_date >='" +  "0000-01-01"+ "'and s_u_date <= '"+ "9999-12-31" + "';";
			
			ArrayList<Song> arraySongs = new ArrayList<Song>();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				String s_id = rs.getString(1);
				Song song = new Song();
				song.setS_id(s_id);
				arraySongs.add(song);
			}
			
			if(arraySongs.size() == 0)
				return new Song[0];
			
			sql = "select s_title from song where";
			for(int i=0; i<arraySongs.size(); i++)
			{
				String s_id = arraySongs.get(i).getS_id();
				sql += " s_id='" + s_id + "'";
				if(i<arraySongs.size()-1)
					sql += " or";
			}
			
			rs = stmt.executeQuery(sql);
			int index = 0;
			while(rs.next())
			{
				String s_title = rs.getString(1);
				arraySongs.get(index).setS_title(s_title);
				index++;
			}
			
			Song[] songs = new Song[arraySongs.size()];
			for(int i=0; i<songs.length; i++)
				songs[i] = arraySongs.get(i);
			
			for(int i=0; i<songs.length; i++)
			{
				sql = "select a_id from rel_song_artist where s_id='" + songs[i].getS_id() + "';";
				rs = stmt.executeQuery(sql);
				ArrayList<String> a_ids = new ArrayList<String>();
				while(rs.next())
				{
					String a_id = rs.getString(1);
					a_ids.add(a_id);
				}
				
				for(int j=0; j<a_ids.size(); j++)
				{
					sql = "select a_name from artist where a_id='" + a_ids.get(j) + "';";
					rs = stmt.executeQuery(sql);
					while(rs.next())
					{
						String a_name = rs.getString(1);
						Artist artist = new Artist();
						artist.setA_id(a_ids.get(j));
						artist.setA_name(a_name);
						songs[i].addArtist(artist);
					}
				}
			}
			
			rs.close();
			stmt.close();
			con.close();
			
			return songs;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
		
	}


	@Override
	public Song[] getArtists(String u_id, String selected_txt) {
		// TODO Auto-generated method stub
		try{
			String url = "jdbc:mysql://localhost:3306/db?useSSL=false";
			String user = "root";
			String password = "";
			
			Connection con = DriverManager.getConnection(url, user, password);
			Statement stmt = con.createStatement();
			
			String sql = "select s_id from db.rel_song_artist where rel_song_artist.a_id ="
					+ "(select a_id from db.artist where artist.a_name like '" + "%" + selected_txt + "%" + "');";
			ArrayList<Song> arraySongs = new ArrayList<Song>();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				String s_id = rs.getString(1);
				Song song = new Song();
				song.setS_id(s_id);
				arraySongs.add(song);
			}
			
			if(arraySongs.size() == 0)
				return new Song[0];
			
			sql = "select s_title from song where";
			for(int i=0; i<arraySongs.size(); i++)
			{
				String s_id = arraySongs.get(i).getS_id();
				sql += " s_id='" + s_id + "'";
				if(i<arraySongs.size()-1)
					sql += " or";
			}
			
			rs = stmt.executeQuery(sql);
			int index = 0;
			while(rs.next())
			{
				String s_title = rs.getString(1);
				arraySongs.get(index).setS_title(s_title);
				index++;
			}
			
			Song[] songs = new Song[arraySongs.size()];
			for(int i=0; i<songs.length; i++)
				songs[i] = arraySongs.get(i);
			
			for(int i=0; i<songs.length; i++)
			{
				sql = "select a_id from rel_song_artist where s_id='" + songs[i].getS_id() + "';";
				rs = stmt.executeQuery(sql);
				ArrayList<String> a_ids = new ArrayList<String>();
				while(rs.next())
				{
					String a_id = rs.getString(1);
					a_ids.add(a_id);
				}
				
				for(int j=0; j<a_ids.size(); j++)
				{
					sql = "select a_name from artist where a_id='" + a_ids.get(j) + "';";
					rs = stmt.executeQuery(sql);
					while(rs.next())
					{
						String a_name = rs.getString(1);
						Artist artist = new Artist();
						artist.setA_id(a_ids.get(j));
						artist.setA_name(a_name);
						songs[i].addArtist(artist);
					}
				}
			}
			
			rs.close();
			stmt.close();
			con.close();
			
			return songs;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Song[] getSongs2(String u_id, String selected_txt) {
		// TODO Auto-generated method stub
		try{
			String url = "jdbc:mysql://localhost:3306/db?useSSL=false";
			String user = "root";
			String password = "";
			
			Connection con = DriverManager.getConnection(url, user, password);
			Statement stmt = con.createStatement();
			
			String sql = "select s_id from db.song where song.s_title like '" + "%" + selected_txt + "%" + "';";
			
			ArrayList<Song> arraySongs = new ArrayList<Song>();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				String s_id = rs.getString(1);
				Song song = new Song();
				song.setS_id(s_id);
				arraySongs.add(song);
			}
			
			if(arraySongs.size() == 0)
				return new Song[0];
			
			sql = "select s_title from song where";
			for(int i=0; i<arraySongs.size(); i++)
			{
				String s_id = arraySongs.get(i).getS_id();
				sql += " s_id='" + s_id + "'";
				if(i<arraySongs.size()-1)
					sql += " or";
			}
			
			rs = stmt.executeQuery(sql);
			int index = 0;
			while(rs.next())
			{
				String s_title = rs.getString(1);
				arraySongs.get(index).setS_title(s_title);
				index++;
			}
			
			Song[] songs = new Song[arraySongs.size()];
			for(int i=0; i<songs.length; i++)
				songs[i] = arraySongs.get(i);
			
			for(int i=0; i<songs.length; i++)
			{
				sql = "select a_id from rel_song_artist where s_id='" + songs[i].getS_id() + "';";
				rs = stmt.executeQuery(sql);
				ArrayList<String> a_ids = new ArrayList<String>();
				while(rs.next())
				{
					String a_id = rs.getString(1);
					a_ids.add(a_id);
				}
				
				for(int j=0; j<a_ids.size(); j++)
				{
					sql = "select a_name from artist where a_id='" + a_ids.get(j) + "';";
					rs = stmt.executeQuery(sql);
					while(rs.next())
					{
						String a_name = rs.getString(1);
						Artist artist = new Artist();
						artist.setA_id(a_ids.get(j));
						artist.setA_name(a_name);
						songs[i].addArtist(artist);
					}
				}
			}
			
			rs.close();
			stmt.close();
			con.close();
			
			return songs;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Song[] getSongs3(String u_id, String selected_txt) {
		// TODO Auto-generated method stub
		try{
			String url = "jdbc:mysql://localhost:3306/db?useSSL=false";
			String user = "root";
			String password = "";
			
			Connection con = DriverManager.getConnection(url, user, password);
			Statement stmt = con.createStatement();
			
			String sql = "select s_id from db.song where song.s_lyrics like '" + "%" + selected_txt + "%" + "';";
			
			ArrayList<Song> arraySongs = new ArrayList<Song>();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				String s_id = rs.getString(1);
				Song song = new Song();
				song.setS_id(s_id);
				arraySongs.add(song);
			}
			
			if(arraySongs.size() == 0)
				return new Song[0];
			
			sql = "select s_title from song where";
			for(int i=0; i<arraySongs.size(); i++)
			{
				String s_id = arraySongs.get(i).getS_id();
				sql += " s_id='" + s_id + "'";
				if(i<arraySongs.size()-1)
					sql += " or";
			}
			
			rs = stmt.executeQuery(sql);
			int index = 0;
			while(rs.next())
			{
				String s_title = rs.getString(1);
				arraySongs.get(index).setS_title(s_title);
				index++;
			}
			
			Song[] songs = new Song[arraySongs.size()];
			for(int i=0; i<songs.length; i++)
				songs[i] = arraySongs.get(i);
			
			for(int i=0; i<songs.length; i++)
			{
				sql = "select a_id from rel_song_artist where s_id='" + songs[i].getS_id() + "';";
				rs = stmt.executeQuery(sql);
				ArrayList<String> a_ids = new ArrayList<String>();
				while(rs.next())
				{
					String a_id = rs.getString(1);
					a_ids.add(a_id);
				}
				
				for(int j=0; j<a_ids.size(); j++)
				{
					sql = "select a_name from artist where a_id='" + a_ids.get(j) + "';";
					rs = stmt.executeQuery(sql);
					while(rs.next())
					{
						String a_name = rs.getString(1);
						Artist artist = new Artist();
						artist.setA_id(a_ids.get(j));
						artist.setA_name(a_name);
						songs[i].addArtist(artist);
					}
				}
			}
			
			rs.close();
			stmt.close();
			con.close();
			
			return songs;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	
	}
	}

