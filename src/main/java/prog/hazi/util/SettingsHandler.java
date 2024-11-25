package prog.hazi.util;

import org.w3c.dom.*;

import prog.hazi.model.Settings;
import prog.hazi.model.Team;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.Color;
import java.io.File;

public class SettingsHandler {

    private SettingsHandler() { /* Hide constructor */ }
    
    public static void writeSettings(Settings st, String filePath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Root element
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

            // Pits and ball counts
            Element boardElement = doc.createElement("Board");

            boardElement.setAttribute("BoardSize", String.valueOf(st.getBoardSize()));
            boardElement.setAttribute("ballCount", String.valueOf(st.getBallCount()));

            rootElement.appendChild(boardElement);

            // Write the content into XML file
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

    public static void readSettings(Settings st, String filePath) {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList teamList = doc.getElementsByTagName("Team");
            for (int i = 0; i < teamList.getLength(); i++) {
                Node teamNode = teamList.item(i);
                if (teamNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element teamElement = (Element) teamNode;

                    int id = Integer.parseInt(teamElement.getAttribute("id"));
                    String name = teamElement.getAttribute("name");
                    Color color = hexToColor(teamElement.getAttribute("color"));
                    Color bgColor = hexToColor(teamElement.getAttribute("bgColor"));

                    Team t = Team.getTeam(id);

                    t.setName(name);
                    t.setColor(color, bgColor);
                }
            }

            NodeList boardNode = doc.getElementsByTagName("Board");
            Element boardElement = (Element) boardNode.item(0);

            st.setBoardSize(Integer.parseInt(boardElement.getAttribute("BoardSize")));
            st.setBallCount(Integer.parseInt(boardElement.getAttribute("ballCount")));

            System.out.println("Settings loaded from " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String colorToHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    private static Color hexToColor(String hex) {
        return new Color(
                Integer.valueOf(hex.substring(1, 3), 16),
                Integer.valueOf(hex.substring(3, 5), 16),
                Integer.valueOf(hex.substring(5, 7), 16));
    }
}