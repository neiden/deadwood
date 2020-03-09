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
            String img = "";

            Node card = cards.item(i);

            name = card.getAttributes().getNamedItem("name").getNodeValue();
            img = card.getAttributes().getNamedItem("img").getNodeValue();
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
                    int[] roleCoordinates = new int[4];
                    NodeList partChildren = currNode.getChildNodes();
                    for (int k = 0; k < partChildren.getLength(); k++) {
                        Node partChildrenNode = partChildren.item(k);
                        if("line".equals(partChildrenNode.getNodeName())){
                            line = partChildrenNode.getTextContent();
                        }
                        else if("area".equals(partChildrenNode.getNodeName())){
                            roleCoordinates[0] = Integer.parseInt(partChildrenNode.getAttributes().getNamedItem("x").getNodeValue());
                            roleCoordinates[1] = Integer.parseInt(partChildrenNode.getAttributes().getNamedItem("y").getNodeValue());
                            roleCoordinates[2] = Integer.parseInt(partChildrenNode.getAttributes().getNamedItem("h").getNodeValue());
                            roleCoordinates[3] = Integer.parseInt(partChildrenNode.getAttributes().getNamedItem("w").getNodeValue());

                        }
                    }

                    roles.add(new Role(level, line, partName, "main", roleCoordinates));

                }
            }
            Scenes.add(new Scene(budget, sceneNum, description, name, roles, img));
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
            ArrayList<Shot> shots = new ArrayList<Shot>();
            ArrayList<Role> roles = new ArrayList<>();

            Node set = sets.item(i);

            String setName = set.getAttributes().getNamedItem("name").getNodeValue();
            int[] setCoordinates = new int[4];
            //System.out.println(setName);

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

                }
                else if("area".equals(currNode.getNodeName())){
                    setCoordinates[0] = Integer.parseInt(currNode.getAttributes().getNamedItem("x").getNodeValue());
                    setCoordinates[1] = Integer.parseInt(currNode.getAttributes().getNamedItem("y").getNodeValue());
                    setCoordinates[2] = Integer.parseInt(currNode.getAttributes().getNamedItem("h").getNodeValue());
                    setCoordinates[3] = Integer.parseInt(currNode.getAttributes().getNamedItem("w").getNodeValue());
                }
                else if ("takes".equals(currNode.getNodeName())) {
                    NodeList takesList = currNode.getChildNodes();

                    for (int k = 0; k < takesList.getLength(); k ++) {
                        Node currTake = takesList.item(k);
                        if("take".equals(currTake.getNodeName())) {
                            NodeList takeChildren = currTake.getChildNodes();
                            int shotNumber = Integer.parseInt(currTake.getAttributes().getNamedItem("number").getNodeValue());
                            int[] shotCoordinates = new int[4];
                            for (int l = 0; l < takeChildren.getLength(); l++) {
                                Node takeChildrenCurrNode = takeChildren.item(l);

                                if ("area".equals(takeChildrenCurrNode.getNodeName())) {
                                    shotCoordinates[0] = Integer.parseInt(takeChildrenCurrNode.getAttributes().getNamedItem("x").getNodeValue());
                                    shotCoordinates[1] = Integer.parseInt(takeChildrenCurrNode.getAttributes().getNamedItem("y").getNodeValue());
                                    shotCoordinates[2] = Integer.parseInt(takeChildrenCurrNode.getAttributes().getNamedItem("h").getNodeValue());
                                    shotCoordinates[3] = Integer.parseInt(takeChildrenCurrNode.getAttributes().getNamedItem("w").getNodeValue());
                                }
                            }
                            shots.add(new Shot(shotNumber, shotCoordinates));
                        }
                    }
                    //System.out.println(shots);
                } else if ("parts".equals(currNode.getNodeName())) {
                    NodeList partsList = currNode.getChildNodes();

                    for (int k = 1; k < partsList.getLength(); k += 2) {
                        Node currPart = partsList.item(k);

                        String partName = currPart.getAttributes().getNamedItem("name").getNodeValue();
                        int level = Integer.parseInt(currPart.getAttributes().getNamedItem("level").getNodeValue());
                        String line = "";

                        NodeList currPartList = currPart.getChildNodes();
                        int[] roleCoordinates = new int[4];
                        for (int l = 0; l < currPartList.getLength(); l++) {
                            Node currPartListNode = currPartList.item(l);

                            if ("line".equals(currPartListNode.getNodeName())) {
                                line = currPartListNode.getTextContent();
                            }
                            else if("area".equals(currPartListNode.getNodeName())){
                                roleCoordinates[0] = Integer.parseInt(currPartListNode.getAttributes().getNamedItem("x").getNodeValue());
                                roleCoordinates[1] = Integer.parseInt(currPartListNode.getAttributes().getNamedItem("y").getNodeValue());
                                roleCoordinates[2] = Integer.parseInt(currPartListNode.getAttributes().getNamedItem("h").getNodeValue());
                                roleCoordinates[3] = Integer.parseInt(currPartListNode.getAttributes().getNamedItem("w").getNodeValue());

                            }
                        }
                        roles.add(new Role(level, line, partName, "extra", roleCoordinates));

                    }
                    //System.out.println(roles);


                }


            }

            Sets.add(new Set(shots, setName, roles, neighbors, setCoordinates));


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



        Sets.add(new Set(null, "trailer", null, neighbors, new int[]{9 , 459, 208, 209}));

        NodeList officeList = root.getElementsByTagName("office");
        Node office = officeList.item(0);
        NodeList officeChild = office.getChildNodes();
        ArrayList<String> neighbors2 = new ArrayList<>();
        ArrayList<Upgrade> upgrades = new ArrayList<>();
        int[] setCoordinates = new int[4];
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
            else if("area".equals(currNode.getNodeName())){
                setCoordinates[0] = Integer.parseInt(currNode.getAttributes().getNamedItem("x").getNodeValue());
                setCoordinates[1] = Integer.parseInt(currNode.getAttributes().getNamedItem("y").getNodeValue());
                setCoordinates[2] = Integer.parseInt(currNode.getAttributes().getNamedItem("h").getNodeValue());
                setCoordinates[3] = Integer.parseInt(currNode.getAttributes().getNamedItem("w").getNodeValue());
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

        Sets.add(new CastingOffice(null, "office", null, neighbors2, upgrades, setCoordinates));

        return Sets;
    }//class
}
