package com.yourtube.c.popupplayer;

import android.content.*;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.util.*;
import android.view.*;
import android.view.inputmethod.*;
import android.webkit.*;
import android.widget.*;

import com.squareup.picasso.*;

import java.util.*;

public class SearchActivity extends AppCompatActivity {


    private EditText searchInput;
    private ListView videosFound;

    private VideoEnabledWebView webView;
    private VideoEnabledWebChromeClient webChromeClient;

    private WebView webview1;




    //private FrameLayout mCustomViewContainer;
    //private WebChromeClient.CustomViewCallback mCustomViewCallback;
    //private LinearLayout mContentView;

    FrameLayout.LayoutParams COVER_SCREEN_GRAVITY_CENTER = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

    private PopupWindow mPopupWindow;
    private LayoutInflater mLayoutInflater;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

      searchInput = (EditText)findViewById(R.id.search_input);
        videosFound = (ListView)findViewById(R.id.videos_found);

        handler = new Handler();



        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    searchOnYoutube(v.getText().toString());
                    return false;
                }
                return true;
            }
        });

       addClickListener();

    }

    private List<VideoItem> searchResults;

    private void searchOnYoutube(final String keywords){
        new Thread(){
            public void run(){
                YoutubeConnector yc = new YoutubeConnector(SearchActivity.this);
                searchResults = yc.search(keywords);
                handler.post(new Runnable(){
                    public void run(){
                        updateVideosFound();
                    }
                });
            }
        }.start();
    }


    private void updateVideosFound(){
        ArrayAdapter<VideoItem> adapter = new ArrayAdapter<VideoItem>(getApplicationContext(), R.layout.video_item, searchResults){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.video_item, parent, false);
                }
                ImageView thumbnail = (ImageView)convertView.findViewById(R.id.video_thumbnail);
                TextView title = (TextView)convertView.findViewById(R.id.video_title);
                TextView description = (TextView)convertView.findViewById(R.id.video_description);

                VideoItem searchResult = searchResults.get(position);

                Picasso.with(getApplicationContext()).load(searchResult.getThumbnailURL()).into(thumbnail);
                title.setText(searchResult.getTitle());
                description.setText(searchResult.getDescription());
                return convertView;
            }
        };

        videosFound.setAdapter(adapter);
    }

   private void addClickListener(){
     videosFound.setOnItemClickListener(new AdapterView.OnItemClickListener() {

           @Override
           public void onItemClick(AdapterView<?> av, View v, int pos,
                                   long id) {

               String r = "'" + searchResults.get(pos).getId() + "'";

               String m = "'" + searchResults.get(pos).getId() + "?rel=0&autohide=1&showinfo=0" +  "'";

               String n = "'" + searchResults.get(pos).getId() + "?modestbranding=1&controls=0;autohide=1&amp;showinfo=0&amp;" +  "'";

               String z = "'" + searchResults.get(pos).getId() + "?modestbranding=1&controls=0&autohide=1&amp&showinfo=0&amp;" +  "'";

             //  final RelativeLayout relative = (RelativeLayout)findViewById(R.id.relative);

            //   VideoView videoView =(VideoView)findViewById(R.id.videoView1);
             //  videoView.setVideoURI();
             //  MediaController mediaController= new MediaController(new );

               final LinearLayout Linear = (LinearLayout)findViewById(R.id.relative);


               mLayoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
               final ViewGroup container = (ViewGroup) mLayoutInflater.inflate(R.layout.textview,null);

           webView = (VideoEnabledWebView)container.findViewById(R.id.webView);

               webview1 = (WebView)container.findViewById(R.id.webView1) ;
               webview1.getSettings().setJavaScriptEnabled(true);
               webview1.getSettings().setDomStorageEnabled(true);
               webview1.setWebChromeClient(webChromeClient);
               webview1.setWebViewClient(new InsideWebViewClient());
               webview1.setPadding(0,0,0,0);




               WebSettings settings = webView.getSettings();

             // webView.getSettings().setJavaScriptEnabled(true);
              // webView.getSettings().setDomStorageEnabled(true);

              // webChromeClient = new VideoEnabledWebChromeClient(Linear,container,v, webView){

               //   @Override
                //   public void onProgressChanged(WebView view, int progress)
                //  {
                //       // Your code...
                //  }
              // };
             // webView.setWebChromeClient(webChromeClient);
             //  webView.setWebViewClient(new InsideWebViewClient());
              // webView.setPadding(0,0,0,0);




               String  video =

                       "<div id=\"player\"></div>" +
                       "    <script>" +
                       "      var tag = document.createElement('script');" +
                       "      tag.src = \"https://www.youtube.com/iframe_api\";" +
                       "      var firstScriptTag = document.getElementsByTagName('script')[0];" +
                       "      firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);" +
                       "      var player;" +
                       "      function onYouTubeIframeAPIReady() {" +
                       "        player = new YT.Player('player', {" +
                       "        height: '100%'," +
                       "          width: '100%'," +
                       "          videoId: " +  z + "  ," +
                       "          events: {" +
                       "            'onReady': onPlayerReady," +
                       "            'onStateChange': onPlayerStateChange" +
                       "          }" +
                       "        });" +
                       "      }" +
                       "      function onPlayerReady(event) {" +
                       "        event.target.playVideo();" +
                       "      }" +
                       "      var done = false;" +
                       "      function onPlayerStateChange(event) {" +
                       "        if (event.data == YT.PlayerState.PLAYING && !done) {" +
                    //   "          setTimeout(stopVideo, 6000);" +
                       "          done = true;" +
                       "        }" +
                       "      }" +
                       "      function stopVideo() {" +
                       "        player.stopVideo();" +
                       "      }" +
                       "    </script>";

               String h = "<script Language=\"JavaScript\">\n" +
                       "var width=678;\n" +
                       "var height=500;\n" +
                       "self.moveTo((screen.availwidth-width)/2,(screen.availheight-height)/2);\n" +
                       "self.resizeTo(width,height);\n" +
                       "</script>";


               String customHtml = "<html>" + "<head>\n" +
           //            "<script language=\"JavaScript\" type=\"text/javascript\">\n" +
                  //     "<!--\n" +
           //            "window.resizeTo(80,80);\n" +
                   //    "//-->\n" +
          //             "</script>"
            //           "<style>\n" +
            //           "div {\n" +
            //           "    width: 500px;\n" +
            //           "    height: 300px;\n" +
            //           "    border: 3px solid #73AD21;\n" +
           //            "}\n" +
           //            "</style>\n" +
           //            "</head>" +
                      "<body>" +

                       "<script Language=\"JavaScript\">\n" +
                      "var width=150;\n" +
                    "var height=300;\n" +
                      "self.moveTo((screen.availwidth-width)/2,(screen.availheight-height)/2);\n" +
                      "self.resizeTo(width,height);\n" +
                      "</script>"


                    /*add the video here*/
                       + video
                 //     + "<b><font size=\""
                  //    + 5 + "\">"
                  //     + "<div id='wrap'>"
                   //    + "Test title"
                    //  + "</font></b>"
                    //  + "<font size=\"" + 3 + "\"><br><br><i>Detail1: " + "Test" + "<br/>" + "new_date" + "<br />Detail2: "+ "Test" +"</i></font><br><br>"
                    //  + "<font size=\"" + 3 + "\">"
                  //    + "Detail content" + "</font>"
                //       + "<br><br><br>"
                       +
                       "</body></html>";

               String R = "<!DOCTYPE html>\n" +
                       "<html>\n" +
                       "    <head>\n" +
                //       "        <title>Hello World</title>\n" +
                       "        <style>\n" +
                       "            html, body {\n" +
                       "                width: 100;\n" +
                       "                height: 100;\n" +
                       "                margin: 0;\n" +
                       "                padding: 0;\n" +

              //         "                background-color: green;\n" +
              //         "            }\n" +
               //        "            #container {\n" +
                //       "                width: inherit;\n" +
                //       "                height: inherit;\n" +
                //       "                margin: 0;\n" +
                //       "                padding: 0;\n" +
                //       "                background-color: pink;\n" +
                //       "            }\n" +
                //       "            h1 {\n" +
                //       "                margin: 0;\n" +
                //       "                padding: 0;\n" +
                //       "            }\n" +
                       "        </style>\n" +
                       "    </head>\n" +
                       "    <body>\n" + video +
                  //     "        <div id=\"container\">\n" +
                  //     "            <h1>Hello World</h1>\n" +
                  //     "        </div>\n" +
                       "    </body>\n";

               String Z = "<!DOCTYPE html>\n" +
                       "<html>\n" +
                       "    <head>\n" +
                       //       "        <title>Hello World</title>\n" +
                       "        <style>\n" +
                       "          head,  body, " +
                       "html" +
                       " {\n" +
                       "                width: 100;\n" +
                       "                height: 100;\n" +
                       "                margin: 0;\n" +
                       "                padding: 0;\n" +
                         "               border: 0;\n" +
                       "                outline: 0;\n "+

               //        "background: transparent;" +


                    //          "                background-color: green;\n" +
                               "            }\n" +
                       //        "            #container {\n" +
                       //       "                width: inherit;\n" +
                       //       "                height: inherit;\n" +
                       //       "                margin: 0;\n" +
                       //       "                padding: 0;\n" +
                       //       "                background-color: pink;\n" +
                       //       "            }\n" +
                       //       "            h1 {\n" +
                       //       "                margin: 0;\n" +
                       //       "                padding: 0;\n" +
                      //        "            }\n" +
                       "        </style>\n" + video +
                       "    </head>\n" +
                   "    <body>\n" +
           //    +
                       //     "        <div id=\"container\">\n" +
                       //     "            <h1>Hello World</h1>\n" +
                       //     "        </div>\n" +
                       "    </body>" +
                      "</html>\n";


              // webView.loadDataWithBaseURL("https://www.youtube.com", R, "text/html; charset=utf-8", "UTF-8", null);
              // webview1.loadDataWithBaseURL("https://www.youtube.com", Z, "text/html; charset=utf-8", "UTF-8", null);

              webview1.loadData(Z, "text/html; charset=utf-8; application/javascript ", "UTF-8");

               //webView.loadUrl("http://m.youtube.com/watch?v=5GL9JoH4Sws");




               mPopupWindow = new PopupWindow(container, ViewGroup.LayoutParams.WRAP_CONTENT,
                       ViewGroup.LayoutParams.WRAP_CONTENT,false);
               mPopupWindow.showAtLocation(Linear,Gravity.NO_GRAVITY,100,100);

               container.setOnTouchListener(new View.OnTouchListener() {
                   @Override
                   public boolean onTouch(View v, MotionEvent event) {
                       mPopupWindow.dismiss();
                       return false;
                   }
               });



               container.setOnTouchListener(new View.OnTouchListener() {
                   int orgX, orgY;
                   int offsetX, offsetY;

                   @Override
                   public boolean onTouch(View v, MotionEvent event) {
                       switch (event.getAction()) {
                           case MotionEvent.ACTION_DOWN:
                               orgX = (int) event.getX();
                               orgY = (int) event.getY();
                               break;
                           case MotionEvent.ACTION_MOVE:
                               offsetX = (int)event.getRawX() - orgX;
                               offsetY = (int)event.getRawY() - orgY;
                               mPopupWindow.update(offsetX, offsetY, 0, 0, false);
                               break;
                       }
                       return true;
                   }});







              //  Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
               // intent.putExtra("VIDEO_ID", searchResults.get(pos).getId());
              //  startActivity(intent);
            }

        });



       }
    }

class InsideWebViewClient extends WebViewClient {
    @Override
    // Force links to be opened inside WebView and not in Default Browser
    // Thanks http://stackoverflow.com/a/33681975/1815624
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }


}
