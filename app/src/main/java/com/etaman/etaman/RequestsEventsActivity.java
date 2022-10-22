package com.etaman.etaman;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class RequestsEventsActivity extends AppCompatActivity {
    private ApiRepository api = ApiRepository.getInstance();
    private JSONArray eventsData;
    private JSONArray requestsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_events);
        getRequestsData();
        getEventsData();
    }

    private int getResIndentifier(String identifier) {
        return getResources().getIdentifier(identifier, "id", getPackageName());
    }

    private void getRequestsData() {
        api.getAllRequestsAPI();
        while (api.requestsData == null) {
        }
        if (api.requestsData != null) {
            requestsData = api.requestsData;

            for (int j = 0; j < requestsData.size(); j++) {
                int i = j + 1;

                int title = getResIndentifier("lblRequestTitle" + i);
                int time = getResIndentifier("lblRequestTime" + i);
                int details = getResIndentifier("lblRequestDetails" + i);
                int client = getResIndentifier("lblRequestClient" + i);

                TextView lblRequestTitle = findViewById(title);
                TextView lblRequestTime = findViewById(time);
                TextView lblRequestDetails = findViewById(details);
                TextView lblRequestClient = findViewById(client);

                String eventTitle = (String) ((JSONObject) requestsData.get(j)).get("title");
                String eventTime = (String) ((JSONObject) requestsData.get(j)).get("startDate") + " - " + (String) ((JSONObject) requestsData.get(j)).get("endDate");
                String eventDetails = (String) ((JSONObject) requestsData.get(j)).get("description");
                String eventClient = (String) ((JSONObject) requestsData.get(j)).get("username");

                lblRequestTitle.setText(eventTitle);
                lblRequestTime.setText(eventTime);
                lblRequestDetails.setText(eventDetails);
                lblRequestClient.setText(eventClient);
            }
        }
    }

    private void getEventsData() {
        api.getAllEventsAPI();
        while (api.eventsData == null) {
        }
        if (api.eventsData != null) {
            eventsData = api.eventsData;

            for (int j = 0; j < eventsData.size(); j++) {
                int i = j + 1;

                int title = getResIndentifier("lblEventTitle" + i);
                int time = getResIndentifier("lblEventTime" + i);
                int details = getResIndentifier("lblEventDetails" + i);
                int client = getResIndentifier("lblEventClient" + i);

                TextView lblEventTitle = findViewById(title);
                TextView lblEventTime = findViewById(time);
                TextView lblEventDetails = findViewById(details);
                TextView lblEventClient = findViewById(client);

                String eventTitle = (String) ((JSONObject) eventsData.get(j)).get("title");
                String eventTime = (String) ((JSONObject) eventsData.get(j)).get("startDate") + " - " + (String) ((JSONObject) eventsData.get(j)).get("endDate");
                String eventDetails = (String) ((JSONObject) eventsData.get(j)).get("description");
                String eventClient = (String) ((JSONObject) eventsData.get(j)).get("username");

                lblEventTitle.setText(eventTitle);
                lblEventTime.setText(eventTime);
                lblEventDetails.setText(eventDetails);
                lblEventClient.setText(eventClient);
            }
        }
    }

}
