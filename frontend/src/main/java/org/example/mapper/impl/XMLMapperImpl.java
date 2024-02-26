package org.example.mapper.impl;

import com.sun.org.apache.xerces.internal.util.Status;
import org.example.Salary;
import org.example.constant.Constant;
import org.example.mapper.FileMapper;
import org.example.util.Utils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class XMLMapperImpl implements FileMapper<Element> {
    @Override
    public Salary apply(Element element, List<String> columnNames, Locale locale) {
        try {
            String numberAVS = getStringOrDefault(element.getElementsByTagName("Data").item(0));
            if (!Utils.isValidFormat(numberAVS)) {
                throw new RuntimeException(Status.NOT_RECOGNIZED.toString());
            }
            String dateDebutString = getStringOrDefault(element.getElementsByTagName("Data").item(3));
            String dateFinishedString = getStringOrDefault(element.getElementsByTagName("Data").item(4));
            Utils.validateDateFormat(dateDebutString,"yyyy-MM-dd'T'HH:mm:ss.SSS");
            Utils.validateDateFormat(dateFinishedString, "yyyy-MM-dd'T'HH:mm:ss.SSS");
            return Salary.newBuilder()
                    .setNumberAVS(numberAVS)
                    .setName(getStringOrDefault(element.getElementsByTagName("Data").item(1)))
                    .setFirstName(getStringOrDefault(element.getElementsByTagName("Data").item(2)))
                    .setDateDebut(dateDebutString)
                    .setDateFinished(dateFinishedString)
                    .setNumberAvsAiApg(Float.parseFloat(getStringOrDefault(element.getElementsByTagName("Data").item(5))))
                    .setNumberAc(Float.parseFloat(getStringOrDefault(element.getElementsByTagName("Data").item(6))))
                    .setNumberAf(Float.parseFloat(getStringOrDefault(element.getElementsByTagName("Data").item(7))))
                    .build();
        }catch (NumberFormatException e) {
            throw new RuntimeException(Constant.MESSAGE_NUMBER_FORMAT_ERROR);
        } catch (Exception e) {
            throw new ArrayIndexOutOfBoundsException(Status.NOT_ALLOWED.toString());
        }
    }
    public static String getStringOrDefault(Node node) {
        return Optional.of(node)
                .map(Node::getTextContent)
                .orElseThrow(() -> new RuntimeException("Found error"));
    }
}
