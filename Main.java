import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    public static ArrayList<Scene> scenes = new ArrayList<>();

    public static void main(String[] args){
        ParseXML parse = new ParseXML();
        String filename = "src/oard.xml";
        try{
            readBoardData(parse.getDocFromFile(filename));

        }catch (Exception e){
            System.out.println(e.getMessage());
        };

        for (int i = 0; i < scenes.size(); i++) {
            System.out.println(scenes.get(i));
        }


        /*
        Game game = new Game();
        game.init();
        while(game.getRunning()){
            game.run();
        }
        System.out.println("Thanks for playing!");*/

    }

    static public void readBoardData(Document doc){
        Element root = doc.getDocumentElement();

        NodeList sets = root.getElementsByTagName("set");


        for (int i = 0; i < sets.getLength() ; i++) {
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

                if("neighbors".equals(currNode.getNodeName())){
                    NodeList neighborList = currNode.getChildNodes();

                    for (int k = 1; k < neighborList.getLength(); k+=2) {
                        Node currNeighbor = neighborList.item(k);
                        String neighborName = currNeighbor.getAttributes().getNamedItem("name").getNodeValue();
                        //System.out.println(neighborName);
                        neighbors.add(neighborName);
                    }

                }

                else if("takes".equals(currNode.getNodeName())){
                    NodeList takesList = currNode.getChildNodes();

                    shots = (int)Math.floor(takesList.getLength()/2);
                    //System.out.println(shots);
                }

                else if("parts".equals(currNode.getNodeName())){
                    NodeList partsList = currNode.getChildNodes();

                    for (int k = 1; k < partsList.getLength(); k+=2) {
                        Node currPart = partsList.item(k);

                        String partName = currPart.getAttributes().getNamedItem("name").getNodeValue();
                        int level = Integer.parseInt(currPart.getAttributes().getNamedItem("level").getNodeValue());
                        String line = "";

                        NodeList currPartList = currPart.getChildNodes();
                        for (int l = 0; l < currPartList.getLength(); l++) {
                            Node currPartListNode = currPartList.item(l);

                            if("line".equals(currPartListNode.getNodeName())){
                                line = currPartListNode.getTextContent();
                            }
                        }
                        roles.add(new Role(level, line, partName));

                    }
                        //System.out.println(roles);


                }



            }

            scenes.add(new Scene(shots, setName, roles, neighbors));



        }


    }
}
