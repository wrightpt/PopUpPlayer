package com.yourtube.c.popupplayer;

import android.annotation.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v7.app.AppCompatActivity;
import android.util.*;
import android.view.*;
import android.view.inputmethod.*;
import android.webkit.*;
import android.widget.*;


import com.google.android.gms.appindexing.*;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.*;
import com.google.android.gms.common.api.*;

import com.squareup.picasso.*;

import java.util.*;

public  class SearchActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {


    private EditText searchInput;
    private ListView videosFound;

    private VideoEnabledWebView webView;
    private VideoEnabledWebChromeClient webChromeClient;

    private WebView webview1;

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;




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


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)

                // .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) )

                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(AppIndex.API).build();



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

           @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
           @Override
           public void onItemClick(AdapterView<?> av, View v, int pos,
                                   long id) {

               String r = "'" + searchResults.get(pos).getId() + "'";

               String m = "'" + searchResults.get(pos).getId() + "?rel=0&autohide=1&showinfo=0" +  "'";

               String m1 =  "'" + searchResults.get(pos).getId() + "?modestbranding=1&controls=0" +  "'";

               String n = "'" + searchResults.get(pos).getId() + "?modestbranding=1&controls=0;autohide=1&amp;showinfo=0&amp;" +  "'";

               String z = "'" + searchResults.get(pos).getId() + "?modestbranding=1&controls=0&autohide=1&amp&showinfo=0&amp;" +  "'";

             //  final RelativeLayout relative = (RelativeLayout)findViewById(R.id.relative);

            //   VideoView videoView =(VideoView)findViewById(R.id.videoView1);
             //  videoView.setVideoURI();
             //  MediaController mediaController= new MediaController(new );

               final LinearLayout Linear = (LinearLayout)findViewById(R.id.relative);


               mLayoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
               final ViewGroup container = (ViewGroup) mLayoutInflater.inflate(R.layout.textview,null);

         //  webView = (VideoEnabledWebView)container.findViewById(R.id.webView);


              webview1 = (WebView)container.findViewById(R.id.webView1) ;
              // webView.getSettings().setJavaScriptEnabled(true);
            //   webView.getSettings().setDomStorageEnabled(true);
            //   webView.setWebChromeClient(webChromeClient);
             //  webView.setWebViewClient(new InsideWebViewClient());
            //   webView.setPadding(0,0,0,0);
             //  webView.getSettings().setMediaPlaybackRequiresUserGesture(false);




               webview1 = (WebView)container.findViewById(R.id.webView1) ;
               webview1.getSettings().setJavaScriptEnabled(true);
               webview1.getSettings().setDomStorageEnabled(true);
              // webview1.setWebChromeClient(webChromeClient);
              // webview1.setWebViewClient(new InsideWebViewClient());
               webview1.setPadding(0,0,0,0);
               webview1.getSettings().setMediaPlaybackRequiresUserGesture(false);




              // WebSettings settings = webView.getSettings();

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
                     //  "       event.target.loadVideoById("    +
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

               String lazyload = "<!DOCTYPE html>\n" +
                       "<!--[if lt IE 7]>      <html class=\"no-js lt-ie9 lt-ie8 lt-ie7\"> <![endif]-->\n" +
                       "<!--[if IE 7]>         <html class=\"no-js lt-ie9 lt-ie8\"> <![endif]-->\n" +
                       "<!--[if IE 8]>         <html class=\"no-js lt-ie9\"> <![endif]-->\n" +
                       "<!--[if gt IE 8]><!--> <html class=\"no-js\"> <!--<![endif]-->\n" +
                       "    <head>\n" +
                       "        <meta charset=\"utf-8\">\n" +
                       "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                       "        <title>Fluid width - lazyYT jQuery plugin demo</title>\n" +
                       "        <meta name=\"description\" content=\"\">\n" +
                       "\n" +
                       "        <link rel=\"stylesheet\" href=\"../lazyYT.css\">\n" +
                       "\n" +
                       "        <style>\n" +
                       "            body {\n" +
                       "                background-color: #252525;\n" +
                       "            }\n" +
                       "            h1 {\n" +
                       "                text-align: center;\n" +
                       "                font-size: 2em;\n" +
                       "                color: #fff;\n" +
                       "            }\n" +
                       "            .container {\n" +
                       "                margin: 100px auto;\n" +
                       "                width: 90%;\n" +
                       "                max-width: 1140px;\n" +
                       "            }\n" +
                       "        </style>\n" +
                       "\n" +
                       "    </head>\n" +
                       "    <body>\n" +
                       "\n" +
                       "        <div class=\"container\">\n" +
                       "            <h1>Fluid width video</h1>\n" +
                       "            <div class=\"js-lazyYT\" data-youtube-id=\"_oEA18Y8gM0\" data-ratio=\"16:9\"></div>\n" +
                       "        </div>\n" +
                       "\n" +
                       "        <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js\"></script>\n" +
                       "        <script src=\"../lazyYT.js\"></script>\n" +
                       "        <script>\n" +
                       "        $( document ).ready(function() {\n" +
                       "            $('.js-lazyYT').lazyYT();\n" +
                       "        });\n" +
                       "        </script>\n" +
                       "\n" +
                       "    </body>\n" +
                       "</html>";

                String youid = "'" + searchResults.get(pos).getId() + "'";
                String video2 = "<div id=\"youtubeembed\" id=\"gdXN0DXMd6Y\"></div>" +
                        "/*global jQuery */\n" +
                        "/*jslint indent: 2 */\n" +
                        "/*!\n" +
                        " * EmbedYt 1.0\n" +
                        " * Author : Parveen Kumar ( http://infoheap.com/ )\n" +
                        "*/\n" +
                        "(function ($) {\n" +
                        "  \"use strict\";\n" +
                        "\n" +
                        "  $.fn.embedyt = function (youid) {\n" +
                        "    var htm = '<div id=\"player'  youid  '\"' + ' style=\"background-image:url(' + 'http://img.youtube.com/vi/'  youid  '/0.jpg' + ');' + 'width:480px;height:360px;text-align:center;line-height:360px;' +  '\">' + '<input type=\"button\" value=\" play \" />' + '</div>';\n" +
                        "    this.html(htm);\n" +
                        "    this.find(\"div input[type=button]\").bind('click', function () {\n" +
                        "      var ifhtml = '<iframe width=\"420\" height=\"315\" src=\"http://www.youtube.com/embed/'  youid  '?autoplay=1\" frameborder=\"0\" allowfullscreen></iframe>';\n" +
                        "      jQuery(this).css(\"cursor\", \"progress\");\n" +
                        "      jQuery(this).parent().parent().html(ifhtml);\n" +
                        "    });\n" +
                        "  };\n" +
                        "\n" +
                        "}(jQuery));" +
                        "jQuery(function () {\n" +
                        "  jQuery(\"div.youtubeembed\").each(function (index) {\n" +
                        "    jQuery(this).embedyt(jQuery(this).attr('id'));\n" +
                        "  });\n" +
                        "});"


                        ;


               String  video1 =


                       //"<div id=\"coverimageforplayer\"><img class=\"introvideo\" src=\"video-cover.jpg\" width=\"800\" height=\"450\" /></div> " +

                     //  "<div onclick=\"this.nextElementSibling.style.display='block'; this.style.display='none'\">\n" +
                     //  "   <img src=\"my_thumbnail.png\" style=\"cursor:pointer\" />\n" +
                     //  "</div>" +

                      // "<div class=\"image\"> <div onclick=\"thevid=document.getElementById('thevideo'); thevid.style.display='block'; this.style.display='none'\"> <img class=\"thumb\" style=\"cursor: pointer;\" src=\"http://img.youtube.com/vi/" + r + "/0.jpg\" alt=\"\" /> " +
                      // "</div>" +
                       //" <div id=\"thevideo\" style=\"display: none;\"" +
                      // "" +
                      // "> [INSERT EMBED CODE FROM YOUTUBE] </div>" +

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
                               "          videoId: " +  r + "  ," +
                            //   "   playerVars: { 'autoplay': 1,  'controls': 0, 'playerapiid': 'ytPlayer', 'border': 0,  'hd': 1, 'version': 3, 'rel': 0, 'color' : 'red' }," +
                               "              playerVars: { 'autoplay': 1, 'controls': 0, 'html5': 1 }," +
                               "          events: {" +
                               "            'onReady': onPlayerReady," +
                             //   "            'onReady':loadVideoById,"+
                               "            'onStateChange': onPlayerStateChange" +
                               "          }" +
                               "        });" +
                               "      }" +
                               "      function onPlayerReady(event) {" +
                         //        "       event.target.loadVideoById("+ m1 +", 0, \"large\") "    +
                               "        event.target.playVideo();" +
                               "      }" +

                               "function cueVideoById(event) {" +
                               "event.target.playVideo() };" +
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
                       "                width: 200px;\n" +
                       "                height: 200px;\n" +
                       "                margin: 0;\n" +
                       "                padding: 0;\n" +
                         "               border: 0;\n" +
                       "                outline: 0;\n "+


                      " font-size: 0;\n" +
               //        "               vertical-align: bottom" +

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
                       "        </style>\n" +
                       video1 +
                       "    </head>\n" +
                   "    <body>\n" +
           //           video2 +
           //    +
                       //     "        <div id=\"container\">\n" +
                       //     "            <h1>Hello World</h1>\n" +
                       //     "        </div>\n" +
                       "    </body>" +
                      "</html>\n";

               String test = "<html>\n" +
                       "<head>\n" +
                       "<script type='text/javascript' src='http://code.jquery.com/jquery-1.5.2.js'></script>\n" +
                       "<script language=\"JavaScript\">\n" +
                       "    <!--      \n" +
                       "/*\n" +
                       "Copyright 2011 : Simone Gianni <simoneg@apache.org>\n" +
                       "\n" +
                       "Released under The Apache License 2.0 \n" +
                       "http://www.apache.org/licenses/LICENSE-2.0\n" +
                       "\n" +
                       "*/\n" +
                       "(function() {\n" +
                       "    function createPlayer(jqe, video, options) {\n" +
                       "        var ifr = $('iframe', jqe);\n" +
                       "        if (ifr.length === 0) {\n" +
                       "            ifr = $('<iframe scrolling=\"no\">');\n" +
                       "            ifr.addClass('player');\n" +
                       "        }\n" +
                       "         var src = 'http://www.youtube.com/embed/' + video.id;\n" +
                       "\n" +
                       "        if (options.playopts) {\n" +
                       "            src += '?';\n" +
                       "            for (var k in options.playopts) {\n" +
                       "                src+= k + '=' + options.playopts[k] + '&';\n" +
                       "            }  \n" +
                       "            src += '_a=b';\n" +
                       "        }\n" +
                       "        ifr.attr('src', src);\n" +
                       "        jqe.append(ifr);  \n" +
                       "    }\n" +
                       "\n" +
                       "    function createCarousel(jqe, videos, options) {\n" +
                       "        var car = $('div.carousel', jqe);\n" +
                       "        if (car.length === 0) {\n" +
                       "            car = $('<div>');\n" +
                       "            car.addClass('carousel');\n" +
                       "            jqe.append(car);\n" +
                       "\n" +
                       "        }\n" +
                       "        $.each(videos, function(i,video) {\n" +
                       "            options.thumbnail(car, video, options); \n" +
                       "        });\n" +
                       "    }\n" +
                       "\n" +
                       "    function createThumbnail(jqe, video, options) {\n" +
                       "        var imgurl = video.thumbnails[0].url;\n" +
                       "        var img = $('img[src=\"' + imgurl + '\"]');\n" +
                       "        if (img.length !== 0) return;\n" +
                       "        img = $('<img>');    \n" +
                       "        img.addClass('thumbnail');\n" +
                       "        jqe.append(img);\n" +
                       "        img.attr('src', imgurl);\n" +
                       "        img.attr('title', video.title);\n" +
                       "        img.click(function() {\n" +
                       "            options.player(options.maindiv, video, $.extend(true,{},options,{playopts:{autoplay:1}}));\n" +
                       "        });\n" +
                       "    }\n" +
                       "\n" +
                       "    var defoptions = {\n" +
                       "        autoplay: false,\n" +
                       "        user: null,\n" +
                       "        carousel: createCarousel,\n" +
                       "        player: createPlayer,\n" +
                       "        thumbnail: createThumbnail,\n" +
                       "        loaded: function() {},\n" +
                       "        playopts: {\n" +
                       "            autoplay: 0,\n" +
                       "            egm: 1,\n" +
                       "            autohide: 1,\n" +
                       "            fs: 1,\n" +
                       "            showinfo: 0\n" +
                       "        }\n" +
                       "    };\n" +
                       "\n" +
                       "\n" +
                       "    $.fn.extend({\n" +
                       "        youTubeChannel: function(options) {\n" +
                       "            var md = $(this);\n" +
                       "            md.addClass('youtube');\n" +
                       "            md.addClass('youtube-channel');\n" +
                       "            var allopts = $.extend(true, {}, defoptions, options);\n" +
                       "            allopts.maindiv = md;\n" +
                       "            $.getJSON('http://gdata.youtube.com/feeds/users/' + allopts.user + '/uploads?alt=json-in-script&format=5&callback=?', null, function(data) { \n" +
                       "\n" +
                       "                var feed = data.feed;\n" +
                       "                var videos = [];\n" +
                       "                $.each(feed.entry, function(i, entry) {\n" +
                       "                    var video = {\n" +
                       "                        title: entry.title.$t,\n" +
                       "                        id: entry.id.$t.match('[^/]*$'),\n" +
                       "                        thumbnails: entry.media$group.media$thumbnail\n" +
                       "                    };\n" +
                       "                    videos.push(video);\n" +
                       "                });\n" +
                       "                allopts.allvideos = videos;\n" +
                       "                allopts.carousel(md, videos, allopts);\n" +
                       "                allopts.player(md, videos[0], allopts);\n" +
                       "                allopts.loaded(videos, allopts);\n" +
                       "            });\n" +
                       "        } \n" +
                       "    });\n" +
                       "\n" +
                       "})();\n" +
                       "\n" +
                       "$(function() {\n" +
                       "    $('#player').youTubeChannel({user:'nellrobinsonmusic'});\n" +
                       "});   \n" +
                       " // -->\n" +
                       "</script>  \n" +
                       "\n" +
                       "<style type=\"text/css\">\n" +
                       "<!--\n" +
                       "#player {\n" +
                       "    width: 853px;\n" +
                       "    height: 480px;\n" +
                       "    overflow: hidden;\n" +
                       "    background: none;\n" +
                       "    position: absolute;\n" +
                       "    border: none;\n" +
                       "}\n" +
                       "\n" +
                       ".youtube .carousel {\n" +
                       "    width: 20%;\n" +
                       "    height: 100%;\n" +
                       "    overflow: auto;\n" +
                       "    position: absolute;\n" +
                       "    right: 0px;\n" +
                       "    z-index: 3;\n" +
                       "}\n" +
                       "\n" +
                       ".youtube .thumbnail {\n" +
                       "    margin: 2px;\n" +
                       "    width: 80%;\n" +
                       "    border: 1px solid black;  \n" +
                       "}\n" +
                       "\n" +
                       ".youtube iframe.player {\n" +
                       "    width: 80%;\n" +
                       "    height: 480px;  \n" +
                       "    overflow: auto;\n" +
                       "    border: 0;\n" +
                       "}\n" +
                       "-->\n" +
                       "</style> \n" +
                       "\n" +
                       "    </head>\n" +
                       "    <body>\n" +
                       "         <div id=\"player\">\n" +
                       "        </div>      \n" +
                       "    </body>\n" +
                       "</html>";





             //  webView.loadDataWithBaseURL("https://www.youtube.com", Z, "text/html; charset=utf-8", "UTF-8", null);
              webview1.loadDataWithBaseURL("https://www.youtube.com", Z, "text/html; charset=utf-8", "UTF-8", null);

            // webview1.loadData(Z, "text/html; charset=utf-8; application/javascript ", "UTF-8");

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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //  Handle action bar item clicks here. The action bar will
        //  automatically handle clicks on the Home/Up button, so long
        //  as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (item.getItemId()) {



            case R.id.sign_in:
                Log.d("sign_in_button1","sign in button1");
                // Intent myintent = new Intent(SearchActivity.this,SignInActivity.class);
                // SearchActivity.this.startActivity(myintent);
                signIn();
                return true;


            // if (id == R.id.action_settings) {

            // }

            default:
                return super.onOptionsItemSelected(item);
        }
    }


class InsideWebViewClient extends WebViewClient {
    @Override
    // Force links to be opened inside WebView and not in Default Browser
    // Thanks http://stackoverflow.com/a/33681975/1815624
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }}


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("onstart", "onstart");
        mGoogleApiClient.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Search Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                // com.yourplayer.c.yourplayer1
                Uri.parse("android-app://com.yourplayer.c.yourplayer1/http/host/path")
                // Uri.parse("android-app://com.patrick.c.simpleplayer1_13_16/http/host/path")
        );
        AppIndex.AppIndexApi.start(mGoogleApiClient, viewAction);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Search Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.yourplayer.c.yourplayer1/http/host/path")
                //Uri.parse("android-app://com.patrick.c.simpleplayer1_13_16/http/host/path")
        );
        AppIndex.AppIndexApi.end(mGoogleApiClient, viewAction);
        mGoogleApiClient.disconnect();
    }

    private void signIn() {
        Log.d("sign in", "sign in");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient );
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


}
