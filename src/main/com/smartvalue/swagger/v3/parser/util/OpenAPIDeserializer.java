package com.smartvalue.swagger.v3.parser.util;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.parser.core.models.ParseOptions;



public class OpenAPIDeserializer extends io.swagger.v3.parser.util.OpenAPIDeserializer 
{
	private String basePath;
	private JsonNode rootNode;
	private Map<String, Object> rootMap;
	private Components components;
    private Map<String,String> localSchemaRefs = new HashMap<>();
	
    public SwaggerParseResult mydeserialize(JsonNode rootNode) {
		return mydeserialize(rootNode, null);
	}
    public SwaggerParseResult mydeserialize(JsonNode rootNode, String path) {
        return mydeserialize(rootNode,path, new ParseOptions());
    }
    
	public SwaggerParseResult mydeserialize(JsonNode rootNode, String path, ParseOptions options) {
        return mydeserialize(rootNode,path, new ParseOptions(), false);
    }
	
	public SwaggerParseResult mydeserialize(JsonNode rootNode, String path, ParseOptions options, boolean isOaiAuthor) {
        basePath = path;
        this.rootNode = rootNode;
        rootMap = new ObjectMapper().convertValue(rootNode, Map.class);
		SwaggerParseResult result = new SwaggerParseResult();
        try {
            ParseResult rootParse = new ParseResult();
            rootParse.setOaiAuthor(options.isOaiAuthor());
            rootParse.setInferSchemaType(options.isInferSchemaType());
            rootParse.setAllowEmptyStrings(options.isAllowEmptyString());
            rootParse.setValidateInternalRefs(options.isValidateInternalRefs());
            JsonOpenAPI api = parseRoot(rootNode, rootParse, path);
            result.openapi31(rootParse.isOpenapi31());
            result.setOpenAPI(api);
            result.setMessages(rootParse.getMessages());
        } catch (Exception e) {
            result.setMessages(Arrays.asList(e.getMessage()));
        }
		return result;
	}
	
	public JsonOpenAPI parseRoot(JsonNode node, ParseResult result, String path) {
		String location = "";
		JsonOpenAPI openAPI = new JsonOpenAPI();
		if (node.getNodeType().equals(JsonNodeType.OBJECT)) {
			ObjectNode rootNode = (ObjectNode) node;

			// required
			String value = getString("openapi", rootNode, true, location, result);

			// we don't even try if the version isn't there
			if (value == null || (!value.startsWith("3.0") && !value.startsWith("3.1"))) {
				return null;
			} else if (value.startsWith("3.1")) {
				result.openapi31(true);
                openAPI.setSpecVersion(SpecVersion.V31);
			}
            if (!value.startsWith("3.0.") && !value.startsWith("3.1.")){
                result.warning(location, "The provided definition does not specify a valid version field");
            }
            openAPI.setOpenapi(value);


			ObjectNode obj = getObject("info", rootNode, true, location, result);
			if (obj != null) {
				Info info = getInfo(obj, "info", result);
				openAPI.setInfo(info);
			}

			obj = getObject("components", rootNode, false, location, result);
			if (obj != null) {
				Components components = getComponents(obj, "components", result);
				openAPI.setComponents(components);
				this.components = components;
                if(result.isValidateInternalRefs()) {
                    /* TODO currently only capable of validating if ref is to root schema withing #/components/schemas
                     * need to evaluate json pointer instead to also allow validation of nested schemas
                     * e.g. #/components/schemas/foo/properties/bar
                     */
                    for (String schema : localSchemaRefs.keySet()) {
                        if (components.getSchemas().get(schema) == null) {
                            result.invalidType(localSchemaRefs.get(schema), schema, "schema", rootNode);
                        }
                    }
                }
			}

			boolean pathsRequired = true;
			if (result.isOpenapi31()) {
				pathsRequired = false;
			}
			obj = getObject("paths", rootNode, pathsRequired, location, result);
			if (obj != null) {
				Paths paths = getPaths(obj, "paths", result);
				openAPI.setPaths(paths);
			}


			ArrayNode array = getArray("servers", rootNode, false, location, result);
			if (array != null && array.size() > 0) {
				openAPI.setServers(getServersList(array, String.format("%s.%s", location, "servers"), result, path));
			} else {
				Server defaultServer = new Server();
				defaultServer.setUrl("/");
				List<Server> servers = new ArrayList<>();
				servers.add(defaultServer);
				openAPI.setServers(servers);
			}

			obj = getObject("externalDocs", rootNode, false, location, result);
			if (obj != null) {
				ExternalDocumentation externalDocs = getExternalDocs(obj, "externalDocs", result);
				openAPI.setExternalDocs(externalDocs);
			}

			array = getArray("tags", rootNode, false, location, result);
			if (array != null && array.size() > 0) {
				openAPI.setTags(getTagList(array, "tags", result));
			}

			array = getArray("security", rootNode, false, location, result);
			if (array != null && array.size() > 0) {
				List<SecurityRequirement> securityRequirements = getSecurityRequirementsList(array, "security",
						result);
				if (securityRequirements != null && securityRequirements.size() > 0) {
					openAPI.setSecurity(securityRequirements);
				}
			}

			if (result.isOpenapi31()) {
				obj = getObject("webhooks", rootNode, false, location, result);
				if (obj != null) {
					Map<String, PathItem> webhooks = getWebhooks(obj, "webhooks", result);
					openAPI.setWebhooks(webhooks);
				}
			}


			Map<String, Object> extensions = getExtensions(rootNode);
			if (extensions != null && extensions.size() > 0) {
				openAPI.setExtensions(extensions);
			}

			if (result.isOpenapi31()) {
				value = getString("jsonSchemaDialect", rootNode, false, location, result);
				if (value != null) {
					if (isValidURI(value)) {
						openAPI.setJsonSchemaDialect(value);
					}else{
						result.warning(location,"jsonSchemaDialect. Invalid url: " + value);
					}
				}
			}

			if(result.isOpenapi31() && openAPI.getComponents() == null && openAPI.getPaths() == null && openAPI.getWebhooks() == null){
				result.warning(location, "The OpenAPI document MUST contain at least one paths field, a components field or a webhooks field");
			}

			Set<String> keys = getKeys(rootNode);
			Map<String, Set<String>> specKeys = KEYS.get(result.isOpenapi31() ? "openapi31" : "openapi30");
			for (String key : keys) {
				if (!specKeys.get("ROOT_KEYS").contains(key) && !key.startsWith("x-")) {
					result.extra(location, key, node.get(key));
				}
				validateReservedKeywords(specKeys, key, location, result);

			}

		} else {
			result.invalidType(location, "openapi", "object", node);
			result.invalid();
			return null;
		}
		return openAPI;
	}

	private void validateReservedKeywords(Map<String, Set<String>> specKeys, String key, String location, ParseResult result) {
		if(!result.isOaiAuthor() && result.isOpenapi31() && specKeys.get("RESERVED_KEYWORDS").stream()
				.filter(rk -> key.startsWith(rk))
				.findAny()
				.orElse(null) != null){
			result.reserved(location, key);
		}
	} 
	
	private boolean isValidURI(String uriString) {
		try {
			URI uri = new URI(uriString);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

}
