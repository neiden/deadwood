import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
public class Main {
    public static void main(String[] args){
        ParseXML parse = new ParseXML();
        String filename = "C:\\Users\\Pinkgodzilla\\IdeaProjects\\Deadwood\\src\\oard.xml";
        try{
            readBoardData(parse.getDocFromFile(filename));

        }catch (Exception e){
            System.out.println(e.getMessage());
        };



        /*Game game = new Game();
        game.init();
        while(game.getRunning()){
            game.run();
        }
        System.out.println("Thanks for playing!");*/

    }

    static public void readBoardData(Document doc){
        Element root = doc.getDocumentElement();


    }
}
