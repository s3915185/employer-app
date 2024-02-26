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
import org.example.EmployerNoSalaries;
import org.example.Salary;
import org.example.config.BasicConfig;
import org.example.constant.Constant;
import org.example.model.pagination.PaginationObject;
import org.example.model.view.DetailViewObject;
import org.example.model.object.SalaryObject;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.Component;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.annotations.lifecycle.PreDestroy;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.CallbackComponent;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.util.FXUtil;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

@Component(id = BasicConfig.DETAIL_STATEFUL_CALLBACK,
        name = "statefulCallback",
        active = true,
        localeID = "en_US",
        resourceBundleLocation = "bundles.languageBundle")
public class DetailStatefulCallBack implements CallbackComponent {
    private Logger log = Logger.getLogger(SearchStatefulCallBack.class.getName());
    @Resource
    private Context context;

    private EmployerNoSalaries employerNoSalaries;
    private List<Salary> detailSalaries;
    private List<Salary> importSalaries;
    private PaginationObject detailPaginationObject;
    private PaginationObject importPaginationObject;

    @Override
    public Object handle(final Message<Event, Object> message) {
        if (message.isMessageBodyTypeOf(EmployerNoSalaries.class)) {
            employerNoSalaries = (EmployerNoSalaries) message.getMessageBody();
            return null;
        }
        if (message.isMessageBodyTypeOf(SalaryObject.class)) {
            SalaryObject salaryObject = (SalaryObject) message.getMessageBody();
            if (salaryObject.getType().equals(Constant.MESSAGE_DETAIL)) {
                detailSalaries = salaryObject.getSalaryList();
            }
            else if (salaryObject.getType().equals(Constant.MESSAGE_IMPORT)) {
                importSalaries = salaryObject.getSalaryList();
            }
            return null;
        }
        if (message.isMessageBodyTypeOf(PaginationObject.class)) {
            PaginationObject object = (PaginationObject) message.getMessageBody();
            if (object.getType().equals(Constant.MESSAGE_DETAIL)) {
                detailPaginationObject = object;
            }
            else if (object.getType().equals(Constant.MESSAGE_IMPORT)) {
                importPaginationObject = object;
            }
            return null;
        }
        if (message.messageBodyEquals(Constant.MESSAGE_NO_SALARIES)) {
            if (detailSalaries != null) { detailSalaries.clear();}
            return null;
        }
        if (message.messageBodyEquals(Constant.MESSAGE_DISPLAY)) {
            DetailViewObject object = new DetailViewObject();
            object.setEmployerNoSalaries(employerNoSalaries);
            object.setSalaries(detailSalaries);
            object.setPaginationObject(detailPaginationObject);
            context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.BODY_TAB), object);
            return null;
        }
        if (message.isMessageBodyTypeOf(String.class)) {
            String number = (String) message.getMessageBody();
            if (employerNoSalaries != null) {
                if (employerNoSalaries.getNumber().equals(number)) {
                    employerNoSalaries = null;
                    if (detailSalaries != null) { detailSalaries.clear();}
                    context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.TAB_COLUMN), Constant.MESSAGE_OFF_DETAIL_BUTTON);
                }
            }
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

}
