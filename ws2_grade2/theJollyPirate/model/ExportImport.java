package model;

import model.Registry;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

// serialize and deserialize a Register (and associated objects)
public class ExportImport {

    public void exportRegistry(Registry obj){
		try {
        FileOutputStream fileOut = new FileOutputStream(getResourceFile());
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(obj);
        out.close();
        fileOut.close();
            } catch (
            IOException e) {
                e.printStackTrace();
            }
    }

    public Registry importRegistry() throws IOException {
        Registry registry = null;
        try {

            FileInputStream fileIn = new FileInputStream(getResourceFile());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            registry = (Registry) in.readObject();
            in.close();
            fileIn.close();

        }
        catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return registry;
    }

    //Got this method from stack overflow - A comment from the user "gbii":
    // https://stackoverflow.com/questions/6192661/how-to-reference-a-resource-file-correctly-for-jar-and-debugging
    //This is so that when running the jar the JollyPirate.txt database is searched for in the path of the jar being runned
    //else if running from the IDE then the project relative path is used
    private File getResourceFile() {
        File file = null;
        URL location = Registry.class.getProtectionDomain().getCodeSource().getLocation();
        String codeLocation = location.toString();
        try {
            if (codeLocation.endsWith(".jar")) {
                //Call from jar
                Path path = Paths.get(location.toURI()).resolve("../" + "JollyPirateDatabase.txt").normalize();
                file = path.toFile();
            } else {
                //Call from IDE
                file = new File(Objects.requireNonNull(Registry.class.getClassLoader().getResource("JollyPirateDatabase.txt")).getPath());
            }
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        return file;
    }

}
