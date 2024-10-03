package io.swagger.v3.oas.models;

import com.smartvalue.swagger.v3.parser.util.Jsonable;

public class JsonStringSchema extends io.swagger.v3.oas.models.media.StringSchema implements Jsonable  {

	
	@Override
    public String toJsonString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("}");
        return sb.toString();
    }

}
