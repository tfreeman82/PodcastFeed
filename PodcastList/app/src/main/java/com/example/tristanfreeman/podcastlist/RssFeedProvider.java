package com.example.tristanfreeman.podcastlist;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tristanfreeman on 1/20/18.
 */

public class RssFeedProvider extends AsyncTask<Void, Void, List<RssItem>> {

    Context context;
    ProgressDialog progressDialog;
    private String urlString = "http://feeds.feedburner.com/blogspot/AndroidDevelopersBackstage?format=xml";
    private String thumbnailUri;
    URL url;
    private static final String ns = null;

    private OnTaskCompleted onComplete;

    public RssFeedProvider(Context context){
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(ArrayList<RssItem> list);
    }

    public void onFinish(OnTaskCompleted onComplete) {
        this.onComplete = onComplete;
    }
    @Override
    protected void onPreExecute() {
       // progressDialog.show();
        super.onPreExecute();
    }



    @Override
    protected List<RssItem> doInBackground(Void... voids) {


            //url = new URL(urlString);

        List<RssItem> list = new ArrayList<RssItem>();
        XmlPullParser parser = Xml.newPullParser();
//        try {
//            readEntry(parser);
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        InputStream stream = null;
        try{
            boolean inItemTag = false;
            String currentTagName = "";

            stream = new URL(urlString).openConnection().getInputStream();
            parser.setInput(stream, null);

            //parser.require(XmlPullParser.TEXT, "ns","media:content");

            int eventType = parser.getEventType();
            boolean done = false;
            RssItem item = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        currentTagName = parser.getName();

                        //Log.d("START TAG", parser.getName());
                        if (currentTagName.equals("item")) {
                            inItemTag = true;
                            item = new RssItem();
                            list.add(item);
                        }else if (currentTagName.equals("content")){
                           // item.setUrl(parser.getText());
                            urlString = readAudioLink(parser);
                            item.setAudioUrl(urlString);
                        }else if (currentTagName.equals("thumbnail")&& inItemTag) {
                            thumbnailUri = readThumnailUri(parser);
                            item.setThumbnailUri(thumbnailUri);
                        }

//                        if (currentTagName.equals("media")){
//                            System.out.print(parser.getText());
//                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            inItemTag = false;
                        }
                        currentTagName = "";
                        break;
                    case XmlPullParser.TEXT:
                        String text = parser.getText();
                        //String url = parser.get

                        if (inItemTag && item != null) {
                            try {
                                switch (currentTagName) {
                                    case "title":
                                        item.setTitle(text);
                                        break;
                                    case "pubDate":
                                        item.setPubDate(text);
                                        break;
                                    case "description":
                                        item.setDescription(text);
                                        break;
                                    case "summary":
                                        item.setSummary(text);
                                        break;
                                    case "media":
                                        item.setAudioUrl(readAudioLink(parser));
                                        //item.setUrl(text);
                                        break;
                                    case "link":
                                        item.setPageLink(text);
                                        break;
                                    default:
                                        break;
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
//                            if (name.equalsIgnoreCase("link")) {
//                                String link = parser.nextText();
//                                Log.i("Attribute", "setLink");
//                                item.setLink(link);
//
//
//                            } else if (name.equalsIgnoreCase("media:content")) {
//                                Log.i("Attribute", "content");
//                                String content = parser.nextText();
//                                Log.d("Content", content);
//                                item.setUrl(content);
//
//                        }

                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            throw new RuntimeException(e);

        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
        return list;
    }

    private RssItem readEntry(XmlPullParser parser) throws  XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null;
        String audioLink = null;
        while (parser.next() != XmlPullParser.START_TAG){
            continue;
        }
        String name = parser.getName();
        if (name.equals("enclosure")){//enclosure is where the audio link is located
            audioLink = readAudioLink(parser);
        }
        return new RssItem();
    }
    private String readAudioLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        String audioLink = "";
        parser.require(XmlPullParser.START_TAG, ns, "content");
        String tag = parser.getName();
        audioLink = parser.getAttributeValue(null, "url");

        parser.nextTag();
        parser.require(XmlPullParser.END_TAG, ns, "content");
        return audioLink;
    }

    private String readThumnailUri(XmlPullParser parser) throws IOException, XmlPullParserException {
        String thumbLink = "";
        parser.require(XmlPullParser.START_TAG, ns, "thumbnail");
        String tag = parser.getName();
        thumbLink = parser.getAttributeValue(null, "url");
        parser.nextTag();
        parser.require(XmlPullParser.END_TAG, ns, "thumbnail");
        return thumbLink;
    }

    @Override
    protected void onPostExecute(List<RssItem> items) {
        super.onPostExecute(items);
        onComplete.onTaskCompleted((ArrayList<RssItem>) items);
        progressDialog.dismiss();
    }

}


