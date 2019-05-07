package kr.ac.sunmoon.client;

import java.util.ArrayList;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Text;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import kr.ac.sunmoon.shared.Artist;
import kr.ac.sunmoon.shared.Song;
public class PML implements EntryPoint {
	public ListBox year;
	public ListBox month;
	public ListBox lbox;
	public TextBox txt;
	
	
	private String selected_year = null;
	private String selected_month = null;
	private String u_id = "u_01";
	
	private String selected_lbox = null;
	private String selected_txt = null;
	
	private VerticalPanel vpresult = new VerticalPanel();
	private DecoratorPanel dp = new DecoratorPanel();
	
	public void onModuleLoad() {
		VerticalPanel vp = new VerticalPanel(); // vp Panel
		vp.setStyleName("vp");
		vp.getElement().getStyle().setWidth(1024, Unit.PX);
		RootPanel.get("main").add(vp);
		final Label resultlabel = new Label("");
		
		vpresult.setStyleName("vpresult");
		vpresult.getElement().getStyle().setWidth(463,Unit.PX);
		Label lbl = new Label(); // title
		lbl.setText("Previous Music List");
		lbl.setStyleName("PML");
		lbl.getElement().getStyle().setColor("darkgray");
		lbl.getElement().getStyle().setFontSize(50, Unit.PX);
		
		vp.add(lbl);		
		vp.setCellHorizontalAlignment(lbl, HorizontalPanel.ALIGN_CENTER);
		HorizontalPanel hp = new HorizontalPanel(); // hp panel
		hp.setStyleName("hp");
		vp.add(hp);
		vp.setCellHorizontalAlignment(hp, HorizontalPanel.ALIGN_CENTER);
		
		final ListBox year = new ListBox(); //year listbox
		hp.add(year);
		year.setStyleName("year");
		year.addItem("선택안함");
		year.addItem("2018");
		year.addItem("2017");
		year.addItem("2016");
		year.addItem("2015");
		year.addItem("2014");
		year.addItem("2013");
		year.getElement().getStyle().setHeight(28, Unit.PX);
		
		final ListBox month = new ListBox(); //month listbox
		hp.add(month);
		month.getElement().getStyle().setHeight(28, Unit.PX);
		month.addStyleName("month");
		month.addItem("선택안함");
		for(int j = 1 ; j<13 ; j++){
			month.addItem(j+"");			
		}

		Button bt = new Button("date select!");    // date search button and get Itemtext
		hp.add(bt);
		bt.addStyleName("bt");
		bt.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				selected_year = year.getSelectedItemText();
				selected_month = month.getSelectedItemText();
				
				if(selected_month == "선택안함"){
					resultlabel.setText(selected_year + "년도 노래 결과입니다.");
					
					if(selected_year == "선택안함"){
						resultlabel.setText("");
					}
					SearchServiceAsync service = GWT.create(SearchService.class);
					service.getSongs_year(u_id, selected_year, selected_month, new AsyncCallback<Song[]>() {
					
					@Override
					public void onSuccess(Song[] result) {
						// TODO Auto-generated method stub
						
						int count = vpresult.getWidgetCount();
						for(int i=0; i<count; i++)
							vpresult.remove(0);
						
						for(int i=0; i<result.length; i++)
						{
							String titles = "노래제목 : " + result[i].getS_title() + " [";
							ArrayList<Artist> artists = result[i].getArtists();
							for(int j=0; j<artists.size(); j++)
							{
								titles += "가수 :" + artists.get(j).getA_name();
								if(j != artists.size()-1)
									titles += ", ";
							}
							titles += "]";
							Label lbl = new Label(titles);
							
							if(selected_txt == ""){
								lbl.setText("");
							}
							vpresult.add(lbl);
							
						}
						if(result.length == 0)
						{
							Label lbl = new Label("검색 결과가 존재하지 않습니다.");
							vpresult.add(lbl);
							}
					}
					
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
					}
				});
				}
				if(selected_year != "선택안함" && selected_month != "선택안함")
					resultlabel.setText(selected_year +"년 "+ selected_month + "월의 검색 결과입니다.");
				SearchServiceAsync service = GWT.create(SearchService.class);
				service.getSongs(u_id, selected_year, selected_month, new AsyncCallback<Song[]>() {
					
					@Override
					public void onSuccess(Song[] result) {
						// TODO Auto-generated method stub
						
						int count = vpresult.getWidgetCount();
						for(int i=0; i<count; i++)
							vpresult.remove(0);
						
						for(int i=0; i<result.length; i++)
						{
							String titles = "노래제목 : " + result[i].getS_title() + " [";
							ArrayList<Artist> artists = result[i].getArtists();
							for(int j=0; j<artists.size(); j++)
							{
								titles += "가수 :" + artists.get(j).getA_name();
								if(j != artists.size()-1)
									titles += ", ";
							}
							titles += "]";
							Label lbl = new Label(titles);

							vpresult.add(lbl);
						}
						
						if(result.length == 0)
						{
							Label lbl = new Label("검색 결과가 존재하지 않습니다.");
							vpresult.add(lbl);
						}
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
					}
				});
				
				
			}
		});
		
		
		
		Button all_song_bt = new Button("all select!");
		hp.add(all_song_bt);
		all_song_bt.addStyleName("all");
		all_song_bt.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				resultlabel.setText("전체 노래 검색결과 입니다.");
				SearchServiceAsync service = GWT.create(SearchService.class);
				service.getSongs_all(u_id, selected_year, selected_month, new AsyncCallback<Song[]>() {
					
					@Override
					public void onSuccess(Song[] result) {
						// TODO Auto-generated method stub
						
						int count = vpresult.getWidgetCount();
						for(int i=0; i<count; i++)
							vpresult.remove(0);
						
						for(int i=0; i<result.length; i++)
						{
							String titles = "노래제목 : " + result[i].getS_title() + " [";
							ArrayList<Artist> artists = result[i].getArtists();
							for(int j=0; j<artists.size(); j++)
							{
								titles += "가수 :" + artists.get(j).getA_name();
								if(j != artists.size()-1)
									titles += ", ";
							}
							titles += "]";
							Label lbl = new Label(titles);
							vpresult.add(lbl);
						}
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
					}
				});
			}
		});
		
		
		HorizontalPanel hp2 = new HorizontalPanel(); // hp panel
		hp.setStyleName("hp2");
		vp.add(hp2);
		
		final ListBox lbox = new ListBox(); // search box
		lbox.addItem("곡");
		lbox.addItem("가수");
		lbox.addItem("가사");
		hp2.add(lbox);
		lbox.setStyleName("checkbox");

		
		
		final TextBox txt = new TextBox(); // text box
		hp2.add(txt);
		txt.addStyleName("txt");
		txt.getElement().setPropertyString("placeholder", "please enter title or artist or lyrics");
		
		PushButton search = new PushButton(new Image("Image/search.png"));
		search.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				selected_lbox = lbox.getSelectedItemText();
				selected_txt = txt.getText();
					if(txt.getText() == ""){
				
						Window.alert("검색어를 입력해주세요.");
						resultlabel.setText("");
						vpresult.clear();
				}
					else
						resultlabel.setText((txt.getText()+"(으)로 검색한 결과입니다."));
					
					
					if(selected_lbox == "곡"){
						
						SearchServiceAsync service = GWT.create(SearchService.class);
						service.getSongs2(u_id, selected_txt, new AsyncCallback<Song[]>() {
						
						@Override
						public void onSuccess(Song[] result) {
							// TODO Auto-generated method stub
							
							int count = vpresult.getWidgetCount();
							for(int i=0; i<count; i++)
								vpresult.remove(0);
							
							for(int i=0; i<result.length; i++)
							{
								String titles = "노래제목 : " + result[i].getS_title() + " [";
								ArrayList<Artist> artists = result[i].getArtists();
								for(int j=0; j<artists.size(); j++)
								{
									titles += "가수 :" + artists.get(j).getA_name();
									if(j != artists.size()-1)
										titles += ", ";
								}
								titles += "]";
								Label lbl = new Label(titles);
								
								if(selected_txt == ""){
									lbl.setText("");
								}
								vpresult.add(lbl);
							}
							if(result.length == 0)
							{
								Label lbl = new Label("검색 결과가 존재하지 않습니다.");
								vpresult.add(lbl);
								}
						}
						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
						}
					});
					}
						if(selected_lbox == "가수"){
						
						SearchServiceAsync service = GWT.create(SearchService.class);
						service.getArtists(u_id, selected_txt, new AsyncCallback<Song[]>() {
						
						@Override
						public void onSuccess(Song[] result) {
							// TODO Auto-generated method stub
							
							int count = vpresult.getWidgetCount();
							for(int i=0; i<count; i++)
								vpresult.remove(0);
							
							for(int i=0; i<result.length; i++)
							{
								String titles = "노래제목 : " + result[i].getS_title() + " [";
								ArrayList<Artist> artists = result[i].getArtists();
								for(int j=0; j<artists.size(); j++)
								{
									titles += "가수 :" + artists.get(j).getA_name();
									if(j != artists.size()-1)
										titles += ", ";
								}
								titles += "]";
								Label lbl = new Label(titles);
								
								if(selected_txt == ""){
									lbl.setText("");
								}
								vpresult.add(lbl);
							}
							if(result.length == 0)
							{
								Label lbl = new Label("검색 결과가 존재하지 않습니다.");
								vpresult.add(lbl);
								}
						}
						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
						}
					});
					}

					if(selected_lbox == "가사"){
						
						SearchServiceAsync service = GWT.create(SearchService.class);
						service.getSongs3(u_id, selected_txt, new AsyncCallback<Song[]>() {
						
						@Override
						public void onSuccess(Song[] result) {
							// TODO Auto-generated method stub
							
							int count = vpresult.getWidgetCount();
							for(int i=0; i<count; i++)
								vpresult.remove(0);
							
							for(int i=0; i<result.length; i++)
							{
								String titles = "노래제목 : " + result[i].getS_title() + " [";
								ArrayList<Artist> artists = result[i].getArtists();
								for(int j=0; j<artists.size(); j++)
								{
									titles += "가수 :" + artists.get(j).getA_name();
									if(j != artists.size()-1)
										titles += ", ";
								}
								titles += "]";
								Label lbl = new Label(titles);
								
								if(selected_txt == ""){
									lbl.setText("");
								}
								vpresult.add(lbl);
							}
							if(result.length == 0)
							{
								Label lbl = new Label("검색 결과가 존재하지 않습니다.");
								vpresult.add(lbl);
								}
						}
						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
						}
					});
					}
					
			}
		});
