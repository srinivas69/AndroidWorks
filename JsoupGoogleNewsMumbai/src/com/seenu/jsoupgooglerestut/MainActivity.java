package com.seenu.jsoupgooglerestut;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;

public class MainActivity extends Activity {

	private String url = "http://www.google.co.in/search?hl=en&gl=in&tbm=nws&authuser=0&q=mumbai&oq=mumbai&gs_l=news-cc.1.0.43j0l9j43i53.4059.5709.0.8296.6.3.0.3.3.0.215.465.0j2j1.3.0...0.0...1ac.1.Cm8MPV3wsZ4";
	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mProgressDialog = new ProgressDialog(MainActivity.this);
		new GetOl().execute(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class GetOl extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			mProgressDialog.setTitle("Parsing");
			mProgressDialog.setMessage("please wait.....");
			// mProgressDialog.setIndeterminate(false);
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub

			// Connect to the web site
			try {
				Document document = Jsoup.connect(params[0]).get();
				Elements ol = document.select("ol");
				System.out.println("<ol>" + ol.size());
				
				for(Element olEle:ol){
					String idName = olEle.id();
					if(idName.equals("rso")){
						
						//System.out.println("Went through");
						Elements li = olEle.select("li");
						System.out.println("<li> : " +li.size() );
						
						int i = 1;
						for(Element liEle : li){
							/*Element divMainEle = liEle.select("div.ts._PY._HH._Hz").first();
							Element divClassEles = divMainEle.select("div.news-lead-article-and-snippet._OY").first();
							Element headlineEle = divClassEles.select("h3.r").first();*/
							Element aEle = liEle.select("a.l").first();
							String headline = aEle.text();
							String url = aEle.attr("href");
							System.out.println(i+". "+headline);
							System.out.println(url+"\n");
							i++;
							
						}
						//String divClassName = li.
					}
					System.out.println(idName);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mProgressDialog.dismiss();
		}

	}

}
