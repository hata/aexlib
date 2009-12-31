/*
 * $Id$
 * 
 * Copyright 2009 Hiroki Ata
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aexlib.gae.sample.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.aexlib.gae.sample.client.SampleService;
import org.aexlib.gae.sample.server.entity.Document;
import org.aexlib.gae.sample.server.entity.Page;
import org.aexlib.gae.sample.server.entity.Reference;

import org.aexlib.gae.datastore.TransactionManager;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SampleServiceImpl extends RemoteServiceServlet implements SampleService {
    public String echo(String message) {
        return "echo: " + message;
    }

    public String createDocument(String name) {
        Document doc = Document.FACTORY.initInstance(name);
        if (doc.putIfAbsent()) {
            long start = System.currentTimeMillis();
            try {
                for (short i = 0;i < 100;i++) {
                    Page page = Page.FACTORY.initInstance(doc);
                    page.setNumber(i);
                    HashSet<String> tags = new HashSet<String>();
                    tags.add(name);
                    tags.add("page" + i);
                    tags.add("" + System.currentTimeMillis());
                    page.setTags(tags);
                    page.setText("Text in page " + i);
                    page.putIfAbsent();
                }
                
                for (int i = 0;i < 10;i++) {
                    Reference ref = Reference.FACTORY.initInstance("reference-" + i);
                    ref.setTitle("reference-title-" + i);
                    ref.setReferer(doc);
                    ref.putIfAbsent();
                }
                
                List<AttachedObject> attachments = new ArrayList<AttachedObject>();
                attachments.add(new AttachedObject("attachement1"));
                attachments.add(new AttachedObject("attachement2"));
                doc.setAttachments(attachments);
                doc.put();
            } catch (Exception e) {
                e.printStackTrace();
                return "error occurs." + e;
            } finally {
            }
            return "Create a new document and objects. time is " +
                (System.currentTimeMillis() - start) + " msec";
        } else {
            return name + " is found in datastore.";
        }
    }

    public String deleteDocument(String name) {
        Document doc = Document.FACTORY.initInstance(name);
        if (doc.isExists()) {
            // This is not the same entity group.
            for (Reference ref : Reference.QUERY.query().filter(Reference.REFERER.equal(doc))) {
                ref.delete();
            }
            // Document and Page is a same entity group.
            TransactionManager.getInstance().begin();
            try {
                for (Page page : Page.QUERY.query(doc)) {
                    page.delete();
                }
                doc.delete();
                TransactionManager.getInstance().commit();
            } finally {
                TransactionManager.getInstance().rollbackIfActive();
            }
            return name + " was deleted.";
        } else {
            return "There is no " + name;
        }
    }

    public String[] queryDocuments() {
        ArrayList<String> nameList = new ArrayList<String>();
        for (Document doc : Document.QUERY.query()) {
            nameList.add(doc.getKey().getName());
        }
        return nameList.toArray(new String[nameList.size()]);
    }
}
