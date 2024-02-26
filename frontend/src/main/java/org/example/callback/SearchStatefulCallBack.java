/************************************************************************
 *
 * Copyright (C) 2010 - 2012
 *
 * [StatefulCallback.java]
 * AHCP Project (http://jacp.googlecode.com)
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either 
 * express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 *
 *
 ************************************************************************/
package org.example.callback;

import javafx.event.Event;
import lombok.Getter;
import lombok.Setter;
import org.example.EmployerNoSalaries;
import org.example.EmployerSearchCriteria;
import org.example.config.BasicConfig;
import org.example.constant.Constant;
import org.example.model.message.CustomSearchViewObject;
import org.example.model.object.EmployerNoSalariesObject;
import org.example.model.pagination.PaginationObject;
import org.example.model.view.SearchViewObject;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.Component;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.annotations.lifecycle.PreDestroy;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.CallbackComponent;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.util.FXUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * a stateful JacpFX component
 *
 * @author Andy Moncsek
 */
@Getter
@Setter
@Component(id = BasicConfig.SEARCH_STATEFUL_CALLBACK,
        name = "statefulCallback",
        active = true,
        localeID = "en_US",
        resourceBundleLocation = "bundles.languageBundle")
public class SearchStatefulCallBack implements CallbackComponent {
    private Logger log = Logger.getLogger(SearchStatefulCallBack.class.getName());
    @Resource
    private Context context;

    private EmployerSearchCriteria employerSearchCriteria;
    private List<EmployerNoSalaries> employerNoSalariesList;
    private PaginationObject paginationObject;
    private String method = "";

    @Override
    public Object handle(final Message<Event, Object> message) {
        if(!message.messageBodyEquals(FXUtil.MessageUtil.INIT))     {}
        if (message.isMessageBodyTypeOf(EmployerSearchCriteria.class)) {
            employerSearchCriteria = (EmployerSearchCriteria) message.getMessageBody();
        }
        if (message.isMessageBodyTypeOf(EmployerNoSalariesObject.class)) {
            EmployerNoSalariesObject object = (EmployerNoSalariesObject) message.getMessageBody();
            employerNoSalariesList = object.getEmployerNoSalaries();
        }
        if (message.isMessageBodyTypeOf(PaginationObject.class)) {
            paginationObject = (PaginationObject) message.getMessageBody();
        }
        if (message.messageBodyEquals(Constant.MESSAGE_NO_DATA)) {
            if (employerSearchCriteria != null) { employerSearchCriteria = null;}
            if (!employerNoSalariesList.isEmpty()) { employerNoSalariesList.clear(); }
            if (paginationObject != null) { paginationObject = null; }
        }
        if (message.messageBodyEquals(Constant.MESSAGE_RELOAD_REQUIRED)) {
            method = Constant.MESSAGE_RELOAD;
        }
        if (message.messageBodyEquals(Constant.MESSAGE_NO_RELOAD_REQUIRED)) {
            method = "";
        }
        if (message.messageBodyEquals(Constant.MESSAGE_DISPLAY) || message.messageBodyEquals("refresh")) {
            context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.BODY_TAB), getSearchViewObject());
        }
        return null;
    }

    @PreDestroy
    /**
     * The @PreDestroy annotations labels methods executed when the component is set to inactive
     */
    public void onPreDestroyComponent() {
        this.log.info("run on tear down of StatefulCallback ");

    }

    @PostConstruct
    /**
     * The @PostConstruct annotation labels methods executed when the component switch from inactive to active state
     * @param resourceBundle
     */
    public void onPostConstructComponent(final ResourceBundle resourceBundle) {
        this.log.info("run on start of StatefulCallback ");

    }

    private SearchViewObject getSearchViewObject() {
        SearchViewObject object = new SearchViewObject();
        object.setEmployerSearchCriteria(employerSearchCriteria);
        object.setEmployerNoSalariesList(employerNoSalariesList);
        object.setPaginationObject(paginationObject);
        object.setMethod(method);
        return object;
    }

}
