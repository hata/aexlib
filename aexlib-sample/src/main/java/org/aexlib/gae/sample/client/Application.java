package com.archtea.gae.sample.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {

    private static SampleServiceAsync sampleService = GWT.create(SampleService.class);

    private Label messageLabel = new Label();
    private TextBox documentName = new TextBox();
    private Button createDocumentButton = new Button("create document");
    private FlexTable queryTable = new FlexTable();
    private Map<Button, String> deleteButtonMap = new HashMap<Button, String>();
    
    
    
    public void onModuleLoad() { 
        buildWidgets();
    }
    
    private void buildWidgets() {
        VerticalPanel panel = new VerticalPanel();
        panel.add(messageLabel);

        HorizontalPanel createDocumentPanel = new HorizontalPanel();
        createDocumentPanel.add(documentName);
        createDocumentPanel.add(createDocumentButton);
        createDocumentButton.addClickHandler(new CreateDocumentClickHandler());
        panel.add(createDocumentPanel);

        Button queryButton = new Button("query document");
        queryButton.addClickHandler(new QueryDocumentClickHandler());
        panel.add(queryButton);
        panel.add(queryTable);
        
        
        RootPanel.get().add(panel);
    }

    private class CreateDocumentClickHandler implements ClickHandler {

        public void onClick(ClickEvent event) {
            String text = documentName.getText();
            if (text != null && text.length() > 0) {
                sampleService.createDocument(text, new CreateDocumentCallback());
            }
        }
        
    }

    private class CreateDocumentCallback implements AsyncCallback<String> {

        public void onFailure(Throwable caught) {
            messageLabel.setText("" + caught);
        }

        public void onSuccess(String result) {
            messageLabel.setText("" + result);
        }
        
    }

    private class QueryDocumentClickHandler implements ClickHandler {

        public void onClick(ClickEvent event) {
            sampleService.queryDocuments(new QueryDocumentCallback());
        }
        
    }
    
    private class QueryDocumentCallback implements AsyncCallback<String[]> {

        public void onFailure(Throwable caught) {
            messageLabel.setText("" + caught);
        }

        public void onSuccess(String[] result) {
            messageLabel.setText("Query OK.");
            queryTable.clear();
            deleteButtonMap.clear();
            int i = 0;
            for (String name : result) {
                Button deleteButton = new Button("delete " + name);
                queryTable.setText(i, 0, name);
                queryTable.setWidget(i++, 1, deleteButton);
                deleteButton.addClickHandler(new DeleteDocumentClickHandler());
                deleteButtonMap.put(deleteButton, name);
            }
        }
        
    }
    
    
    private class DeleteDocumentClickHandler implements ClickHandler {

        public void onClick(ClickEvent event) {
            String name = deleteButtonMap.get(event.getSource());
            if (name != null && name.length() > 0) {
                sampleService.deleteDocument(name, new CreateDocumentCallback());
            }
        }
        
    }

}
