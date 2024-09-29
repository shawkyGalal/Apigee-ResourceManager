package com.smartvalue.swagger.v3.parser.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class SwaggerParseResult {

	private List<String> messages = null;
    private JsonOpenAPI openAPI;
    private boolean openapi31;

    public SwaggerParseResult messages(List<String> messages) {
        this.messages = messages;
        return this;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public SwaggerParseResult message(String message) {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(message);
        return this;
    }

    public SwaggerParseResult addMessages(List<String> messages) {
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        this.messages.addAll(messages);
        return this;
    }

    public JsonOpenAPI getOpenAPI() {
        return openAPI;
    }

    public void setOpenAPI(JsonOpenAPI openAPI) {
        this.openAPI = openAPI;
    }

    public static SwaggerParseResult ofError(String message){
        final SwaggerParseResult result = new SwaggerParseResult();
        result.setMessages(Collections.singletonList(message));
        return result;
    }

    public void setOpenapi31(boolean openapi31) {
        this.openapi31 = openapi31;
    }

    public SwaggerParseResult openapi31(boolean openapi31) {
        this.openapi31 = openapi31;
        return this;
    }

    public boolean isOpenapi31() {
        return this.openapi31;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SwaggerParseResult result = (SwaggerParseResult) o;
        return openapi31 == result.openapi31 && Objects.equals(messages, result.messages) && Objects.equals(openAPI, result.openAPI);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messages, openAPI, openapi31);
    }

	public static SwaggerParseResult CopyFrom(SwaggerParseResult result) {
		// TODO Auto-generated method stub
		return null;
	}
}
