package com.jobarth.deutsche.bahn.data.server;

import com.google.common.collect.Lists;
import com.jobarth.deutsche.bahn.data.domain.TimetableStop;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * An implementation of {@link TimetableParser} that parses XML.
 */
public class XmlTimetableParser implements TimetableParser {

    private final String xmlBody;

    public XmlTimetableParser(String xmlBody) {
        this.xmlBody = xmlBody;
    }

    @Override
    public Collection<TimetableStop> parseTimetableStops() throws IOException, ParserConfigurationException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        ByteArrayInputStream input = new ByteArrayInputStream(xmlBody.getBytes(StandardCharsets.UTF_8));
        Document doc = builder.parse(input);
        Element root = doc.getDocumentElement();

        Collection<TimetableStop> parsedTimetableStops = Lists.newArrayListWithCapacity(root.getChildNodes().getLength());

        for (int i = 0; i < root.getChildNodes().getLength(); i++) {
            Node timetableStop = root.getChildNodes().item(i);
            TimetableStopParser timetableStopParser = new XmlTimetableStopParser(timetableStop);
            //create timetable stop object

            parsedTimetableStops.add(timetableStopParser.parseTimetableStop());
        }
        return parsedTimetableStops;
    }
}
