package controller;

import model.Registry;

import java.io.*;

public class ExportInport {
    // Let's serialize an Object
    public void exportRegistry(Registry obj){
		try {
        FileOutputStream fileOut = new FileOutputStream("/home/haraldur/Desktop/Skolinn/1DV607/ws2_grade2/theJollyPirate/JollyPirateDatabase.txt");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(obj);
        out.close();
        fileOut.close();
        System.out.println("\nSerialization Successful... Checkout your specified output file..\n");

    } catch (
    FileNotFoundException e) {
        e.printStackTrace();
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
            System.out.println("Importing data finished");
            in.close();
            fileIn.close();

        }
        catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return registry;

    }

}
