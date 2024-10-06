package com.smartvalue.codeGenerators;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlParserGenerator {

public static void main(String[] artgs) throws Exception
{
	XmlParserGenerator xlg = new XmlParserGenerator();
	String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n"
			+ "<ProxyEndpoint name=\"default\">\r\n"
			+ "    <Description/>\r\n"
			+ "    <DefaultFaultRule>\r\n"
			+ "        <AlwaysEnforce>true</AlwaysEnforce>\r\n"
			+ "        <Step>\r\n"
			+ "            <Name>FC-DefaultFaultHandler</Name>\r\n"
			+ "        </Step>\r\n"
			+ "    </DefaultFaultRule>\r\n"
			+ "    <FaultRules/>\r\n"
			+ "    <PreFlow name=\"PreFlow\">\r\n"
			+ "        <Request>\r\n"
			+ "            <Step>\r\n"
			+ "                <Name>AE-GetAppAttributes</Name>\r\n"
			+ "                <Condition>AAAABBBBB</Condition>\r\n"
			+ "            </Step>\r\n"
			+ "            <Step>\r\n"
			+ "                <Name>EV-GetAppAttributesFromAccessEntry</Name>\r\n"
			+ "                <Condition>AAAABBBCCC</Condition>\r\n"
			+ "            </Step>\r\n"
			+ "        </Request>\r\n"
			+ "        <Response/>\r\n"
			+ "    </PreFlow>\r\n"
			+ "    <PostFlow name=\"PostFlow\">\r\n"
			+ "        <Request/>\r\n"
			+ "        <Response/>\r\n"
			+ "    </PostFlow>\r\n"
			+ "    <Flows>\r\n"
			+ "        <Flow name=\"GetOAS\">\r\n"
			+ "            <Description>Get OpenAPI Specification</Description>\r\n"
			+ "            <Request>\r\n"
			+ "                <Step>\r\n"
			+ "                    <Name>FC-Conditional-Flow-Hook</Name>\r\n"
			+ "                </Step>\r\n"
			+ "                <Step>\r\n"
			+ "                    <Name>FC-OasParams</Name>\r\n"
			+ "                </Step>\r\n"
			+ "            </Request>\r\n"
			+ "            <Response/>\r\n"
			+ "            <Condition>AAAAAAAAAAAAAA</Condition>\r\n"
			+ "        </Flow>\r\n"
			+ "        <Flow name=\"Service_Notify\">\r\n"
			+ "            <Description>XXXXXXXX</Description>\r\n"
			+ "            <Request>\r\n"
			+ "                <Step>\r\n"
			+ "                    <Name>FC-Conditional-Flow-Hook</Name>\r\n"
			+ "                </Step>\r\n"
			+ "                <Step>\r\n"
			+ "                    <Name>AM-RemoveContentLengthHeader</Name>\r\n"
			+ "                    <Condition>BBBBBBBBBBBB</Condition>\r\n"
			+ "                </Step>\r\n"
			+ "            </Request>\r\n"
			+ "            <Response/>\r\n"
			+ "            <Condition>CCCCCCCC</Condition>\r\n"
			+ "        </Flow>\r\n"
			+ "        <Flow name=\"Service_NotifyDetails\">\r\n"
			+ "            <Description>YYYYYYYYYYYYY</Description>\r\n"
			+ "            <Request>\r\n"
			+ "                <Step>\r\n"
			+ "                    <Name>FC-Conditional-Flow-Hook</Name>\r\n"
			+ "                </Step>\r\n"
			+ "            </Request>\r\n"
			+ "            <Response/>\r\n"
			+ "            <Condition>CCCCCCCCCCC</Condition>\r\n"
			+ "        </Flow>\r\n"
			+ "        \r\n"
			+ "    </Flows>\r\n"
			+ "    <PostClientFlow>\r\n"
			+ "        <Request/>\r\n"
			+ "        <Response>\r\n"
			+ "            <Step>\r\n"
			+ "                <Name>FC-ELK-Logger</Name>\r\n"
			+ "            </Step>\r\n"
			+ "        </Response>\r\n"
			+ "    </PostClientFlow>\r\n"
			+ "    <HTTPProxyConnection>\r\n"
			+ "        <BasePath>/sms-governance</BasePath>\r\n"
			+ "        <Properties/>\r\n"
			+ "        <VirtualHost>default</VirtualHost>\r\n"
			+ "        <VirtualHost>unsecure-special-f5-need</VirtualHost>\r\n"
			+ "    </HTTPProxyConnection>\r\n"
			+ "    <RouteRule name=\"default\">\r\n"
			+ "        <TargetEndpoint>default</TargetEndpoint>\r\n"
			+ "    </RouteRule>\r\n"
			+ "</ProxyEndpoint>" ; 
	String outputDirectory = "" ;
	String packageN = "com.smartvalue.apigee.proxyBundle" ; 
	xlg.generateJavaClassFromXml(xmlString
			, outputDirectory
			, packageN);
}
public void generateJavaClassFromXml(String xmlString , String m_outputDirectory, String m_package ) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();

    Document document = builder.parse(new InputSource(new StringReader(xmlString)));

    // Create a map to store generated Java classes
    Map<String, String> generatedClasses = new HashMap<>();

    // Process the XML document and generate Java classes
    generateJavaClasses(document.getDocumentElement(), generatedClasses , m_package);

    // Write the generated Java classes to files
    writeGeneratedClassesToFile(generatedClasses , m_outputDirectory);
}

private void generateJavaClasses(Element element, Map<String, String> generatedClasses , String m_package) {
    String className = element.getAttribute("name");
    String classContents = generateClassContents(element , m_package);

    if (!generatedClasses.containsKey(className)) {
        generatedClasses.put(className, classContents);
        for (Element childElement : getChildrenElements(element)) {
            generateJavaClasses(childElement, generatedClasses , m_package);
        }
    }
}

private String generateClassContents(Element element , String m_package) {
    StringBuilder classContents = new StringBuilder();
    classContents.append("package ").append(m_package);
    classContents.append("public class ").append(element.getAttribute("name")).append(" {\n");

    for (Element childElement : getChildrenElements(element)) {
        if (childElement.hasChildNodes()) {
            classContents.append("    private ").append(getDataType(childElement)).append(" ").append(childElement.getAttribute("name")).append(";\n");
        }
    }

    classContents.append("\n");
    classContents.append("    // Getters and setters\n");
    classContents.append("}");

    return classContents.toString();
}

private List<Element> getChildrenElements(Element element) {
    List<Element> children = new ArrayList<>();
    NodeList childNodes = element.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
        Node childNode = childNodes.item(i);

        if (childNode.getNodeType() == Node.ELEMENT_NODE) {
            children.add((Element) childNode);
        }
    }
    return children;

}

private String getDataType(Element element) {
    String dataType = "String"; // Default data type

    // Determine data type based on element attributes or content (adjust as needed)
    // ...

    return dataType;
}

private void writeGeneratedClassesToFile(Map<String, String> generatedClasses , String m_outputDirectory) throws IOException {
    new File(m_outputDirectory).mkdirs();

    for (Map.Entry<String, String> entry : generatedClasses.entrySet()) {
        String className = entry.getKey();
        String classContents = entry.getValue();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(m_outputDirectory + "/" + className + ".java"))) {
            writer.write(classContents);
        }
    }
}


}
