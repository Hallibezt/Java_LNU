package controller;

import model.Registry;

import java.io.*;

public class ExportInport {
    // Let's serialize an Object
    public void exportRegistry(Registry obj){
		try { // TODO: 2020-09-13 Change to relative path 
        FileOutputStream fileOut = new FileOutputStream("/home/haraldur/Desktop/Skolinn/1DV607/ws2_grade2/theJollyPirate/JollyPirateDatabase.txt");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(obj);
        out.close();
        fileOut.close();       

    } catch (
    IOException e) {
        e.printStackTrace();
    }
    }
    // Let's deserialize an Object
    public Registry importRegistry() throws IOException {
        Registry registry = null;
        try {
            FileInputStream fileIn = new FileInputStream("/home/haraldur/Desktop/Skolinn/1DV607/ws2_grade2/theJollyPirate/JollyPirateDatabase.txt");
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

}
