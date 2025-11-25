package prog.hazi.util;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import prog.hazi.model.Settings;
import prog.hazi.model.Team;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

public class SettingsHandler {

    private SettingsHandler() { /* Hide constructor */ }
    
    /**
     * Writes the settings to an XML file at the specified file path.
     *
     * @param st the Settings object containing the settings to be written
     * @param filePath the path of the file where the settings will be saved
     */
    public static void writeSettings(Settings st, String filePath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Settings");
            doc.appendChild(rootElement);

            for (Team t: Team.values()) {
                Element teamElement = doc.createElement("Team");
                teamElement.setAttribute("id", String.valueOf(t.id));
                teamElement.setAttribute("name", t.getName());
                teamElement.setAttribute("color", colorToHex(t.getColor()));
                teamElement.setAttribute("bgColor", colorToHex(t.getBgColor()));
                rootElement.appendChild(teamElement);
            }

            Element boardElement = doc.createElement("Board");

            boardElement.setAttribute("BoardSize", String.valueOf(st.getBoardSize()));
            boardElement.setAttribute("ballCount", String.valueOf(st.getBallCount()));

            rootElement.appendChild(boardElement);


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));

            transformer.transform(source, result);

            System.out.println("Settings saved to " + filePath);

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }

    /**
     * Reads settings from an XML file and applies them to the provided Settings object.
     *
     * @param st the Settings object to apply the read settings to
     * @param filePath the path to the XML file containing the settings
     */
    public static void readSettings(Settings st, String filePath) {
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        Document doc;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xmlFile);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            System.out.println("File not found or invalid format, resetting to default settings, and creating new settings file.");
            resetSettings(st);
            writeSettings(st, filePath);
            return;
        }

        doc.getDocumentElement().normalize();

        NodeList teamList = doc.getElementsByTagName("Team");
        for (int i = 0; i < teamList.getLength(); i++) {
            Node teamNode = teamList.item(i);
            if (teamNode.getNodeType() != Node.ELEMENT_NODE)
                continue;

            Element teamElement = (Element)teamNode;

            int id = Integer.parseInt(teamElement.getAttribute("id"));
            Team t = Team.getTeam(id);
            t.setName(teamElement.getAttribute("name"));
            try {
                t.setColor(hexToColor(teamElement.getAttribute("color")), hexToColor(teamElement.getAttribute("bgColor")));
            } catch (NumberFormatException e) {
                // Use default colors if the color is invalid
            }
        }

        NodeList boardNode = doc.getElementsByTagName("Board");
        if (boardNode.item(0).getNodeType() != Node.ELEMENT_NODE)
            return;
        Element boardElement = (Element) boardNode.item(0);

        st.setBoardSize(Integer.parseInt(boardElement.getAttribute("BoardSize")));
        st.setBallCount(Integer.parseInt(boardElement.getAttribute("ballCount")));

        System.out.println("Settings loaded from " + filePath);
    }
    
    /**
     * Resets the settings to their default values.
     * 
     * This method resets the provided Settings object and sets the default names
     * and colors for the NORTH and SOUTH teams. The NORTH team is set to "Red" with
     * primary color (255, 68, 51) and secondary color (184, 62, 51). The SOUTH team
     * is set to "Blue" with primary color (75, 127, 210) and secondary color (39, 84, 157).
     * 
     * @param st the Settings object to be reset
     */
    public static void resetSettings(Settings st) {
        st.reset();
        Team.NORTH.setName("Red");
        Team.NORTH.setColor(new Color(255, 68, 51), new Color(184, 62, 51));
        Team.SOUTH.setName("Blue");
        Team.SOUTH.setColor(new Color(75, 127, 210), new Color(39, 84, 157));
    }

    /**
     * Converts a Color object to its hexadecimal string representation.
     *
     * @param color the Color object to be converted
     * @return the hexadecimal string representation of the color in the format "#FFFFFF"
     */
    private static String colorToHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * Converts a hexadecimal color string to a Color object.
     *
     * @param hex the hexadecimal color string in the format "#FFFFFF"
     * @return the Color object representing the specified color
     * @throws NumberFormatException if the hex string is not a valid hexadecimal color code
     */
    private static Color hexToColor(String hex) throws NumberFormatException {
        return new Color(
                Integer.valueOf(hex.substring(1, 3), 16),
                Integer.valueOf(hex.substring(3, 5), 16),
                Integer.valueOf(hex.substring(5, 7), 16));
    }
}