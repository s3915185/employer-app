package org.example.config;

import org.example.factory.ObservableResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.example")
public class BasicConfig {

    @Bean
    public ObservableResourceFactory observableResourceFactory() {
        ObservableResourceFactory observableResourceFactory = new ObservableResourceFactory();
        observableResourceFactory.setupResourceForLanguage();
        return observableResourceFactory;
    }
    public static final String PERSPECTIVE_ONE = "idPone";
    public static final String PERSPECTIVE_TWO = "idPtwo";

    public static final String COMPONENT_LEFT = "id002";
    public static final String COMPONENT_RIGHT = "id003";
    public static final String STATELESS_CALLBACK = "id004";
    public static final String STATEFUL_CALLBACK = "id005";


    public static final String DIALOG_FRAGMENT = "idD1";

    public static final String TARGET_CONTAINER_LEFT = "PLeft";
    public static final String TARGET_CONTAINER_MAIN = "PMain";


    public static final String PERSPECTIVE_MAIN = "idMain";
    public static final String SEARCH_INPUT_VIEW = "idSearchInputView";
    public static final String SEARCH_TABLE_VIEW = "idSearchTableView";

    public static final String HEADER = "idHeader";
    public static final String TAB_COLUMN = "idTabColumn";

    public static final String BODY_TAB = "idBodyTab";
    public static final String SEARCH_TAB = "idSearchTab";
    public static final String DETAIL_TAB = "idDetailTab";
    public static final String HEADER_TAB = "idHeaderTab";
    public static final String DELETE_MESSAGE_TAB = "idDeleteMessageTab";
    public static final String DELETE_MESSAGE_FRAGMENT = "idDeleteMessageFragment";
    public static final String UPPER_SMALL_MESSAGE_TAB = "idUpperSmallMessageTab";
    public static final String UPPER_SMALL_MESSAGE_FRAGMENT = "idUpperSmallMessageFragment";


    public static final String TARGET_CONTAINER_HEADER = "PHeader";
    public static final String TARGET_CONTAINER_TAB_COLUMN = "PTabColumn";
    public static final String TARGET_CONTAINER_BODY_LAYOUT = "PBodyLayout";
    public static final String TARGET_CONTAINER_DELETE_POPUP = "PDeletePopUp";
    public static final String TARGET_CONTAINER_UPPER_SMALL_MESSAGE_POPUP = "PUpperSmallMessagePopUp";

    public static final String TARGET_CONTAINER_TOP = "PTop";
    public static final String TARGET_CONTAINER_BOT = "PBot";

    public static final String SEARCH_STATEFUL_CALLBACK = "idSearchCallBack";
    public static final String DETAIL_STATEFUL_CALLBACK = "idDetailCallBack";

}
