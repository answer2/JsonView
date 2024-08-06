package com.answer.library.JsonView.bean;
import java.util.List;
import com.google.gson.JsonObject;

public class DefinitionBean {

    public static final String TAG = "DefinitionBean";
    
    private JsonObject var;
    private List<String> menu;

    public void setVar(JsonObject var) {
        this.var = var;
    }

    public JsonObject getVar() {
        return var;
    }

    public void setMenu(List<String> menu) {
        this.menu = menu;
    }

    public List<String> getMenu() {
        return menu;
    }

    
}
