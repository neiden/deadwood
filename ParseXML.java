// Example Code for parsing XML file
// Dr. Moushumi Sharmin
// CSCI 345

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;

public class ParseXML {


    // building a document from the XML file
    // returns a Document object after loading the book.xml file.
    public Document getDocFromFile(String filename)
            throws ParserConfigurationException {
        {


            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = null;

            try {
                doc = db.parse(filename);
            } catch (Exception ex) {
                System.out.println("XML parse failure");
                ex.printStackTrace();
            }
            return doc;
        } // exception handling

    }

    static public ArrayList<Scene> readCardData(Document doc){
        Element root = doc.getDocumentElement();
        ArrayList<Scene> Scenes = new ArrayList<>();


        NodeList cards = root.getElementsByTagName("card");
        for (int i = 0; i < cards.getLength(); i++) {
            ArrayList<Role> roles = new ArrayList<>();
            int budget = 0;
            int sceneNum = 0;
            String description = "";
            String name;

            Node card = cards.item(i);

            name = card.getAttributes().getNamedItem("name").getNodeValue();
            budget = Integer.parseInt(card.getAttributes().getNamedItem("budget").getNodeValue());

            NodeList children = card.getChildNodes();
            for (int j = 0; j < children.getLength(); j++) {
                Node currNode = children.item(j);

                if("scene".equals(currNode.getNodeName())){

                    description = currNode.getTextContent();
                    sceneNum = Integer.parseInt(currNode.getAttributes().getNamedItem("number").getNodeValue());
                  //  System.out.println(name + " \t"  + budget + "\t" + description + " \t" + sceneNum + "\n");
                }

                else if("part".equals(currNode.getNodeName())){
                    int level = Integer.parseInt(currNode.getAttributes().getNamedItem("level").getNodeValue());
                    String partName = currNode.getAttributes().getNamedItem("name").getNodeValue();
                    String line = "";
                    NodeList partChildren = currNode.getChildNodes();
                    for (int k = 0; k < partChildren.getLength(); k++) {
                        Node partChildrenNode = partChildren.item(k);
                        if("line".equals(partChildrenNode.getNodeName())){
                            line = partChildrenNode.getTextContent();
                        }
                    }

                    roles.add(new Role(level, line, partName, "main"));

                }
            }
            Scenes.add(new Scene(budget, sceneNum, description, name, roles));
        }


        return Scenes;
    }

    static public ArrayList<Set> readBoardData(Document doc) {
        Element root = doc.getDocumentElement();
        ArrayList<Set> Sets = new ArrayList<>();

        NodeList sets = root.getElementsByTagName("set");


        for (int i = 0; i < sets.getLength(); i++) {
            // System.out.println("printing information for Set " + (i) );

            ArrayList<String> neighbors = new ArrayList<>();
            int shots = 0;
            ArrayList<Role> roles = new ArrayList<>();

            Node set = sets.item(i);

            String setName = set.getAttributes().getNamedItem("name").getNodeValue();
            // System.out.println(setName);

            NodeList children = set.getChildNodes();
            for (int j = 0; j < children.getLength(); j++) {
                Node currNode = children.item(j);

                if ("neighbors".equals(currNode.getNodeName())) {
                    NodeList neighborList = currNode.getChildNodes();

                    for (int k = 1; k < neighborList.getLength(); k += 2) {
                        Node currNeighbor = neighborList.item(k);
                        String neighborName = currNeighbor.getAttributes().getNamedItem("name").getNodeValue();
                        //System.out.println(neighborName);
                        neighbors.add(neighborName);
                    }

                } else if ("takes".equals(currNode.getNodeName())) {
                    NodeList takesList = currNode.getChildNodes();

                    shots = (int) Math.floor(takesList.getLength() / 2);
                    //System.out.println(shots);
                } else if ("parts".equals(currNode.getNodeName())) {
                    NodeList partsList = currNode.getChildNodes();

                    for (int k = 1; k < partsList.getLength(); k += 2) {
                        Node currPart = partsList.item(k);

                        String partName = currPart.getAttributes().getNamedItem("name").getNodeValue();
                        int level = Integer.parseInt(currPart.getAttributes().getNamedItem("level").getNodeValue());
                        String line = "";

                        NodeList currPartList = currPart.getChildNodes();
                        for (int l = 0; l < currPartList.getLength(); l++) {
                            Node currPartListNode = currPartList.item(l);

                            if ("line".equals(currPartListNode.getNodeName())) {
                                line = currPartListNode.getTextContent();
                            }
                        }
                        roles.add(new Role(level, line, partName, "extra"));

                    }
                    //System.out.println(roles);


                }


            }

            Sets.add(new Set(shots, setName, roles, neighbors));


        }

        NodeList trailerList = root.getElementsByTagName("trailer");
        Node trailer = trailerList.item(0);
        NodeList trailerNeighbors = trailer.getChildNodes();
        ArrayList<String> neighbors = new ArrayList<>();
        for (int i = 0; i < trailerNeighbors.getLength(); i++) {
            Node currNode = trailerNeighbors.item(i);
            if("neighbors".equals(currNode.getNodeName())){
                NodeList neighborList = currNode.getChildNodes();
                for (int j = 1; j < neighborList.getLength(); j+=2) {
                    Node currNeighbor = neighborList.item(j);
                    String neighborName = currNeighbor.getAttributes().getNamedItem("name").getNodeValue();
                    neighbors.add(neighborName);
                }
            }

        }



        Sets.add(new Set(0, "Trailer", null, neighbors));

        NodeList officeList = root.getElementsByTagName("office");
        Node office = officeList.item(0);
        NodeList officeChild = office.getChildNodes();
        ArrayList<String> neighbors2 = new ArrayList<>();
        ArrayList<Upgrade> upgrades = new ArrayList<>();
        for (int i = 2; i < 7; i++) {
            upgrades.add(new Upgrade(i));
        }

        for (int i = 0; i < officeChild.getLength(); i++) {
            Node currNode = officeChild.item(i);
            if("neighbors".equals(currNode.getNodeName())){
                NodeList neighborList = currNode.getChildNodes();
                for (int j = 1; j < neighborList.getLength(); j+=2) {
                    Node currNeighbor = neighborList.item(j);
                    String neighborName = currNeighbor.getAttributes().getNamedItem("name").getNodeValue();
                    neighbors2.add(neighborName);
                }
            }
            else if("upgrades".equals(currNode.getNodeName())){
                NodeList upgradeList = currNode.getChildNodes();
                for (int j = 1; j < upgradeList.getLength(); j+=2) {
                    Node currUpgrade = upgradeList.item(j);
                    String currency = currUpgrade.getAttributes().getNamedItem("currency").getNodeValue();
                    int amount = Integer.parseInt(currUpgrade.getAttributes().getNamedItem("amt").getNodeValue());
                    int level = Integer.parseInt(currUpgrade.getAttributes().getNamedItem("level").getNodeValue());
                    if(currency.equals("dollar")){
                        upgrades.get(level - 2).setDollars(amount);
                    }
                    else{
                        upgrades.get(level - 2).setCredits(amount);
                    }
                }
            }
        }

        Sets.add(new CastingOffice(0, "Casting Office", null, neighbors2, upgrades));

        return Sets;
    }//class
}