//		css
		hp2.add(search);
		vp.add(resultlabel);
		vp.add(dp);
		dp.add(vpresult);
		vp.setCellHorizontalAlignment(hp2, HorizontalPanel.ALIGN_CENTER);
		vp.setCellHorizontalAlignment(dp, HorizontalPanel.ALIGN_CENTER);
		vp.setCellHorizontalAlignment(resultlabel, HorizontalPanel.ALIGN_CENTER);
		hp.getElement().getStyle().setMarginTop(30, Unit.PX);
		hp.getElement().getStyle().setMarginBottom(30, Unit.PX);	
		hp.getElement().getStyle().setBorderColor("darkgray");
		hp2.getElement().getStyle().setBorderStyle(BorderStyle.HIDDEN);
		vp.getElement().getStyle().setBorderStyle(BorderStyle.HIDDEN);
		bt.getElement().getStyle().setFontStyle(FontStyle.ITALIC);
		all_song_bt.getElement().getStyle().setFontStyle(FontStyle.ITALIC);
		lbl.getElement().getStyle().setFontStyle(FontStyle.ITALIC);
		vpresult.getElement().getStyle().setBackgroundColor("white");
		dp.getElement().getStyle().setBackgroundColor("white");
		dp.getElement().getStyle().setBackgroundColor("white");
		vp.getElement().getStyle().setBackgroundColor("ALICEBLUE");
		year.getElement().getStyle().setBorderStyle(BorderStyle.HIDDEN);
		month.getElement().getStyle().setBorderStyle(BorderStyle.HIDDEN);
		lbox.getElement().getStyle().setBorderStyle(BorderStyle.HIDDEN);
		txt.getElement().getStyle().setBorderStyle(BorderStyle.HIDDEN);

	}
}

